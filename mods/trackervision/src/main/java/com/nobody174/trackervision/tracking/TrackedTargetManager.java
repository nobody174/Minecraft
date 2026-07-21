//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

import java.util.UUID;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;

import com.nobody174.trackervision.config.TrackerVisionConfig;

/**
 * Client-side store for the currently tracked target and its selection
 * mode. v0.5 supports a single tracked entity at a time, selected either
 * manually ({@link TrackingMode#LOCKED}, via {@code /track lock}) or
 * automatically ({@link TrackingMode#NEAREST}, refreshed each tick by
 * {@code NearestTargetScanner}); multi-target tracking
 * ({@link TrackingMode#GROUP}/{@code FILTERED}) is v1.0 scope.
 *
 * <p>{@link #computeState(Entity, float)} needs the live entity (for
 * hostility) and the current distance (for range), so it's computed by
 * callers that already have that context (HUD/glow layers) rather than
 * cached here.</p>
 */
public final class TrackedTargetManager {
    private static UUID lockedTargetId;
    private static TrackingMode mode = TrackingMode.LOCKED;
    private static long acquiredAtNanos;

    private TrackedTargetManager() {
    }

    public static void lock(Entity entity) {
        setTargetId(entity.getUUID());
        mode = TrackingMode.LOCKED;
    }

    /** Called by the Nearest-mode scanner; does not change {@link #mode}. */
    public static void setAutoSelectedTarget(UUID entityId) {
        setTargetId(entityId);
    }

    private static void setTargetId(UUID newTargetId) {
        if (newTargetId != null && !newTargetId.equals(lockedTargetId)) {
            acquiredAtNanos = System.nanoTime();
        }
        lockedTargetId = newTargetId;
    }

    public static void clear() {
        lockedTargetId = null;
    }

    /**
     * Nanoseconds since the current target was (re-)acquired, for the
     * lock-acquired pulse animation. See docs/UI_STYLE_GUIDE.md.
     */
    public static long getAcquiredAgeNanos() {
        return System.nanoTime() - acquiredAtNanos;
    }

    public static void setMode(TrackingMode newMode) {
        mode = newMode;
        if (newMode == TrackingMode.NEAREST) {
            lockedTargetId = null;
        }
    }

    public static TrackingMode getMode() {
        return mode;
    }

    public static boolean isLocked() {
        return lockedTargetId != null;
    }

    public static UUID getLockedTargetId() {
        return lockedTargetId;
    }

    public static TargetState computeState(Entity target, float distance) {
        if (distance > TrackerVisionConfig.getFarDistance()) {
            return TargetState.OUT_OF_RANGE;
        }
        if (target instanceof Enemy) {
            return TargetState.HOSTILE_LOCKED;
        }
        return TargetState.TRACKING;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
