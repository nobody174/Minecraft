package com.nobody174.glowtools;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.nobody174.glowtools.server.command.AdminCommandHandler;
import java.nio.file.Path;

@Mod("glowtools_sc")
public class GlowToolsMod {
    public static final String MOD_ID = "glowtools_sc";

    public GlowToolsMod(IEventBus modEventBus, ModContainer modContainer) {
        // Client events are handled via @EventBusSubscriber in GlowToolsClientEvents
    }

    @EventBusSubscriber(modid = MOD_ID)
    public static class ServerEvents {
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            try {
                AdminCommandHandler.registerAdminCommands(event.getDispatcher());
            } catch (Exception e) {
                System.err.println("Failed to initialize admin commands: " + e.getMessage());
            }
        }
    }
}
