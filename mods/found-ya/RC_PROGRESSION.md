# TrackerVision RC Progression — v1.0.0

Following **RELEASE_CANDIDATE_SYSTEM.md** strictly. No shortcuts.

---

## RC1 → RC2 → RC3 → Release

### ✅ v1.0.0-RC1 (2026-06-25)
**Feature-Complete Candidate**

- All v1.0 features implemented
  - ✅ Beacon mode (sky-to-target 300px pillar)
  - ✅ Search mode (persistent multi-entity reveal)
  - ✅ Multiple tracking profiles (Default/PvP/Exploration)
  - ✅ Rim-boost shader post-process
- All critical bugs fixed
  - ✅ Entity selector @e[...] NullPointerException
  - ✅ Server-side client-class loading error
- Build succeeds cleanly
- Git tagged: v1.0.0-RC1

---

### 🔄 v1.0.0-RC2 (2026-06-26 → 2026-06-30)
**Bug-Fix Candidate — IN PROGRESS**

**What's Done:**
- ✅ All v1.0 features working (verified via 10/10 critical manual tests)
- ✅ All critical bugs fixed
- ✅ RC2_VALIDATION_PLAN.md created with acceptance criteria
- ✅ Documentation updated (README, CHANGELOG, TODO)

**What's Pending (User Testing Required):**
- ⏳ Performance profiling pass (30 min)
  - FPS stability under tracking load
  - Memory leak check
  - Shader toggle cycles
- ⏳ Extended 4+ hour play-testing (edge cases, stress scenarios)
  - Peaceful/Creative mode scenarios
  - Survival mode natural gameplay
  - Entity selector edge cases
  - Config extremes
  - Terrain occlusion verification
  - Profile persistence across restart
- ⏳ Documentation polish (ARCHITECTURE.md, KNOWN_ISSUES.md updates)

**Promotion Criteria:**
- Zero crashes during extended testing
- Performance stable (steady 60 FPS, no memory leaks)
- All expected behaviors verified
- No new critical bugs found
- Docs complete and accurate

**Timeline:**
- 2026-06-27: Performance profiling + short play-test
- 2026-06-28: Extended 4+ hour play-testing
- 2026-06-29: Docs polish
- 2026-06-30: Final RC2 review
- **2026-07-03**: v1.0.0-RC2 tag (if all pass)

---

### ⏳ v1.0.0-RC3 (2026-07-03 → 2026-07-10)
**Release Validation Candidate — PENDING**

**What to Expect:**
- Final code freeze (no new features, only critical fix-only)
- RC3_VALIDATION_PLAN.md with final checklist
- Go/No-Go decision for Release
- One final 2+ hour play-test pass

**Promotion Criteria:**
- All RC2 bugs fixed
- All RELEASE_CHECKLIST.md items verified
- Documentation final
- Public release readiness confirmed

---

### ⏳ v1.0.0 Final Release (2026-07-10)
**Production Release**

**What Happens:**
- v1.0.0 tag created (no -RC suffix)
- Repository made public on GitHub
- GitHub Actions CI/CD workflows enabled
- GitHub Release published with JAR artifact
- Announcement (Discord, Reddit, etc.)
- v1.0.0 added to public release tracking

---

## Key Dates & Responsibilities

| Date | Milestone | Owner | Status |
|------|-----------|-------|--------|
| 2026-06-25 | RC1 tagged | Autonomous | ✅ Done |
| 2026-06-26 | RC2 validation plan created | Autonomous | ✅ Done |
| 2026-06-27 | Perf profiling + short test | **User** | ⏳ Pending |
| 2026-06-28 | Extended 4+ hr play-test | **User** | ⏳ Pending |
| 2026-06-29 | Docs polish + fixes | Autonomous | ⏳ Pending |
| 2026-06-30 | RC2 final review | **User** | ⏳ Pending |
| 2026-07-03 | RC2 tag + RC3 plan | Autonomous | ⏳ Pending |
| 2026-07-10 | RC3 tag + Release | Autonomous | ⏳ Pending |

---

## Ruleset Compliance

This progression follows RELEASE_CANDIDATE_SYSTEM.md:

✅ **RC1:** Feature-complete, buildable, basic testing  
✅ **RC2:** Bug-fix candidate, extended testing, user validation required  
✅ **RC3:** Release validation, final checklist, go/no-go decision  
✅ **Release:** Production-ready, public, CI/CD active  

**No shortcuts. No skipping phases. Quality > Speed.**

---

## Next Step for You

When ready, load up Minecraft with the latest JAR and start testing:

1. **Performance test** (30 min)
   - Lock target and track for 30 min
   - Monitor FPS stability
   - Toggle search mode with many entities nearby
   - Toggle rim-boost shader 5+ times
   - Check for frame-rate hiccups or memory leaks

2. **Extended play-testing** (4+ hours)
   - Follow scenarios in RC2_VALIDATION_PLAN.md
   - Test edge cases (off-screen, occlusion, extremes)
   - Try entity selector variations
   - Switch profiles frequently
   - Save/load/restart to verify persistence

Report your findings in your next message, and I'll:
- Fix any bugs found
- Polish documentation
- Tag v1.0.0-RC2
- Create v1.0.0-RC3 plan
- Target final release 2026-07-10

