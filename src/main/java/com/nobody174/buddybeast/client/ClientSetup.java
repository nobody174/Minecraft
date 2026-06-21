//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.client;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import com.nobody174.buddybeast.BuddyBeast;
import com.nobody174.buddybeast.entity.ModEntities;

public class ClientSetup {
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BUDDY_BEAST.get(), BuddyBeastRenderer::new);
    }
}

// Built with assistance from Claude Code by Anthropic.
