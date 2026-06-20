//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobody174.bossradar.network.BossRadarSyncPacket;
import java.util.List;

public class BossRadarItem extends Item {
    private static final int SCAN_RANGE = 128;
    private static final int SCAN_INTERVAL = 10;
    private static final Logger LOGGER = LogManager.getLogger();

    public BossRadarItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            long worldTime = level.getGameTime();
            if (worldTime % SCAN_INTERVAL == 0) {
                scanForBosses(level, player);
            }
        }
    }

    private void scanForBosses(Level level, Player player) {
        LOGGER.info("[BossRadar] scanForBosses called for player: {}", player.getName().getString());

        double closestDistance = SCAN_RANGE * SCAN_RANGE;
        LivingEntity closestMob = null;

        List<LivingEntity> entities = level.getEntitiesOfClass(
            LivingEntity.class,
            player.getBoundingBox().inflate(SCAN_RANGE)
        );

        LOGGER.info("[BossRadar] Found {} total entities in range", entities.size());

        for (LivingEntity entity : entities) {
            if (entity.equals(player)) continue;

            EntityType<?> type = entity.getType();
            String entityName = type.toString();
            LOGGER.info("[BossRadar] Checking entity type: {}", entityName);

            boolean isTracked = isBossMob(type);
            LOGGER.info("[BossRadar] Entity {} is tracked: {}", entityName, isTracked);

            if (isTracked) {
                LOGGER.info("[BossRadar] Found tracked mob: {}", entity.getName().getString());
                double distSq = player.distanceToSqr(entity);
                if (distSq < closestDistance) {
                    closestDistance = distSq;
                    closestMob = entity;
                }
            }
        }

        if (closestMob != null) {
            double distance = Math.sqrt(closestDistance);
            LOGGER.info("[BossRadar] Sending packet for boss: {} at distance: {}", closestMob.getName().getString(), distance);
            BossRadarSyncPacket packet = new BossRadarSyncPacket(closestMob.getName().getString(), distance, closestMob.getX(), closestMob.getY(), closestMob.getZ());
            PacketDistributor.sendToPlayer((net.minecraft.server.level.ServerPlayer) player, packet);
        } else {
            LOGGER.info("[BossRadar] No boss found, sending empty packet");
            BossRadarSyncPacket packet = new BossRadarSyncPacket("", 0, 0, 0, 0);
            PacketDistributor.sendToPlayer((net.minecraft.server.level.ServerPlayer) player, packet);
        }
    }

    private boolean isBossMob(EntityType<?> type) {
        return type == EntityType.WITHER ||
               type == EntityType.ENDER_DRAGON ||
               type == EntityType.ELDER_GUARDIAN ||
               type == EntityType.WARDEN ||
               type == EntityType.RAVAGER ||
               type == EntityType.EVOKER;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6Boss Detection Item"));
        tooltip.add(Component.literal("§7Detects nearby boss mobs"));
        tooltip.add(Component.literal("§7Range: 128 blocks"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            player.displayClientMessage(Component.literal("§6Boss Radar activated!"), true);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) {
            context.getPlayer().displayClientMessage(Component.literal("§6Boss Radar activated!"), true);
        }
        return InteractionResult.SUCCESS;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
