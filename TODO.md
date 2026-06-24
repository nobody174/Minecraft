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
- [x] Additional XP sources: crafting (`PlayerEvent.ItemCraftedEvent`), exploration (`Player.walkDist` delta, 20-tick batched), mining/chopping (`BlockEvent.BreakEvent`, 1 XP/block)
- [x] In-game smoke test: confirmed kill XP grants correctly via off-hand vessel; confirmed walking-only exploration XP did NOT cover block-breaking as expected — fixed with the BlockEvent.BreakEvent source above (still needs in-game re-test)
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
- [x] Fixed: DEFENSE_BUFF skill effect was a documented no-op — now halves the next incoming damage instance, consumed on that hit
- [x] In-game smoke test (real 2-player co-op via laptop): battle triggered correctly, HUD/HP bars showed for both pets, took multiple rounds, winner message displayed correctly — confirmed working
- [x] Found and fixed: pets visually wandered independently with normal AI during battle, totally decoupled from the HP/skill simulation — added `BattleVisuals` (AI freeze, mutual facing, per-round lunge)
- [x] In-game smoke test (2-player co-op): pets froze and faced each other correctly, confirmed working
- [x] Found and fixed: pets ended up standing inside each other after a few rounds — lunge had no way to return home, so repeated lunges compounded; now records a home spot per entity and returns to it the tick after lunging
- [ ] In-game smoke test: re-test the same battle now that the lunge/overlap fix is in place — confirm pets stay visually separated across many rounds instead of converging; confirm low-HP creature uses a defensive/heal skill and DEFENSE_BUFF actually reduces the next hit (requires manual play-test)

### Minimal battle UI + networking
- [x] `BattleHudOverlay` (HP bars + numbered skill prompts), `BattleSkillChoicePayload`, `BattleStateSyncPayload`, `BattleNetworking`
- [ ] In-game smoke test: confirm the battle HUD appears when a battle starts, confirm pressing 1-5 during the input window actually overrides the AI's choice, confirm the HUD disappears when the battle ends (requires manual play-test — this is the area most likely to have an unnoticed client-rendering or packet-registration issue, since it could not be visually verified in this autonomous run)

### Original capture device visuals
- [x] "Rune-Bound Vessel" item model + procedurally-generated textures (1 default + 4 rarity variants)
- [x] Rarity-based texture switching wired up via vanilla `custom_model_data` overrides (the only mechanism available pre-1.21.2); set on capture via `PetData.syncCustomModelData()`, cleared on release
- [ ] In-game smoke test: confirm the capture ball renders with the new texture, and that the texture changes to match rolled rarity (common/uncommon/rare/epic), and reverts to default after release (requires manual play-test)

### Testing infrastructure
- [x] `PetEvolution.DEBUG_LOGGING` toggle (`-Dpetevolution.debug=true`)
- [x] `/petevolution test` command spawns a fully-statted, fully-skilled test pet
- [ ] In-game smoke test: run `/petevolution test`, confirm a wolf spawns with the expected stats and can immediately participate in a battle (requires manual play-test)
