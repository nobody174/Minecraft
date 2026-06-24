//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

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
 */
final class BattleVisuals {

    private static final double LUNGE_DISTANCE = 0.5;

    private BattleVisuals() {
    }

    /** Call once when a session starts: disables normal AI and snaps both to face each other. */
    static void lockOnStart(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, (entityA, entityB) -> {
            setNoAi(entityA, true);
            setNoAi(entityB, true);
            faceEachOther(entityA, entityB);
        });
    }

    /** Call once per server tick while a session is active: keeps both pets facing each other. */
    static void tickFacing(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, BattleVisuals::faceEachOther);
    }

    /** Call when a round resolves: a brief lunge toward the opponent for the participant whose skill just landed. */
    static void lunge(MinecraftServer server, BattleParticipant user, BattleParticipant target) {
        LivingEntity userEntity = resolve(server, user);
        LivingEntity targetEntity = resolve(server, target);
        if (userEntity == null || targetEntity == null) {
            return;
        }

        Vec3 toTarget = targetEntity.position().subtract(userEntity.position());
        double length = toTarget.length();
        if (length < 1.0e-4) {
            return;
        }
        Vec3 lungeOffset = toTarget.normalize().scale(LUNGE_DISTANCE);
        Vec3 lungePos = userEntity.position().add(lungeOffset);
        userEntity.teleportTo(lungePos.x, lungePos.y, lungePos.z);
    }

    /** Call once when a session concludes: restores normal AI so the pet resumes wandering. */
    static void unlockOnEnd(MinecraftServer server, BattleParticipant a, BattleParticipant b) {
        withBoth(server, a, b, (entityA, entityB) -> {
            setNoAi(entityA, false);
            setNoAi(entityB, false);
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
