//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

import com.nobody174.buddybeast.BuddyBeast;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BuddyBeast.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<BuddyBeastEntity>> BUDDY_BEAST =
        ENTITY_TYPES.register("buddy_beast", () ->
            EntityType.Builder.of(BuddyBeastEntity::new, MobCategory.CREATURE)
                .sized(0.8f, 1.9f)
                .eyeHeight(1.3f)
                .clientTrackingRange(8)
                .updateInterval(3)
                .build("buddy_beast")
        );
}

// Built with assistance from Claude Code by Anthropic.
