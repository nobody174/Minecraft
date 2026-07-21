//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import com.nobody174.foundya.config.FoundYaConfig;
import com.nobody174.foundya.tracking.SearchModeManager;

/**
 * Options/Search Mode/Profile, pulled out of {@link TrackerMenuScreen}'s
 * original flat button list. Search Mode's button carries a one-line
 * description drawn beneath it — the label alone ("ON"/"OFF") didn't
 * explain that it reveals every nearby entity at once, independent of
 * whatever's currently tracked, which was confusing in the first version.
 */
public final class SettingsSubmenuScreen extends Screen {

    private static final int DESCRIPTION_COLOR = 0xFFAAAAAA;

    private final Screen parent;
    private int panelTop;

    public SettingsSubmenuScreen(Screen parent) {
        super(Component.literal("Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(TrackerMenuLayout.MARGIN).rowSpacing(2);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        rows.addChild(Button.builder(Component.literal("Options..."), button -> {
                if (minecraft != null) {
                    minecraft.setScreen(new FoundYaConfigScreen(this));
                }
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        rows.addChild(Button.builder(searchModeLabel(), button -> {
                SearchModeManager.setEnabled(!SearchModeManager.isEnabled());
                clearWidgets();
                init();
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        rows.addChild(Button.builder(
                Component.literal("Profile: " + FoundYaConfig.getActiveProfileName()),
                button -> cycleProfile())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        rows.addChild(Button.builder(Component.literal("Back"), button -> onClose())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        panelTop = TrackerMenuLayout.positionBottomLeft(grid, width, height);
        grid.visitWidgets(this::addRenderableWidget);
    }

    private Component searchModeLabel() {
        return Component.literal("Search Mode: " + (SearchModeManager.isEnabled() ? "ON" : "OFF"));
    }

    /** Switches to the next profile in registration order and rebuilds the widgets to reflect its settings. */
    private void cycleProfile() {
        var names = FoundYaConfig.getProfileNames();
        int currentIndex = names.indexOf(FoundYaConfig.getActiveProfileName());
        String next = names.get((currentIndex + 1) % names.size());
        FoundYaConfig.setActiveProfile(next);
        clearWidgets();
        init();
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawString(font, "Search Mode reveals every nearby entity at",
            TrackerMenuLayout.MARGIN, panelTop - 24, DESCRIPTION_COLOR, false);
        graphics.drawString(font, "once, separate from your tracked target.",
            TrackerMenuLayout.MARGIN, panelTop - 14, DESCRIPTION_COLOR, false);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
