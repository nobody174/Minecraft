//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.event;

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

import com.nobody174.petevolution.component.ModAttachments;
import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;
import com.nobody174.petevolution.component.PetOwnerData;
import com.nobody174.petevolution.component.PetStatApplier;
import com.nobody174.petevolution.item.ModItems;

public class PetBattleEvent {

    private static final int XP_REWARD_FOR_WIN = 25;
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

        if (isOnCooldown(targetPet, serverLevel.getGameTime())) {
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

        if (isOnCooldown(challengerPet, serverLevel.getGameTime())) {
            challenger.displayClientMessage(
                Component.literal("Your pet is still recovering from its last battle.").withStyle(ChatFormatting.RED), true);
            return;
        }

        resolveBattle(challenger, challengerPet, challengerPetData, targetPet, targetPetData, targetOwnerData.ownerId());

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

    private void resolveBattle(ServerPlayer challenger, LivingEntity challengerPet, PetData challengerData,
                                LivingEntity targetPet, PetData targetData, UUID defenderId) {
        int challengerPower = battlePower(challengerData);
        int defenderPower = battlePower(targetData);

        boolean challengerWins = challengerPower >= defenderPower;

        LivingEntity winnerPet = challengerWins ? challengerPet : targetPet;
        PetData winnerData = challengerWins ? challengerData : targetData;
        PetData updatedWinnerData = winnerData.withXp(XP_REWARD_FOR_WIN);
        winnerPet.setData(ModAttachments.RELEASED_PET_DATA.get(), updatedWinnerData);
        PetStatApplier.apply(winnerPet, updatedWinnerData);

        challenger.displayClientMessage(
            Component.literal(challengerWins ? "Your pet won the battle!" : "Your pet lost the battle.")
                .withStyle(challengerWins ? ChatFormatting.GREEN : ChatFormatting.RED),
            true);

        if (challenger.server.getPlayerList().getPlayer(defenderId) instanceof ServerPlayer defender) {
            defender.displayClientMessage(
                Component.literal(challengerWins ? "Your pet lost the battle." : "Your pet won the battle!")
                    .withStyle(challengerWins ? ChatFormatting.RED : ChatFormatting.GREEN),
                true);
        }
    }

    private int battlePower(PetData data) {
        return data.hp() + data.atk() * 2 + data.def() + data.spd();
    }
}

// Built with assistance from Claude Code by Anthropic.
