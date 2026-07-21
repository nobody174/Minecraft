//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

import com.nobody174.foundya.config.FoundYaConfig;

/**
 * Drives {@link TrackingMode#NEAREST}: every {@link #SCAN_INTERVAL_TICKS}
 * ticks, re-selects the closest valid {@link LivingEntity} within
 * {@link com.nobody174.foundya.config.FoundYaConfig#getFarDistance()}
 * as the tracked target, additionally filtered by
 * {@link TrackedTargetManager#getCategory()}/{@link TrackedTargetManager#getTypeFilter()}
 * when set (e.g. "nearest enemy", "nearest zombie"). Purely client-side
 * cosmetic selection — no networking, unlike boss-radar's server-side
 * item-tick scan, since Found Ya!'s tracking state never needs to
 * leave this client.
 */
public final class NearestTargetScanner {

    private static final int SCAN_INTERVAL_TICKS = 10;

    private NearestTargetScanner() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, NearestTargetScanner::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (TrackedTargetManager.getMode() != TrackingMode.NEAREST) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (mc.level == null || player == null) {
            return;
        }
        if (player.tickCount % SCAN_INTERVAL_TICKS != 0) {
            return;
        }

        float range = FoundYaConfig.getFarDistance();
        List<LivingEntity> candidates = NearbyEntityScan.candidatesWithinRange(mc.level, player, range);

        TrackingCategory category = TrackedTargetManager.getCategory();
        var typeFilter = TrackedTargetManager.getTypeFilter();

        LivingEntity nearest = null;
        double nearestDistSq = (double) range * range;
        for (LivingEntity candidate : candidates) {
            if (!NearbyEntityScan.isValidCandidate(candidate, player)) {
                continue;
            }
            if (!category.matches(candidate)) {
                continue;
            }
            if (typeFilter != null && candidate.getType() != typeFilter) {
                continue;
            }
            double distSq = player.distanceToSqr(candidate);
            if (distSq < nearestDistSq) {
                nearestDistSq = distSq;
                nearest = candidate;
            }
        }

        TrackedTargetManager.setAutoSelectedTarget(nearest != null ? nearest.getUUID() : null);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
