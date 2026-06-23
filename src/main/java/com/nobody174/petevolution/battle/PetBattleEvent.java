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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.ModDataComponents;
import com.nobody174.petevolution.creature.PetData;
import com.nobody174.petevolution.creature.PetOwnerData;
import com.nobody174.petevolution.capture.ModItems;

/**
 * Battle-trigger interaction handler. v2.0: starts a tick-driven {@link BattleSession}
 * via {@link BattleEngine} instead of resolving instantly via stat-power comparison.
 * The right-click trigger, nearby-owned-pet lookup, and 30-second per-pet anti-farm
 * cooldown are all preserved unchanged from v0.2.0.
 */
public class PetBattleEvent {

    private static final int BATTLE_COOLDOWN_TICKS = 20 * 30;

    private final Map<UUID, Long> lastBattleTick = new HashMap<>();

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer challenger)) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!(event.getTarget() instanceof LivingEntity targetPet)) {
            return;
        }

        ItemStack heldStack = challenger.getMainHandItem();
        if (!heldStack.is(ModItems.CAPTURE_BALL.get()) || heldStack.has(ModDataComponents.PET_DATA.get())) {
            return;
        }

        PetOwnerData targetOwnerData = targetPet.getData(ModAttachments.PET_OWNER.get());
        PetData targetPetData = targetPet.getData(ModAttachments.RELEASED_PET_DATA.get());
        if (targetOwnerData == null || targetPetData == null) {
            return;
        }
        if (targetOwnerData.ownerId().equals(challenger.getUUID())) {
            return;
        }

        if (isOnCooldown(targetPet, serverLevel.getGameTime()) || BattleEngine.hasActiveSession(targetPet.getUUID())) {
            challenger.displayClientMessage(
                Component.literal("That pet is still recovering from its last battle.").withStyle(ChatFormatting.RED), true);
            return;
        }

        LivingEntity challengerPet = findNearbyOwnedPet(serverLevel, challenger.getUUID(), targetPet);
        if (challengerPet == null) {
            challenger.displayClientMessage(
                Component.literal("You need your own released pet nearby to battle.").withStyle(ChatFormatting.RED), true);
            return;
        }

        PetData challengerPetData = challengerPet.getData(ModAttachments.RELEASED_PET_DATA.get());
        if (challengerPetData == null) {
            return;
        }

        if (isOnCooldown(challengerPet, serverLevel.getGameTime()) || BattleEngine.hasActiveSession(challengerPet.getUUID())) {
            challenger.displayClientMessage(
                Component.literal("Your pet is still recovering from its last battle.").withStyle(ChatFormatting.RED), true);
            return;
        }

        BattleParticipant challengerParticipant = new BattleParticipant(challengerPet, challenger.getUUID(), challengerPetData);
        BattleParticipant defenderParticipant = new BattleParticipant(targetPet, targetOwnerData.ownerId(), targetPetData);

        BattleSession session = new BattleSession(serverLevel.getServer(), challenger, challengerParticipant, defenderParticipant);
        BattleEngine.startSession(session);

        challenger.displayClientMessage(
            Component.literal("Battle started! Watch the battle HUD.").withStyle(ChatFormatting.YELLOW), true);

        long now = serverLevel.getGameTime();
        lastBattleTick.put(challengerPet.getUUID(), now);
        lastBattleTick.put(targetPet.getUUID(), now);

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.CONSUME);
    }

    private boolean isOnCooldown(LivingEntity pet, long currentTick) {
        Long last = lastBattleTick.get(pet.getUUID());
        return last != null && currentTick - last < BATTLE_COOLDOWN_TICKS;
    }

    private LivingEntity findNearbyOwnedPet(ServerLevel level, UUID ownerId, LivingEntity near) {
        return level.getEntitiesOfClass(LivingEntity.class, near.getBoundingBox().inflate(8.0)).stream()
            .filter(entity -> entity != near)
            .filter(entity -> {
                PetOwnerData ownerData = entity.getData(ModAttachments.PET_OWNER.get());
                return ownerData != null && ownerData.ownerId().equals(ownerId);
            })
            .findFirst()
            .orElse(null);
    }
}

// Built with assistance from Claude Code by Anthropic.
