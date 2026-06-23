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
import java.util.Random;

public class IdleGoal extends Goal {
    private final BuddyBeastEntity buddy;
    private final Random random;
    private int idleTicks;

    public IdleGoal(BuddyBeastEntity buddy) {
        this.buddy = buddy;
        this.random = new Random();
        this.idleTicks = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        this.idleTicks++;

        if (this.idleTicks > 100) {
            // Randomly wander
            if (this.random.nextFloat() < 0.1f) {
                double x = this.buddy.getX() + (this.random.nextDouble() - 0.5) * 10;
                double y = this.buddy.getY();
                double z = this.buddy.getZ() + (this.random.nextDouble() - 0.5) * 10;

                this.buddy.getNavigation().moveTo(x, y, z, 0.4);
                this.idleTicks = 0;
            }
        }
    }

    @Override
    public void stop() {
        this.buddy.getNavigation().stop();
    }
}

// Built with assistance from Claude Code by Anthropic.
