//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.trackervision.client.command.TrackCommand;
import com.nobody174.trackervision.client.gui.TrackerVisionConfigScreen;
import com.nobody174.trackervision.client.hud.TrackerHudOverlay;
import com.nobody174.trackervision.client.render.RimBoostEffect;
import com.nobody174.trackervision.client.render.TrackedTargetGlowRenderer;
import com.nobody174.trackervision.config.TrackerVisionConfigFile;
import com.nobody174.trackervision.tracking.NearestTargetScanner;
import com.nobody174.trackervision.tracking.SearchModeScanner;

public final class TrackerVisionClientSetup {
    private TrackerVisionClientSetup() {
    }

    /** Called only on the client side. Registers all client-only systems and the config screen. */
    public static void setupClient(IEventBus modEventBus, ModContainer modContainer) {
        init(modEventBus);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
            (container, parent) -> new TrackerVisionConfigScreen(parent));
        modEventBus.addListener((FMLClientSetupEvent event) ->
            event.enqueueWork(TrackerVisionConfigFile::load));
    }

    public static void init(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(TrackerVisionClientSetup::registerCommands);
        TrackedTargetGlowRenderer.register(modEventBus);
        TrackerHudOverlay.register(modEventBus);
        NearestTargetScanner.register(modEventBus);
        SearchModeScanner.register(modEventBus);
        RimBoostEffect.register(modEventBus);
    }

    private static void registerCommands(final RegisterClientCommandsEvent event) {
        TrackCommand.register(event.getDispatcher());
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
