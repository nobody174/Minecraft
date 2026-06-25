//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.server;

import com.nobody174.armoraura.ArmorAuraMod;
import com.nobody174.armoraura.networking.AuraStatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorDetectionManager {

    // Only re-broadcast when a player's aura state actually changes, instead of every tick.
    private final Map<Integer, AuraState> lastKnownState = new HashMap<>();

    private record AuraState(boolean hasAura, int color, float intensity) {
    }

    public ArmorDetectionManager() {
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        lastKnownState.remove(event.getEntity().getId());
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (event.getServer() == null) {
            return;
        }

        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        for (ServerPlayer player : players) {
            detectAura(player, players);
        }
    }

    private void detectAura(ServerPlayer player, List<ServerPlayer> allPlayers) {
        boolean hasAura = false;
        int auraColor = 0xFFFFFF;
        float intensity = 0.5f;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            ItemStack armor = player.getItemBySlot(slot);
            if (armor.isEmpty()) continue;

            // Detect any non-empty armor piece as aura
            hasAura = true;
            // TODO: Determine color and intensity from enchantments or tags
            break;
        }

        AuraState newState = new AuraState(hasAura, auraColor, intensity);
        AuraState oldState = lastKnownState.get(player.getId());
        if (newState.equals(oldState)) {
            return; // No change since last tick — skip the broadcast entirely
        }
        lastKnownState.put(player.getId(), newState);

        ArmorAuraMod.LOGGER.debug("[ArmorDetectionManager] Player {} aura changed: hasAura={}, color=0x{}, intensity={}",
            player.getName().getString(), hasAura, Integer.toHexString(auraColor), intensity);

        AuraStatePacket packet = new AuraStatePacket(player.getId(), hasAura, auraColor, intensity);
        for (ServerPlayer serverPlayer : allPlayers) {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
