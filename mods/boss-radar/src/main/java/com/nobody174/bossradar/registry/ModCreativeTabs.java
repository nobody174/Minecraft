//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "bossradar");

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BOSS_RADAR_TAB = CREATIVE_TABS.register("boss_radar",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Boss Radar"))
                    .icon(() -> ModItems.BOSS_RADAR.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BOSS_RADAR.get());
                    })
                    .build());
}

//*Built with assistance from __Claude Code__ by Anthropic.*
