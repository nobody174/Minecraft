//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Named shortcuts for {@code /auraglow color}, so players don't have to look
 * up or remember hex codes. Raw 6-digit hex is still always accepted; this is
 * just a convenience lookup layered on top.
 */
public class AuraGlowPalette {

    private static final Map<String, Integer> NAMED_COLORS = new LinkedHashMap<>();

    static {
        NAMED_COLORS.put("cyan", 0x33CCFF);
        NAMED_COLORS.put("white", 0xFFFFFF);
        NAMED_COLORS.put("red", 0xFF2222);
        NAMED_COLORS.put("green", 0x33FF33);
        NAMED_COLORS.put("gold", 0xFFD700);
        NAMED_COLORS.put("purple", 0x9933FF);
        NAMED_COLORS.put("orange", 0xFF8800);
        NAMED_COLORS.put("pink", 0xFF66CC);
        NAMED_COLORS.put("blue", 0x3366FF);
        NAMED_COLORS.put("yellow", 0xFFFF33);
    }

    public static Iterable<String> names() {
        return NAMED_COLORS.keySet();
    }

    /** Returns the RGB value for a known name, or {@code null} if not a recognized name. */
    public static Integer resolve(String name) {
        return NAMED_COLORS.get(name.toLowerCase());
    }
}

// Built with assistance from Claude Code by Anthropic.
