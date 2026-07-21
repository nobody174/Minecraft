//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nobody174.trackervision.config.TrackerVisionConfig;
import com.nobody174.trackervision.tracking.SearchModeManager;
import com.nobody174.trackervision.tracking.TargetState;
import com.nobody174.trackervision.tracking.TrackedTargetManager;

/**
 * Draws a thin, crisp single-pass rim around the locked target by
 * re-rendering its own model very slightly enlarged with additive
 * blending, hooked off {@link RenderLivingEvent.Post} so it works for any
 * {@link LivingEntity} type without per-renderer registration. Deliberately
 * a single tight pass (not a dual inner/outer soft-bloom halo) so it reads
 * as a sharp accent rather than a glow — the HUD overlay layer carries
 * TrackerVision's actual visual identity (see docs/UI_STYLE_GUIDE.md); this
 * is a subtle supporting cue, not the focal effect.
 *
 * <p>When the locked target is behind solid blocks, a second pass draws a
 * flat, depth-test-disabled silhouette of its model so the rim is still
 * visible through walls (vanilla's own spectator-glow outline uses the same
 * {@code NO_DEPTH_TEST} technique). This pass uses
 * {@link DefaultVertexFormat#POSITION_COLOR} instead of the textured
 * {@code NEW_ENTITY} format used by the rim above — a flat unlit color
 * reads as a clean silhouette rather than a textured model bleeding through
 * geometry. See docs/RENDERING_RESEARCH.md.</p>
 *
 * <p>While {@link SearchModeManager} is enabled, every entity in its
 * revealed set also gets the additive rim (neutral color, lower alpha,
 * no through-wall silhouette pass) — a lighter treatment than the locked
 * target's, so Search Mode reads as a broad area scan rather than
 * competing visually with an actual lock.</p>
 *
 * <p>Whenever the locked target's rim is drawn, {@link RimBoostEffect} is
 * flagged to run its full-screen post-process pass at the end of the
 * frame, giving the already-additive rim a soft bloom-like punch — see
 * {@code RimBoostEffect} for why a manual full-screen quad was used
 * instead of a full {@code PostChain} JSON pipeline.</p>
 */
public final class TrackedTargetGlowRenderer {

    private static final float RIM_SCALE = 1.035f;
    private static final int RIM_ALPHA = 110;
    private static final int SILHOUETTE_ALPHA = 70;
    private static final int SEARCH_RIM_ALPHA = 70;
    private static final int SEARCH_REVEAL_COLOR = 0xFFE8EEF2;

    private static final Map<ResourceLocation, RenderType> ADDITIVE_RIM_TYPES = new ConcurrentHashMap<>();
    private static RenderType silhouetteType;

    private TrackedTargetGlowRenderer() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(RenderLivingEvent.Post.class, TrackedTargetGlowRenderer::onRenderLivingPost);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void onRenderLivingPost(RenderLivingEvent.Post event) {
        LivingEntity entity = event.getEntity();
        if (entity.isInvisible()) {
            return;
        }

        boolean isLocked = entity.getUUID().equals(TrackedTargetManager.getLockedTargetId());
        boolean isSearchRevealed = !isLocked && SearchModeManager.isEnabled()
            && SearchModeManager.getRevealedTargets().contains(entity.getUUID());
        if (!isLocked && !isSearchRevealed) {
            return;
        }

        var player = Minecraft.getInstance().player;
        LivingEntityRenderer renderer = event.getRenderer();
        EntityModel model = renderer.getModel();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource bufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        VertexConsumer consumer = bufferSource.getBuffer(additiveRim(renderer.getTextureLocation(entity)));
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

        if (isLocked) {
            float distance = player != null ? (float) player.position().distanceTo(entity.position()) : 0.0F;
            TargetState state = TrackedTargetManager.computeState(entity, distance);
            int rgb = state.colorRgb();
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            poseStack.pushPose();
            poseStack.scale(RIM_SCALE, RIM_SCALE, RIM_SCALE);
            model.renderToBuffer(poseStack, consumer, packedLight, overlay,
                FastColor.ARGB32.color(RIM_ALPHA, red, green, blue));
            poseStack.popPose();
            if (TrackerVisionConfig.isRimBoostEnabled()) {
                RimBoostEffect.markActiveThisFrame();
            }

            if (player != null && isOccluded(player, entity)) {
                VertexConsumer silhouetteConsumer = bufferSource.getBuffer(silhouette());
                poseStack.pushPose();
                poseStack.scale(RIM_SCALE, RIM_SCALE, RIM_SCALE);
                model.renderToBuffer(poseStack, silhouetteConsumer, packedLight, overlay,
                    FastColor.ARGB32.color(SILHOUETTE_ALPHA, red, green, blue));
                poseStack.popPose();
            }
        } else {
            int red = (SEARCH_REVEAL_COLOR >> 16) & 0xFF;
            int green = (SEARCH_REVEAL_COLOR >> 8) & 0xFF;
            int blue = SEARCH_REVEAL_COLOR & 0xFF;

            poseStack.pushPose();
            poseStack.scale(RIM_SCALE, RIM_SCALE, RIM_SCALE);
            model.renderToBuffer(poseStack, consumer, packedLight, overlay,
                FastColor.ARGB32.color(SEARCH_RIM_ALPHA, red, green, blue));
            poseStack.popPose();
        }
    }

    /**
     * True if a block occludes the straight line from the player's eyes to
     * the target's center, meaning the normal depth-tested rim wouldn't be
     * visible without this silhouette pass.
     */
    private static boolean isOccluded(LivingEntity player, LivingEntity target) {
        if (player.level() == null) {
            return false;
        }
        var eyePos = player.getEyePosition();
        var targetPos = target.getBoundingBox().getCenter();
        var hit = player.level().clip(new net.minecraft.world.level.ClipContext(
            eyePos, targetPos,
            net.minecraft.world.level.ClipContext.Block.COLLIDER,
            net.minecraft.world.level.ClipContext.Fluid.NONE,
            player));
        return hit.getType() != HitResult.Type.MISS;
    }

    private static RenderType additiveRim(ResourceLocation texture) {
        return ADDITIVE_RIM_TYPES.computeIfAbsent(texture, location -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setOverlayState(RenderStateShard.OVERLAY)
                .createCompositeState(true);
            return RenderType.create("trackervision_additive_rim", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, state);
        });
    }

    /**
     * Flat, depth-test-disabled silhouette RenderType for the through-wall
     * pass. Uses {@code POSITION_COLOR} (not the textured entity format)
     * paired with {@code POSITION_COLOR_SHADER} — the same pairing vanilla
     * uses for its debug overlays — since the model's
     * {@code renderToBuffer} call silently drops any vertex element
     * (UV/overlay/light/normal) the target format doesn't declare.
     */
    private static RenderType silhouette() {
        if (silhouetteType == null) {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderType.COLOR_WRITE)
                .createCompositeState(false);
            silhouetteType = RenderType.create("trackervision_silhouette",
                DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, state);
        }
        return silhouetteType;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
