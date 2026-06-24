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

public final class EvolutionRules {
    /**
     * 10 total levels (stage 0-9, level() = evoStage + 1). Originally capped at
     * MAX_STAGE = 2 (3 levels) — expanded after real play-testing showed a pet could
     * rack up 15,000+ XP at stage 2 with nowhere for it to go, far outpacing the
     * original 100/300 XP thresholds.
     */
    public static final int MAX_STAGE = 9;
    public static final int HP_GAIN_PER_STAGE = 10;
    public static final int ATK_GAIN_PER_STAGE = 3;
    public static final int DEF_GAIN_PER_STAGE = 3;
    public static final int SPD_GAIN_PER_STAGE = 2;
    public static final int SPECIAL_GAIN_PER_STAGE = 3;

    /**
     * Minimum level (see PetData.level(), 1-indexed) required to unlock the Nth skill slot (0-indexed).
     * With MAX_STAGE = 9, level ranges 1-10; slots 0-1 unlock immediately, slot 2 at level 4,
     * slots 3-4 at level 8, so all 5 skills are available well before the final level rather
     * than only at the very last stage.
     */
    public static final int[] SKILL_SLOT_UNLOCK_LEVEL = {1, 1, 4, 8, 8};

    /** Roughly-doubling (x1.8) curve from a 100 XP base: fast early levels, meaningfully grindier later ones. */
    private static final int[] XP_THRESHOLDS = {100, 180, 324, 583, 1049, 1888, 3398, 6116, 11008};

    private EvolutionRules() {
    }

    public static int xpThresholdFor(int currentStage) {
        if (currentStage >= XP_THRESHOLDS.length) {
            return Integer.MAX_VALUE;
        }
        return XP_THRESHOLDS[currentStage];
    }

    /** How many of the species' (up to 5) skill slots are unlocked at the given level. */
    public static int unlockedSkillCount(int level) {
        int count = 0;
        for (int unlockLevel : SKILL_SLOT_UNLOCK_LEVEL) {
            if (level >= unlockLevel) {
                count++;
            }
        }
        return count;
    }
}

// Built with assistance from Claude Code by Anthropic.
