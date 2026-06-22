# Architecture

## Overview

Pet Evolution uses **Data Components** (NeoForge 1.21.1's NBT replacement) to attach pet data directly to the capture ball ItemStack. This avoids separate save-file or NBT-tag bookkeeping and persists automatically with the item.

## Core Systems

### Data Layer
- `component/PetData.java` — Record holding species id, stats (HP/ATK/DEF/SPD), XP, evolution stage.
- `component/ModDataComponents.java` — DeferredRegister for the custom `DataComponentType<PetData>`.

### Item Layer
- `item/CaptureBallItem.java` — Capture/release interaction logic.
- `item/ModItems.java` — DeferredRegister for mod items.

### Networking
- `network/PetSyncPacket.java` — Record payload (StreamCodec) for syncing pet data changes to clients.
- `network/PetNetworkHandler.java` — Registers payload handlers via `RegisterPayloadHandlersEvent`.

Pattern reused from `buddy-beast`'s `BuddySyncPacket`/`BuddyNetworkHandler` (see REUSED_FROM.md).

### Client
- `client/PetTooltipHandler.java` — `ItemTooltipEvent` listener rendering stats on the capture ball tooltip.
- `client/PetHudOverlay.java` — `RenderGuiLayerEvent` HUD display of active pet stats/evolution progress.

### Events
- `event/PetXpEvent.java` — Listens for XP-granting actions (combat, crafting, exploration) and updates `PetData` on the held capture ball.

### Gameplay Effects
- `component/PetStatApplier.java` — Translates `PetData` into vanilla `Attributes` (MAX_HEALTH, ATTACK_DAMAGE, ARMOR, MOVEMENT_SPEED) on the released mob, so captured/trained/evolved stats have real combat impact rather than being purely cosmetic.

## Data Flow

1. Player captures entity → server creates `PetData` from `EntityType` + base stats → sets on ItemStack via data component.
2. XP-granting event fires → server reads `PetData`, computes new XP/stats, writes back immutable copy via `stack.update(component, data, fn)`.
3. If evolution threshold met → server updates `PetData.evolutionStage` and species visuals.
4. Server syncs relevant changes to client via `PetSyncPacket` for HUD display (data component itself does not require a packet for tooltip persistence, since it travels with the ItemStack in the inventory sync NeoForge already performs — packet is for HUD-specific lightweight updates without full inventory resync).
