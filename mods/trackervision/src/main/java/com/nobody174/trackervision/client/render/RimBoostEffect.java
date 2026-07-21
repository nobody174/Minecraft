//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.io.IOException;

/**
 * A real custom core-shader post-process pass (the v1.0 "shader pipeline"
 * roadmap item) — a cheap, full-screen brightness boost applied only to
 * already-bright pixels (the additive rim {@link TrackedTargetGlowRenderer}
 * just drew), so the rim reads with a soft bloom-like punch without a full
 * jump-flood/dilation outline rewrite. Deliberately scoped lightweight per
 * the explicit conditional in docs/RENDERING_RESEARCH.md ("only if the team
 * decides variable-thickness outlines are worth the GLSL investment") — this
 * delivers a genuine shader pipeline without that investment or its flagged
 * Iris-compatibility risk (see RISK_REGISTER.md).
 *
 * <p>Manual full-screen-quad approach rather than a {@code PostChain} JSON
 * pipeline: cheaper to toggle per-frame, no swap-chain bookkeeping. Renders
 * into a private offscreen target so the read (sampling the main target)
 * and write never alias the same bound framebuffer.</p>
 */
public final class RimBoostEffect {

    private static final ResourceLocation SHADER_LOCATION =
        ResourceLocation.fromNamespaceAndPath("trackervision", "rim_boost");
    private static final float INTENSITY = 1.4F;

    private static ShaderInstance shader;
    private static com.mojang.blaze3d.pipeline.TextureTarget scratchTarget;
    private static volatile boolean activeThisFrame = false;

    private RimBoostEffect() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(RimBoostEffect::onRegisterShaders);
        NeoForge.EVENT_BUS.addListener(RimBoostEffect::onRenderLevelStage);
    }

    /** Called by {@link TrackedTargetGlowRenderer} whenever it draws the locked target's rim this frame. */
    public static void markActiveThisFrame() {
        activeThisFrame = true;
    }

    private static void onRegisterShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(
                new ShaderInstance(event.getResourceProvider(), SHADER_LOCATION, DefaultVertexFormat.POSITION_TEX),
                loaded -> shader = loaded);
        } catch (IOException e) {
            org.slf4j.LoggerFactory.getLogger("TrackerVision").error("Failed to load rim_boost shader", e);
        }
    }

    private static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            return;
        }
        boolean shouldRun = activeThisFrame;
        activeThisFrame = false;
        if (!shouldRun || shader == null) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        var mainTarget = mc.getMainRenderTarget();
        int width = mainTarget.width;
        int height = mainTarget.height;

        if (scratchTarget == null) {
            scratchTarget = new com.mojang.blaze3d.pipeline.TextureTarget(width, height, false, false);
        }
        scratchTarget.resize(width, height, false);

        scratchTarget.bindWrite(true);
        RenderSystem.setShader(() -> shader);
        RenderSystem.setShaderTexture(0, mainTarget.getColorTextureId());
        if (shader.getUniform("Intensity") != null) {
            shader.getUniform("Intensity").set(INTENSITY);
        }

        GlStateManager._depthMask(false);
        GlStateManager._disableDepthTest();

        var buffer = Tesselator.getInstance();
        var builder = buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.addVertex(-1, -1, 0).setUv(0, 0);
        builder.addVertex(1, -1, 0).setUv(1, 0);
        builder.addVertex(1, 1, 0).setUv(1, 1);
        builder.addVertex(-1, 1, 0).setUv(0, 1);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        GlStateManager._enableDepthTest();
        GlStateManager._depthMask(true);

        mainTarget.bindWrite(true);
        scratchTarget.blitToScreen(width, height);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
