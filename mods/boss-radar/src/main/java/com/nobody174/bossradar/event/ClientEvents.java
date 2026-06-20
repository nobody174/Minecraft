//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

@EventBusSubscriber(modid = "bossradar", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRightClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) {
            Player player = net.minecraft.client.Minecraft.getInstance().player;
            if (player != null) {
                ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (itemInHand.getItem().getClass().getSimpleName().equals("BossRadarItem")) {
                    player.displayClientMessage(Component.literal("§6Boss Radar activated!"), true);
                }
            }
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
