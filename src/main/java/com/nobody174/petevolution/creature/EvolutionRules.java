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

    private static final int[] XP_THRESHOLDS = {100, 300};

    private EvolutionRules() {
    }

    public static int xpThresholdFor(int currentStage) {
        if (currentStage >= XP_THRESHOLDS.length) {
            return Integer.MAX_VALUE;
        }
        return XP_THRESHOLDS[currentStage];
    }
}

// Built with assistance from Claude Code by Anthropic.
