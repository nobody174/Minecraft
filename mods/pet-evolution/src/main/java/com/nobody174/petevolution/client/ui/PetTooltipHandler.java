//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client.ui;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import com.nobody174.petevolution.creature.ModDataComponents;
import com.nobody174.petevolution.creature.PetData;

public class PetTooltipHandler {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        PetData data = event.getItemStack().get(ModDataComponents.PET_DATA.get());
        if (data == null) {
            return;
        }

        event.getToolTip().add(Component.literal(data.rarity().name() + " " + data.speciesId())
            .withStyle(data.rarity().color()));
        event.getToolTip().add(Component.literal(
            "HP: " + data.hp() + " | ATK: " + data.atk() + " | DEF: " + data.def() + " | SPD: " + data.spd() + " | SPECIAL: " + data.special())
            .withStyle(ChatFormatting.GREEN));
        event.getToolTip().add(Component.literal("XP: " + data.xp() + " | Level " + data.level() + " (Stage " + data.evoStage() + ")")
            .withStyle(ChatFormatting.AQUA));
    }
}

// Built with assistance from Claude Code by Anthropic.
