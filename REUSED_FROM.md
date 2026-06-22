# Reused From Portfolio

## Networking Pattern — from `buddy-beast` (later removed, see below)

**Reused:** Record-based `CustomPacketPayload` + `StreamCodec.composite` + `RegisterPayloadHandlersEvent`/`PayloadRegistrar` registration pattern.

**Source:** `buddy-beast/src/main/java/com/nobody174/buddybeast/network/BuddySyncPacket.java` and `BuddyNetworkHandler.java`.

**Why it was removed:** Pet Evolution only ever needs to show the *local* player's own held capture ball stats (tooltip + HUD), and `ItemStack` data components already travel with vanilla inventory sync. The custom `PetSyncPacket` push was redundant and had a real staleness bug — the HUD kept showing the last-known stats after release because no packet fires for that case. Removed `PetSyncPacket`/`PetNetworkHandler`/`ClientPetState`; `PetHudOverlay` now reads `ModDataComponents.PET_DATA` directly off `Minecraft.getInstance().player.getMainHandItem()` every frame, which is always correct and self-healing. Kept this entry as a record of the dead end — a custom packet would become necessary again only if the HUD needed to show another player's pet (e.g. in Phase 2 pet-vs-pet battles).

## Status/Display Pattern — conceptual reuse from `buddy-beast`

**Reused:** The idea of a simple enum/record-driven status display (`StatusIndicator.BuddyStatus`) informed the tooltip stat-line layout in `PetTooltipHandler`, though Pet Evolution's stats are richer (HP/ATK/DEF/SPD/XP/evolution stage vs. a single status enum) so the code itself was written fresh rather than copied.
