# Changelog

All notable changes to Pet Evolution are documented here.

## [Unreleased] — v0.1.0-rc1

### Added
- Project scaffolding (Gradle, NeoForge metadata, folder structure)
- Initial mod class and event bus registration
- `PetData` data component (Codec + StreamCodec) with HP/ATK/DEF/SPD/XP/evolution stage
- `CaptureBallItem`: capture wild mobs (right-click), release them back into the world (right-click block)
- Capture restrictions: players, vehicles, leashed mobs cannot be captured
- Tooltip display of pet stats on the capture ball
- XP system: killing an entity while holding a captured pet ball grants XP (`PetXpEvent`)
- Evolution: stat thresholds trigger automatic stage-up with stat gains (`EvolutionRules`)
- HUD overlay showing active pet's stats, XP, and evolution stage
- `SpeciesStats`: base stats now vary by captured species category (hostile/passive/neutral)
- Crafting XP: `PlayerEvent.ItemCraftedEvent` grants XP to held capture ball
- Exploration XP: tracks `Player.walkDist` delta per-player, batched every 20 ticks, grants XP per distance threshold
- `PetStatApplier`: applies PetData stats to the released mob's vanilla attributes (max health, attack damage, armor, movement speed) so stats and evolution have real gameplay effect

### Fixed
- HUD stale-state bug: `PetHudOverlay` now reads `PetData` directly from the held capture ball's data component each frame instead of relying on a server-pushed `PetSyncPacket`, which never fired on release and left the HUD showing outdated stats

### Removed
- `PetSyncPacket`, `PetNetworkHandler`, `ClientPetState` — redundant once the HUD reads the (already vanilla-synced) ItemStack data component directly; see REUSED_FROM.md for rationale

## [Unreleased] — v0.2.0 (Phase 2, in progress)

### Added
- `component/PetOwnerData.java` + `component/ModAttachments.java`: NeoForge entity attachments giving released pets a persistent owner UUID (`PET_OWNER`) and live stat snapshot (`RELEASED_PET_DATA`) independent of the original capture ball item
- `event/PetBattleEvent.java`: pet-vs-pet battle system. Right-click another player's released pet while holding an empty capture ball to challenge it with your own nearby released pet; resolved instantly via derived battle power (`hp + atk*2 + def + spd`); winner gains XP and has stats reapplied; 30-second per-pet cooldown prevents rapid re-challenge XP farming

### Fixed
- Re-capturing a previously-released pet no longer resets it to species base stats — `CaptureBallItem` now checks for an existing `RELEASED_PET_DATA` attachment and preserves battle/XP progress if present
