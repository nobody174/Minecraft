//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.render;

/**
 * No rendering code lives here yet — this class exists purely to document the
 * v2.0 capture device visual design and its current limitations (see also
 * CHANGELOG.md and FUTURE_FEATURES.md for the same notes).
 *
 * <h2>Design: "Rune-Bound Vessel"</h2>
 * An original, non-franchise capture device design: a faceted hexagonal vessel
 * (not a sphere split into two colored halves) with a glowing rune-diamond core
 * and a horizontal seam band. Textures are procedurally generated 32x32 PNGs
 * (via {@code java.awt.image.BufferedImage} + {@code ImageIO} in a one-off
 * generator script, not committed to the build) in 5 color variants:
 * {@code capture_ball.png} (neutral default) and one per {@code PetRarity}
 * tier ({@code capture_ball_common.png}, {@code _uncommon}, {@code _rare},
 * {@code _epic}), with the glow/vessel colors matched to each rarity's
 * existing {@code ChatFormatting} color from {@code PetRarity}.
 *
 * <h2>Rarity variants are wired up via vanilla custom_model_data overrides</h2>
 * NeoForge 1.21.1 predates the data-component-driven item model system added
 * in 1.21.2 ({@code minecraft:select}/{@code range_dispatch}), so the only
 * available mechanism is the older vanilla {@code overrides} predicate system
 * keyed off the {@code custom_model_data} float component. The item model
 * ({@code assets/petevolution/models/item/capture_ball.json}) declares one
 * override per rarity tier, each pointing at its own model JSON
 * ({@code capture_ball_common.json}, {@code _uncommon}, {@code _rare},
 * {@code _epic}). {@code PetData.syncCustomModelData(ItemStack)} sets the
 * component to {@code rarity.ordinal() + 1} (0 is reserved for the neutral
 * default, used when no PetData/rarity is present); {@code CaptureBallItem}
 * calls it on capture and clears the component on release so an emptied ball
 * reverts to the default texture. Not yet visually confirmed in-game — see
 * TODO.md for the outstanding manual smoke test.
 */
public final class CaptureBallRenderNotes {

    private CaptureBallRenderNotes() {
    }
}

// Built with assistance from Claude Code by Anthropic.
