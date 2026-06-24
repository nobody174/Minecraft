//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.capture;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.phys.Vec3;

import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.PetBehaviorMode;

/**
 * Applies a released pet's {@link PetBehaviorMode} to its real AI: STAY disables
 * normal AI entirely (so it stops wandering — added after a real two-player test
 * showed players unable to relocate a pet they'd released, since nothing kept it
 * in place); FOLLOW re-enables AI and adds a lightweight goal that paths the pet
 * toward its owner.
 *
 * Battles temporarily override {@code setNoAi} themselves via {@code BattleVisuals}
 * and restore it on conclusion — restoring to vanilla AI rather than back to the
 * pet's actual STAY/FOLLOW mode would silently undo this system, so
 * {@code BattleVisuals.unlockOnEnd} re-applies the stored mode instead of just
 * flipping {@code setNoAi(false)} (see that class).
 */
public final class PetBehaviorController {

    private PetBehaviorController() {
    }

    public static void applyMode(LivingEntity entity, PetBehaviorMode mode) {
        if (!(entity instanceof Mob mob)) {
            return;
        }

        removeFollowGoal(mob);

        switch (mode) {
            case STAY -> mob.setNoAi(true);
            case FOLLOW -> {
                mob.setNoAi(false);
                mob.goalSelector.addGoal(0, new FollowOwnerGoal(mob));
            }
        }
    }

    private static void removeFollowGoal(Mob mob) {
        mob.goalSelector.getAvailableGoals().stream()
            .filter(wrapped -> wrapped.getGoal() instanceof FollowOwnerGoal)
            .toList()
            .forEach(wrapped -> mob.goalSelector.removeGoal(wrapped.getGoal()));
    }

    /** Lightweight follow-the-owner goal, reused from MoveToBlockGoal's targeting pattern but tracking a moving player. */
    private static final class FollowOwnerGoal extends Goal {
        private static final double FOLLOW_RANGE = 12.0;
        private static final double STOP_DISTANCE = 3.0;

        private final Mob mob;

        FollowOwnerGoal(Mob mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            return owner() != null;
        }

        @Override
        public void tick() {
            ServerPlayer owner = owner();
            if (owner == null) {
                return;
            }
            double distSqr = mob.distanceToSqr(owner);
            if (distSqr > STOP_DISTANCE * STOP_DISTANCE && distSqr < FOLLOW_RANGE * FOLLOW_RANGE) {
                mob.getNavigation().moveTo(owner, 1.0);
            } else if (distSqr >= FOLLOW_RANGE * FOLLOW_RANGE) {
                Vec3 ownerPos = owner.position();
                mob.getNavigation().moveTo(ownerPos.x, ownerPos.y, ownerPos.z, 1.2);
            }
        }

        private ServerPlayer owner() {
            var ownerData = mob.getData(ModAttachments.PET_OWNER.get());
            if (ownerData == null || !(mob.level() instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
                return null;
            }
            return serverLevel.getServer().getPlayerList().getPlayer(ownerData.ownerId());
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
