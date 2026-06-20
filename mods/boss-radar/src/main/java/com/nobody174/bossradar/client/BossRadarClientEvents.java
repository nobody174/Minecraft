//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

@EventBusSubscriber(modid = "bossradar", value = Dist.CLIENT)
public class BossRadarClientEvents {

    @SubscribeEvent
    public static void onInputEvent(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                var mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
                var offHand = player.getItemInHand(InteractionHand.OFF_HAND);

                boolean hasBossRadar = mainHand.getItem().getClass().getSimpleName().equals("BossRadarItem") ||
                                      offHand.getItem().getClass().getSimpleName().equals("BossRadarItem");

                if (hasBossRadar) {
                    var targetName = player.getPersistentData().getString("BossRadar_TargetName");
                    if (!targetName.isEmpty()) {
                        var distance = player.getPersistentData().getDouble("BossRadar_Distance");
                        player.displayClientMessage(
                            net.minecraft.network.chat.Component.literal(
                                "§6Target: §r" + targetName + " [" + (int) distance + "m]"
                            ),
                            true
                        );
                    }
                }
            }
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
