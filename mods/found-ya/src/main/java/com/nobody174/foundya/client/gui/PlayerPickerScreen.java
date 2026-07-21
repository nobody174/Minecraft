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

import com.nobody174.foundya.tracking.TrackedTargetManager;

/**
 * Lists every other player currently visible to the client (from
 * {@code Minecraft.level.players()}, which already reflects the server's
 * tracked-player set in both singleplayer and multiplayer) as clickable
 * buttons — clicking one locks onto that player via
 * {@link TrackedTargetManager#lock}. No typing required, so a parent can
 * find their kid ("dad, come HERE") without needing to know or spell an
 * exact username.
 *
 * <p>Only ever opened by {@code TrackSubmenuScreen} after it's confirmed
 * at least one other player exists — if nobody's around, that caller sends
 * a chat message and closes the whole menu itself rather than opening this
 * screen just to show a dead-end "no players" row.</p>
 */
public final class PlayerPickerScreen extends Screen {

    private final Screen parent;

    public PlayerPickerScreen(Screen parent) {
        super(Component.literal("Track a Player"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(TrackerMenuLayout.MARGIN).rowSpacing(2);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        Player localPlayer = minecraft != null ? minecraft.player : null;
        var otherPlayers = minecraft != null && minecraft.level != null
            ? minecraft.level.players()
            : java.util.List.<Player>of();

        for (Player candidate : otherPlayers) {
            if (candidate == localPlayer) {
                continue;
            }
            String name = candidate.getName().getString();
            rows.addChild(Button.builder(Component.literal(name), button -> lockOnto(candidate))
                .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
                .build());
        }

        rows.addChild(Button.builder(Component.literal("Back"), button -> onClose())
            .size(TrackerMenuLayout.WIDGET_WIDTH, TrackerMenuLayout.ROW_HEIGHT)
            .build());

        TrackerMenuLayout.positionBottomLeft(grid, width, height);
        grid.visitWidgets(this::addRenderableWidget);
    }

    private void lockOnto(Player target) {
        TrackedTargetManager.lock(target);
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
