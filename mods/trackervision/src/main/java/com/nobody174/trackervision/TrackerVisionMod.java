//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import com.nobody174.trackervision.client.TrackerVisionClientSetup;

@Mod(TrackerVisionMod.MOD_ID)
public class TrackerVisionMod {
    public static final String MOD_ID = "trackervision";

    public TrackerVisionMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerPackets);

        if (FMLEnvironment.dist.isClient()) {
            TrackerVisionClientSetup.setupClient(modEventBus, modContainer);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup is handled in TrackerVisionClientSetup
    }

    private void registerPackets(final RegisterPayloadHandlersEvent event) {
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
