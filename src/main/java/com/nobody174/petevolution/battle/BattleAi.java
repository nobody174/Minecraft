//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import java.util.Comparator;
import java.util.List;

import com.nobody174.petevolution.skills.Skill;
import com.nobody174.petevolution.skills.SkillRegistry;

/**
 * Auto-combat decision logic: chooses a skill for a participant with no player
 * override input. HP-based behavior change per the design requirement —
 * below {@link #LOW_HP_THRESHOLD} fraction of max HP, prefer a ready defensive/
 * healing skill; otherwise (or if none is ready) use the highest-power ready
 * offensive skill; if nothing is ready at all, fall back to the registry's
 * always-available "tackle" (0 cooldown) so a turn is never wasted.
 */
public final class BattleAi {

    private static final double LOW_HP_THRESHOLD = 0.35;

    private BattleAi() {
    }

    public static Skill chooseSkill(BattleParticipant self) {
        List<Skill> unlocked = self.unlockedSkills();
        boolean lowHp = self.currentHp() <= self.maxHp() * LOW_HP_THRESHOLD;

        if (lowHp) {
            Skill defensive = bestReady(self, unlocked, Skill::isDefensive);
            if (defensive != null) {
                return defensive;
            }
            if (self.isSkillReady(SkillRegistry.RECOVER)) {
                return SkillRegistry.RECOVER;
            }
        }

        Skill offensive = bestReady(self, unlocked, Skill::isOffensive);
        if (offensive != null) {
            return offensive;
        }

        // Nothing offensive ready — try any ready skill at all (including defensive while healthy).
        Skill anyReady = bestReady(self, unlocked, skill -> true);
        if (anyReady != null) {
            return anyReady;
        }

        // Absolute fallback: the base "tackle" skill always has a 0-tick cooldown.
        return unlocked.isEmpty() ? SkillRegistry.allSkills().get(0) : unlocked.get(0);
    }

    private static Skill bestReady(BattleParticipant self, List<Skill> candidates, java.util.function.Predicate<Skill> filter) {
        return candidates.stream()
            .filter(filter)
            .filter(self::isSkillReady)
            .max(Comparator.comparingInt(Skill::power))
            .orElse(null);
    }
}

// Built with assistance from Claude Code by Anthropic.
