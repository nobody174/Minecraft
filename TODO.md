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

- [x] In-game smoke test: capture and release confirmed working (cow/pig/sheep); HUD and tooltip confirmed showing live stats on the held ball
- [x] Additional XP sources: crafting (`PlayerEvent.ItemCraftedEvent`), exploration (`Player.walkDist` delta, 20-tick batched)
- [x] Stat-based combat effects: `PetStatApplier` sets MAX_HEALTH/ATTACK_DAMAGE/ARMOR from PetData and scales MOVEMENT_SPEED relative to vanilla base, applied to the released mob on spawn
- [x] Multiple species base-stat variation (hostile/passive/neutral categories via `SpeciesStats`)
- [x] Creative tab registration for `CaptureBallItem` (added to Tools & Utilities)

## Technical debt flagged during Week 2

- [x] Fixed: exploration/tick XP tracking now cleans up per-player map entries on `PlayerLoggedOutEvent`
- [x] Fixed: HUD showed stale pet stats after release (no packet fired on release) — removed push-packet networking entirely, HUD now reads the held item's data component directly each frame

## Week 3

- [x] In-game smoke test of capture → release → combat-stat-effect loop confirmed (capture/release verified working end-to-end)
- [ ] Full XP → evolve loop and RC1 → RC2 → RC3 testing pass still outstanding

## Phase 2 — Battle System

- [x] `PET_OWNER`/`RELEASED_PET_DATA` attachments for persistent released-pet identity
- [x] `PetBattleEvent`: right-click challenge, nearby-pet lookup, instant power-based resolution, XP reward
- [x] Fix: re-capturing a released pet preserves progress instead of resetting to species base
- [ ] In-game smoke test: release two pets (different players/test accounts), trigger a battle, confirm winner XP + message, confirm re-capture preserves progress (requires a second test account/player — still outstanding)
- [x] Fixed: 30-second per-pet battle cooldown added to prevent rapid re-challenge XP farming

## Phase 2 — Rarity Tiers

- [x] `PetRarity` enum: COMMON/UNCOMMON/RARE/EPIC, weighted roll, stat multiplier
- [x] Applied at capture time in `SpeciesStats.baseStatsFor`
- [x] Tooltip + HUD color-coded rarity display
- [x] In-game smoke test: captured multiple mobs, confirmed COMMON (white) and RARE (blue) rolls with correct stat multipliers applied and color-coded tooltip/HUD display

## Phase 2 — Breeding Mechanics

- [x] `PetBreedingEvent`: right-click your own fully-evolved released pet (with an empty capture ball) while another of your own fully-evolved pets of the same species is nearby to breed an offspring
- [x] Offspring stats are the average of both parents' stats, with a 5% chance to roll a rarity upgrade (scaling stats up proportionally)
- [x] 5-minute per-pet breeding cooldown to prevent rapid re-breeding
- [x] In-game smoke test: confirmed the evolution-stage gate correctly rejects breeding attempts on non-fully-evolved pets (chat message shown as designed)
- [ ] Still outstanding: confirm a successful breed (two fully-evolved same-species pets) actually spawns an offspring with correct averaged stats/rarity
