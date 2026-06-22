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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ArmorDetectionManager {

    public ArmorDetectionManager() {
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (event.getServer() == null) {
            ArmorAuraMod.LOGGER.debug("[ArmorDetectionManager] Server is null, skipping tick");
            return;
        }

        java.util.List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        ArmorAuraMod.LOGGER.debug("[ArmorDetectionManager] ServerTick - checking {} players", players.size());

        players.forEach(player -> {
            detectAura(player, players);
        });
    }

    private void detectAura(Player player, java.util.List<ServerPlayer> allPlayers) {
        boolean hasAura = false;
        int auraColor = 0xFFFFFF;
        float intensity = 0.5f;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            ItemStack armor = player.getItemBySlot(slot);
            if (armor.isEmpty()) continue;

            // Detect any non-empty armor piece as aura
            hasAura = true;
            ArmorAuraMod.LOGGER.debug("[ArmorDetectionManager] Player {} has armor in slot {}",
                player.getName().getString(), slot.getName());
            // TODO: Determine color and intensity from enchantments or tags
            break;
        }

        ArmorAuraMod.LOGGER.info("[ArmorDetectionManager] Player {} detection: hasAura={}, color=0x{}, intensity={}",
            player.getName().getString(), hasAura, Integer.toHexString(auraColor), intensity);

        AuraStatePacket packet = new AuraStatePacket(
            player.getId(),
            hasAura,
            auraColor,
            intensity
        );

        // Send packet to all connected players
        ArmorAuraMod.LOGGER.debug("[ArmorDetectionManager] Sending aura packet to {} players", allPlayers.size());
        for (ServerPlayer serverPlayer : allPlayers) {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
