//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.capture;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.nobody174.petevolution.PetEvolution;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(PetEvolution.MOD_ID);

    public static final Supplier<CaptureBallItem> CAPTURE_BALL = ITEMS.registerItem(
        "capture_ball",
        CaptureBallItem::new,
        new Item.Properties().stacksTo(1)
    );

    private ModItems() {
    }
}

// Built with assistance from Claude Code by Anthropic.
