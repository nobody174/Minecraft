//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.foundya.client.command.TrackCommand;
import com.nobody174.foundya.client.gui.FoundYaConfigScreen;
import com.nobody174.foundya.client.hud.TrackerHudOverlay;
import com.nobody174.foundya.client.render.RimBoostEffect;
import com.nobody174.foundya.client.render.TrackedTargetGlowRenderer;
import com.nobody174.foundya.config.FoundYaConfigFile;
import com.nobody174.foundya.tracking.NearestTargetScanner;
import com.nobody174.foundya.tracking.SearchModeScanner;
import com.nobody174.foundya.tracking.TrackedTargetManager;

public final class FoundYaClientSetup {
    private FoundYaClientSetup() {
    }

    /** Called only on the client side. Registers all client-only systems and the config screen. */
    public static void setupClient(IEventBus modEventBus, ModContainer modContainer) {
        init(modEventBus);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
            (container, parent) -> new FoundYaConfigScreen(parent));
        modEventBus.addListener((FMLClientSetupEvent event) ->
            event.enqueueWork(FoundYaConfigFile::load));
    }

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(FoundYaClientSetup::registerCommands);
        TrackedTargetManager.register(modEventBus);
        TrackedTargetGlowRenderer.register(modEventBus);
        TrackerHudOverlay.register(modEventBus);
        NearestTargetScanner.register(modEventBus);
        SearchModeScanner.register(modEventBus);
        RimBoostEffect.register(modEventBus);
        TrackerKeybinds.register(modEventBus);
    }

    private static void registerCommands(final RegisterClientCommandsEvent event) {
        TrackCommand.register(event.getDispatcher());
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
