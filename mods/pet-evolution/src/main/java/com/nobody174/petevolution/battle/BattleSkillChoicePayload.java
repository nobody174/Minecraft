//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import com.nobody174.petevolution.PetEvolution;

/**
 * Client-to-server payload carrying the player's battle-skill override choice.
 *
 * This is a deliberate, intentional use of NeoForge's custom networking — see
 * CHANGELOG.md "v2.0 Battle UI" entry for why this is NOT a regression of the
 * push-packet pattern that was removed earlier in development. That removal
 * eliminated a server-push state-sync packet (PetSyncPacket) that had become
 * redundant once the HUD started reading data components directly off the
 * synced ItemStack. This payload is the opposite direction and a fundamentally
 * different kind of packet: it carries one-shot PLAYER INPUT (a skill pick)
 * that has no other path to the server — there is no data component or
 * attachment the server could "just read" to learn what the player clicked.
 * Removing this would mean dropping the player-override feature entirely,
 * not simplifying an already-redundant sync path.
 */
public record BattleSkillChoicePayload(String skillId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BattleSkillChoicePayload> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PetEvolution.MOD_ID, "battle_skill_choice"));

    public static final StreamCodec<io.netty.buffer.ByteBuf, BattleSkillChoicePayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, BattleSkillChoicePayload::skillId,
        BattleSkillChoicePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
