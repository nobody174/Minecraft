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

## Phase 3 — v2.0.0 Major Expansion (built autonomously; everything below requires manual play-testing — none of it has been verified in an actual running game, only by successful compilation and code-tracing)

### Modular restructure
- [x] Package restructure into capture/creature/battle/skills/client.ui/render — build-verified, zero intended behavior change
- [ ] In-game smoke test: confirm capture/release/XP/evolve/battle/breed all still work identically post-refactor (requires manual play-test)

### Creature stats/leveling
- [x] `special` 5th core stat added to `PetData`, `StreamCodec` re-nested to fit
- [x] `level()` derived from `evoStage` (no parallel level field)
- [x] `SpeciesStats`/`PetStatApplier`/`EvolutionRules`/tooltip/HUD/breeding updated for Special
- [ ] In-game smoke test: capture a mob, confirm Special stat shows correctly in tooltip/HUD, evolve and confirm Special increases (requires manual play-test)

### Skills system
- [x] `Skill`/`SkillEffectType`/`SkillElement`/`SkillRegistry` (fixed in-code registry, not JSON datapack — see CHANGELOG.md for reasoning)
- [x] Level-gated skill unlocking (3-5 skills by full evolution)
- [ ] In-game smoke test: confirm a freshly-captured pet has fewer unlocked skills than a fully-evolved one (requires manual play-test)

### Battle engine
- [x] `BattleParticipant`/`BattleAi`/`BattleSession`/`BattleEngine` replacing instant stat comparison
- [x] HP-based auto-combat behavior change (defensive/heal preference below 35% HP)
- [x] Skill cooldowns enforced via tick-based round resolution (one round per 2 seconds)
- [x] Existing right-click trigger, nearby-pet lookup, and 30-second cooldown preserved
- [x] Fixed during regression pass: damage advantage multiplier now uses the actually-applied skill's element, not a re-derived one
- [ ] In-game smoke test: trigger a battle between two released pets, confirm it takes multiple rounds (not instant), confirm low-HP creature uses a defensive/heal skill, confirm winner gets XP (requires manual play-test)

### Minimal battle UI + networking
- [x] `BattleHudOverlay` (HP bars + numbered skill prompts), `BattleSkillChoicePayload`, `BattleStateSyncPayload`, `BattleNetworking`
- [ ] In-game smoke test: confirm the battle HUD appears when a battle starts, confirm pressing 1-5 during the input window actually overrides the AI's choice, confirm the HUD disappears when the battle ends (requires manual play-test — this is the area most likely to have an unnoticed client-rendering or packet-registration issue, since it could not be visually verified in this autonomous run)

### Original capture device visuals
- [x] "Rune-Bound Vessel" item model + procedurally-generated textures (1 default + 4 rarity variants, rarity switching NOT yet wired up — documented limitation)
- [ ] In-game smoke test: confirm the capture ball renders with the new texture instead of the missing-texture placeholder it had before (requires manual play-test — also could not be visually verified in this autonomous run)
- [ ] Follow-up (not done): wire up rarity-based texture switching via a custom ItemModel or custom_model_data range_dispatch (see FUTURE_FEATURES.md)

### Testing infrastructure
- [x] `PetEvolution.DEBUG_LOGGING` toggle (`-Dpetevolution.debug=true`)
- [x] `/petevolution test` command spawns a fully-statted, fully-skilled test pet
- [ ] In-game smoke test: run `/petevolution test`, confirm a wolf spawns with the expected stats and can immediately participate in a battle (requires manual play-test)
