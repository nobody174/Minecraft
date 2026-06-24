//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.creature;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Applies {@link PetData} stats to a released mob's vanilla attributes.
 *
 * Note: {@code special} (v2.0) has no vanilla attribute equivalent and is
 * intentionally not applied here — it is consumed entirely by the skills
 * and battle engine for non-physical skill power scaling.
 */
public final class PetStatApplier {

    private static final int BASELINE_SPD = 5;
    private static final double SPEED_PERCENT_PER_SPD = 0.05;

    private PetStatApplier() {
    }

    public static void apply(LivingEntity entity, PetData data) {
        setBase(entity, Attributes.MAX_HEALTH, data.hp());
        entity.setHealth(entity.getMaxHealth());
        setBase(entity, Attributes.ATTACK_DAMAGE, data.atk());
        setBase(entity, Attributes.ARMOR, data.def());

        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            double vanillaBase = speed.getBaseValue();
            int spdDelta = data.spd() - BASELINE_SPD;
            speed.setBaseValue(vanillaBase * (1.0 + spdDelta * SPEED_PERCENT_PER_SPD));
        }
    }

    private static void setBase(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, double value) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
