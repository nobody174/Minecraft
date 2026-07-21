# TrackerVision v1.0.0-RC2 Validation Plan

**Status:** Bug-fix candidate (in progress)  
**Target Release Date:** 2026-07-03 (v1.0.0-RC3) → 2026-07-10 (v1.0.0 Final)

---

## RC2 Acceptance Criteria

Per RELEASE_CANDIDATE_SYSTEM.md, RC2 requires:

- ✅ All v1.0.0 features working (all 4 implemented and tested)
- ✅ Extended in-client testing (10/10 critical manual tests passed)
- ✅ All critical bugs fixed (entity selector NPE fixed; server client-class loading fixed)
- ⏳ Performance profiling pass
- ⏳ Extended 4+ hour play-testing with edge cases
- ⏳ Documentation polish
- ⏳ v1.0.0-RC2 tag (ready to commit after validation)

---

## Tasks Remaining for RC2

### 1. Performance Profiling ⏳

**Why:** Ensure no frame-rate hitches or memory leaks under typical gameplay.

**What to test:**
- Lock target and track for 30+ minutes; monitor FPS in F3 debug screen
- Rapid lock/unlock cycles (10+ times) — no stutters
- Search mode enabled with 20+ entities nearby — measure FPS impact
- Config screen open/close cycles — no lag
- Rim-boost shader: toggle on/off 5 times, verify no texture memory buildup

**Pass Criteria:**
- Steady 60 FPS with tracking enabled (no frame-time spikes > 5ms)
- No memory leaks (Java heap stable after GC)
- Rim-boost shader doesn't consume extra VRAM after toggle cycles

**Files to check:**
- `RimBoostEffect.java`: TextureTarget cleanup on toggle
- `SearchModeScanner.java`: 10-tick throttle prevents per-frame overhead
- `TrackedTargetGlowRenderer.java`: RenderType caching prevents allocations

---

### 2. Extended Play-Testing (4+ Hours) ⏳

**Why:** Catch edge cases and unexpected behavior under realistic gameplay.

**Scenario A: Peaceful/Creative Mode (30 min)**
- Spawn mobs with spawn eggs
- Lock/unlock each mob type: zombie, skeleton, witch, creeper, baby zombie, armored zombie
- Toggle search mode with 5+ mobs nearby
- Verify reticle, caret, silhouette, beacon all render correctly
- Test profile switching while locked to a target

**Scenario B: Survival Mode (60 min)**
- Natural mob spawning, normal survival gameplay
- Auto-select (NEAREST mode) as mobs spawn naturally
- Toggle search mode during combat
- Test beacon visibility at extreme distances (100+ blocks away)
- Save/load world — verify profile settings persisted
- Disconnect and reconnect to multiplayer server (if available)

**Scenario C: Edge Cases (90+ min)**
- **Off-screen targets:** Lock mob, move away until off-screen — caret should point to it
- **Horizontal/vertical extremes:** Lock mob far below/above player — beacon should point correctly
- **Terrain occlusion:** Lock mob behind solid walls — silhouette should show through
- **Rapid target switching:** Switch profiles 20+ times — no crashes or memory spikes
- **Entity selector edge cases:**
  - `/track lock @e[]` (empty selector)
  - `/track lock @e[type=minecraft:bat,limit=1,sort=nearest]` (flying mob)
  - `/track lock @e[type=minecraft:player]` (if multiplayer available)
  - `/track lock @e[distance=..10]` (nearby only)
- **Config extremes:**
  - Set `bracketBaseSize` to min/max values
  - Set `nearDistance` > `farDistance` (should clamp correctly)
  - Toggle `trackingEnabled` while locked — should freeze reticle
- **Shader toggle:** Toggle `rimBoostEnabled` 10+ times while locked to target with rim visible

**Pass Criteria:**
- No crashes during any scenario
- No unexpected behavior (reticle off-screen, caret pointing wrong direction, etc.)
- Silhouette visibility consistent with line-of-sight
- Profile settings persist across save/load/restart
- All commands work as documented
- Performance remains stable (no FPS drops > 10 points)

---

### 3. Documentation Polish ⏳

**Files to review and update:**

1. **ARCHITECTURE.md** — verify all system components documented correctly
   - [ ] TrackedTargetGlowRenderer section up-to-date
   - [ ] RimBoostEffect shader pipeline explained
   - [ ] SearchModeManager independent design explained
   - [ ] Profile system architecture documented

2. **README.md** — user-facing guide
   - [ ] "Known Limitations" section updated with RC2 status
   - [ ] All command examples tested and working
   - [ ] Installation section reflects current build output

3. **CHANGELOG.md** — version history clarity
   - [ ] v1.0.0-RC2 section complete and clear
   - [ ] All feature descriptions match implemented behavior

4. **KNOWN_ISSUES.md** — capture any edge cases found during testing
   - [ ] Document any unexpected behavior (even if working as designed)
   - [ ] Document any visual glitches or performance issues found

5. **TESTING_NOTES.md** (new, optional) — document testing methodology
   - [ ] How to run performance profiling
   - [ ] How to trigger edge cases
   - [ ] Expected behavior vs. actual behavior matrix

---

## Schedule

| Date | Milestone | Owner |
|------|-----------|-------|
| 2026-06-26 | RC2 Validation Plan created | Autonomous |
| 2026-06-27 | Performance profiling + short play-test | User |
| 2026-06-28 | Extended 4+ hour play-testing | User |
| 2026-06-29 | Docs polish + edge case fixes | Autonomous |
| 2026-06-30 | Final RC2 validation review | User |
| 2026-07-03 | v1.0.0-RC3 tag (if all pass) | Autonomous |
| 2026-07-10 | v1.0.0 Final release (if RC3 passes) | Autonomous |

---

## RC2 → RC3 Promotion

Once all tasks above are **complete and pass**, I will:

1. Tag v1.0.0-RC2 in git
2. Update CHANGELOG.md with RC2 completion date
3. Create v1.0.0-RC3 Validation Plan
4. Proceed to RC3 (final validation, no code changes)

---

## Notes

- This plan follows RELEASE_CANDIDATE_SYSTEM.md strictly: no skipping steps
- Quality over speed — all items must pass before promotion to RC3
- User testing is critical for RC2; I can't drive Minecraft client in this environment
- Any critical bugs found → fix immediately, re-test, then continue
- Non-critical polish → document for v1.0.1 patch

