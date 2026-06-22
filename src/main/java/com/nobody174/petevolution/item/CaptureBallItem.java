//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.item;

import net.minecraft.core.BlockPos;
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

import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;

public class CaptureBallItem extends Item {

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

        ResourceLocation speciesId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        PetData captured = PetData.baseStatsFor(speciesId.toString());
        stack.set(ModDataComponents.PET_DATA.get(), captured);

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
        serverLevel.addFreshEntity(released);

        stack.remove(ModDataComponents.PET_DATA.get());

        return InteractionResult.CONSUME;
    }
}

// Built with assistance from Claude Code by Anthropic.
