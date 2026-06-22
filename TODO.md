# TODO

## Week 1 — Complete

- [x] Define `PetData` record + Codec/StreamCodec
- [x] Register `ModDataComponents`
- [x] Implement `CaptureBallItem` capture interaction
- [x] Implement release interaction
- [x] Build succeeds
- [x] XP system (kill-based) + evolution stage-up
- [x] HUD overlay + tooltip display

## Current (Week 2)

- [ ] In-game smoke test: capture → kill mob/craft/explore for XP → evolve → release (requires manual play-test)
- [x] Additional XP sources: crafting (`PlayerEvent.ItemCraftedEvent`), exploration (`Player.walkDist` delta, 20-tick batched)
- [x] Stat-based combat effects: `PetStatApplier` sets MAX_HEALTH/ATTACK_DAMAGE/ARMOR from PetData and scales MOVEMENT_SPEED relative to vanilla base, applied to the released mob on spawn
- [x] Multiple species base-stat variation (hostile/passive/neutral categories via `SpeciesStats`)
- [x] Creative tab registration for `CaptureBallItem` (added to Tools & Utilities)

## Technical debt flagged during Week 2

- [x] Fixed: exploration/tick XP tracking now cleans up per-player map entries on `PlayerLoggedOutEvent`
- [x] Fixed: HUD showed stale pet stats after release (no packet fired on release) — removed push-packet networking entirely, HUD now reads the held item's data component directly each frame

## Week 3

- [ ] In-game smoke test of full capture → XP → evolve → release → combat-stat-effect loop (requires manual play-test)
- [ ] Testing pass, RC1 → RC2 → RC3

## Phase 2 — Battle System

- [x] `PET_OWNER`/`RELEASED_PET_DATA` attachments for persistent released-pet identity
- [x] `PetBattleEvent`: right-click challenge, nearby-pet lookup, instant power-based resolution, XP reward
- [x] Fix: re-capturing a released pet preserves progress instead of resetting to species base
- [ ] In-game smoke test: release two pets (different players/test accounts), trigger a battle, confirm winner XP + message, confirm re-capture preserves progress (requires manual play-test)
- [x] Fixed: 30-second per-pet battle cooldown added to prevent rapid re-challenge XP farming

## Phase 2 — Rarity Tiers

- [x] `PetRarity` enum: COMMON/UNCOMMON/RARE/EPIC, weighted roll, stat multiplier
- [x] Applied at capture time in `SpeciesStats.baseStatsFor`
- [x] Tooltip + HUD color-coded rarity display
- [ ] In-game smoke test: capture several mobs, confirm rarity distribution looks roughly weighted-correct and stat multipliers apply (requires manual play-test)

## Phase 2 — Breeding Mechanics

- [x] `PetBreedingEvent`: right-click your own fully-evolved released pet (with an empty capture ball) while another of your own fully-evolved pets of the same species is nearby to breed an offspring
- [x] Offspring stats are the average of both parents' stats, with a 5% chance to roll a rarity upgrade (scaling stats up proportionally)
- [x] 5-minute per-pet breeding cooldown to prevent rapid re-breeding
- [ ] In-game smoke test: breed two fully-evolved same-species pets, confirm offspring stats/rarity and cooldown behavior (requires manual play-test)
