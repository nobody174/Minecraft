//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

import java.util.UUID;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.foundya.config.FoundYaConfig;

/**
 * Client-side store for the currently tracked target and its selection
 * mode. Supports a single tracked entity at a time, selected either
 * manually ({@link TrackingMode#LOCKED}, via {@code /track lock}) or
 * automatically ({@link TrackingMode#NEAREST}, refreshed each tick by
 * {@code NearestTargetScanner}), optionally narrowed by
 * {@link TrackingCategory} (nearest enemy/friendly/player) or a specific
 * {@link EntityType} (nearest zombie, etc.) — see {@link #setCategory}/
 * {@link #setTypeFilter}. This is what actually replaced the originally
 * planned multi-target {@code TrackingMode.GROUP}/{@code FILTERED} modes:
 * filtering the single auto-selected target covered the real use cases
 * without needing multi-target state.
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
    private static TrackingCategory category = TrackingCategory.ANY;
    private static EntityType<?> typeFilter;

    private TrackedTargetManager() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(TrackedTargetManager::onLoggingOut);
    }

    /** Resets lock state on disconnect so a fresh session doesn't inherit the previous world's target/mode. */
    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        lockedTargetId = null;
        mode = TrackingMode.LOCKED;
        acquiredAtNanos = 0L;
        category = TrackingCategory.ANY;
        typeFilter = null;
    }

    public static void lock(Entity entity) {
        setTargetId(entity.getUUID());
        mode = TrackingMode.LOCKED;
        category = TrackingCategory.ANY;
        typeFilter = null;
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
        if (newMode != mode) {
            lockedTargetId = null;
        }
        mode = newMode;
        category = TrackingCategory.ANY;
        typeFilter = null;
    }

    public static TrackingMode getMode() {
        return mode;
    }

    /**
     * Switches to {@link TrackingMode#NEAREST} filtered by {@code newCategory}
     * (e.g. "nearest enemy"), clearing any type filter and the current
     * auto-selected target — same reasoning as {@link #setMode}: changing
     * what NEAREST searches for shouldn't silently keep a stale pick.
     */
    public static void setCategory(TrackingCategory newCategory) {
        category = newCategory;
        typeFilter = null;
        lockedTargetId = null;
        mode = TrackingMode.NEAREST;
    }

    public static TrackingCategory getCategory() {
        return category;
    }

    /**
     * Switches to {@link TrackingMode#NEAREST} restricted to a specific
     * {@link EntityType} (e.g. "nearest zombie"), clearing the category
     * filter (a type filter is more specific than a category) and the
     * current auto-selected target.
     */
    public static void setTypeFilter(EntityType<?> newType) {
        typeFilter = newType;
        category = TrackingCategory.ANY;
        lockedTargetId = null;
        mode = TrackingMode.NEAREST;
    }

    public static EntityType<?> getTypeFilter() {
        return typeFilter;
    }

    public static boolean isLocked() {
        return lockedTargetId != null;
    }

    public static UUID getLockedTargetId() {
        return lockedTargetId;
    }

    /**
     * Resolves the currently tracked target's UUID back to a live
     * {@link Entity} in {@code level}, or {@code null} if nothing is
     * tracked or the entity isn't currently loaded/rendered. Shared by
     * {@code TrackerHudOverlay} (to draw the reticle/beacon) and the menu
     * screens (to convert an auto-follow into an explicit {@link #lock}).
     */
    public static Entity findLockedEntity(ClientLevel level) {
        if (lockedTargetId == null || level == null) {
            return null;
        }
        for (Entity entity : level.entitiesForRendering()) {
            if (entity.getUUID().equals(lockedTargetId)) {
                return entity;
            }
        }
        return null;
    }

    public static TargetState computeState(Entity target, float distance) {
        if (distance > FoundYaConfig.getFarDistance()) {
            return TargetState.OUT_OF_RANGE;
        }
        if (target instanceof Enemy) {
            return TargetState.HOSTILE_LOCKED;
        }
        return TargetState.TRACKING;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
