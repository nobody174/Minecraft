# Reused From Portfolio

## Networking Pattern — from `buddy-beast`

**Reused:** Record-based `CustomPacketPayload` + `StreamCodec.composite` + `RegisterPayloadHandlersEvent`/`PayloadRegistrar` registration pattern.

**Source:** `buddy-beast/src/main/java/com/nobody174/buddybeast/network/BuddySyncPacket.java` and `BuddyNetworkHandler.java`.

**Adapted into:** `pet-evolution/src/main/java/com/nobody174/petevolution/network/PetSyncPacket.java` and `PetNetworkHandler.java`.

**Why:** Saves re-deriving the correct NeoForge 1.21.1 networking boilerplate (payload type registration, StreamCodec wiring, client-side `enqueueWork` handling) — roughly 1-2 hours of API research avoided since the pattern was already verified working in buddy-beast.

## Status/Display Pattern — conceptual reuse from `buddy-beast`

**Reused:** The idea of a simple enum/record-driven status display (`StatusIndicator.BuddyStatus`) informed the tooltip stat-line layout in `PetTooltipHandler`, though Pet Evolution's stats are richer (HP/ATK/DEF/SPD/XP/evolution stage vs. a single status enum) so the code itself was written fresh rather than copied.
