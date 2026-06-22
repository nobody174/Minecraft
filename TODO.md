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
