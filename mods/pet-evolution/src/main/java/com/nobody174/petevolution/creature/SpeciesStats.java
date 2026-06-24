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

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;

public final class SpeciesStats {

    private record BaseStats(int hp, int atk, int def, int spd, int special) {
    }

    private static final BaseStats HOSTILE = new BaseStats(24, 8, 4, 6, 5);
    private static final BaseStats PASSIVE = new BaseStats(18, 3, 6, 4, 7);
    private static final BaseStats NEUTRAL = new BaseStats(20, 5, 5, 5, 6);

    private SpeciesStats() {
    }

    public static PetData baseStatsFor(Mob mob, String speciesId) {
        BaseStats stats = classify(mob);
        PetRarity rarity = PetRarity.roll(mob.getRandom());
        double multiplier = rarity.statMultiplier();

        return new PetData(
            (int) Math.round(stats.hp() * multiplier),
            (int) Math.round(stats.atk() * multiplier),
            (int) Math.round(stats.def() * multiplier),
            (int) Math.round(stats.spd() * multiplier),
            (int) Math.round(stats.special() * multiplier),
            0, 0, speciesId, rarity);
    }

    private static BaseStats classify(Mob mob) {
        if (mob instanceof Enemy) {
            return HOSTILE;
        }
        if (mob instanceof Animal) {
            return PASSIVE;
        }
        return NEUTRAL;
    }
}

// Built with assistance from Claude Code by Anthropic.
