//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.creature;

public final class EvolutionRules {
    public static final int MAX_STAGE = 2;
    public static final int HP_GAIN_PER_STAGE = 10;
    public static final int ATK_GAIN_PER_STAGE = 3;
    public static final int DEF_GAIN_PER_STAGE = 3;
    public static final int SPD_GAIN_PER_STAGE = 2;
    public static final int SPECIAL_GAIN_PER_STAGE = 3;

    /**
     * Minimum level (see PetData.level(), 1-indexed) required to unlock the Nth skill slot (0-indexed).
     * With MAX_STAGE = 2, level ranges 1-3; this grants slot 0-1 at level 1, slot 2 at level 2,
     * slots 3-4 at level 3 (fully evolved), giving 3-5 skills unlocked progressively as required.
     */
    public static final int[] SKILL_SLOT_UNLOCK_LEVEL = {1, 1, 2, 3, 3};

    private static final int[] XP_THRESHOLDS = {100, 300};

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
