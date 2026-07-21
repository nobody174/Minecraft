//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.hud;

import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

/**
 * Projects a world position into screen space for HUD drawing.
 *
 * <p>Called from {@link net.neoforged.neoforge.client.event.RenderGuiEvent.Post},
 * where {@code RenderSystem}'s matrices already belong to the 2D HUD pass —
 * the 3D camera view/projection must be reconstructed manually from
 * {@link Camera}, there is no exposed "world to screen" utility in vanilla.</p>
 *
 * <p>Uses {@code Minecraft.options.fov()} (the raw user setting) rather than
 * the actual rendered FOV, since {@code GameRenderer.getFov} is private and
 * not accessible to mod code. This means projected positions will drift
 * slightly during fog/zoom/death-animation FOV changes — acceptable for a
 * HUD overlay, not acceptable if this math is ever reused for anything
 * pixel-precise.</p>
 */
public final class ScreenProjection {

    private ScreenProjection() {
    }

    /**
     * @param worldPos target position in world space. Callers projecting a
     *                  moving entity must pass an already-interpolated
     *                  position (e.g. {@code entity.getPosition(partialTick)});
     *                  {@link Camera#getPosition()} only interpolates the
     *                  camera itself, not other entities.
     * @return the projection result; check {@link Result#isInFrontOfCamera()}
     *         before using the screen coordinates, since points behind the
     *         camera still produce numeric (meaningless) screen coordinates.
     */
    public static Result project(Vec3 worldPos) {
        Minecraft mc = Minecraft.getInstance();
        GameRenderer gameRenderer = mc.gameRenderer;
        Camera camera = gameRenderer.getMainCamera();
        Window window = mc.getWindow();

        double fovDegrees = mc.options.fov().get().doubleValue();
        Matrix4f projMatrix = gameRenderer.getProjectionMatrix(fovDegrees);

        Vec3 camPos = camera.getPosition();
        Quaternionf invRot = camera.rotation().conjugate(new Quaternionf());
        Matrix4f viewMatrix = new Matrix4f().rotation(invRot);

        Vector4f clip = new Vector4f(
            (float) (worldPos.x - camPos.x),
            (float) (worldPos.y - camPos.y),
            (float) (worldPos.z - camPos.z),
            1.0F);
        viewMatrix.transform(clip);
        projMatrix.transform(clip);

        boolean inFront = clip.w > 0.0F;
        float ndcX = inFront ? clip.x / clip.w : -clip.x;
        float ndcY = inFront ? clip.y / clip.w : -clip.y;

        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        float screenX = (ndcX * 0.5F + 0.5F) * screenWidth;
        float screenY = (1.0F - (ndcY * 0.5F + 0.5F)) * screenHeight;

        boolean onScreen = inFront
            && screenX >= 0 && screenX <= screenWidth
            && screenY >= 0 && screenY <= screenHeight;

        return new Result(screenX, screenY, inFront, onScreen);
    }

    public record Result(float screenX, float screenY, boolean isInFrontOfCamera, boolean isOnScreen) {
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
