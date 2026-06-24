//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.testing;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import com.nobody174.petevolution.creature.EvolutionRules;
import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.PetData;
import com.nobody174.petevolution.creature.PetOwnerData;
import com.nobody174.petevolution.creature.PetRarity;
import com.nobody174.petevolution.creature.PetStatApplier;

/**
 * Registers {@code /petevolution test} — spawns a fully-evolved, fully-statted,
 * fully-skilled test pet owned by the invoking player, right at their feet, so
 * battle/skill/breeding behavior can be manually verified without first capturing
 * a real mob. Server-side only (per the multiplayer-safety convention followed
 * throughout this codebase).
 */
public final class TestCommand {

    private static final String TEST_SPECIES = "minecraft:wolf";
    private static final int TEST_HP = 40;
    private static final int TEST_ATK = 14;
    private static final int TEST_DEF = 10;
    private static final int TEST_SPD = 9;
    private static final int TEST_SPECIAL = 12;

    private static final double NEAREST_OWNED_PET_RADIUS = 16.0;

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("petevolution")
                .then(Commands.literal("test")
                    .requires(source -> source.hasPermission(2))
                    .executes(this::spawnTestPet))
                .then(Commands.literal("xp")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes(this::grantXpToNearestOwnedPet)))
        );
    }

    /**
     * Grants XP directly to the nearest released pet owned by the command issuer,
     * for quickly testing evolution/skill-unlock thresholds without needing to
     * grind real kills/mining/crafting. OP-only ({@code hasPermission(2)}, same
     * gate as {@code /petevolution test}).
     */
    private int grantXpToNearestOwnedPet(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command must be run by a player."));
            return 0;
        }

        LivingEntity nearestPet = player.level().getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(NEAREST_OWNED_PET_RADIUS))
            .stream()
            .filter(entity -> {
                PetOwnerData ownerData = entity.getData(ModAttachments.PET_OWNER.get());
                return ownerData != null && ownerData.ownerId().equals(player.getUUID());
            })
            .min(java.util.Comparator.comparingDouble(player::distanceToSqr))
            .orElse(null);

        if (nearestPet == null) {
            source.sendFailure(Component.literal("No owned released pet found within " + (int) NEAREST_OWNED_PET_RADIUS + " blocks."));
            return 0;
        }

        PetData current = nearestPet.getData(ModAttachments.RELEASED_PET_DATA.get());
        if (current == null) {
            source.sendFailure(Component.literal("Nearest owned entity has no PetData attached."));
            return 0;
        }

        PetData updated = current.withXp(amount);
        nearestPet.setData(ModAttachments.RELEASED_PET_DATA.get(), updated);
        PetStatApplier.apply(nearestPet, updated);

        source.sendSuccess(() -> Component.literal(
            "Granted " + amount + " XP to nearest owned pet — now XP " + updated.xp() + ", stage " + updated.evoStage()
                + (updated.evoStage() > current.evoStage() ? " (evolved!)" : "")
        ).withStyle(ChatFormatting.GREEN), false);

        return 1;
    }

    private int spawnTestPet(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!(source.getEntity() instanceof Player player) || !(source.getLevel() instanceof ServerLevel level)) {
            source.sendFailure(Component.literal("This command must be run by a player."));
            return 0;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return 0;
        }

        ResourceLocation speciesId = ResourceLocation.parse(TEST_SPECIES);
        EntityType<?> type = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.get(speciesId);
        Entity spawned = type.create(level);
        if (spawned == null) {
            source.sendFailure(Component.literal("Failed to create test creature."));
            return 0;
        }

        spawned.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), 0f);

        PetData testData = new PetData(TEST_HP, TEST_ATK, TEST_DEF, TEST_SPD, TEST_SPECIAL,
            0, EvolutionRules.MAX_STAGE, TEST_SPECIES, PetRarity.EPIC);

        if (spawned instanceof LivingEntity livingSpawned) {
            PetStatApplier.apply(livingSpawned, testData);
            livingSpawned.setData(ModAttachments.RELEASED_PET_DATA.get(), testData);
            livingSpawned.setData(ModAttachments.PET_OWNER.get(), new PetOwnerData(serverPlayer.getUUID()));
        }

        level.addFreshEntity(spawned);

        source.sendSuccess(() -> Component.literal(
            "Spawned a fully-evolved EPIC test pet (HP " + TEST_HP + " ATK " + TEST_ATK + " DEF " + TEST_DEF
                + " SPD " + TEST_SPD + " SPECIAL " + TEST_SPECIAL + ", level " + testData.level()
                + ", " + testData.level() + "-stage skill unlock) at your position.")
            .withStyle(ChatFormatting.GREEN), false);

        return 1;
    }
}

// Built with assistance from Claude Code by Anthropic.
