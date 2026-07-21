//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;

import com.nobody174.foundya.config.FoundYaConfig;
import com.nobody174.foundya.config.FoundYaConfigFile;

/**
 * In-game settings screen, replacing the {@code /track config} commands as
 * the primary config interface per
 * {@code TrackerVision_Production_Design_Package_v2/08_COMMAND_SPEC.md}
 * ("Future: GUI replaces commands"). The commands remain available — this
 * doesn't remove them, it's just a friendlier front end over the same
 * {@link FoundYaConfig} fields. Built from vanilla widgets only (no
 * NeoForge {@code ConfigurationScreen}, since that's spec-driven off
 * {@code ModConfigSpec} and this mod's config is a hand-rolled JSON file).
 * Changes apply live and are persisted on close.
 */
public final class FoundYaConfigScreen extends Screen {

    private static final int ROW_HEIGHT = 24;
    private static final int WIDGET_WIDTH = 280;

    private final Screen parent;

    public FoundYaConfigScreen(Screen parent) {
        super(Component.literal("Found Ya! Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(8).rowSpacing(8);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        rows.addChild(Button.builder(
                Component.literal("Profile: " + FoundYaConfig.getActiveProfileName() + " (click to cycle)"),
                button -> cycleProfile())
            .size(WIDGET_WIDTH, ROW_HEIGHT)
            .build());

        rows.addChild(
            Checkbox.builder(Component.literal("Tracking enabled"), font)
                .selected(FoundYaConfig.isTrackingEnabled())
                .onValueChange((checkbox, value) -> FoundYaConfig.setTrackingEnabled(value))
                .build());

        rows.addChild(new DistanceSlider(
            "Near distance", FoundYaConfig.getNearDistance(), 1.0F, 32.0F,
            FoundYaConfig::setNearDistance));

        rows.addChild(new DistanceSlider(
            "Far distance", FoundYaConfig.getFarDistance(), 8.0F, 256.0F,
            FoundYaConfig::setFarDistance));

        rows.addChild(new DistanceSlider(
            "Bracket size", FoundYaConfig.getBracketBaseSize(), 8.0F, 64.0F,
            value -> FoundYaConfig.setBracketBaseSize(Math.round(value))));

        rows.addChild(
            Checkbox.builder(Component.literal("Beacon mode for distant targets"), font)
                .selected(FoundYaConfig.isBeaconEnabled())
                .onValueChange((checkbox, value) -> FoundYaConfig.setBeaconEnabled(value))
                .build());

        rows.addChild(new DistanceSlider(
            "Beacon distance", FoundYaConfig.getBeaconDistance(), 8.0F, 256.0F,
            FoundYaConfig::setBeaconDistance));

        rows.addChild(
            Checkbox.builder(Component.literal("Rim boost shader (bloom-style punch on lock)"), font)
                .selected(FoundYaConfig.isRimBoostEnabled())
                .onValueChange((checkbox, value) -> FoundYaConfig.setRimBoostEnabled(value))
                .build());

        grid.arrangeElements();
        grid.visitWidgets(this::addRenderableWidget);
        grid.setPosition((width - grid.getWidth()) / 2, 40);

        addRenderableWidget(net.minecraft.client.gui.components.Button.builder(
                Component.literal("Done"),
                button -> onClose())
            .bounds((width - 150) / 2, height - 32, 150, 20)
            .build());
    }

    /** Switches to the next profile in registration order and rebuilds the widgets to reflect its settings. */
    private void cycleProfile() {
        List<String> names = FoundYaConfig.getProfileNames();
        int currentIndex = names.indexOf(FoundYaConfig.getActiveProfileName());
        String next = names.get((currentIndex + 1) % names.size());
        FoundYaConfig.setActiveProfile(next);
        clearWidgets();
        init();
    }

    @Override
    public void onClose() {
        FoundYaConfigFile.save();
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 16, 0xFFFFFFFF);
    }

    /** Float-valued vanilla slider bound directly to a {@link FoundYaConfig} setter. */
    private final class DistanceSlider extends AbstractSliderButton {
        private final String label;
        private final float min;
        private final float max;
        private final java.util.function.Consumer<Float> setter;

        DistanceSlider(String label, float initialValue, float min, float max, java.util.function.Consumer<Float> setter) {
            super(0, 0, WIDGET_WIDTH, ROW_HEIGHT, Component.empty(),
                Mth.clamp((initialValue - min) / (max - min), 0.0, 1.0));
            this.label = label;
            this.min = min;
            this.max = max;
            this.setter = setter;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            float current = min + (float) value * (max - min);
            setMessage(Component.literal(label + ": " + Math.round(current)));
        }

        @Override
        protected void applyValue() {
            float current = min + (float) value * (max - min);
            setter.accept(current);
        }
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
