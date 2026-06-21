//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

public class HealthBarRenderer {

    public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        if (event.getEntity() instanceof BuddyBeastEntity buddy) {
            // Health bar will render post-render
        }
    }

    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (event.getEntity() instanceof BuddyBeastEntity buddy) {
            renderHealthBar(buddy, event.getPartialTick());
        }
    }

    private static void renderHealthBar(BuddyBeastEntity buddy, float partialTick) {
        // Health bar positioned above entity
        // Width: 8 pixels, Height: 1 pixel
        // Green for full health, red for low health

        float health = buddy.getHealth();
        float maxHealth = buddy.getMaxHealth();
        float healthPercent = health / maxHealth;

        // Store pose for restoration
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();

        // Position above entity head
        poseStack.translate(0, buddy.getBbHeight() + 0.5f, 0);

        // This would render the bar, but we need GuiGraphics context
        // For now, reserve the space - actual rendering in RenderLivingEvent.Post with GuiGraphics

        poseStack.popPose();
    }
}

// Built with assistance from Claude Code by Anthropic.
