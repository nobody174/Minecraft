//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;

import com.nobody174.buddybeast.entity.BuddyBeastEntity;
import com.nobody174.buddybeast.entity.ModEntities;

import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.List;

public class BuddyCommand {
    private static final int MAX_SPAWN_COUNT = 100;

    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("buddybeast")
            .then(Commands.literal("spawnmany")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("count", IntegerArgumentType.integer(1, MAX_SPAWN_COUNT))
                    .executes(BuddyCommand::spawnMany)))
            .then(Commands.literal("killall")
                .requires(source -> source.hasPermission(2))
                .executes(context -> killAll(context, false))
                .then(Commands.argument("includeTamed", BoolArgumentType.bool())
                    .executes(context -> killAll(context, BoolArgumentType.getBool(context, "includeTamed"))))));
    }

    private static int killAll(CommandContext<CommandSourceStack> context, boolean includeTamed) {
        CommandSourceStack source = context.getSource();

        if (!(source.getLevel() instanceof ServerLevel level)) {
            source.sendFailure(Component.literal("This command only works on a server level."));
            return 0;
        }

        List<BuddyBeastEntity> buddies = level.getEntitiesOfClass(BuddyBeastEntity.class, level.getWorldBorder().getCollisionShape().bounds());
        int removed = 0;

        for (BuddyBeastEntity buddy : buddies) {
            if (!includeTamed && buddy.isTamed()) {
                continue;
            }
            buddy.discard();
            removed++;
        }

        int finalRemoved = removed;
        source.sendSuccess(() -> Component.literal("§6Removed " + finalRemoved + " buddies"
            + (includeTamed ? " (including tamed)." : " (untamed only - pass 'true' to include tamed).")), true);
        return removed;
    }

    private static int spawnMany(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        int count = IntegerArgumentType.getInteger(context, "count");

        if (!(source.getLevel() instanceof ServerLevel level)) {
            source.sendFailure(Component.literal("This command only works on a server level."));
            return 0;
        }

        var origin = source.getPosition();
        int spawned = 0;

        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI * i) / count;
            double radius = 2.0 + (i / 10.0);
            double x = origin.x + Math.cos(angle) * radius;
            double z = origin.z + Math.sin(angle) * radius;

            BuddyBeastEntity buddy = new BuddyBeastEntity(ModEntities.BUDDY_BEAST.get(), level);
            buddy.moveTo(x, origin.y, z, 0.0f, 0.0f);
            buddy.finalizeSpawn(level, level.getCurrentDifficultyAt(buddy.blockPosition()), MobSpawnType.COMMAND, null);
            level.addFreshEntity(buddy);
            spawned++;
        }

        int finalSpawned = spawned;
        source.sendSuccess(() -> Component.literal("§6Spawned " + finalSpawned + " buddies for performance testing."), true);
        return spawned;
    }
}

// Built with assistance from Claude Code by Anthropic.
