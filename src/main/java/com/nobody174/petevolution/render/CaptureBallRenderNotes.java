//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
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
 * <h2>Known limitation: rarity variants are not yet wired up in-game</h2>
 * The item model ({@code assets/petevolution/models/item/capture_ball.json})
 * currently references only the neutral {@code capture_ball.png} texture.
 * Dynamically switching the rendered texture based on the item's
 * {@code PetData.rarity()} data component in NeoForge 1.21.1 requires either:
 * (a) a custom {@code ItemModel}/{@code ItemModel.Unbaked} implementation
 *     registered via the item model deserializer registry, reading the
 *     component at bake/render time, or
 * (b) a numeric range_dispatch keyed off a custom int (e.g. mapping rarity
 *     ordinal to {@code custom_model_data}) set on the stack at capture time.
 * Both are real, supported approaches, but both add render-pipeline code with
 * a meaningful chance of subtly breaking item rendering if a CMD/component
 * key mismatches, and there was no way to visually verify the result in this
 * autonomous, no-game-launch run. Per the task's explicit fallback guidance,
 * the conservative choice was made: ship the single base texture now, leave
 * the 4 rarity-variant PNGs in {@code textures/item/} ready to use, and
 * document this as a follow-up (see FUTURE_FEATURES.md) for a human to wire
 * up and visually verify in-game.
 */
public final class CaptureBallRenderNotes {

    private CaptureBallRenderNotes() {
    }
}

// Built with assistance from Claude Code by Anthropic.
