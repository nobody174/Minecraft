//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import com.nobody174.foundya.config.FoundYaConfig;
import com.nobody174.foundya.config.FoundYaConfigFile;

/**
 * {@code /track profile ...} — named, switchable bundles of the
 * near/far distance, bracket size, accent color, and beacon settings
 * (e.g. a tighter "PvP" profile vs. a longer-range "Exploration" one),
 * so a player doesn't have to re-tune every setting by hand when
 * switching activities. Distinct from {@link com.nobody174.foundya.tracking.TrackingMode},
 * which picks *which entity* gets tracked, not how it's rendered.
 */
public final class TrackProfileCommand {
    private TrackProfileCommand() {
    }

    private static final SuggestionProvider<CommandSourceStack> PROFILE_NAMES =
        (ctx, builder) -> SharedSuggestionProvider.suggest(FoundYaConfig.getProfileNames(), builder);

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("profile")
            .then(Commands.literal("list")
                .executes(TrackProfileCommand::list))
            .then(Commands.literal("use")
                .then(Commands.argument("name", StringArgumentType.greedyString())
                    .suggests(PROFILE_NAMES)
                    .executes(TrackProfileCommand::use)))
            .then(Commands.literal("create")
                .then(Commands.argument("name", StringArgumentType.greedyString())
                    .executes(TrackProfileCommand::create)))
            .then(Commands.literal("delete")
                .then(Commands.argument("name", StringArgumentType.greedyString())
                    .suggests(PROFILE_NAMES)
                    .executes(TrackProfileCommand::delete)));
    }

    private static int list(CommandContext<CommandSourceStack> ctx) {
        String active = FoundYaConfig.getActiveProfileName();
        StringBuilder summary = new StringBuilder("Found Ya! profiles: ");
        for (String name : FoundYaConfig.getProfileNames()) {
            summary.append(name.equals(active) ? "[" + name + "]" : name).append("  ");
        }
        final Component message = Component.literal(summary.toString().trim());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int use(CommandContext<CommandSourceStack> ctx) {
        String name = StringArgumentType.getString(ctx, "name");
        if (!FoundYaConfig.setActiveProfile(name)) {
            ctx.getSource().sendFailure(Component.literal("No profile named \"" + name + "\"."));
            return 0;
        }
        FoundYaConfigFile.save();
        final Component message = Component.literal("Found Ya! profile: " + name);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int create(CommandContext<CommandSourceStack> ctx) {
        String name = StringArgumentType.getString(ctx, "name");
        if (!FoundYaConfig.createProfile(name)) {
            ctx.getSource().sendFailure(Component.literal("A profile named \"" + name + "\" already exists."));
            return 0;
        }
        FoundYaConfigFile.save();
        final Component message = Component.literal("Created profile \"" + name + "\" from the active profile's settings.");
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int delete(CommandContext<CommandSourceStack> ctx) {
        String name = StringArgumentType.getString(ctx, "name");
        if (!FoundYaConfig.deleteProfile(name)) {
            ctx.getSource().sendFailure(Component.literal(
                "Can't delete \"" + name + "\" — it doesn't exist, is active, or is the last remaining profile."));
            return 0;
        }
        FoundYaConfigFile.save();
        final Component message = Component.literal("Deleted profile \"" + name + "\".");
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
