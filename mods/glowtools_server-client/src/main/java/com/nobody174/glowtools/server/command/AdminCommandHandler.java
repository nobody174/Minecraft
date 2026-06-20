package com.nobody174.glowtools.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Handles admin commands for GlowTools mod
 * /glowtool effect command syncs effect choice to client
 */
public class AdminCommandHandler {

    public static void registerAdminCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Register /glowtool command
        dispatcher.register(
            Commands.literal("glowtool")
                .then(
                    Commands.literal("effect")
                        .then(
                            Commands.argument("effectName", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    String[] effects = {"enchanted_hit", "enchant", "glow", "flame", "electric_spark",
                                                       "soul", "soul_fire_flame", "sweep_attack", "crit", "poof",
                                                       "end_rod", "portal", "glow_squid_ink", "dragon_breath",
                                                       "small_flame", "snowflake", "gust", "totem_of_undying",
                                                       "spore_blossom_air", "sculk_soul"};
                                    for (String effect : effects) {
                                        builder.suggest(effect);
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    String effectName = StringArgumentType.getString(context, "effectName");

                                    // Only players can use this command
                                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                                        source.sendFailure(Component.literal("Only players can use this command"));
                                        return 0;
                                    }

                                    return setPlayerEffect(player, effectName);
                                })
                        )
                )
                .then(
                    Commands.literal("help")
                        .executes(context -> {
                            context.getSource().sendSuccess(() -> Component.literal(
                                "§6GlowTools Commands:\n" +
                                "§e/glowtool effect <name>§r - Change your tool glow effect\n" +
                                "§eAvailable:§r enchanted_hit, enchant, glow, flame, electric_spark, soul, " +
                                "soul_fire_flame, sweep_attack, crit, poof, end_rod, portal, glow_squid_ink, " +
                                "dragon_breath, small_flame, snowflake, gust, totem_of_undying, spore_blossom_air, sculk_soul"
                            ), true);
                            return 1;
                        })
                )
        );
    }

    private static int setPlayerEffect(ServerPlayer player, String effectName) {
        if (!isValidEffect(effectName)) {
            player.displayClientMessage(
                Component.literal("§cUnknown effect: " + effectName),
                false
            );
            return 0;
        }

        player.getPersistentData().putString("glowtools:particle_effect", effectName);

        player.displayClientMessage(
            Component.literal("§aGlowTools effect changed to: §e" + effectName),
            false
        );

        return 1;
    }

    private static boolean isValidEffect(String name) {
        return switch (name.toLowerCase()) {
            case "enchanted_hit", "enchant", "glow", "flame", "electric_spark", "soul",
                 "soul_fire_flame", "sweep_attack", "crit", "poof", "end_rod", "portal",
                 "glow_squid_ink", "dragon_breath", "small_flame",
                 "snowflake", "gust", "totem_of_undying",
                 "spore_blossom_air", "sculk_soul" -> true;
            default -> false;
        };
    }
}
