//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import com.nobody174.buddybeast.command.BuddyCommand;
import com.nobody174.buddybeast.entity.ModEntities;
import com.nobody174.buddybeast.entity.BuddyBeastEntity;
import com.nobody174.buddybeast.network.BuddyNetworkHandler;
import com.nobody174.buddybeast.client.ClientSetup;
import com.nobody174.buddybeast.client.BuddyDevConfig;

@Mod("buddybeast")
public class BuddyBeast {
    public static final String MOD_ID = "buddybeast";

    public BuddyBeast(ModContainer container, IEventBus modEventBus) {
        // Register deferred registries
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // Register network handlers
        modEventBus.addListener(BuddyNetworkHandler::register);

        // Register client-side renderer registration
        modEventBus.addListener(ClientSetup::registerEntityRenderers);

        // Register entity attributes (required for any Mob/LivingEntity)
        modEventBus.addListener((EntityAttributeCreationEvent event) ->
            event.put(ModEntities.BUDDY_BEAST.get(), BuddyBeastEntity.createAttributes().build()));

        // Dev-tuning config (client-only): NeoForge watches the TOML file on
        // disk and live-reloads it, so visual constants can be tuned without
        // rebuilding/restarting the game.
        container.registerConfig(ModConfig.Type.CLIENT, BuddyDevConfig.SPEC);

        // /buddybeast spawnmany <count> - performance testing aid
        NeoForge.EVENT_BUS.addListener(BuddyCommand::register);
    }
}

// Built with assistance from Claude Code by Anthropic.
