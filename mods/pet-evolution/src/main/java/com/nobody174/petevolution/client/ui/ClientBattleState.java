//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client.ui;

import java.util.List;

import com.nobody174.petevolution.battle.BattleStateSyncPayload;

/**
 * Client-side holding cell for the most recent {@link BattleStateSyncPayload}, read
 * by {@link BattleHudOverlay} each frame. This is intentionally the ONLY client-side
 * mutable state introduced by the battle UI — everything else (which skill is
 * legal to pick, win/loss, stat changes) stays server-authoritative.
 */
public final class ClientBattleState {

    private static volatile BattleStateSyncPayload latest = null;

    private ClientBattleState() {
    }

    public static void update(BattleStateSyncPayload payload) {
        latest = payload;
    }

    public static BattleStateSyncPayload current() {
        return latest;
    }

    public static boolean isActive() {
        return latest != null && latest.active();
    }

    public static List<String> availableSkillIds() {
        return latest == null ? List.of() : latest.availableSkillIds();
    }
}

// Built with assistance from Claude Code by Anthropic.
