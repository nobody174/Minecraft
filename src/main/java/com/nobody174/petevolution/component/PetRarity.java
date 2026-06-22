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

import net.minecraft.ChatFormatting;
import net.minecraft.util.RandomSource;

public enum PetRarity {
    COMMON(1.0, 60, ChatFormatting.WHITE),
    UNCOMMON(1.15, 25, ChatFormatting.GREEN),
    RARE(1.35, 12, ChatFormatting.BLUE),
    EPIC(1.6, 3, ChatFormatting.LIGHT_PURPLE);

    private final double statMultiplier;
    private final int rollWeight;
    private final ChatFormatting color;

    PetRarity(double statMultiplier, int rollWeight, ChatFormatting color) {
        this.statMultiplier = statMultiplier;
        this.rollWeight = rollWeight;
        this.color = color;
    }

    public double statMultiplier() {
        return statMultiplier;
    }

    public ChatFormatting color() {
        return color;
    }

    public static PetRarity roll(RandomSource random) {
        int totalWeight = 0;
        for (PetRarity rarity : values()) {
            totalWeight += rarity.rollWeight;
        }

        int roll = random.nextInt(totalWeight);
        int cumulative = 0;
        for (PetRarity rarity : values()) {
            cumulative += rarity.rollWeight;
            if (roll < cumulative) {
                return rarity;
            }
        }
        return COMMON;
    }
}

// Built with assistance from Claude Code by Anthropic.
