//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.component;

import java.util.function.Supplier;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.nobody174.petevolution.PetEvolution;

public final class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PetEvolution.MOD_ID);

    public static final Supplier<DataComponentType<PetData>> PET_DATA =
        DATA_COMPONENT_TYPES.registerComponentType("pet_data",
            builder -> builder
                .persistent(PetData.CODEC)
                .networkSynchronized(PetData.STREAM_CODEC));

    private ModDataComponents() {
    }
}

// Built with assistance from Claude Code by Anthropic.
