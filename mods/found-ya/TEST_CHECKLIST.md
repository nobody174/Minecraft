# TrackerVision v1.0.0 — Test Checklist

Complete manual in-client test pass for v1.0.0 release validation.

## Core Features (7 items — already tested ✅)

- [x] Lock target with `/track lock @e[limit=1,sort=nearest]`
- [x] Search Mode toggle `/track search true|false`
- [x] Multiple profiles `/track profile use <name>`
- [x] Config screen (mod list → Config button)
- [x] Beacon mode visible at distance
- [x] Through-wall silhouette rendering
- [x] Rim-boost shader (post-process bloom)

---

## Extended Testing (10 critical items — test these now)

### 1. Off-Screen Caret (Direction Indicator)
- [ ] Lock a target
- [ ] Move so the target is behind you or off-screen
- [ ] Verify yellow **caret appears at screen edge** pointing toward target
- [ ] Caret should clamp to screen edges smoothly
- [ ] Move target back on-screen, caret should disappear

**Expected:** Directional arrow visible at screen edge, pointing to locked target

---

### 2. Silhouette Through Walls
- [ ] Lock a target
- [ ] Walk so a solid block (stone, dirt, etc.) blocks line-of-sight to target
- [ ] Verify target's **glow remains visible through the wall**
- [ ] Glow color should match locked-target state (red/cyan/amber)
- [ ] Walk back into line-of-sight, verify silhouette disappears

**Expected:** Faint glowing outline visible through solid blocks

---

### 3. Beacon Distance Threshold
- [ ] Lock a target far away (~100m)
- [ ] Verify **sky-to-target beacon pillar** is visible on screen
- [ ] Walk closer to exactly 48m away (beacon distance)
- [ ] Verify beacon **switches to bracket reticle** as you cross the threshold
- [ ] Walk back to 50m, beacon should reappear

**Expected:** Clean transition between bracket (close) and beacon (far)

---

### 4. Search Mode + Locked Target (Both Active)
- [ ] Lock a target: `/track lock @e[limit=1,sort=nearest]`
- [ ] Enable search mode: `/track search true`
- [ ] Look around
- [ ] Verify locked target has **bright rim**
- [ ] Verify other nearby mobs have **lighter rim**
- [ ] The two should be visually distinct (locked = brighter)

**Expected:** Locked target stands out from search-revealed entities

---

### 5. Profile Settings Persist After Restart
- [ ] Switch to PvP profile: `/track profile use PvP`
- [ ] Adjust a setting: `/track config farDistance 30`
- [ ] Verify it changed: `/track config show` should show `farDistance=30`
- [ ] **Close and restart Minecraft**
- [ ] Verify PvP profile is still active
- [ ] Verify `farDistance=30` is still set (didn't reset to 64)

**Expected:** Settings saved to disk and reloaded on next session

---

### 6. Config Screen Profile Cycling
- [ ] Open config screen (mod list → Config button)
- [ ] Click **"Profile: [name] (click to cycle)"** button
- [ ] Should cycle: Default → PvP → Exploration → Default
- [ ] After each click, verify all sliders/checkboxes update
- [ ] e.g., PvP should have shorter `farDistance`, red accent color
- [ ] Exploration should have longer `farDistance`, cyan color

**Expected:** Profile button cycles through all profiles, sliders update instantly

---

### 7. Nearest Mode Auto-Select Chain
- [ ] `/track mode nearest` (auto-select mode)
- [ ] Gather 3+ mobs nearby
- [ ] Lock should be on the closest one
- [ ] Kill that mob
- [ ] Verify lock **automatically switches to the next-closest mob**
- [ ] Kill that mob, verify lock switches again

**Expected:** Seamless auto-selection as closest mob dies

---

### 8. Hostile vs Passive Target Colors
- [ ] Lock a **zombie** (hostile): `/track lock @e[type=zombie,limit=1,sort=nearest]`
- [ ] Reticle bracket should be **RED**
- [ ] Lock a **sheep** (passive): `/track lock @e[type=sheep,limit=1,sort=nearest]`
- [ ] Reticle bracket should be **CYAN**
- [ ] Walk beyond far-distance (default 64m) from locked target
- [ ] Bracket should turn **AMBER** (out of range)

**Expected:** Color changes: Hostile=Red, Passive=Cyan, OutOfRange=Amber

---

### 9. Bracket Size Scaling with Distance
- [ ] Lock a target at near-distance (8m)
- [ ] Bracket should be **bright and full-sized**
- [ ] Walk to far-distance (64m)
- [ ] Bracket should be **dim and small**
- [ ] Walk back to near-distance
- [ ] Verify smooth fade, not jumpy

**Expected:** Smooth distance-based alpha/scale falloff

---

### 10. Reticle Motion (Breathing + Pulse)
- [ ] Lock a fresh target (hasn't been locked before in this session)
- [ ] Watch the bracket for 2 seconds
- [ ] Should see **lock-acquired pulse** (scale grows then settles, ~1.5s)
- [ ] Watch for next 10 seconds
- [ ] Should see **subtle breathing** (continuous gentle scale oscillation)
- [ ] Motion should be smooth, not jittery

**Expected:** Pulse on lock + continuous breathing, smooth animation

---

## Optional Polish Tests (5 items — can defer to v1.0.1)

- [ ] Iris/shader pack compatibility (enable Iris, verify no black screen or shader conflicts)
- [ ] Server loading (dedicated server starts cleanly with mod installed)
- [ ] Entity selector variants (try `@e[type=zombie]`, `@e[name=foo]`, etc.)
- [ ] FOV zoom interaction (zoom in with spyglass, verify reticle stays positioned correctly)
- [ ] Simultaneous lock in multiplayer (if applicable)

---

## Summary

**Critical Path (10 tests above):** ~20-30 minutes total
**Full Path (15 tests):** ~45 minutes total

After all 10 critical tests pass, v1.0.0 is **production-ready for public release**.

All tests assume a standard vanilla Minecraft 1.21.1 world with mobs nearby. Use `/summon zombie` to spawn test targets if needed.

---

## Notes

- Latest JAR: `trackervision-1.0.0-mc1_21_1.jar`
- Updated: 2026-06-26
- Last changes: Entity selector fix + sky-beam beacon enhancement
