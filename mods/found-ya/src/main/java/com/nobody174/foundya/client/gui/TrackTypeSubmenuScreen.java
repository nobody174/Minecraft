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

import java.util.List;
import java.util.Locale;

import com.nobody174.foundya.tracking.EntityTypeAliases;
import com.nobody174.foundya.tracking.TrackedTargetManager;
import com.nobody174.foundya.tracking.TrackingCategory;

/**
 * Lists the specific entity types for one {@link TrackingCategory}
 * (Enemy or Friendly), pulled from {@link EntityTypeAliases}, plus an
 * "All Enemies"/"All Friendlies" option at the top that does what the
 * plain category button used to do directly (before this submenu existed)
 * — one class serves both categories since the only difference is the
 * category and its alias list.
 */
public final class TrackTypeSubmenuScreen extends Screen {

    private final Screen parent;
    private final TrackingCategory category;
    private final List<String> aliases;

    public TrackTypeSubmenuScreen(Screen parent, TrackingCategory category, List<String> aliases) {
        super(Component.literal(category == TrackingCategory.ENEMY ? "Enemies" : "Friendlies"));
        this.parent = parent;
        this.category = category;
        this.aliases = aliases;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(TrackerMenuLayout.MARGIN).rowSpacing(2);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        String allLabel = category == TrackingCategory.ENEMY ? "All Enemies" : "All Friendlies";
        rows.addChild(Button.builder(Component.literal(allLabel), button -> {
                TrackedTargetManager.setCategory(category);
                if (minecraft != null) {
                    minecraft.setScreen(null);
                }
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        for (String alias : aliases) {
            rows.addChild(Button.builder(Component.literal(displayName(alias)), button -> trackType(alias))
                .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
                .build());
        }

        rows.addChild(Button.builder(Component.literal("Back"), button -> onClose())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        TrackerMenuLayout.positionBottomLeft(grid, width, height);
        grid.visitWidgets(this::addRenderableWidget);
    }

    private void trackType(String alias) {
        var type = EntityTypeAliases.resolve(alias);
        if (type != null) {
            TrackedTargetManager.setTypeFilter(type);
        }
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    private static String displayName(String alias) {
        return Character.toUpperCase(alias.charAt(0)) + alias.substring(1).toLowerCase(Locale.ROOT);
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
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
