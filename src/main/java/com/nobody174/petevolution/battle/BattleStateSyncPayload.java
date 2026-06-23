//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
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
 * Server-to-client payload describing the current state of the recipient's active
 * battle (HP fractions for both sides, whether it's their input window, and the
 * id strings of their currently-unlocked skills) for the minimal battle HUD.
 *
 * Unlike the old PetSyncPacket that was removed earlier (see REUSED_FROM.md), this
 * payload is NOT redundant: a BattleSession only exists in server memory (it isn't
 * a data component or attachment on any synced entity/item), so there is no
 * "just read it off the synced object" path available to the client the way there
 * is for PetData. Sent once per round resolution (every BattleSession.ROUND_TICKS),
 * not every tick, to keep it lightweight.
 */
public record BattleStateSyncPayload(
    boolean active,
    float challengerHpFraction,
    float defenderHpFraction,
    boolean awaitingInput,
    java.util.List<String> availableSkillIds
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BattleStateSyncPayload> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PetEvolution.MOD_ID, "battle_state_sync"));

    public static final StreamCodec<io.netty.buffer.ByteBuf, BattleStateSyncPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, BattleStateSyncPayload::active,
        ByteBufCodecs.FLOAT, BattleStateSyncPayload::challengerHpFraction,
        ByteBufCodecs.FLOAT, BattleStateSyncPayload::defenderHpFraction,
        ByteBufCodecs.BOOL, BattleStateSyncPayload::awaitingInput,
        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), BattleStateSyncPayload::availableSkillIds,
        BattleStateSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
