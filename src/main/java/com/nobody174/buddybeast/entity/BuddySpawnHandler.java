//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.entity;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.minecraft.world.entity.EntityType;

public class BuddySpawnHandler {
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof BuddyBeastEntity buddy) {
            // Initialize AI for buddy when it joins level
            if (!event.getLevel().isClientSide && event.getLevel() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                // Finalize spawn with proper parameters
                net.minecraft.world.DifficultyInstance difficulty = serverLevel.getCurrentDifficultyAt(buddy.blockPosition());
                buddy.finalizeSpawn(serverLevel, difficulty, net.minecraft.world.entity.MobSpawnType.NATURAL, null);
            }
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
