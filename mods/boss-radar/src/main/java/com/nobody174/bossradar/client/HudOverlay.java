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
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = "bossradar", value = Dist.CLIENT)
public class HudOverlay {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onHudRender(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        boolean hasBossRadar = mainHand.getItem() instanceof com.nobody174.bossradar.item.BossRadarItem ||
                              offHand.getItem() instanceof com.nobody174.bossradar.item.BossRadarItem;

        if (!hasBossRadar) return;

        LOGGER.info("[BossRadar] HUD render event fired with Boss Radar item in hand");

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        String targetName = player.getPersistentData().getString("BossRadar_TargetName");
        if (targetName.isEmpty()) {
            drawHudText(guiGraphics, centerX, centerY, "No Target");
            return;
        }

        double distance = player.getPersistentData().getDouble("BossRadar_Distance");
        int color = getDistanceColor((int) distance);

        drawDistanceRing(guiGraphics, centerX, centerY, 20, color);
        drawHudText(guiGraphics, centerX, centerY + 30, targetName + " [" + (int) distance + "m]");
    }

    private static void drawDistanceRing(GuiGraphics guiGraphics, int centerX, int centerY, int radius, int color) {
        int segments = 32;
        for (int i = 0; i < segments; i++) {
            double angle1 = (i / (double) segments) * Math.PI * 2;
            double angle2 = ((i + 1) / (double) segments) * Math.PI * 2;

            int x1 = centerX + (int) (Math.cos(angle1) * radius);
            int y1 = centerY + (int) (Math.sin(angle1) * radius);
            int x2 = centerX + (int) (Math.cos(angle2) * radius);
            int y2 = centerY + (int) (Math.sin(angle2) * radius);

            guiGraphics.hLine(x1, x2, y1, color | 0xFF000000);
        }
    }

    private static void drawHudText(GuiGraphics guiGraphics, int x, int y, String text) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, x, y, 0xFFFFFF);
    }

    private static int getDistanceColor(int distance) {
        if (distance < 32) {
            return 0xFF0000;
        } else if (distance < 64) {
            return 0xFFFF00;
        } else {
            return 0x00FF00;
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
