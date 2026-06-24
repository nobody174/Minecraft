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

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import com.nobody174.petevolution.creature.ModAttachments;
import com.nobody174.petevolution.creature.ModDataComponents;
import com.nobody174.petevolution.creature.PetData;
import com.nobody174.petevolution.creature.PetOwnerData;
import com.nobody174.petevolution.creature.PetStatApplier;
import com.nobody174.petevolution.creature.SpeciesStats;

public class CaptureBallItem extends Item {

    /**
     * Debounces {@link #useOn} per player to one release per server tick — found
     * during real play-testing that releasing while holding filled vessels in two
     * different slots (e.g. an active hotbar slot and the off-hand/shield slot)
     * released BOTH pets from a single right-click. Vanilla block interaction is
     * only supposed to dispatch {@code useOn} for the main hand per click, so this
     * is a defensive guard against whatever duplicate dispatch was actually
     * happening rather than a fix to a specific confirmed root cause.
     */
    private static final java.util.Map<java.util.UUID, Long> LAST_RELEASE_TICK = new java.util.HashMap<>();

    public CaptureBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (stack.has(ModDataComponents.PET_DATA.get())) {
            return InteractionResult.FAIL;
        }

        if (!(target instanceof Mob) || target instanceof Player) {
            return InteractionResult.FAIL;
        }

        if (target.isVehicle() || (target instanceof Mob mob && mob.isLeashed())) {
            return InteractionResult.FAIL;
        }

        PetOwnerData ownerData = target.getData(ModAttachments.PET_OWNER.get());
        if (ownerData != null && !ownerData.ownerId().equals(player.getUUID())) {
            return InteractionResult.FAIL;
        }

        PetData existingProgress = target.getData(ModAttachments.RELEASED_PET_DATA.get());
        PetData captured;
        if (existingProgress != null) {
            captured = existingProgress;
        } else {
            ResourceLocation speciesId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
            captured = SpeciesStats.baseStatsFor((Mob) target, speciesId.toString());
        }
        stack.set(ModDataComponents.PET_DATA.get(), captured);
        captured.syncCustomModelData(stack);

        target.discard();

        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if (context.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        PetData data = stack.get(ModDataComponents.PET_DATA.get());
        if (data == null) {
            return InteractionResult.PASS;
        }

        if (!(context.getLevel() instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }

        if (context.getPlayer() != null) {
            long now = serverLevel.getGameTime();
            java.util.UUID playerId = context.getPlayer().getUUID();
            Long last = LAST_RELEASE_TICK.get(playerId);
            if (last != null && last == now) {
                return InteractionResult.FAIL;
            }
            LAST_RELEASE_TICK.put(playerId, now);
        }

        ResourceLocation speciesId = ResourceLocation.parse(data.speciesId());
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(speciesId);
        if (type == null) {
            return InteractionResult.FAIL;
        }

        BlockPos releasePos = context.getClickedPos().relative(context.getClickedFace());
        Entity released = type.create(serverLevel);
        if (released == null) {
            return InteractionResult.FAIL;
        }

        released.moveTo(releasePos.getX() + 0.5, releasePos.getY(), releasePos.getZ() + 0.5,
            context.getPlayer() != null ? context.getPlayer().getYRot() : 0f, 0f);

        if (released instanceof LivingEntity livingReleased) {
            PetStatApplier.apply(livingReleased, data);
            released.setData(ModAttachments.RELEASED_PET_DATA.get(), data);
            if (context.getPlayer() != null) {
                released.setData(ModAttachments.PET_OWNER.get(), new PetOwnerData(context.getPlayer().getUUID()));
                String speciesName = data.speciesId().contains(":") ? data.speciesId().substring(data.speciesId().indexOf(':') + 1) : data.speciesId();
                released.setCustomName(net.minecraft.network.chat.Component.literal(context.getPlayer().getGameProfile().getName() + "'s " + speciesName));
                released.setCustomNameVisible(true);
            }
            PetBehaviorController.applyMode(livingReleased, livingReleased.getData(ModAttachments.PET_BEHAVIOR_MODE.get()));
        }

        serverLevel.addFreshEntity(released);

        stack.remove(ModDataComponents.PET_DATA.get());
        stack.remove(DataComponents.CUSTOM_MODEL_DATA);

        return InteractionResult.CONSUME;
    }
}

// Built with assistance from Claude Code by Anthropic.
