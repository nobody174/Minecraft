//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura;

import com.nobody174.armoraura.client.AuraConfigFile;
import com.nobody174.armoraura.client.AuraRenderer;
import com.nobody174.armoraura.networking.AuraPacketHandler;
import com.nobody174.armoraura.server.ArmorDetectionManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ArmorAuraMod.MODID)
public class ArmorAuraMod {
    public static final String MODID = "armoraura";
    public static final Logger LOGGER = LoggerFactory.getLogger("ArmorAura");

    public ArmorAuraMod(ModContainer container, IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(AuraPacketHandler::register);

        // ArmorDetectionManager handles ServerTickEvent
        NeoForge.EVENT_BUS.register(new ArmorDetectionManager());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("ArmorAura common setup");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("ArmorAura client setup");
        NeoForge.EVENT_BUS.register(AuraRenderer.class);

        // Initialize config file system
        event.enqueueWork(() -> {
            AuraConfigFile.init();
            AuraConfigFile.loadConfig();
            // Initialize client-only commands
            com.nobody174.armoraura.client.AuraClientCommands.init();
        });
    }
}

// Built with assistance from Claude Code by Anthropic.
