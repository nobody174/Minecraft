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

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;

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
}

// Built with assistance from Claude Code by Anthropic.
