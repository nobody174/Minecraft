//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class AuraHUD {

    private static boolean hasAura = false;
    private static int auraColor = 0xFFFFFF;
    private static float auraIntensity = 0.5f;

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        renderAuraStatus(guiGraphics, hasAura, auraColor, auraIntensity);
    }

    private static void renderAuraStatus(GuiGraphics guiGraphics, boolean hasAura, int color, float intensity) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!hasAura) {
            String status = "Aura: OFF";
            guiGraphics.drawString(minecraft.font, status, 10, 10, 0xFFFF0000, false);
            return;
        }

        // Aura is active—display with color and intensity
        String status = "Aura: ON";
        guiGraphics.drawString(minecraft.font, status, 10, 10, 0xFF00FF00, false);

        String colorHex = String.format("Color: #%06X", color & 0xFFFFFF);
        guiGraphics.drawString(minecraft.font, colorHex, 10, 25, color | 0xFF000000, false);

        String intensityText = String.format("Intensity: %.1f", intensity);
        guiGraphics.drawString(minecraft.font, intensityText, 10, 40, 0xFFFFFFFF, false);

        // Draw a color indicator box
        int boxX = 10;
        int boxY = 55;
        int boxSize = 16;
        guiGraphics.fill(boxX, boxY, boxX + boxSize, boxY + boxSize, color | 0xFF000000);
    }

    public static void setAuraState(boolean active, int color, float intensity) {
        hasAura = active;
        auraColor = color;
        auraIntensity = intensity;
    }
}

// Built with assistance from Claude Code by Anthropic.
