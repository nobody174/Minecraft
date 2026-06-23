//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import org.joml.Matrix4f;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

public class HealthBarRenderer {

    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (event.getEntity() instanceof BuddyBeastEntity buddy) {
            renderHealthBar(buddy, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
        }
    }

    private static void renderHealthBar(BuddyBeastEntity buddy, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        String text = (int) buddy.getHealth() + " / " + (int) buddy.getMaxHealth();
        int color = buddy.getHealth() < buddy.getMaxHealth() * 0.3f ? 0xFFFF5555 : 0xFF55FF55;

        poseStack.pushPose();
        poseStack.translate(0.0, buddy.getBbHeight() + 0.5, 0.0);
        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        poseStack.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix = poseStack.last().pose();
        float halfWidth = -font.width(text) / 2.0f;
        font.drawInBatch(text, halfWidth, 0, color, false, matrix, buffer,
                Font.DisplayMode.NORMAL, 0, packedLight);

        poseStack.popPose();
    }
}

// Built with assistance from Claude Code by Anthropic.
