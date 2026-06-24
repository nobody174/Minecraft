//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.skills;

/**
 * Optional elemental "type" tag for a skill, used for a simple advantage/disadvantage
 * multiplier in battle resolution. Kept intentionally small (3 elements) to limit
 * scope — NEUTRAL skills never get an advantage/disadvantage multiplier.
 */
public enum SkillElement {
    NEUTRAL,
    FIERCE,
    GUARDIAN,
    SWIFT;

    private static final double ADVANTAGE_MULTIPLIER = 1.2;
    private static final double DISADVANTAGE_MULTIPLIER = 0.85;

    /**
     * Simple rock-paper-scissors-style advantage cycle: FIERCE > SWIFT > GUARDIAN > FIERCE.
     * NEUTRAL has no advantage or disadvantage against anything.
     */
    public double advantageMultiplierAgainst(SkillElement defending) {
        if (this == NEUTRAL || defending == NEUTRAL || this == defending) {
            return 1.0;
        }
        boolean hasAdvantage = (this == FIERCE && defending == SWIFT)
            || (this == SWIFT && defending == GUARDIAN)
            || (this == GUARDIAN && defending == FIERCE);
        return hasAdvantage ? ADVANTAGE_MULTIPLIER : DISADVANTAGE_MULTIPLIER;
    }
}

// Built with assistance from Claude Code by Anthropic.
