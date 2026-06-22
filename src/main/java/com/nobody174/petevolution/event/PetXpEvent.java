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

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;
import com.nobody174.petevolution.network.PetSyncPacket;

public class PetXpEvent {

    private static final int XP_PER_KILL = 10;
    private static final int XP_PER_CRAFT = 2;
    private static final float DISTANCE_PER_XP = 50.0f;
    private static final int EXPLORATION_CHECK_INTERVAL_TICKS = 20;

    private final Map<UUID, Float> distanceAccumulator = new HashMap<>();
    private final Map<UUID, Integer> tickCounter = new HashMap<>();

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        if (!(killed.getKillCredit() instanceof ServerPlayer player)) {
            return;
        }
        grantXpToHeldBall(player, XP_PER_KILL);
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        grantXpToHeldBall(player, XP_PER_CRAFT);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        UUID id = player.getUUID();
        int ticks = tickCounter.merge(id, 1, Integer::sum);
        if (ticks < EXPLORATION_CHECK_INTERVAL_TICKS) {
            return;
        }
        tickCounter.put(id, 0);

        float delta = player.walkDist - player.walkDistO;
        float accumulated = distanceAccumulator.merge(id, Math.max(delta, 0f), Float::sum);

        if (accumulated >= DISTANCE_PER_XP) {
            int xpGrants = (int) (accumulated / DISTANCE_PER_XP);
            distanceAccumulator.put(id, accumulated % DISTANCE_PER_XP);
            grantXpToHeldBall(player, xpGrants);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
        UUID id = event.getEntity().getUUID();
        distanceAccumulator.remove(id);
        tickCounter.remove(id);
    }

    private void grantXpToHeldBall(ServerPlayer player, int amount) {
        if (amount <= 0) {
            return;
        }

        ItemStack heldBall = player.getMainHandItem();
        PetData data = heldBall.get(ModDataComponents.PET_DATA.get());
        if (data == null) {
            return;
        }

        PetData updated = data.withXp(amount);
        heldBall.set(ModDataComponents.PET_DATA.get(), updated);

        PacketDistributor.sendToPlayer(player, new PetSyncPacket(player.getId(), updated));
    }
}

// Built with assistance from Claude Code by Anthropic.
