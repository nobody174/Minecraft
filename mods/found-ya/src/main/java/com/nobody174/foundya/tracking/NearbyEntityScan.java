//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

import java.util.List;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Shared AABB query used by both {@link NearestTargetScanner} and
 * {@link SearchModeScanner}: every valid (alive, visible, non-player)
 * {@link LivingEntity} within {@code range} of the player. Pulled out of
 * both scanners since they ran the identical query independently.
 */
final class NearbyEntityScan {

    private NearbyEntityScan() {
    }

    static List<LivingEntity> candidatesWithinRange(ClientLevel level, Player player, float range) {
        AABB searchBox = player.getBoundingBox().inflate(range);
        return level.getEntitiesOfClass(LivingEntity.class, searchBox);
    }

    static boolean isValidCandidate(LivingEntity candidate, Player player) {
        return candidate != player && candidate.isAlive() && !candidate.isInvisible();
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
