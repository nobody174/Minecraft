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
import net.minecraft.world.entity.Entity;

import com.nobody174.foundya.tracking.TrackedTargetManager;

/**
 * Primary player-facing entry point into Found Ya!, replacing the need
 * to memorize {@code /track ...} subcommand names: a small, bottom-left
 * anchored menu opened via a keybind or bare {@code /track}. Aimed at an
 * audience where typed English commands are a poor primary interface
 * (kids, non-English speakers, or just "dad, come HERE" panic moments) —
 * every option here has an equivalent typed command that keeps working
 * unchanged. Kept to 4 top-level actions (see {@link TrackSubmenuScreen}/
 * {@link SettingsSubmenuScreen} for the rest) after the first version's
 * flat 9-button list turned out to fill the whole display.
 */
public final class TrackerMenuScreen extends Screen {

    public TrackerMenuScreen() {
        super(Component.literal("Found Ya!"));
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(TrackerMenuLayout.MARGIN).rowSpacing(2);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        rows.addChild(navButton("Track...", () -> new TrackSubmenuScreen(this)));

        rows.addChild(actionButton("Clear tracked target", () -> {
            TrackedTargetManager.clear();
            return true;
        }));

        rows.addChild(actionButton("Lock", this::lockCurrentTarget));

        rows.addChild(navButton("Settings...", () -> new SettingsSubmenuScreen(this)));

        rows.addChild(Button.builder(Component.literal("Exit"), button -> onClose())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        TrackerMenuLayout.positionBottomLeft(grid, width, height);
        grid.visitWidgets(this::addRenderableWidget);
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

    /** {@code action} returns whether it succeeded — a failed action leaves the menu open so the player sees why. */
    private Button actionButton(String label, java.util.function.BooleanSupplier action) {
        return Button.builder(Component.literal(label), button -> {
                if (action.getAsBoolean()) {
                    onClose();
                }
            })
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build();
    }

    /**
     * Freezes the current auto-followed target (nearest enemy/friendly/
     * player/etc.) into an explicit {@link TrackedTargetManager#lock}, so
     * it stops updating to whatever's newly nearest. Requires resolving
     * the tracked UUID back to a live entity, since {@code lock} needs the
     * entity itself, not just its ID.
     */
    private boolean lockCurrentTarget() {
        if (minecraft == null || minecraft.level == null) {
            return false;
        }
        Entity target = TrackedTargetManager.findLockedEntity(minecraft.level);
        if (target == null) {
            feedback("Nothing is currently being tracked.");
            return false;
        }
        TrackedTargetManager.lock(target);
        feedback("Locked onto " + target.getName().getString() + ".");
        return true;
    }

    private void feedback(String message) {
        if (minecraft != null && minecraft.player != null) {
            minecraft.player.displayClientMessage(Component.literal(message), false);
        }
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
