//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.capture;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.PetBehaviorMode;
import com.nobody174.petevolution.creature.PetOwnerData;

/**
 * Handles left-clicking (attacking) a released, owned pet: for the owner holding
 * a vessel, this cycles its AI mode — STAY -&gt; FOLLOW -&gt; STAY -&gt; (3rd click)
 * abandon ownership entirely — instead of dealing damage; for anyone else
 * (including the owner without a vessel in hand), the attack is simply cancelled.
 * Added after a real test showed any player could attack and (separately)
 * recapture another player's released pet — owned pets are now both
 * un-attackable by non-owners and immune to the owner's own left-click damage
 * so the toggle gesture is safe to use repeatedly.
 *
 * Abandoning ownership leaves the pet existing as a wild mob, free for any
 * player to recapture, with its name tag and owner attachment cleared.
 * Recapture itself remains the existing, separate right-click action.
 */
public class PetBehaviorEvent {

    private static final int ABANDON_AT_CLICK = 3;

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!(event.getTarget() instanceof LivingEntity target)) {
            return;
        }

        PetOwnerData ownerData = target.getData(ModAttachments.PET_OWNER.get());
        if (ownerData == null) {
            return;
        }

        // Always cancel damage against an owned pet — neither the owner nor anyone else
        // should be able to hurt it by attacking, regardless of what's in hand.
        event.setCanceled(true);

        if (!ownerData.ownerId().equals(player.getUUID())) {
            return;
        }

        ItemStack heldStack = player.getMainHandItem();
        if (!heldStack.is(ModItems.CAPTURE_BALL.get())) {
            return;
        }

        int clicks = target.getData(ModAttachments.PET_TOGGLE_COUNT.get()) + 1;

        if (clicks >= ABANDON_AT_CLICK) {
            abandonOwnership(target);
            player.displayClientMessage(
                Component.literal("Released from your ownership — anyone can recapture it now.").withStyle(ChatFormatting.YELLOW), true);
            return;
        }

        target.setData(ModAttachments.PET_TOGGLE_COUNT.get(), clicks);
        PetBehaviorMode next = clicks % 2 == 1 ? PetBehaviorMode.FOLLOW : PetBehaviorMode.STAY;
        target.setData(ModAttachments.PET_BEHAVIOR_MODE.get(), next);
        PetBehaviorController.applyMode(target, next);
        player.displayClientMessage(
            Component.literal("Pet mode: " + next.name()).withStyle(ChatFormatting.AQUA), true);
    }

    private void abandonOwnership(LivingEntity target) {
        target.removeData(ModAttachments.PET_OWNER);
        target.removeData(ModAttachments.PET_BEHAVIOR_MODE);
        target.removeData(ModAttachments.PET_TOGGLE_COUNT);
        target.setCustomName(null);
        target.setCustomNameVisible(false);
        // Restore full vanilla AI — it's a free wild mob now, not STAY-locked, which
        // would have left it frozen in place forever (a real bug found in testing:
        // setNoAi(true) was applied here, so an "abandoned" pet never actually moved).
        if (target instanceof net.minecraft.world.entity.Mob mob) {
            mob.setNoAi(false);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
