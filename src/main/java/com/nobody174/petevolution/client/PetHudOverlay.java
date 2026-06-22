//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import com.nobody174.petevolution.component.EvolutionRules;
import com.nobody174.petevolution.component.ModDataComponents;
import com.nobody174.petevolution.component.PetData;

public class PetHudOverlay {

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ItemStack heldBall = player.getMainHandItem();
        PetData data = heldBall.get(ModDataComponents.PET_DATA.get());
        if (data == null) {
            return;
        }

        int x = 10;
        int y = 10;
        int threshold = EvolutionRules.xpThresholdFor(data.evoStage());

        graphics.drawString(
            Minecraft.getInstance().font,
            "[" + data.rarity().name() + "] " + data.speciesId() + " | HP " + data.hp() + " ATK " + data.atk() + " DEF " + data.def() + " SPD " + data.spd(),
            x, y, data.rarity().color().getColor() != null ? data.rarity().color().getColor() : 0xFFFFFF
        );
        graphics.drawString(
            Minecraft.getInstance().font,
            "XP " + data.xp() + "/" + (threshold == Integer.MAX_VALUE ? "MAX" : threshold) + " | Stage " + data.evoStage(),
            x, y + 10, 0x55FF55
        );
    }
}

// Built with assistance from Claude Code by Anthropic.
