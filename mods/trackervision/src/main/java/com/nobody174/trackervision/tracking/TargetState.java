//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.tracking;

/**
 * Visual state of the currently locked target, driving accent color across
 * the glow layer and HUD overlay. Palette per docs/UI_STYLE_GUIDE.md.
 */
public enum TargetState {
    TRACKING(0xFF35E0E0),
    HOSTILE_LOCKED(0xFFFF3B3B),
    OUT_OF_RANGE(0xFFFFB02E);

    private final int colorRgb;

    TargetState(int colorRgb) {
        this.colorRgb = colorRgb;
    }

    public int colorRgb() {
        return colorRgb;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
