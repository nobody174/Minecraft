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
import net.minecraft.world.entity.player.Player;

import com.nobody174.foundya.tracking.EntityTypeAliases;
import com.nobody174.foundya.tracking.TrackedTargetManager;
import com.nobody174.foundya.tracking.TrackingCategory;

/**
 * The tracking options pulled out of {@link TrackerMenuScreen}'s original
 * flat button list. "Nearest Enemy"/"Nearest Friendly (animals)" open
 * {@link TrackTypeSubmenuScreen} for a specific-type pick (zombie, wolf,
 * etc.) rather than immediately committing to "any" of that category.
 * "Nearest Player" and "By name..." both check for another player first
 * via {@link #hasOtherPlayer()} — if nobody's around, the whole menu closes
 * with a chat message rather than leaving the player stuck in a submenu or
 * a dead-end player list.
 */
public final class TrackSubmenuScreen extends Screen {

    private final Screen parent;

    public TrackSubmenuScreen(Screen parent) {
        super(Component.literal("Track"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(TrackerMenuLayout.MARGIN).rowSpacing(2);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        rows.addChild(categoryButton("Nearest (anything)", TrackingCategory.ANY));
        rows.addChild(navButton("Nearest Enemy",
            () -> new TrackTypeSubmenuScreen(this, TrackingCategory.ENEMY, EntityTypeAliases.enemyAliases())));
        rows.addChild(navButton("Nearest Friendly (animals)",
            () -> new TrackTypeSubmenuScreen(this, TrackingCategory.FRIENDLY, EntityTypeAliases.friendlyAliases())));
        rows.addChild(Button.builder(Component.literal("Nearest Player"), button -> trackNearestPlayer())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());
        rows.addChild(Button.builder(Component.literal("By name..."), button -> trackByName())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());
        rows.addChild(Button.builder(Component.literal("Back"), button -> onClose())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        TrackerMenuLayout.positionBottomLeft(grid, width, height);
        grid.visitWidgets(this::addRenderableWidget);
    }

    private Button categoryButton(String label, TrackingCategory category) {
        return Button.builder(Component.literal(label), button -> {
                TrackedTargetManager.setCategory(category);
                onClose();
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build();
    }

    private Button navButton(String label, java.util.function.Supplier<Screen> target) {
        return Button.builder(Component.literal(label), button -> {
                if (minecraft != null) {
                    minecraft.setScreen(target.get());
                }
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build();
    }

    /** Closes the whole menu (not just this screen) on "no players", instead of leaving the player stuck with nothing to do. */
    private void trackNearestPlayer() {
        if (!hasOtherPlayer()) {
            sendNoPlayersMessage();
            return;
        }
        TrackedTargetManager.setCategory(TrackingCategory.PLAYER);
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    /** Only opens {@link PlayerPickerScreen} if there's actually someone to list — otherwise same "no players" close as {@link #trackNearestPlayer()}. */
    private void trackByName() {
        if (!hasOtherPlayer()) {
            sendNoPlayersMessage();
            return;
        }
        if (minecraft != null) {
            minecraft.setScreen(new PlayerPickerScreen(this));
        }
    }

    private boolean hasOtherPlayer() {
        if (minecraft == null || minecraft.level == null || minecraft.player == null) {
            return false;
        }
        for (Player candidate : minecraft.level.players()) {
            if (candidate != minecraft.player) {
                return true;
            }
        }
        return false;
    }

    private void sendNoPlayersMessage() {
        if (minecraft != null && minecraft.player != null) {
            minecraft.player.displayClientMessage(Component.literal("No other players nearby."), false);
        }
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
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
