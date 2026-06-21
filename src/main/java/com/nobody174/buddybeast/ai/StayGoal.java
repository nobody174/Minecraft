//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.ai;

import net.minecraft.world.entity.ai.goal.Goal;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

import java.util.EnumSet;

public class StayGoal extends Goal {
    private final BuddyBeastEntity buddy;

    public StayGoal(BuddyBeastEntity buddy) {
        this.buddy = buddy;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.buddy.isTamed();
    }

    @Override
    public void start() {
        this.buddy.getNavigation().stop();
    }

    @Override
    public void tick() {
        // Stay in place
    }
}

// Built with assistance from Claude Code by Anthropic.
