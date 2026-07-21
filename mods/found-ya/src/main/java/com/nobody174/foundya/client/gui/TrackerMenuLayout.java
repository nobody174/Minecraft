//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.gui;

import net.minecraft.client.gui.layouts.GridLayout;

/**
 * Shared sizing/positioning for Found Ya!'s menu screens: a small panel
 * anchored to the bottom-left corner (near where chat normally sits)
 * instead of a full-width, centered options page. All of
 * {@link TrackerMenuScreen}, {@link TrackSubmenuScreen},
 * {@link SettingsSubmenuScreen}, and {@link PlayerPickerScreen} use this so
 * the whole flow reads as one small tool rather than one compact screen
 * linking out to full-size ones.
 */
final class TrackerMenuLayout {

    static final int ROW_HEIGHT = 16;
    static final int WIDGET_WIDTH = 140;
    static final int MARGIN = 8;

    private TrackerMenuLayout() {
    }

    /**
     * Anchors {@code grid} to the bottom-left corner, {@link #MARGIN} px in
     * from each edge, and returns the panel's top Y so callers can position
     * extra content (e.g. a description label) directly above it.
     */
    static int positionBottomLeft(GridLayout grid, int screenWidth, int screenHeight) {
        grid.arrangeElements();
        int top = screenHeight - MARGIN - grid.getHeight();
        grid.setPosition(MARGIN, top);
        return top;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
