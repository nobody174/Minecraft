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

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class BuddyInteractionHandler {
    public static InteractionResult handleRightClick(BuddyBeastEntity buddy, Player player, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // If not tamed, tame it
        if (!buddy.isTamed()) {
            buddy.setTamed(true);
            buddy.setOwnerUUID(player.getUUID());
            buddy.setOwnerName(player.getName().getString());

            // Notify player
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("§6§lBuddy tamed!"),
                true
            );
            return InteractionResult.SUCCESS;
        }

        // If tamed by player, toggle between following and staying
        if (buddy.getOwnerUUID() != null && buddy.getOwnerUUID().equals(player.getUUID())) {
            boolean nowStaying = !buddy.isStaying();
            buddy.setStaying(nowStaying);

            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                    nowStaying ? "§6Buddy is staying" : "§6Buddy is following you"
                ),
                true
            );
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}

// Built with assistance from Claude Code by Anthropic.
