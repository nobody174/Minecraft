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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import com.nobody174.petevolution.component.EvolutionRules;
import com.nobody174.petevolution.component.ModAttachments;
import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;
import com.nobody174.petevolution.component.PetOwnerData;
import com.nobody174.petevolution.component.PetRarity;
import com.nobody174.petevolution.component.PetStatApplier;
import com.nobody174.petevolution.item.ModItems;

public class PetBreedingEvent {

    private static final int BREEDING_COOLDOWN_TICKS = 20 * 60 * 5;
    private static final double NEARBY_RADIUS = 6.0;
    private static final double RARITY_UPGRADE_CHANCE = 0.05;

    private final Map<UUID, Long> lastBreedTick = new HashMap<>();

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!(event.getTarget() instanceof LivingEntity petB)) {
            return;
        }

        ItemStack heldStack = player.getMainHandItem();
        if (!heldStack.is(ModItems.CAPTURE_BALL.get()) || heldStack.has(ModDataComponents.PET_DATA.get())) {
            return;
        }

        PetOwnerData ownerBData = petB.getData(ModAttachments.PET_OWNER.get());
        PetData dataB = petB.getData(ModAttachments.RELEASED_PET_DATA.get());
        if (ownerBData == null || dataB == null) {
            return;
        }
        if (!ownerBData.ownerId().equals(player.getUUID())) {
            return;
        }

        LivingEntity petA = findOtherOwnedPetOfSameSpecies(serverLevel, player.getUUID(), petB, dataB.speciesId());
        if (petA == null) {
            return;
        }

        PetData dataA = petA.getData(ModAttachments.RELEASED_PET_DATA.get());
        if (dataA == null) {
            return;
        }

        if (dataA.evoStage() < EvolutionRules.MAX_STAGE || dataB.evoStage() < EvolutionRules.MAX_STAGE) {
            player.displayClientMessage(
                Component.literal("Both pets must be fully evolved to breed.").withStyle(ChatFormatting.RED), true);
            return;
        }

        long now = serverLevel.getGameTime();
        if (isOnCooldown(petA, now) || isOnCooldown(petB, now)) {
            player.displayClientMessage(
                Component.literal("One of these pets isn't ready to breed again yet.").withStyle(ChatFormatting.RED), true);
            return;
        }

        Entity offspring = breed(serverLevel, player, petA, dataA, petB, dataB);
        if (offspring == null) {
            return;
        }

        lastBreedTick.put(petA.getUUID(), now);
        lastBreedTick.put(petB.getUUID(), now);

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.CONSUME);
    }

    private boolean isOnCooldown(LivingEntity pet, long currentTick) {
        Long last = lastBreedTick.get(pet.getUUID());
        return last != null && currentTick - last < BREEDING_COOLDOWN_TICKS;
    }

    private LivingEntity findOtherOwnedPetOfSameSpecies(ServerLevel level, UUID ownerId, LivingEntity near, String speciesId) {
        return level.getEntitiesOfClass(LivingEntity.class, near.getBoundingBox().inflate(NEARBY_RADIUS)).stream()
            .filter(entity -> entity != near)
            .filter(entity -> {
                PetOwnerData ownerData = entity.getData(ModAttachments.PET_OWNER.get());
                PetData petData = entity.getData(ModAttachments.RELEASED_PET_DATA.get());
                return ownerData != null && petData != null
                    && ownerData.ownerId().equals(ownerId)
                    && petData.speciesId().equals(speciesId);
            })
            .findFirst()
            .orElse(null);
    }

    private Entity breed(ServerLevel level, ServerPlayer player, LivingEntity petA, PetData dataA, LivingEntity petB, PetData dataB) {
        ResourceLocation speciesId = ResourceLocation.parse(dataA.speciesId());
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(speciesId);
        if (type == null) {
            return null;
        }

        Entity offspring = type.create(level);
        if (offspring == null) {
            return null;
        }

        double midX = (petA.getX() + petB.getX()) / 2.0;
        double midY = (petA.getY() + petB.getY()) / 2.0;
        double midZ = (petA.getZ() + petB.getZ()) / 2.0;
        offspring.moveTo(midX, midY, midZ, petA.getYRot(), 0f);

        PetRarity offspringRarity = rollOffspringRarity(level, dataA.rarity(), dataB.rarity());
        double rarityUpgradeFactor = offspringRarity.statMultiplier()
            / Math.max(dataA.rarity().statMultiplier(), dataB.rarity().statMultiplier());

        PetData offspringData = new PetData(
            averageStat(dataA.hp(), dataB.hp(), rarityUpgradeFactor),
            averageStat(dataA.atk(), dataB.atk(), rarityUpgradeFactor),
            averageStat(dataA.def(), dataB.def(), rarityUpgradeFactor),
            averageStat(dataA.spd(), dataB.spd(), rarityUpgradeFactor),
            0, 0, dataA.speciesId(), offspringRarity);

        if (offspring instanceof LivingEntity livingOffspring) {
            PetStatApplier.apply(livingOffspring, offspringData);
            offspring.setData(ModAttachments.RELEASED_PET_DATA.get(), offspringData);
            offspring.setData(ModAttachments.PET_OWNER.get(), new PetOwnerData(player.getUUID()));
        }

        level.addFreshEntity(offspring);

        player.displayClientMessage(
            Component.literal("Your pets bred! A new " + offspringRarity.name() + " " + dataA.speciesId() + " was born.")
                .withStyle(offspringRarity.color()),
            true);

        return offspring;
    }

    private PetRarity rollOffspringRarity(ServerLevel level, PetRarity rarityA, PetRarity rarityB) {
        PetRarity better = rarityA.ordinal() >= rarityB.ordinal() ? rarityA : rarityB;
        if (better.ordinal() < PetRarity.values().length - 1 && level.getRandom().nextDouble() < RARITY_UPGRADE_CHANCE) {
            return PetRarity.values()[better.ordinal() + 1];
        }
        return better;
    }

    private int averageStat(int statA, int statB, double rarityUpgradeFactor) {
        double base = (statA + statB) / 2.0;
        return (int) Math.round(base * rarityUpgradeFactor);
    }
}

// Built with assistance from Claude Code by Anthropic.
