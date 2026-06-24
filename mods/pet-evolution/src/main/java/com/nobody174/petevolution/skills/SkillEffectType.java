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
 * Broad effect category for a skill, used by the auto-combat AI to decide
 * when a skill is appropriate (e.g. HEAL/DEFENSE skills are preferred at low HP).
 */
public enum SkillEffectType {
    PHYSICAL_DAMAGE,
    SPECIAL_DAMAGE,
    HEAL,
    DEFENSE_BUFF
}

// Built with assistance from Claude Code by Anthropic.
