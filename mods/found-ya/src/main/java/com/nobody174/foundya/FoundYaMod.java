//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

import com.nobody174.foundya.client.FoundYaClientSetup;

/**
 * No common-side setup, packet registration, or {@code FMLCommonSetupEvent}
 * listener here: Found Ya! is entirely client-side cosmetic state (see
 * {@code side="CLIENT"} in neoforge.mods.toml) with no networking, so there
 * is nothing for the common bus to do beyond gating client setup.
 */
@Mod(FoundYaMod.MOD_ID)
public class FoundYaMod {
    public static final String MOD_ID = "foundya";

    public FoundYaMod(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist.isClient()) {
            FoundYaClientSetup.setupClient(modEventBus, modContainer);
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
