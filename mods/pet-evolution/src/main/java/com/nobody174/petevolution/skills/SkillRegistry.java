//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.skills;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Fixed, in-code registry of all skills. Every species shares this same skill list
 * (creatures unlock a prefix of it progressively by level, see {@link #skillsForLevel}),
 * which keeps the v2.0 skills system simple while still being genuinely data-driven —
 * the {@link Skill} records themselves are plain data, just not loaded from JSON.
 */
public final class SkillRegistry {

    /** Ordered 0-4: slot order matches EvolutionRules.SKILL_SLOT_UNLOCK_LEVEL. */
    private static final List<Skill> SKILLS = List.of(
        new Skill("tackle", "Tackle", SkillEffectType.PHYSICAL_DAMAGE, SkillElement.NEUTRAL, 6, 0),
        new Skill("focus_bolt", "Focus Bolt", SkillEffectType.SPECIAL_DAMAGE, SkillElement.NEUTRAL, 6, 20 * 3),
        new Skill("guarded_stance", "Guarded Stance", SkillEffectType.DEFENSE_BUFF, SkillElement.GUARDIAN, 4, 20 * 8),
        new Skill("rapid_strike", "Rapid Strike", SkillEffectType.PHYSICAL_DAMAGE, SkillElement.SWIFT, 10, 20 * 6),
        new Skill("ember_surge", "Ember Surge", SkillEffectType.SPECIAL_DAMAGE, SkillElement.FIERCE, 12, 20 * 10)
    );

    private static final Map<String, Skill> BY_ID = new LinkedHashMap<>();

    static {
        for (Skill skill : SKILLS) {
            BY_ID.put(skill.id(), skill);
        }
    }

    /** A simple universal healing skill, used as the auto-combat low-HP fallback for all creatures. */
    public static final Skill RECOVER = new Skill("recover", "Recover", SkillEffectType.HEAL, SkillElement.NEUTRAL, 8, 20 * 12);

    private SkillRegistry() {
    }

    public static Skill byId(String id) {
        return BY_ID.get(id);
    }

    public static List<Skill> allSkills() {
        return SKILLS;
    }

    /** The ordered list of skill slots unlocked for a creature at the given level (1+). */
    public static List<Skill> skillsForLevel(int level) {
        int count = com.nobody174.petevolution.creature.EvolutionRules.unlockedSkillCount(level);
        return SKILLS.subList(0, Math.min(count, SKILLS.size()));
    }
}

// Built with assistance from Claude Code by Anthropic.
