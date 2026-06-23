//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.client;

import net.neoforged.neoforge.common.ModConfigSpec;

// NeoForge watches the generated TOML file on disk and fires a reload event
// automatically when it changes - so editing buddybeast-client.toml and saving
// while the client is running updates these values with no rebuild/restart.
// Only put values here that are safe to read fresh every render call (cheap
// visual/numeric tuning), not anything touching world state or registries.
public class BuddyDevConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue REAR_HEAD_OFFSET = BUILDER
        .comment(
            "How far (in blocks, 1.0 = 16px) the two-headed cow's rear head is shifted",
            "along the body's spine, after its baked pivot is cancelled out.",
            "0.0 = mirrored exactly onto the front head's position.",
            "Increase to slide it toward the tail. Edit and save this file while",
            "the game is running - no restart needed."
        )
        .defineInRange("rearHeadOffset", 0.4, -2.0, 2.0);

    public static final ModConfigSpec SPEC = BUILDER.build();
}

// Built with assistance from Claude Code by Anthropic.
