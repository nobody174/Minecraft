//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

import com.nobody174.petevolution.client.PetTooltipHandler;
import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.item.ModItems;
import com.nobody174.petevolution.network.PetNetworkHandler;

@Mod(PetEvolution.MOD_ID)
public class PetEvolution {
    public static final String MOD_ID = "petevolution";

    public PetEvolution(ModContainer container, IEventBus modEventBus) {
        ModDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(PetNetworkHandler::register);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            NeoForge.EVENT_BUS.register(new PetTooltipHandler());
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
