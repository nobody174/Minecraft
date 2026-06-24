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

/**
 * Released-pet AI mode, toggled by left-clicking your own released pet while
 * holding a vessel: STAY -&gt; FOLLOW -&gt; STAY again. A separate explicit action
 * (the 3rd left-click per the design) abandons ownership entirely rather than
 * cycling back into this enum — see {@code PetBehaviorEvent} for that handling.
 *
 * Released pets default to STAY rather than vanilla wandering AI — a real
 * two-player test showed pets walking off mid-battle and players unable to
 * find their pet again after releasing it, since nothing kept it in place.
 */
public enum PetBehaviorMode {
    STAY,
    FOLLOW;

    public PetBehaviorMode next() {
        return this == STAY ? FOLLOW : STAY;
    }
}

// Built with assistance from Claude Code by Anthropic.
