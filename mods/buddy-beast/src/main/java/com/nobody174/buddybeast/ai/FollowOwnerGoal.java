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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

import java.util.EnumSet;

public class FollowOwnerGoal extends Goal {
    private final BuddyBeastEntity buddy;
    private final double speedMultiplier;
    private final float stopDistance;
    private final float startDistance;
    private final PathNavigation pathNavigation;
    private int lastCanUseCheck;

    public FollowOwnerGoal(BuddyBeastEntity buddy, double speedMultiplier) {
        this.buddy = buddy;
        this.speedMultiplier = speedMultiplier;
        this.pathNavigation = buddy.getNavigation();
        this.stopDistance = 2.0f;
        this.startDistance = 10.0f;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Throttle checks to avoid constant pathfinding
        if (++this.lastCanUseCheck < 10) {
            return false;
        }
        this.lastCanUseCheck = 0;

        if (!this.buddy.isTamed()) {
            return false;
        }

        LivingEntity owner = this.buddy.findOwner();
        if (owner == null) {
            return false;
        }

        double distanceSq = this.buddy.distanceToSqr(owner);
        return distanceSq > (this.startDistance * this.startDistance);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.buddy.isTamed()) {
            return false;
        }

        LivingEntity owner = this.buddy.findOwner();
        if (owner == null) {
            return false;
        }

        double distanceSq = this.buddy.distanceToSqr(owner);
        return distanceSq > (this.stopDistance * this.stopDistance);
    }

    @Override
    public void start() {
        this.pathNavigation.setSpeedModifier(this.speedMultiplier);
    }

    @Override
    public void stop() {
        this.pathNavigation.stop();
    }

    @Override
    public void tick() {
        LivingEntity owner = this.buddy.findOwner();
        if (owner == null) {
            return;
        }

        // Move towards owner
        this.pathNavigation.moveTo(owner, this.speedMultiplier);

        // Look at owner
        double dx = owner.getX() - this.buddy.getX();
        double dy = owner.getEyeY() - this.buddy.getEyeY();
        double dz = owner.getZ() - this.buddy.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance > 0) {
            float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
            float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
            this.buddy.setXRot(pitch);
            this.buddy.setYRot(yaw);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
