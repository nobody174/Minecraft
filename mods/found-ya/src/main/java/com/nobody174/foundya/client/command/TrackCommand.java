//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

import com.nobody174.foundya.client.gui.TrackerMenuScreen;
import com.nobody174.foundya.tracking.EntityTypeAliases;
import com.nobody174.foundya.tracking.SearchModeManager;
import com.nobody174.foundya.tracking.TrackedTargetManager;
import com.nobody174.foundya.tracking.TrackingCategory;
import com.nobody174.foundya.tracking.TrackingMode;

public final class TrackCommand {
    private TrackCommand() {
    }

    private static final SuggestionProvider<CommandSourceStack> TYPE_ALIASES =
        (ctx, builder) -> SharedSuggestionProvider.suggest(EntityTypeAliases.knownAliases(), builder);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("track")
            .executes(TrackCommand::openMenu)
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
            .then(Commands.literal("enemy")
                .executes(ctx -> setCategory(ctx, TrackingCategory.ENEMY)))
            .then(Commands.literal("friendly")
                .executes(ctx -> setCategory(ctx, TrackingCategory.FRIENDLY)))
            .then(Commands.literal("player")
                .executes(ctx -> setCategory(ctx, TrackingCategory.PLAYER)))
            .then(Commands.literal("type")
                .then(Commands.argument("alias", StringArgumentType.word())
                    .suggests(TYPE_ALIASES)
                    .executes(TrackCommand::setTypeFilter)))
            .then(Commands.literal("search")
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(TrackCommand::setSearchMode)))
            .then(TrackConfigCommand.build())
            .then(TrackProfileCommand.build()));
    }

    private static int openMenu(CommandContext<CommandSourceStack> ctx) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new TrackerMenuScreen());
        return 1;
    }

    private static int setCategory(CommandContext<CommandSourceStack> ctx, TrackingCategory category) {
        TrackedTargetManager.setCategory(category);
        final Component message = Component.literal("Found Ya!: tracking nearest " + category.name().toLowerCase());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setTypeFilter(CommandContext<CommandSourceStack> ctx) {
        String alias = StringArgumentType.getString(ctx, "alias");
        EntityType<?> type = EntityTypeAliases.resolve(alias);
        if (type == null) {
            ctx.getSource().sendFailure(Component.literal("Unknown entity type \"" + alias + "\"."));
            return 0;
        }
        TrackedTargetManager.setTypeFilter(type);
        final Component message = Component.literal("Found Ya!: tracking nearest " + alias);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setSearchMode(CommandContext<CommandSourceStack> ctx) {
        boolean value = BoolArgumentType.getBool(ctx, "value");
        SearchModeManager.setEnabled(value);
        final Component message = Component.literal("Found Ya! search mode: " + value);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setMode(CommandContext<CommandSourceStack> ctx, TrackingMode mode) {
        TrackedTargetManager.setMode(mode);
        final Component message = Component.literal("Found Ya! mode: " + mode);
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
            ctx.getSource().sendFailure(Component.literal("Found Ya! can only lock onto living entities."));
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
            ? "Found Ya!: tracking target " + TrackedTargetManager.getLockedTargetId()
            : "Found Ya!: no target locked.");
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
