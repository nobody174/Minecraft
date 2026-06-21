//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import com.nobody174.buddybeast.entity.ModEntities;
import com.nobody174.buddybeast.network.BuddyNetworkHandler;

@Mod("buddybeast")
public class BuddyBeast {
    public static final String MOD_ID = "buddybeast";

    public BuddyBeast(ModContainer container, IEventBus modEventBus) {
        // Register deferred registries
        modEventBus.register(ModEntities.class);
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // Register network handlers
        modEventBus.addListener(BuddyNetworkHandler::register);
    }
}

// Built with assistance from Claude Code by Anthropic.
