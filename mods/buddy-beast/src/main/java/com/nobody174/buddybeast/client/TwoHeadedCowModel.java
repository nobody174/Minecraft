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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelPart;

public class TwoHeadedCowModel<T extends net.minecraft.world.entity.Entity> extends CowModel<T> {
    private final ModelPart rearHead;

    public TwoHeadedCowModel(ModelPart root) {
        super(root);
        this.rearHead = root.getChild("head");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);

        // TUNING: edit config/buddybeast-client.toml ("rearHeadOffset") while
        // the game is running and save - NeoForge live-reloads it, no rebuild
        // or restart needed. Read fresh every call so changes apply instantly.
        final float REAR_HEAD_OFFSET = BuddyDevConfig.REAR_HEAD_OFFSET.get().floatValue();

        // ModelPart.render() always re-applies the part's own baked pivot
        // (rearHead.x/y/z, in pixels) on top of whatever is already on the
        // PoseStack - it is not a one-time setup we can skip. Reading the
        // real x/y/z fields removes the guesswork: cancel the baked pivot
        // exactly (move to torso origin), mirror the yaw, then shift by
        // REAR_HEAD_OFFSET along local z (which after the 180-degree mirror
        // points toward the tail) before render() re-adds its own offset.
        float px = this.rearHead.x / 16.0F;
        float py = this.rearHead.y / 16.0F;
        float pz = this.rearHead.z / 16.0F;

        poseStack.pushPose();
        poseStack.translate(-px, -py, -pz); // cancel baked pivot -> torso origin
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(0.0, 0.0, REAR_HEAD_OFFSET); // slide toward tail along mirrored z
        this.rearHead.render(poseStack, buffer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }
}

// Built with assistance from Claude Code by Anthropic.
