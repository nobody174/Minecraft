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

import java.util.Map;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;

public final class SpeciesStats {

    private record BaseStats(int hp, int atk, int def, int spd) {
    }

    private static final BaseStats HOSTILE = new BaseStats(24, 8, 4, 6);
    private static final BaseStats PASSIVE = new BaseStats(18, 3, 6, 4);
    private static final BaseStats NEUTRAL = new BaseStats(20, 5, 5, 5);

    private SpeciesStats() {
    }

    public static PetData baseStatsFor(Mob mob, String speciesId) {
        BaseStats stats = classify(mob);
        return new PetData(stats.hp(), stats.atk(), stats.def(), stats.spd(), 0, 0, speciesId);
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
