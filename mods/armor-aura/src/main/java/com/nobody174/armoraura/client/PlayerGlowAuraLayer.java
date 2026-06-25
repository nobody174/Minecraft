//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Renders a bright, pulsing colored glow over a player's body by re-drawing
 * two enlarged copies of the player model (an inner "shine" pass and a
 * larger, fainter outer "halo" pass) using additive blending, so the glow
 * color only ever brightens the screen and is never darkened by the
 * underlying skin texture. This intentionally avoids touching the vanilla
 * outline buffer (which derives its color from {@code Entity.getTeamColor()})
 * so the glow color is fully custom and independent of scoreboard teams.
 */
public class PlayerGlowAuraLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final float INNER_SCALE = 1.08f;
    private static final float OUTER_SCALE = 1.22f;

    private static final Map<ResourceLocation, RenderType> ADDITIVE_GLOW_TYPES = new ConcurrentHashMap<>();

    private final PlayerModel<AbstractClientPlayer> glowModel;

    public PlayerGlowAuraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer,
                                EntityModelSet modelSet, boolean slim) {
        super(renderer);
        this.glowModel = new PlayerModel<>(modelSet.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
    }

    /**
     * Same shader/format/lightmap setup as {@link RenderType#entityTranslucentEmissive}
     * but with additive blending instead of normal alpha blending, so dark
     * texture pixels (black armor, shadows) don't dim the glow color.
     */
    private static RenderType additiveGlow(ResourceLocation texture) {
        return ADDITIVE_GLOW_TYPES.computeIfAbsent(texture, location -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true);
            return RenderType.create("armoraura_additive_glow", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, state);
        });
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player,
                        float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                        float netHeadYaw, float headPitch) {
        if (player.isInvisible() || player.isSpectator()) {
            return;
        }
        if (!AuraGlowManager.isGlowing(player)) {
            return;
        }

        int rgb = AuraGlowManager.getColor(player);
        float intensity = AuraGlowManager.getIntensity(player);
        float pulseSpeed = AuraGlowConfig.getPulseSpeed();

        float pulse = pulseSpeed <= 0.0f
            ? 1.0f
            : 0.8f + 0.2f * (float) Math.sin((ageInTicks + partialTick) * 0.1 * pulseSpeed);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        getParentModel().copyPropertiesTo(glowModel);
        glowModel.prepareMobModel(player, limbSwing, limbSwingAmount, partialTick);
        glowModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        VertexConsumer consumer = buffer.getBuffer(additiveGlow(getTextureLocation(player)));
        int overlay = LivingEntityRenderer.getOverlayCoords(player, 0.0F);

        // Outer halo: larger, fainter, additive — reads as a soft bright bloom.
        int outerAlpha = Math.round(150 * Math.max(0.0f, Math.min(1.0f, intensity * pulse)));
        poseStack.pushPose();
        poseStack.scale(OUTER_SCALE, OUTER_SCALE, OUTER_SCALE);
        glowModel.renderToBuffer(poseStack, consumer, packedLight, overlay,
            FastColor.ARGB32.color(outerAlpha, red, green, blue));
        poseStack.popPose();

        // Inner shine: tighter to the body, brighter — reads as the main glow.
        int innerAlpha = Math.round(255 * Math.max(0.0f, Math.min(1.0f, intensity * pulse)));
        poseStack.pushPose();
        poseStack.scale(INNER_SCALE, INNER_SCALE, INNER_SCALE);
        glowModel.renderToBuffer(poseStack, consumer, packedLight, overlay,
            FastColor.ARGB32.color(innerAlpha, red, green, blue));
        poseStack.popPose();
    }
}

// Built with assistance from Claude Code by Anthropic.
