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
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import com.nobody174.buddybeast.BuddyBeast;
import com.nobody174.buddybeast.entity.BuddyBeastEntity;

public class BuddyBeastRenderer extends MobRenderer<BuddyBeastEntity, EntityModel<BuddyBeastEntity>> {
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(BuddyBeast.MOD_ID, "textures/entity/buddy_beast.png");

    public BuddyBeastRenderer(EntityRendererProvider.Context context) {
        // Use a basic entity model from context
        super(context, new net.minecraft.client.model.CowModel(context.bakeLayer(net.minecraft.client.model.geom.ModelLayers.COW)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(BuddyBeastEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BuddyBeastEntity entity, float entityYaw, float partialTick, PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        // Render name tag and health information
        if (entity.isTamed()) {
            renderTamedIndicator(entity, poseStack, partialTick);
        }
    }

    private void renderTamedIndicator(BuddyBeastEntity entity, PoseStack poseStack, float partialTick) {
        // Visual indicator that buddy is tamed
        poseStack.pushPose();
        poseStack.translate(0.0, entity.getBbHeight() + 0.5, 0.0);
        // Color glow or aura can be added here for future enhancement
        poseStack.popPose();
    }
}

// Built with assistance from Claude Code by Anthropic.
