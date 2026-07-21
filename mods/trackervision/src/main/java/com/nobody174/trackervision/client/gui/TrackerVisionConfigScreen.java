//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;

import com.nobody174.trackervision.config.TrackerVisionConfig;
import com.nobody174.trackervision.config.TrackerVisionConfigFile;

/**
 * In-game settings screen, replacing the {@code /track config} commands as
 * the primary config interface per
 * {@code TrackerVision_Production_Design_Package_v2/08_COMMAND_SPEC.md}
 * ("Future: GUI replaces commands"). The commands remain available — this
 * doesn't remove them, it's just a friendlier front end over the same
 * {@link TrackerVisionConfig} fields. Built from vanilla widgets only (no
 * NeoForge {@code ConfigurationScreen}, since that's spec-driven off
 * {@code ModConfigSpec} and this mod's config is a hand-rolled JSON file).
 * Changes apply live and are persisted on close.
 */
public final class TrackerVisionConfigScreen extends Screen {

    private static final int ROW_HEIGHT = 24;
    private static final int WIDGET_WIDTH = 280;

    private final Screen parent;

    public TrackerVisionConfigScreen(Screen parent) {
        super(Component.literal("TrackerVision Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridLayout grid = new GridLayout().columnSpacing(8).rowSpacing(8);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        rows.addChild(Button.builder(
                Component.literal("Profile: " + TrackerVisionConfig.getActiveProfileName() + " (click to cycle)"),
                button -> cycleProfile())
            .size(WIDGET_WIDTH, ROW_HEIGHT)
            .build());

        rows.addChild(
            Checkbox.builder(Component.literal("Tracking enabled"), font)
                .selected(TrackerVisionConfig.isTrackingEnabled())
                .onValueChange((checkbox, value) -> TrackerVisionConfig.setTrackingEnabled(value))
                .build());

        rows.addChild(new DistanceSlider(
            "Near distance", TrackerVisionConfig.getNearDistance(), 1.0F, 32.0F,
            TrackerVisionConfig::setNearDistance));

        rows.addChild(new DistanceSlider(
            "Far distance", TrackerVisionConfig.getFarDistance(), 8.0F, 256.0F,
            TrackerVisionConfig::setFarDistance));

        rows.addChild(new DistanceSlider(
            "Bracket size", TrackerVisionConfig.getBracketBaseSize(), 8.0F, 64.0F,
            value -> TrackerVisionConfig.setBracketBaseSize(Math.round(value))));

        rows.addChild(
            Checkbox.builder(Component.literal("Beacon mode for distant targets"), font)
                .selected(TrackerVisionConfig.isBeaconEnabled())
                .onValueChange((checkbox, value) -> TrackerVisionConfig.setBeaconEnabled(value))
                .build());

        rows.addChild(new DistanceSlider(
            "Beacon distance", TrackerVisionConfig.getBeaconDistance(), 8.0F, 256.0F,
            TrackerVisionConfig::setBeaconDistance));

        rows.addChild(
            Checkbox.builder(Component.literal("Rim boost shader (bloom-style punch on lock)"), font)
                .selected(TrackerVisionConfig.isRimBoostEnabled())
                .onValueChange((checkbox, value) -> TrackerVisionConfig.setRimBoostEnabled(value))
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
        List<String> names = TrackerVisionConfig.getProfileNames();
        int currentIndex = names.indexOf(TrackerVisionConfig.getActiveProfileName());
        String next = names.get((currentIndex + 1) % names.size());
        TrackerVisionConfig.setActiveProfile(next);
        clearWidgets();
        init();
    }

    @Override
    public void onClose() {
        TrackerVisionConfigFile.save();
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 16, 0xFFFFFFFF);
    }

    /** Float-valued vanilla slider bound directly to a {@link TrackerVisionConfig} setter. */
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
