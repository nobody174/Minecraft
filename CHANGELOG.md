# Changelog

All notable changes to Pet Evolution are documented here.

## [Unreleased] — v0.1.0

### Added
- Project scaffolding (Gradle, NeoForge metadata, folder structure)
- Initial mod class and event bus registration
- `PetData` data component (Codec + StreamCodec) with HP/ATK/DEF/SPD/XP/evolution stage
- `CaptureBallItem`: capture wild mobs (right-click), release them back into the world (right-click block)
- Capture restrictions: players, vehicles, leashed mobs cannot be captured
- Networking: `PetSyncPacket`/`PetNetworkHandler` (pattern reused from buddy-beast)
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
