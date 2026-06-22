//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PetData(int hp, int atk, int def, int spd, int xp, int evoStage, String speciesId, PetRarity rarity) {

    public static final Codec<PetData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("hp").forGetter(PetData::hp),
            Codec.INT.fieldOf("atk").forGetter(PetData::atk),
            Codec.INT.fieldOf("def").forGetter(PetData::def),
            Codec.INT.fieldOf("spd").forGetter(PetData::spd),
            Codec.INT.fieldOf("xp").forGetter(PetData::xp),
            Codec.INT.fieldOf("evoStage").forGetter(PetData::evoStage),
            Codec.STRING.fieldOf("speciesId").forGetter(PetData::speciesId),
            Codec.STRING.xmap(PetRarity::valueOf, PetRarity::name).fieldOf("rarity").forGetter(PetData::rarity)
        ).apply(instance, PetData::new)
    );

    private record CoreStats(int hp, int atk, int def, int spd) {
        static final StreamCodec<ByteBuf, CoreStats> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CoreStats::hp,
            ByteBufCodecs.VAR_INT, CoreStats::atk,
            ByteBufCodecs.VAR_INT, CoreStats::def,
            ByteBufCodecs.VAR_INT, CoreStats::spd,
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
        CoreStats.STREAM_CODEC, data -> new CoreStats(data.hp(), data.atk(), data.def(), data.spd()),
        MetaFields.STREAM_CODEC, data -> new MetaFields(data.xp(), data.evoStage(), data.speciesId(), data.rarity()),
        (core, meta) -> new PetData(core.hp(), core.atk(), core.def(), core.spd(), meta.xp(), meta.evoStage(), meta.speciesId(), meta.rarity())
    );

    public PetData withXp(int gained) {
        int newXp = xp + gained;
        int newStage = evoStage;
        int newHp = hp;
        int newAtk = atk;
        int newDef = def;
        int newSpd = spd;

        if (newXp >= EvolutionRules.xpThresholdFor(evoStage) && evoStage < EvolutionRules.MAX_STAGE) {
            newStage = evoStage + 1;
            newHp += EvolutionRules.HP_GAIN_PER_STAGE;
            newAtk += EvolutionRules.ATK_GAIN_PER_STAGE;
            newDef += EvolutionRules.DEF_GAIN_PER_STAGE;
            newSpd += EvolutionRules.SPD_GAIN_PER_STAGE;
        }

        return new PetData(newHp, newAtk, newDef, newSpd, newXp, newStage, speciesId, rarity);
    }
}

// Built with assistance from Claude Code by Anthropic.
