//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.tracking;

import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.Map;

/**
 * Friendly lowercase names for {@code /track <alias>} (e.g. {@code /track zombie})
 * so players don't have to type raw selectors like {@code @e[type=minecraft:zombie]}.
 * Not exhaustive — covers the mob types already exercised in
 * TEST_REPORT_RC2_VALIDATION.md's test scenarios; extend as needed.
 *
 * <p>{@link #enemyAliases()}/{@link #friendlyAliases()} split the same
 * entries by category so the tracking menu can list "the enemy ones" vs.
 * "the friendly ones" separately (see {@code TrackTypeSubmenuScreen}) —
 * the type constants themselves still live only in {@link #ALIASES}.</p>
 */
public final class EntityTypeAliases {

    private static final Map<String, EntityType<?>> ALIASES = Map.ofEntries(
        Map.entry("zombie", EntityType.ZOMBIE),
        Map.entry("skeleton", EntityType.SKELETON),
        Map.entry("creeper", EntityType.CREEPER),
        Map.entry("witch", EntityType.WITCH),
        Map.entry("spider", EntityType.SPIDER),
        Map.entry("enderman", EntityType.ENDERMAN),
        Map.entry("sheep", EntityType.SHEEP),
        Map.entry("cow", EntityType.COW),
        Map.entry("pig", EntityType.PIG),
        Map.entry("chicken", EntityType.CHICKEN),
        Map.entry("horse", EntityType.HORSE),
        Map.entry("wolf", EntityType.WOLF),
        Map.entry("cat", EntityType.CAT),
        Map.entry("villager", EntityType.VILLAGER)
    );

    private static final List<String> ENEMY_ALIASES =
        List.of("zombie", "skeleton", "creeper", "spider", "witch", "enderman");

    private static final List<String> FRIENDLY_ALIASES =
        List.of("sheep", "cow", "pig", "chicken", "horse", "wolf", "cat", "villager");

    private EntityTypeAliases() {
    }

    /** Null if {@code alias} isn't a recognized entity type name. */
    public static EntityType<?> resolve(String alias) {
        return ALIASES.get(alias.toLowerCase(java.util.Locale.ROOT));
    }

    public static Iterable<String> knownAliases() {
        return ALIASES.keySet();
    }

    public static List<String> enemyAliases() {
        return ENEMY_ALIASES;
    }

    public static List<String> friendlyAliases() {
        return FRIENDLY_ALIASES;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
