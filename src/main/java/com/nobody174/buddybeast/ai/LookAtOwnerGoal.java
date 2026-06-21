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

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

import java.util.EnumSet;

public class LookAtOwnerGoal extends Goal {
    private final BuddyBeastEntity buddy;
    private LivingEntity owner;
    private int lookTime;

    public LookAtOwnerGoal(BuddyBeastEntity buddy) {
        this.buddy = buddy;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.buddy.isTamed()) {
            return false;
        }

        this.owner = this.buddy.findOwner();
        return this.owner != null && this.owner.distanceToSqr(this.buddy) < 128.0; // 8 block range
    }

    @Override
    public boolean canContinueToUse() {
        return this.owner != null && this.owner.isAlive() && this.buddy.distanceToSqr(this.owner) < 128.0;
    }

    @Override
    public void tick() {
        if (this.owner != null) {
            // Look at owner with idle animation
            double dx = this.owner.getX() - this.buddy.getX();
            double dy = this.owner.getEyeY() - this.buddy.getEyeY();
            double dz = this.owner.getZ() - this.buddy.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance > 0) {
                float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
                float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
                this.buddy.setXRot(pitch * 0.5f); // Subtle head tilt
                this.buddy.setYRot(yaw);
            }

            this.lookTime++;
        }
    }

    @Override
    public void stop() {
        this.owner = null;
        this.lookTime = 0;
    }
}

// Built with assistance from Claude Code by Anthropic.
