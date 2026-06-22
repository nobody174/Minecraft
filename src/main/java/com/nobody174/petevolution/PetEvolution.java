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
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;

import com.nobody174.petevolution.client.ClientSetup;
import com.nobody174.petevolution.client.PetTooltipHandler;
import com.nobody174.petevolution.component.ModAttachments;
import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.event.PetBattleEvent;
import com.nobody174.petevolution.event.PetBreedingEvent;
import com.nobody174.petevolution.event.PetXpEvent;
import com.nobody174.petevolution.item.ModItems;

@Mod(PetEvolution.MOD_ID)
public class PetEvolution {
    public static final String MOD_ID = "petevolution";

    public PetEvolution(ModContainer container, IEventBus modEventBus) {
        ModDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(PetEvolution::addCreativeTabItems);
        NeoForge.EVENT_BUS.register(new PetXpEvent());
        NeoForge.EVENT_BUS.register(new PetBattleEvent());
        NeoForge.EVENT_BUS.register(new PetBreedingEvent());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            NeoForge.EVENT_BUS.register(new PetTooltipHandler());
            modEventBus.addListener(ClientSetup::registerGuiLayers);
        }
    }

    private static void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.CAPTURE_BALL.get());
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
