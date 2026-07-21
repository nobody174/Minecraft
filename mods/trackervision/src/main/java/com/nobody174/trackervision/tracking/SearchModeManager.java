//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Persistent on/off state for Search Mode: a multi-target reveal,
 * independent of {@link TrackedTargetManager}'s single locked/nearest
 * target. While enabled, {@link com.nobody174.trackervision.tracking.SearchModeScanner}
 * keeps {@link #getRevealedTargets()} refreshed with every valid
 * {@code LivingEntity} within range, and the render layer draws the rim
 * accent on all of them simultaneously. This is the multi-target "GROUP"
 * case the v2 planning pack's Tracking Engine doc flagged as v1.0 scope —
 * a true {@code TrackingMode.GROUP} would let it interoperate with a
 * single locked target too, but that integration is left for a future
 * pass since search and lock/nearest don't need to share state to be
 * useful independently.
 */
public final class SearchModeManager {

    private static volatile boolean enabled = false;
    private static final Set<UUID> revealedTargets = ConcurrentHashMap.newKeySet();

    private SearchModeManager() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        if (!value) {
            revealedTargets.clear();
        }
    }

    public static Set<UUID> getRevealedTargets() {
        return Collections.unmodifiableSet(revealedTargets);
    }

    static void setRevealedTargets(Set<UUID> targets) {
        revealedTargets.clear();
        revealedTargets.addAll(targets);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
