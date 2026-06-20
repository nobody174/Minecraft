//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.nobody174.bossradar.item.BossRadarItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("bossradar");

    public static final DeferredHolder<Item, Item> BOSS_RADAR = ITEMS.register("boss_radar",
            () -> new BossRadarItem(new Item.Properties()));
}

//*Built with assistance from __Claude Code__ by Anthropic.*
