//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

/**
 * How the currently tracked target is selected. Per
 * {@code TrackerVision_Production_Design_Package_v2/05_TRACKING_ENGINE.md}.
 *
 * <p>v0.5 implements {@link #LOCKED} (manual, via {@code /track lock}) and
 * {@link #NEAREST} (auto-select closest valid entity each tick). {@link #GROUP}
 * and {@link #FILTERED} are v1.0 scope — multi-target tracking requires the
 * single-target {@link TrackedTargetManager} to become a collection first.</p>
 */
public enum TrackingMode {
    LOCKED,
    NEAREST
}

//*Built with assistance from __Claude Code__ by Anthropic.*
