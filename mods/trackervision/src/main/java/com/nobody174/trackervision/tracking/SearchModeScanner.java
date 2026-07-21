//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.nobody174.trackervision.config.TrackerVisionConfig;

/**
 * Refreshes {@link SearchModeManager#getRevealedTargets()} while Search
 * Mode is on: every {@link #SCAN_INTERVAL_TICKS} ticks, collects every
 * valid {@code LivingEntity} within {@code farDistance} of the player.
 * Same throttle/validity rules as {@link NearestTargetScanner}, just
 * collecting all matches instead of the single closest one.
 */
public final class SearchModeScanner {

    private static final int SCAN_INTERVAL_TICKS = 10;

    private SearchModeScanner() {
    }

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, SearchModeScanner::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (!SearchModeManager.isEnabled()) {
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

        float range = TrackerVisionConfig.getFarDistance();
        AABB searchBox = player.getBoundingBox().inflate(range);
        List<LivingEntity> candidates = mc.level.getEntitiesOfClass(LivingEntity.class, searchBox);

        Set<UUID> revealed = new HashSet<>();
        double rangeSq = (double) range * range;
        for (LivingEntity candidate : candidates) {
            if (candidate == player || !candidate.isAlive() || candidate.isInvisible()) {
                continue;
            }
            if (player.distanceToSqr(candidate) <= rangeSq) {
                revealed.add(candidate.getUUID());
            }
        }

        SearchModeManager.setRevealedTargets(revealed);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
