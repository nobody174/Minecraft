//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.foundya.client.gui.TrackerMenuScreen;

/**
 * Opens {@link TrackerMenuScreen} via keybind (default: {@code K}, unbound
 * if that conflicts on a given keyboard layout — players can rebind in
 * Controls like any other mod keybind). {@code /track} with no
 * subcommand opens the same screen for players who'd rather type it; see
 * {@code TrackCommand}.
 */
public final class TrackerKeybinds {

    private static final KeyMapping OPEN_MENU = new KeyMapping(
        "key.foundya.open_menu",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_K,
        "key.categories.foundya");

    private TrackerKeybinds() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(TrackerKeybinds::registerKeyMapping);
        NeoForge.EVENT_BUS.addListener(TrackerKeybinds::onClientTick);
    }

    private static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(OPEN_MENU);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (OPEN_MENU.consumeClick()) {
            if (mc.screen == null && mc.player != null) {
                mc.setScreen(new TrackerMenuScreen());
            }
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
