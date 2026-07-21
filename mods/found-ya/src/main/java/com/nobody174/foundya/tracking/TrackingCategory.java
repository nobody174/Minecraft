//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

/**
 * Filters which entities {@link NearestTargetScanner} considers, on top of
 * the existing valid-candidate checks in {@link NearbyEntityScan}. Replaces
 * the never-built {@code TrackingMode.GROUP}/{@code FILTERED} plan from the
 * original design docs — filtering the single auto-selected target turned
 * out to cover the real use cases (track a pet, track hostiles, track a
 * player) without needing multi-target state.
 */
public enum TrackingCategory {
    /** No filtering — closest valid living entity, same as the original NEAREST behavior. */
    ANY,
    /** Closest {@link Enemy} (zombies, skeletons, creepers, etc.). */
    ENEMY,
    /** Closest non-hostile, non-player living entity (animals, pets, villagers). */
    FRIENDLY,
    /** Closest other player (excludes the local player). */
    PLAYER;

    public boolean matches(LivingEntity candidate) {
        return switch (this) {
            case ANY -> true;
            case ENEMY -> candidate instanceof Enemy;
            case FRIENDLY -> !(candidate instanceof Enemy) && !(candidate instanceof Player);
            case PLAYER -> candidate instanceof Player;
        };
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
