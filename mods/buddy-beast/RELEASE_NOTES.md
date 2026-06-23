//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

# Release Notes

## v0.1.0-rc1 — Core Systems Validation

All core systems implemented and verified server-side (RCON) and via single-client
testing: entity spawning, goal-based AI (follow/stay/idle/look-at-owner), right-click
taming, NBT persistence across world reload and full server restart, health sync
over the network, and the two-headed cow placeholder rendering. No outstanding bugs
from Weeks 1-3 testing.

## v0.1.0-rc2 — Multiplayer & Bug-Fix Candidate

Added a second live client (separate machine) for real multiplayer validation:
taming, stay/follow toggle, pathfinding (including swimming), damage/death, and
buddy visibility were all confirmed working across machines. Three bugs found and
fixed during this round:
- Two-headed cow rear-head offset wasn't baked into the code default, causing a
  visual mismatch on machines without a locally tuned dev config
- A stale dev config left over from before that fix kept overriding the corrected
  default on one test machine
- `/buddybeast killall` used the wrong bounding box and always removed 0 entities

## v0.1.0-rc3 — Release Validation

Performance validated via `/buddybeast spawnmany 30` with no issues. Despawn/
chunk-unload behavior audited and confirmed correct (tamed buddies persist via
`setPersistenceRequired()`, untamed buddies despawn like normal mobs by design).
Documentation (CHANGELOG, KNOWN_ISSUES, TECHNICAL_DEBT) brought up to date with
actual tested state. Decision made to ship the two-headed cow placeholder as the
v0.1.0 appearance; a custom model is deferred to v0.2.0+ per MVP-first scope.

## v0.1.0 — Final

All RC rounds passed with no remaining open issues. First public-readiness
checkpoint for Buddy Beast.
