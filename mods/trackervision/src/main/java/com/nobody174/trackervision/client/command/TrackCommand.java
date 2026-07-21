//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

import com.nobody174.trackervision.tracking.SearchModeManager;
import com.nobody174.trackervision.tracking.TrackedTargetManager;
import com.nobody174.trackervision.tracking.TrackingMode;

public final class TrackCommand {
    private TrackCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("track")
            .then(Commands.literal("lock")
                .then(Commands.argument("target", EntityArgument.entities())
                    .executes(TrackCommand::lockTarget)))
            .then(Commands.literal("clear")
                .executes(TrackCommand::clearTarget))
            .then(Commands.literal("status")
                .executes(TrackCommand::status))
            .then(Commands.literal("mode")
                .then(Commands.literal("locked")
                    .executes(ctx -> setMode(ctx, TrackingMode.LOCKED)))
                .then(Commands.literal("nearest")
                    .executes(ctx -> setMode(ctx, TrackingMode.NEAREST))))
            .then(Commands.literal("search")
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(TrackCommand::setSearchMode)))
            .then(TrackConfigCommand.build())
            .then(TrackProfileCommand.build()));
    }

    private static int setSearchMode(CommandContext<CommandSourceStack> ctx) {
        boolean value = BoolArgumentType.getBool(ctx, "value");
        SearchModeManager.setEnabled(value);
        final Component message = Component.literal("TrackerVision search mode: " + value);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setMode(CommandContext<CommandSourceStack> ctx, TrackingMode mode) {
        TrackedTargetManager.setMode(mode);
        final Component message = Component.literal("TrackerVision mode: " + mode);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int lockTarget(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            ctx.getSource().sendFailure(Component.literal("Not in a world."));
            return 0;
        }
        Collection<? extends Entity> targets = EntityArgument.getEntities(ctx, "target");
        if (targets.isEmpty()) {
            ctx.getSource().sendFailure(Component.literal("No entities matched."));
            return 0;
        }
        Entity target = targets.iterator().next();
        if (!(target instanceof LivingEntity)) {
            ctx.getSource().sendFailure(Component.literal("TrackerVision can only lock onto living entities."));
            return 0;
        }
        TrackedTargetManager.lock(target);
        final Component message = Component.literal("Locked target: " + target.getName().getString());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int clearTarget(CommandContext<CommandSourceStack> ctx) {
        TrackedTargetManager.clear();
        final Component message = Component.literal("Target cleared.");
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int status(CommandContext<CommandSourceStack> ctx) {
        boolean locked = TrackedTargetManager.isLocked();
        final Component message = Component.literal(locked
            ? "TrackerVision: tracking target " + TrackedTargetManager.getLockedTargetId()
            : "TrackerVision: no target locked.");
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
