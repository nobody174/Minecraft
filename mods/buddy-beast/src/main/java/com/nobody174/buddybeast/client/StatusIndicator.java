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

import com.nobody174.buddybeast.entity.BuddyBeastEntity;

public class StatusIndicator {

    public enum BuddyStatus {
        IDLE("Idle", 0xFFFFFF),
        FOLLOWING("Following", 0x00FF00),
        STAYING("Staying", 0xFFFF00),
        ATTACKING("Attacking", 0xFF0000);

        public final String displayName;
        public final int color;

        BuddyStatus(String displayName, int color) {
            this.displayName = displayName;
            this.color = color;
        }
    }

    public static BuddyStatus getStatus(BuddyBeastEntity buddy) {
        if (!buddy.isTamed()) {
            return BuddyStatus.IDLE;
        }

        // Check if following owner
        if (buddy.isFollowing()) {
            return BuddyStatus.FOLLOWING;
        }

        // Check if staying in place
        if (buddy.getNavigation().isDone()) {
            return BuddyStatus.STAYING;
        }

        return BuddyStatus.IDLE;
    }

    public static String getStatusText(BuddyBeastEntity buddy) {
        BuddyStatus status = getStatus(buddy);
        return status.displayName;
    }

    public static int getStatusColor(BuddyBeastEntity buddy) {
        BuddyStatus status = getStatus(buddy);
        return status.color;
    }
}

// Built with assistance from Claude Code by Anthropic.
