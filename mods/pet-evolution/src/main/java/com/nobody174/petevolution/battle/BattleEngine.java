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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.PetData;
import com.nobody174.petevolution.creature.PetStatApplier;

/**
 * Ticks all active {@link BattleSession}s once per server tick. Replaces the old
 * instant stat-power-comparison resolution in PetBattleEvent with proper
 * tick-based turn progression (required since skills have cooldowns).
 *
 * Sessions are keyed by the challenger's pet entity UUID, since a given pet can
 * only be in one battle at a time (enforced by the existing battle-cooldown logic
 * in PetBattleEvent, which still gates whether a new session can start).
 */
public final class BattleEngine {

    private static final int XP_REWARD_FOR_WIN = 25;
    private static final Map<UUID, BattleSession> ACTIVE_SESSIONS = new ConcurrentHashMap<>();

    public static void startSession(BattleSession session) {
        ACTIVE_SESSIONS.put(session.challenger().entityId(), session);
    }

    public static boolean hasActiveSession(UUID petEntityId) {
        for (BattleSession session : ACTIVE_SESSIONS.values()) {
            if (session.challenger().entityId().equals(petEntityId) || session.defender().entityId().equals(petEntityId)) {
                return true;
            }
        }
        return false;
    }

    /** Looks up the active session a given owner's challenging pet is part of, for routing the battle-input payload. */
    public static BattleSession findSessionForChallenger(UUID ownerId) {
        for (BattleSession session : ACTIVE_SESSIONS.values()) {
            if (session.challenger().ownerId().equals(ownerId)) {
                return session;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (ACTIVE_SESSIONS.isEmpty()) {
            return;
        }

        ACTIVE_SESSIONS.entrySet().removeIf(entry -> {
            BattleSession session = entry.getValue();
            boolean done = session.tick();
            if (done) {
                rewardWinnerAndSync(event.getServer(), session);
            }
            return done;
        });
    }

    private void rewardWinnerAndSync(net.minecraft.server.MinecraftServer server, BattleSession session) {
        boolean challengerWins = !session.challenger().isDefeated();
        BattleParticipant winner = challengerWins ? session.challenger() : session.defender();

        for (var level : server.getAllLevels()) {
            var entity = level.getEntity(winner.entityId());
            if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
                PetData updated = winner.petData().withXp(XP_REWARD_FOR_WIN);
                living.setData(ModAttachments.RELEASED_PET_DATA.get(), updated);
                PetStatApplier.apply(living, updated);
            }
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
