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

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.client.ui.ClientBattleState;
import com.nobody174.petevolution.skills.Skill;
import com.nobody174.petevolution.skills.SkillRegistry;

/**
 * Registers both battle network payload channels.
 *
 * {@link BattleSkillChoicePayload} (client-to-server): the player's skill-override
 * pick — genuinely new player input with no other path to the server, so a custom
 * payload here is the correct pattern (see that class's doc comment for the full
 * "is this a regression of the removed PetSyncPacket" analysis — it is not).
 *
 * {@link BattleStateSyncPayload} (server-to-client): minimal, event-driven (once
 * per battle round, not per-tick) HP/skill-availability sync for the battle HUD,
 * since BattleSession state lives only in server memory and has no synced
 * component/attachment the client could read directly.
 */
public final class BattleNetworking {

    private BattleNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PetEvolution.MOD_ID).versioned("1");
        registrar.playToServer(
            BattleSkillChoicePayload.TYPE,
            BattleSkillChoicePayload.STREAM_CODEC,
            BattleNetworking::handleServer
        );
        registrar.playToClient(
            BattleStateSyncPayload.TYPE,
            BattleStateSyncPayload.STREAM_CODEC,
            BattleNetworking::handleClient
        );
    }

    private static void handleClient(BattleStateSyncPayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        if (net.neoforged.fml.loading.FMLEnvironment.dist == Dist.CLIENT) {
            ClientBattleState.update(payload);
        }
    }

    private static void handleServer(BattleSkillChoicePayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        Skill chosen = SkillRegistry.byId(payload.skillId());
        if (chosen == null) {
            return;
        }

        BattleSession session = BattleEngine.findSessionForChallenger(player.getUUID());
        if (session == null || session.isFinished()) {
            return;
        }

        // Only accept the choice if it's actually one of the challenger's unlocked skills
        // (or the universal RECOVER skill) — prevents a modified client from submitting
        // an arbitrary/unlocked skill id.
        boolean isLegal = session.challenger().unlockedSkills().contains(chosen) || chosen.id().equals(SkillRegistry.RECOVER.id());
        if (!isLegal) {
            return;
        }

        session.submitPlayerChoice(chosen);
    }
}

// Built with assistance from Claude Code by Anthropic.
