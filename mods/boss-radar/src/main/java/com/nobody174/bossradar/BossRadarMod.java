//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistration;

import com.nobody174.bossradar.network.BossRadarSyncPacket;
import com.nobody174.bossradar.network.BossRadarPacketHandler;
import com.nobody174.bossradar.registry.ModCreativeTabs;
import com.nobody174.bossradar.registry.ModItems;

@Mod("bossradar")
public class BossRadarMod {
    public static final String MOD_ID = "bossradar";

    public BossRadarMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerPackets);

        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup tasks
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup tasks
    }

    private void registerPackets(final RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(BossRadarSyncPacket.TYPE, BossRadarSyncPacket.STREAM_CODEC, BossRadarPacketHandler::handleSyncPacket);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
