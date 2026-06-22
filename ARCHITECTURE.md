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

### Client
- `client/PetTooltipHandler.java` — `ItemTooltipEvent` listener rendering stats on the capture ball tooltip.
- `client/PetHudOverlay.java` — `RenderGuiLayerEvent` HUD display reading `PetData` directly from the local player's held capture ball each frame (no custom networking — see REUSED_FROM.md for why the earlier packet-based approach was removed).

### Events
- `event/PetXpEvent.java` — Listens for XP-granting actions (combat, crafting, exploration) and updates `PetData` on the held capture ball.

### Gameplay Effects
- `component/PetStatApplier.java` — Translates `PetData` into vanilla `Attributes` (MAX_HEALTH, ATTACK_DAMAGE, ARMOR, MOVEMENT_SPEED) on the released mob, so captured/trained/evolved stats have real combat impact rather than being purely cosmetic.

## Data Flow

1. Player captures entity → server creates `PetData` from `EntityType` + base stats → sets on ItemStack via data component.
2. XP-granting event fires → server reads `PetData`, computes new XP/stats, writes back immutable copy via `stack.set(component, updated)`.
3. If evolution threshold met → server updates `PetData.evolutionStage` and stat gains.
4. The data component travels with the ItemStack via vanilla inventory sync — no custom packet needed. `PetTooltipHandler` and `PetHudOverlay` both read the component directly off the (already-synced) held ItemStack on the client.
5. On release, `PetStatApplier` reads the final `PetData` and applies it to the spawned mob's vanilla attributes (MAX_HEALTH, ATTACK_DAMAGE, ARMOR, MOVEMENT_SPEED).
