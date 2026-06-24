//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client.ui;

import java.util.List;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.battle.BattleSkillChoicePayload;
import com.nobody174.petevolution.battle.BattleStateSyncPayload;
import com.nobody174.petevolution.skills.Skill;
import com.nobody174.petevolution.skills.SkillRegistry;

/**
 * Minimal HUD-only battle overlay: two HP bars (vanilla-styled flat colored rects,
 * no custom textures needed) and, while a battle is active, a row of up to 5
 * number-key skill prompts (1-5, vanilla-hotbar-key style). Deliberately NOT a
 * full GUI Screen — no mouse capture, doesn't pause or block other gameplay,
 * per the "minimal/non-blocking" requirement.
 *
 * Number-key input is polled once per client tick via {@link #onClientTick} rather
 * than a key-binding, to keep this self-contained without registering new
 * KeyMapping entries that could conflict with other mods/vanilla hotbar slots —
 * we deliberately read the raw GLFW key state for digits 1-5 only while the
 * battle HUD is showing, so this never intercepts normal hotbar switching
 * outside of an active battle.
 */
public final class BattleHudOverlay {

    private static final int[] DIGIT_KEYS = {
        org.lwjgl.glfw.GLFW.GLFW_KEY_1, org.lwjgl.glfw.GLFW.GLFW_KEY_2, org.lwjgl.glfw.GLFW.GLFW_KEY_3,
        org.lwjgl.glfw.GLFW.GLFW_KEY_4, org.lwjgl.glfw.GLFW.GLFW_KEY_5
    };
    private static final boolean[] WAS_DOWN = new boolean[DIGIT_KEYS.length];

    private BattleHudOverlay() {
    }

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        BattleStateSyncPayload state = ClientBattleState.current();
        if (state == null || !state.active()) {
            return;
        }

        int screenWidth = graphics.guiWidth();
        int barWidth = 80;
        int barHeight = 6;
        int x = screenWidth / 2 - barWidth - 10;
        int y = 20;

        drawHpBar(graphics, x, y, barWidth, barHeight, state.challengerHpFraction(), 0x55FF55);
        drawHpBar(graphics, screenWidth / 2 + 10, y, barWidth, barHeight, state.defenderHpFraction(), 0xFF5555);

        graphics.drawCenteredString(Minecraft.getInstance().font, "Your Pet", x + barWidth / 2, y - 10, 0xFFFFFF);
        graphics.drawCenteredString(Minecraft.getInstance().font, "Opponent", screenWidth / 2 + 10 + barWidth / 2, y - 10, 0xFFFFFF);

        if (state.awaitingInput()) {
            renderSkillPrompts(graphics, screenWidth, y + barHeight + 16, state.availableSkillIds());
        }
    }

    private static void drawHpBar(GuiGraphics graphics, int x, int y, int width, int height, float fraction, int color) {
        graphics.fill(x, y, x + width, y + height, 0xFF202020);
        int filled = Math.round(width * Math.max(0f, Math.min(1f, fraction)));
        graphics.fill(x, y, x + filled, y + height, 0xFF000000 | color);
    }

    private static void renderSkillPrompts(GuiGraphics graphics, int screenWidth, int y, List<String> skillIds) {
        int slotWidth = 90;
        int totalWidth = slotWidth * skillIds.size();
        int startX = screenWidth / 2 - totalWidth / 2;

        for (int i = 0; i < skillIds.size(); i++) {
            Skill skill = SkillRegistry.byId(skillIds.get(i));
            if (skill == null) {
                continue;
            }
            String label = "[" + (i + 1) + "] " + skill.displayName();
            graphics.drawCenteredString(Minecraft.getInstance().font, label, startX + slotWidth * i + slotWidth / 2, y, 0xFFFF55);
        }
    }

    /**
     * Drains pending clicks on the vanilla hotbar-slot-switch keybinds (1-5) while a
     * battle is active, so pressing a digit to pick a skill doesn't also switch the
     * player's hotbar slot — found during a real co-op test where pressing 1/2 during
     * a battle prompt swapped hotbar slots and never registered as a skill choice.
     * Must run in {@link ClientTickEvent.Pre} (before vanilla's own keybind handling
     * in {@code Minecraft.tick()} consumes the click) rather than {@code Post}.
     */
    public static void onClientTickPre(ClientTickEvent.Pre event) {
        if (!ClientBattleState.isActive()) {
            return;
        }
        var options = Minecraft.getInstance().options;
        var hotbarKeys = new net.minecraft.client.KeyMapping[] {
            options.keyHotbarSlots[0], options.keyHotbarSlots[1], options.keyHotbarSlots[2],
            options.keyHotbarSlots[3], options.keyHotbarSlots[4]
        };
        for (net.minecraft.client.KeyMapping key : hotbarKeys) {
            key.consumeClick();
        }
    }

    /** Polls digit keys 1-5 once per client tick and sends a skill choice payload on press (not hold). */
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!ClientBattleState.isActive()) {
            return;
        }
        long windowHandle = Minecraft.getInstance().getWindow().getWindow();
        List<String> available = ClientBattleState.availableSkillIds();

        for (int i = 0; i < DIGIT_KEYS.length && i < available.size(); i++) {
            boolean isDown = org.lwjgl.glfw.GLFW.glfwGetKey(windowHandle, DIGIT_KEYS[i]) == org.lwjgl.glfw.GLFW.GLFW_PRESS;
            if (isDown && !WAS_DOWN[i]) {
                if (PetEvolution.DEBUG_LOGGING) {
                    PetEvolution.LOGGER.info("[Battle] Sending skill choice payload for key {}: {}", i + 1, available.get(i));
                }
                PacketDistributor.sendToServer(new BattleSkillChoicePayload(available.get(i)));
            }
            WAS_DOWN[i] = isDown;
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
