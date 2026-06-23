//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.creature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;

/**
 * Core persistent stat/progress record for a captured or released pet.
 *
 * v2.0 adds a 5th core stat, {@code special} (used by the skills system for
 * non-physical skill power scaling). {@code StreamCodec.composite} caps out at
 * 6 field+getter pairs per group, so with 5 core stats {@code CoreStats} alone
 * now fills that group; the remaining 4 fields stay nested in {@code MetaFields}
 * as before. evoStage continues to double as the "level" concept per design
 * decision documented in CHANGELOG.md — no parallel level field was introduced.
 */
public record PetData(int hp, int atk, int def, int spd, int special, int xp, int evoStage, String speciesId, PetRarity rarity) {

    public static final Codec<PetData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("hp").forGetter(PetData::hp),
            Codec.INT.fieldOf("atk").forGetter(PetData::atk),
            Codec.INT.fieldOf("def").forGetter(PetData::def),
            Codec.INT.fieldOf("spd").forGetter(PetData::spd),
            Codec.INT.optionalFieldOf("special", 0).forGetter(PetData::special),
            Codec.INT.fieldOf("xp").forGetter(PetData::xp),
            Codec.INT.fieldOf("evoStage").forGetter(PetData::evoStage),
            Codec.STRING.fieldOf("speciesId").forGetter(PetData::speciesId),
            Codec.STRING.xmap(PetRarity::valueOf, PetRarity::name).fieldOf("rarity").forGetter(PetData::rarity)
        ).apply(instance, PetData::new)
    );

    /** hp/atk/def/spd/special — 5 fields, at the StreamCodec.composite arity cap. */
    private record CoreStats(int hp, int atk, int def, int spd, int special) {
        static final StreamCodec<ByteBuf, CoreStats> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CoreStats::hp,
            ByteBufCodecs.VAR_INT, CoreStats::atk,
            ByteBufCodecs.VAR_INT, CoreStats::def,
            ByteBufCodecs.VAR_INT, CoreStats::spd,
            ByteBufCodecs.VAR_INT, CoreStats::special,
            CoreStats::new
        );
    }

    private record MetaFields(int xp, int evoStage, String speciesId, PetRarity rarity) {
        static final StreamCodec<ByteBuf, MetaFields> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, MetaFields::xp,
            ByteBufCodecs.VAR_INT, MetaFields::evoStage,
            ByteBufCodecs.STRING_UTF8, MetaFields::speciesId,
            ByteBufCodecs.STRING_UTF8.map(PetRarity::valueOf, PetRarity::name), MetaFields::rarity,
            MetaFields::new
        );
    }

    public static final StreamCodec<ByteBuf, PetData> STREAM_CODEC = StreamCodec.composite(
        CoreStats.STREAM_CODEC, data -> new CoreStats(data.hp(), data.atk(), data.def(), data.spd(), data.special()),
        MetaFields.STREAM_CODEC, data -> new MetaFields(data.xp(), data.evoStage(), data.speciesId(), data.rarity()),
        (core, meta) -> new PetData(core.hp(), core.atk(), core.def(), core.spd(), core.special(), meta.xp(), meta.evoStage(), meta.speciesId(), meta.rarity())
    );

    /** Convenience constructor for callers that don't yet care about Special (defaults to 0). */
    public PetData(int hp, int atk, int def, int spd, int xp, int evoStage, String speciesId, PetRarity rarity) {
        this(hp, atk, def, spd, 0, xp, evoStage, speciesId, rarity);
    }

    /**
     * Level is derived from evoStage rather than tracked as a separate field —
     * evoStage already gates evolution-based stat/skill unlocks, so introducing
     * a parallel level concept would duplicate that progression axis.
     */
    public int level() {
        return evoStage + 1;
    }

    public PetData withXp(int gained) {
        int newXp = xp + gained;
        int newStage = evoStage;
        int newHp = hp;
        int newAtk = atk;
        int newDef = def;
        int newSpd = spd;
        int newSpecial = special;

        if (newXp >= EvolutionRules.xpThresholdFor(evoStage) && evoStage < EvolutionRules.MAX_STAGE) {
            newStage = evoStage + 1;
            newHp += EvolutionRules.HP_GAIN_PER_STAGE;
            newAtk += EvolutionRules.ATK_GAIN_PER_STAGE;
            newDef += EvolutionRules.DEF_GAIN_PER_STAGE;
            newSpd += EvolutionRules.SPD_GAIN_PER_STAGE;
            newSpecial += EvolutionRules.SPECIAL_GAIN_PER_STAGE;
        }

        return new PetData(newHp, newAtk, newDef, newSpd, newSpecial, newXp, newStage, speciesId, rarity);
    }

    /**
     * Syncs the vanilla {@code custom_model_data} component to this pet's rarity so the
     * capture ball's item model (whose JSON {@code overrides} key off custom_model_data,
     * since 1.21.1 predates the data-component-driven model system added in 1.21.2)
     * renders the matching rarity-tinted texture. {@code ordinal() + 1} reserves 0 for
     * the neutral default texture used when no PetData/rarity is present on the stack.
     */
    public void syncCustomModelData(ItemStack stack) {
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(rarity.ordinal() + 1));
    }
}

// Built with assistance from Claude Code by Anthropic.
