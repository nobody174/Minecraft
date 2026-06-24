//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import com.nobody174.petevolution.capture.PetBehaviorController;
import com.nobody174.petevolution.creature.ModAttachments;

/**
 * Makes the two pets in a {@link BattleSession} visually engage each other instead
 * of wandering independently with their normal AI, which is otherwise completely
 * decoupled from the battle's HP/skill simulation (confirmed by a real two-player
 * test: the battle resolved correctly, but the pets never turned to face or
 * approached one another).
 *
 * Freezes normal AI for the duration (so they stop wandering), keeps both facing
 * each other every tick, and nudges each entity in a brief forward-and-back lunge
 * synced to its skill landing each round — mirroring the player's existing
 * attack-lunge feel, but as a real position nudge server-side since there is no
 * client animation hook for an arbitrary vanilla mob's attack swing.
 *
 * Each entity's position when the session starts is recorded as its "home spot"
 * and it's snapped back there after each lunge — without this, repeated lunges
 * toward the opponent's current (already-lunged) position compound over rounds
 * and the two pets end up standing inside each other (observed in a real
 * two-player test before this fix).
 */
final class BattleVisuals {

    private static final double LUNGE_DISTANCE = 0.4;
    private static final double MIN_SEPARATION = 1.5;

    private static final Map<UUID, Vec3> HOME_POSITIONS = new HashMap<>();
    private static final Set<UUID> LUNGED = new HashSet<>();

    private BattleVisuals() {
    }

    /** Call once when a session starts: disables normal AI, records home positions, and snaps both to face each other. */
    static void lockOnStart(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, (entityA, entityB) -> {
            setNoAi(entityA, true);
            setNoAi(entityB, true);
            HOME_POSITIONS.put(entityA.getUUID(), entityA.position());
            HOME_POSITIONS.put(entityB.getUUID(), entityB.position());
            faceEachOther(entityA, entityB);
        });
    }

    /**
     * Call once per server tick while a session is active: keeps both pets facing each
     * other, and returns any entity mid-lunge back to its home spot (one tick after the
     * lunge was triggered, so the forward step is briefly visible before snapping back).
     */
    static void tickFacing(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, (entityA, entityB) -> {
            returnHomeIfLunged(entityA);
            returnHomeIfLunged(entityB);
            faceEachOther(entityA, entityB);
        });
    }

    /**
     * Call when a round resolves: a brief lunge toward the opponent for the participant
     * whose skill just landed. The next {@link #tickFacing} call returns it to its
     * recorded home spot, so repeated lunges across rounds don't compound into the two
     * pets standing inside each other (observed in a real two-player test before this fix).
     */
    static void lunge(MinecraftServer server, BattleParticipant user, BattleParticipant target) {
        LivingEntity userEntity = resolve(server, user);
        LivingEntity targetEntity = resolve(server, target);
        if (userEntity == null || targetEntity == null) {
            return;
        }

        Vec3 home = HOME_POSITIONS.getOrDefault(userEntity.getUUID(), userEntity.position());
        Vec3 targetHome = HOME_POSITIONS.getOrDefault(targetEntity.getUUID(), targetEntity.position());

        Vec3 toTarget = targetHome.subtract(home);
        double length = toTarget.length();
        if (length < 1.0e-4) {
            return;
        }

        double travel = Math.max(0, Math.min(LUNGE_DISTANCE, length - MIN_SEPARATION));
        Vec3 lungePos = home.add(toTarget.normalize().scale(travel));
        userEntity.teleportTo(lungePos.x, lungePos.y, lungePos.z);
        LUNGED.add(userEntity.getUUID());
    }

    private static void returnHomeIfLunged(LivingEntity entity) {
        if (!LUNGED.remove(entity.getUUID())) {
            return;
        }
        Vec3 home = HOME_POSITIONS.get(entity.getUUID());
        if (home != null) {
            entity.teleportTo(home.x, home.y, home.z);
        }
    }

    /**
     * Call once when a session concludes: restores each pet's actual STAY/FOLLOW
     * mode (not just a blanket {@code setNoAi(false)}, which would silently undo
     * STAY for any pet that had it set before the battle started) and forgets
     * recorded home positions.
     */
    static void unlockOnEnd(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, (entityA, entityB) -> {
            PetBehaviorController.applyMode(entityA, entityA.getData(ModAttachments.PET_BEHAVIOR_MODE.get()));
            PetBehaviorController.applyMode(entityB, entityB.getData(ModAttachments.PET_BEHAVIOR_MODE.get()));
            HOME_POSITIONS.remove(entityA.getUUID());
            HOME_POSITIONS.remove(entityB.getUUID());
            LUNGED.remove(entityA.getUUID());
            LUNGED.remove(entityB.getUUID());
        });
    }

    private static void faceEachOther(LivingEntity entityA, LivingEntity entityB) {
        lookAt(entityA, entityB);
        lookAt(entityB, entityA);
    }

    private static void lookAt(LivingEntity looker, LivingEntity target) {
        Vec3 toTarget = target.position().subtract(looker.position());
        if (toTarget.lengthSqr() < 1.0e-4) {
            return;
        }
        double yaw = Math.toDegrees(Math.atan2(-toTarget.x, toTarget.z));
        looker.setYRot((float) yaw);
        looker.setYHeadRot((float) yaw);
        looker.yBodyRot = (float) yaw;
    }

    private static void setNoAi(LivingEntity entity, boolean noAi) {
        if (entity instanceof Mob mob) {
            mob.setNoAi(noAi);
        }
    }

    private static void withBoth(MinecraftServer server, BattleParticipant a, BattleParticipant b,
                                  java.util.function.BiConsumer<LivingEntity, LivingEntity> action) {
        LivingEntity entityA = resolve(server, a);
        LivingEntity entityB = resolve(server, b);
        if (entityA != null && entityB != null) {
            action.accept(entityA, entityB);
        }
    }

    private static LivingEntity resolve(MinecraftServer server, BattleParticipant participant) {
        for (ServerLevel level : server.getAllLevels()) {
            if (level.getEntity(participant.entityId()) instanceof LivingEntity living) {
                return living;
            }
        }
        return null;
    }
}

// Built with assistance from Claude Code by Anthropic.
