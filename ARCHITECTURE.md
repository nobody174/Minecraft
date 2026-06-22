# Architecture

## Overview

Pet Evolution uses **Data Components** (NeoForge 1.21.1's NBT replacement) to attach pet data directly to the capture ball ItemStack. This avoids separate save-file or NBT-tag bookkeeping and persists automatically with the item.

## Core Systems

### Data Layer
- `component/PetData.java` — Record holding species id, stats (HP/ATK/DEF/SPD), XP, evolution stage, rarity.
- `component/PetRarity.java` — Enum (COMMON/UNCOMMON/RARE/EPIC) with a per-tier stat multiplier and weighted roll (`PetRarity.roll`, weights 60/25/12/3) used at capture time.
- `component/ModDataComponents.java` — DeferredRegister for the custom `DataComponentType<PetData>` (item-bound, for the capture ball while a pet is stored).
- `component/PetOwnerData.java` / `component/ModAttachments.java` — NeoForge `AttachmentType`s on released pet *entities* (not items): `PET_OWNER` records which player's pet this is, `RELEASED_PET_DATA` carries the live `PetData` snapshot so a released pet keeps evolving/battling independent of any item.

### Item Layer
- `item/CaptureBallItem.java` — Capture/release interaction logic.
- `item/ModItems.java` — DeferredRegister for mod items.

### Client
- `client/PetTooltipHandler.java` — `ItemTooltipEvent` listener rendering stats on the capture ball tooltip.
- `client/PetHudOverlay.java` — `RenderGuiLayerEvent` HUD display reading `PetData` directly from the local player's held capture ball each frame (no custom networking — see REUSED_FROM.md for why the earlier packet-based approach was removed).

### Events
- `event/PetXpEvent.java` — Listens for XP-granting actions (combat, crafting, exploration) and updates `PetData` on the held capture ball.
- `event/PetBattleEvent.java` — Phase 2: `PlayerInteractEvent.EntityInteract` listener. A player holding an empty capture ball right-clicks another player's released pet to challenge it; the challenger's own released pet (found within 8 blocks via `PET_OWNER` attachment) is the combatant. Battles resolve instantly by comparing a derived "battle power" score (`hp + atk*2 + def + spd`) rather than simulating real-time combat, since released pets are plain vanilla mobs without battle AI. The winner's `RELEASED_PET_DATA` gains XP and has `PetStatApplier` reapplied. A 30-second per-pet cooldown (keyed by entity UUID, in-memory) prevents rapid re-challenge XP farming.

### Gameplay Effects
- `component/PetStatApplier.java` — Translates `PetData` into vanilla `Attributes` (MAX_HEALTH, ATTACK_DAMAGE, ARMOR, MOVEMENT_SPEED) on the released mob, so captured/trained/evolved stats have real combat impact rather than being purely cosmetic.

## Data Flow

1. Player captures entity → server classifies it (`SpeciesStats`), rolls a `PetRarity`, scales base stats by the rarity multiplier → creates `PetData` → sets on ItemStack via data component.
2. XP-granting event fires → server reads `PetData`, computes new XP/stats, writes back immutable copy via `stack.set(component, updated)`.
3. If evolution threshold met → server updates `PetData.evolutionStage` and stat gains.
4. The data component travels with the ItemStack via vanilla inventory sync — no custom packet needed. `PetTooltipHandler` and `PetHudOverlay` both read the component directly off the (already-synced) held ItemStack on the client.
5. On release, `PetStatApplier` reads the final `PetData` and applies it to the spawned mob's vanilla attributes (MAX_HEALTH, ATTACK_DAMAGE, ARMOR, MOVEMENT_SPEED); the same `PetData` is also attached to the entity itself (`RELEASED_PET_DATA`) along with the owning player's UUID (`PET_OWNER`), so the released pet has a persistent identity independent of the now-empty capture ball.
6. Released pets can battle (Phase 2): `PetBattleEvent` compares two pets' derived power, grants the winner XP, and reapplies stats — entirely through the entity attachments, with no further interaction with the original capture ball items required.
