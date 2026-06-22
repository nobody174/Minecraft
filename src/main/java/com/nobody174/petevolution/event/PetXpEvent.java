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

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;
import com.nobody174.petevolution.network.PetSyncPacket;

public class PetXpEvent {

    private static final int XP_PER_KILL = 10;

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        if (!(killed.getKillCredit() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack heldBall = player.getMainHandItem();
        PetData data = heldBall.get(ModDataComponents.PET_DATA.get());
        if (data == null) {
            return;
        }

        PetData updated = data.withXp(XP_PER_KILL);
        heldBall.set(ModDataComponents.PET_DATA.get(), updated);

        PacketDistributor.sendToPlayer(player, new PetSyncPacket(player.getId(), updated));
    }
}

// Built with assistance from Claude Code by Anthropic.
