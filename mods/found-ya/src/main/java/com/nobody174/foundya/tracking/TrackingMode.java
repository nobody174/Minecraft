//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

/**
 * How the currently tracked target is selected: {@link #LOCKED} (manual,
 * via {@code /track lock}) or {@link #NEAREST} (auto-select closest valid
 * entity each tick, optionally narrowed by {@link TrackingCategory} or a
 * specific entity type — see {@link TrackedTargetManager#setCategory} and
 * {@link TrackedTargetManager#setTypeFilter}). An originally planned
 * multi-target {@code GROUP}/{@code FILTERED} mode was superseded by that
 * category/type filtering — it covers the real use cases (track a pet,
 * track hostiles, track a specific player) without needing the single-target
 * {@link TrackedTargetManager} to become a collection.
 */
public enum TrackingMode {
    LOCKED,
    NEAREST
}

//*Built with assistance from __Claude Code__ by Anthropic.*
