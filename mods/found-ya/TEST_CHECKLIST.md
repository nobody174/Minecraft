# Manual Regression Test Checklist

A manual in-client pass to run after changes that touch tracking,
rendering, or the HUD. Assumes a standard vanilla Minecraft 1.21.1 world
with mobs nearby (`/summon zombie` etc. to spawn test targets if needed).
~20-30 minutes for all 10.

### 1. Off-Screen Caret (Direction Indicator)
- [ ] Lock a target
- [ ] Move so the target is behind you or off-screen
- [ ] Verify caret appears at screen edge, pointing toward target
- [ ] Caret should clamp to screen edges smoothly
- [ ] Move target back on-screen, caret should disappear

**Expected:** Directional arrow visible at screen edge, pointing to locked target

---

### 2. Silhouette Through Walls
- [ ] Lock a target
- [ ] Walk so a solid block (stone, dirt, etc.) blocks line-of-sight to target
- [ ] Verify target's glow remains visible through the wall
- [ ] Glow color should match locked-target state (red/cyan/amber)
- [ ] Walk back into line-of-sight, verify silhouette disappears

**Expected:** Faint glowing outline visible through solid blocks

---

### 3. Beacon Distance Threshold
- [ ] Lock a target far away (~100m)
- [ ] Verify sky-to-target beacon pillar is visible on screen
- [ ] Walk closer to exactly the configured beacon distance (default 48m)
- [ ] Verify beacon switches to bracket reticle as you cross the threshold
- [ ] Walk back out, beacon should reappear

**Expected:** Clean transition between bracket (close) and beacon (far)

---

### 4. Search Mode + Locked Target (Both Active)
- [ ] Lock a target: `/track lock @e[limit=1,sort=nearest]`
- [ ] Enable search mode: `/track search true`
- [ ] Look around
- [ ] Verify locked target has a bright rim, other nearby mobs a lighter rim
- [ ] The two should be visually distinct (locked = brighter)

**Expected:** Locked target stands out from search-revealed entities

---

### 5. Profile Settings Persist After Restart
- [ ] Switch to PvP profile: `/track profile use PvP`
- [ ] Adjust a setting: `/track config farDistance 30`
- [ ] Verify it changed: `/track config show` should show `farDistance=30`
- [ ] Close and restart Minecraft
- [ ] Verify PvP profile is still active and `farDistance=30` persisted

**Expected:** Settings saved to disk and reloaded on next session

---

### 6. Config Screen Profile Cycling
- [ ] Open config screen (mod list → Config button)
- [ ] Click "Profile: [name] (click to cycle)"
- [ ] Should cycle through all profiles; sliders/checkboxes update each time

**Expected:** Profile button cycles through all profiles, sliders update instantly

---

### 7. Nearest Mode Auto-Select Chain
- [ ] `/track mode nearest` (or Track → Nearest in the menu)
- [ ] Gather 3+ mobs nearby
- [ ] Lock should be on the closest one
- [ ] Kill that mob, verify lock automatically switches to the next-closest

**Expected:** Seamless auto-selection as closest mob dies

---

### 8. Hostile vs Passive Target Colors
- [ ] Lock a zombie (hostile) — reticle should be red
- [ ] Lock a sheep (passive) — reticle should be cyan
- [ ] Walk beyond far-distance from the locked target — reticle should be amber

**Expected:** Hostile=Red, Passive=Cyan, OutOfRange=Amber

---

### 9. Bracket Size Scaling with Distance
- [ ] Lock a target at near-distance — bracket bright and full-sized
- [ ] Walk to far-distance — bracket dim and small
- [ ] Walk back — verify smooth fade, not jumpy

**Expected:** Smooth distance-based alpha/scale falloff

---

### 10. Reticle Motion (Breathing + Pulse)
- [ ] Lock a fresh target (not previously locked this session)
- [ ] Watch for ~2s — should see a lock-acquired pulse (grows then settles)
- [ ] Watch for ~10s more — should see subtle continuous breathing
- [ ] Motion should be smooth, not jittery

**Expected:** Pulse on lock + continuous breathing, smooth animation

---

## Menu-specific checks (added with the Found Ya! menu UI)

- [ ] Open menu via `K` and via bare `/track` — both open the same compact,
      bottom-left anchored menu
- [ ] Track → Nearest Player while solo shows "No other players nearby."
      in chat and closes the menu (doesn't get stuck in a submenu)
- [ ] Track → Nearest Enemy/Friendly opens a type submenu (zombie/skeleton/
      etc., or wolf/cow/etc.) with an "All Enemies"/"All Friendlies" option
- [ ] Lock (top menu) freezes the current auto-followed target; shows
      "Nothing is currently being tracked." if nothing was being followed
