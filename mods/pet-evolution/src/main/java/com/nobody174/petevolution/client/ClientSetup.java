//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.minecraft.resources.ResourceLocation;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.client.ui.PetHudOverlay;
import com.nobody174.petevolution.client.ui.BattleHudOverlay;

public final class ClientSetup {

    private static final ResourceLocation PET_HUD_ID =
        ResourceLocation.fromNamespaceAndPath(PetEvolution.MOD_ID, "pet_hud");
    private static final ResourceLocation BATTLE_HUD_ID =
        ResourceLocation.fromNamespaceAndPath(PetEvolution.MOD_ID, "battle_hud");

    private ClientSetup() {
    }

    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(PET_HUD_ID, PetHudOverlay::render);
        event.registerAboveAll(BATTLE_HUD_ID, BattleHudOverlay::render);
    }
}

// Built with assistance from Claude Code by Anthropic.
