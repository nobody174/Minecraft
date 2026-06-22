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

public record PetData(int hp, int atk, int def, int spd, int xp, int evoStage, String speciesId) {

    public static final Codec<PetData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("hp").forGetter(PetData::hp),
            Codec.INT.fieldOf("atk").forGetter(PetData::atk),
            Codec.INT.fieldOf("def").forGetter(PetData::def),
            Codec.INT.fieldOf("spd").forGetter(PetData::spd),
            Codec.INT.fieldOf("xp").forGetter(PetData::xp),
            Codec.INT.fieldOf("evoStage").forGetter(PetData::evoStage),
            Codec.STRING.fieldOf("speciesId").forGetter(PetData::speciesId)
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

    public static final StreamCodec<ByteBuf, PetData> STREAM_CODEC = StreamCodec.composite(
        CoreStats.STREAM_CODEC, data -> new CoreStats(data.hp(), data.atk(), data.def(), data.spd()),
        ByteBufCodecs.VAR_INT, PetData::xp,
        ByteBufCodecs.VAR_INT, PetData::evoStage,
        ByteBufCodecs.STRING_UTF8, PetData::speciesId,
        (core, xp, evoStage, speciesId) -> new PetData(core.hp(), core.atk(), core.def(), core.spd(), xp, evoStage, speciesId)
    );

    public static PetData baseStatsFor(String speciesId) {
        return new PetData(20, 5, 5, 5, 0, 0, speciesId);
    }

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

        return new PetData(newHp, newAtk, newDef, newSpd, newXp, newStage, speciesId);
    }
}

// Built with assistance from Claude Code by Anthropic.
