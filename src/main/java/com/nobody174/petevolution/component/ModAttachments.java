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

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import com.nobody174.petevolution.PetEvolution;

public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, PetEvolution.MOD_ID);

    public static final Supplier<AttachmentType<PetOwnerData>> PET_OWNER =
        ATTACHMENT_TYPES.register("pet_owner",
            () -> AttachmentType.builder(() -> (PetOwnerData) null)
                .serialize(PetOwnerData.CODEC)
                .build());

    public static final Supplier<AttachmentType<PetData>> RELEASED_PET_DATA =
        ATTACHMENT_TYPES.register("released_pet_data",
            () -> AttachmentType.builder(() -> (PetData) null)
                .serialize(PetData.CODEC)
                .build());

    private ModAttachments() {
    }
}

// Built with assistance from Claude Code by Anthropic.
