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
 * Data model for a single skill. Skills are registered in-code via {@link SkillRegistry}
 * rather than as Codec-backed JSON datapack entries — see CHANGELOG.md "v2.0 Skills System"
 * entry for the reasoning (datapack dynamic-registry wiring was judged higher-risk than
 * warranted for a fixed, designer-authored skill list in this autonomous run).
 *
 * @param id           unique skill identifier, e.g. "ember_strike"
 * @param displayName  human-readable name shown in the battle UI
 * @param effectType   broad effect category, drives auto-combat AI decisions
 * @param element      elemental tag for the advantage/disadvantage multiplier
 * @param power        base power; scaled against ATK (physical) or SPECIAL (special) stat
 * @param cooldownTicks ticks before this skill can be used again by the same creature
 */
public record Skill(String id, String displayName, SkillEffectType effectType, SkillElement element, int power, int cooldownTicks) {

    public boolean isOffensive() {
        return effectType == SkillEffectType.PHYSICAL_DAMAGE || effectType == SkillEffectType.SPECIAL_DAMAGE;
    }

    public boolean isDefensive() {
        return effectType == SkillEffectType.HEAL || effectType == SkillEffectType.DEFENSE_BUFF;
    }
}

// Built with assistance from Claude Code by Anthropic.
