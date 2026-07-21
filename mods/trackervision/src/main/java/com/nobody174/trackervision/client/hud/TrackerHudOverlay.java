//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;

import org.joml.Matrix4f;

import com.nobody174.trackervision.config.TrackerVisionConfig;
import com.nobody174.trackervision.tracking.TargetState;
import com.nobody174.trackervision.tracking.TrackedTargetManager;

/**
 * Draws TrackerVision's actual visual identity: a corner-bracket reticle
 * over the locked target when it's on-screen, a clamped directional caret
 * when it isn't, and a distance readout. See docs/UI_STYLE_GUIDE.md for
 * the design rationale (thin geometric brackets over full boxes, depth-based
 * color/alpha falloff, restrained motion) — this is deliberately not a port
 * of boss-radar's ring-indicator HUD.
 */
public final class TrackerHudOverlay {

    private static final int BRACKET_CORNER_LENGTH = 7;
    private static final int BRACKET_STROKE = 2;

    private static final float MIN_ALPHA = 0.2F;
    private static final float MIN_SCALE = 0.7F;

    private static final int EDGE_MARGIN = 24;
    private static final int CARET_SIZE = 10;

    private static final int TEXT_COLOR_NEUTRAL = 0xFFE8EEF2;
    private static final int TEXT_OUTLINE = 0xC0000000;

    private static final int BEACON_HEIGHT = 300;
    private static final int BEACON_STROKE = 3;

    private TrackerHudOverlay() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(TrackerHudOverlay::onRenderGuiPost);
    }

    private static void onRenderGuiPost(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || !TrackedTargetManager.isLocked()
            || !TrackerVisionConfig.isTrackingEnabled()) {
            return;
        }

        Entity target = findLockedEntity(mc);
        if (target == null) {
            return;
        }

        DeltaTracker deltaTracker = event.getPartialTick();
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        Vec3 targetPos = target.getPosition(partialTick).add(0, target.getBbHeight() * 0.5, 0);
        float distance = (float) mc.player.position().distanceTo(targetPos);

        TargetState state = TrackedTargetManager.computeState(target, distance);
        int accentColor = state == TargetState.TRACKING
            ? TrackerVisionConfig.getTrackingAccentColor()
            : state.colorRgb();

        float nearDistance = TrackerVisionConfig.getNearDistance();
        float farDistance = TrackerVisionConfig.getFarDistance();
        float t = Mth.clamp((distance - nearDistance) / (farDistance - nearDistance), 0.0F, 1.0F);
        float s = t * t * (3.0F - 2.0F * t);
        float alpha = Mth.lerp(s, 1.0F, MIN_ALPHA);
        float scale = Mth.lerp(s, 1.0F, MIN_SCALE) * lockPulseScale() * breathingScale();

        ScreenProjection.Result projection = ScreenProjection.project(targetPos);
        GuiGraphics graphics = event.getGuiGraphics();

        boolean useBeacon = TrackerVisionConfig.isBeaconEnabled()
            && distance > TrackerVisionConfig.getBeaconDistance()
            && projection.isOnScreen();

        if (useBeacon) {
            ScreenProjection.Result baseProjection = ScreenProjection.project(target.getPosition(partialTick));
            drawBeacon(graphics, baseProjection.screenX(), baseProjection.screenY(), accentColor, alpha);
            drawDistanceText(graphics, baseProjection.screenX(), baseProjection.screenY() - BEACON_HEIGHT, distance, alpha);
        } else if (projection.isOnScreen()) {
            drawReticle(graphics, projection.screenX(), projection.screenY(), accentColor, alpha, scale);
            drawDistanceText(graphics, projection.screenX(), projection.screenY(), distance, alpha);
        } else {
            drawOffScreenCaret(graphics, projection, accentColor, alpha);
        }
    }

    private static Entity findLockedEntity(Minecraft mc) {
        var lockedId = TrackedTargetManager.getLockedTargetId();
        if (lockedId == null || mc.level == null) {
            return null;
        }
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity.getUUID().equals(lockedId)) {
                return entity;
            }
        }
        return null;
    }

    private static void drawReticle(GuiGraphics graphics, float centerX, float centerY, int accentColor, float alpha, float scale) {
        int color = withAlpha(accentColor, alpha);
        float half = (TrackerVisionConfig.getBracketBaseSize() * scale) / 2.0F;
        float corner = BRACKET_CORNER_LENGTH * scale;
        int stroke = Math.max(1, Math.round(BRACKET_STROKE * scale));

        float left = centerX - half;
        float right = centerX + half;
        float top = centerY - half;
        float bottom = centerY + half;

        // Top-left
        graphics.fill((int) left, (int) top, (int) (left + corner), (int) top + stroke, color);
        graphics.fill((int) left, (int) top, (int) left + stroke, (int) (top + corner), color);
        // Top-right
        graphics.fill((int) (right - corner), (int) top, (int) right, (int) top + stroke, color);
        graphics.fill((int) right - stroke, (int) top, (int) right, (int) (top + corner), color);
        // Bottom-left
        graphics.fill((int) left, (int) bottom - stroke, (int) (left + corner), (int) bottom, color);
        graphics.fill((int) left, (int) (bottom - corner), (int) left + stroke, (int) bottom, color);
        // Bottom-right
        graphics.fill((int) (right - corner), (int) bottom - stroke, (int) right, (int) bottom, color);
        graphics.fill((int) right - stroke, (int) (bottom - corner), (int) right, (int) bottom, color);
    }

    /**
     * Sky-to-target beacon for far-away targets (beyond
     * {@code TrackerVisionConfig.getBeaconDistance()}): a tall vertical accent
     * pillar extending from high on-screen down to the target's position,
     * with a gradient fade (brightest near the target, fading to near-invisible
     * at the top so it reads as anchored). Capped with a small chevron at the
     * target's feet. Replaces the bracket reticle at range since a heavily
     * shrunk bracket is hard to spot — the beacon's whole purpose is making
     * distant targets visible from anywhere on-screen.
     */
    private static void drawBeacon(GuiGraphics graphics, float baseX, float baseY, int accentColor, float alpha) {
        int half = BEACON_STROKE / 2;
        for (int i = 0; i < BEACON_HEIGHT; i++) {
            float segmentAlpha = alpha * (1.0F - (float) i / BEACON_HEIGHT);
            int color = withAlpha(accentColor, segmentAlpha);
            int y = (int) baseY - i;
            graphics.fill((int) baseX - half, y, (int) baseX - half + BEACON_STROKE, y + 1, color);
        }

        int chevronColor = withAlpha(accentColor, alpha);
        int tipY = (int) baseY - BEACON_HEIGHT;
        drawCaret(graphics, baseX, tipY, -(float) (Math.PI / 2.0), chevronColor);
    }

    private static void drawDistanceText(GuiGraphics graphics, float centerX, float centerY, float distance, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        String text = Math.round(distance) + "m";
        int color = withAlpha(TEXT_COLOR_NEUTRAL, alpha);
        int textWidth = mc.font.width(text);
        int x = Math.round(centerX) - textWidth / 2;
        int y = Math.round(centerY) + TrackerVisionConfig.getBracketBaseSize() / 2 + 4;
        drawOutlinedString(graphics, text, x, y, color, withAlpha(TEXT_OUTLINE, alpha));
    }

    private static void drawOutlinedString(GuiGraphics graphics, String text, int x, int y, int color, int outlineColor) {
        Minecraft mc = Minecraft.getInstance();
        graphics.drawString(mc.font, text, x - 1, y, outlineColor, false);
        graphics.drawString(mc.font, text, x + 1, y, outlineColor, false);
        graphics.drawString(mc.font, text, x, y - 1, outlineColor, false);
        graphics.drawString(mc.font, text, x, y + 1, outlineColor, false);
        graphics.drawString(mc.font, text, x, y, color, false);
    }

    private static void drawOffScreenCaret(GuiGraphics graphics, ScreenProjection.Result projection, int accentColor, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        float centerX = width / 2.0F;
        float centerY = height / 2.0F;

        float dx = projection.screenX() - centerX;
        float dy = projection.screenY() - centerY;
        if (dx == 0 && dy == 0) {
            dx = 0.001F;
        }
        float bearing = (float) Math.atan2(dy, dx);

        float halfW = centerX - EDGE_MARGIN;
        float halfH = centerY - EDGE_MARGIN;
        float scaleX = halfW / Math.abs(dx == 0 ? 0.0001F : dx);
        float scaleY = halfH / Math.abs(dy == 0 ? 0.0001F : dy);
        float clampScale = Math.min(scaleX, scaleY);

        float caretX = centerX + dx * clampScale;
        float caretY = centerY + dy * clampScale;

        int color = withAlpha(accentColor, alpha);
        drawCaret(graphics, caretX, caretY, bearing, color);
    }

    /**
     * Draws a flat-color equilateral triangle pointing along {@code bearing},
     * submitted directly to {@link RenderType#gui()} as a degenerate quad
     * (the 4th vertex repeats the 3rd) since {@code GuiGraphics.fill} only
     * supports axis-aligned rectangles.
     */
    private static void drawCaret(GuiGraphics graphics, float x, float y, float bearing, int color) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.mulPose(Axis.ZP.rotation(bearing));

        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer consumer = graphics.bufferSource().getBuffer(RenderType.gui());

        float tip = CARET_SIZE * 0.6F;
        float back = CARET_SIZE * 0.4F;
        float spread = CARET_SIZE * 0.5F;

        consumer.addVertex(matrix, tip, 0, 0).setColor(color);
        consumer.addVertex(matrix, -back, -spread, 0).setColor(color);
        consumer.addVertex(matrix, -back, spread, 0).setColor(color);
        consumer.addVertex(matrix, -back, spread, 0).setColor(color);

        poseStack.popPose();
    }

    private static int withAlpha(int rgb, float alpha) {
        int a = Mth.clamp(Math.round(alpha * 255), 0, 255);
        return (a << 24) | (rgb & 0x00FFFFFF);
    }

    /**
     * Damped-spring scale pulse on target acquisition, settling to 1.0
     * within roughly a second. See docs/UI_STYLE_GUIDE.md "Lock-acquired
     * pulse".
     */
    private static float lockPulseScale() {
        float ageSeconds = TrackedTargetManager.getAcquiredAgeNanos() / 1.0e9F;
        if (ageSeconds > 1.5F) {
            return 1.0F;
        }
        return 1.0F + 0.25F * (float) (Math.exp(-ageSeconds * 8.0) * Math.sin(ageSeconds * 40.0));
    }

    /** Continuous subtle breathing on the reticle, independent of game state. */
    private static float breathingScale() {
        double nowSeconds = System.nanoTime() / 1.0e9;
        return 0.97F + 0.03F * (float) Math.sin(nowSeconds * 2.5);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
