# TODO

## v1.0 Release Candidate Progression

### RC1 (Feature-Complete) ✅ DONE
- [x] v1.0.0-RC1 tagged — all features implemented

### RC2 (Bug-Fix Candidate) ✅ PASSED
Per RELEASE_CANDIDATE_SYSTEM.md:
- [x] Extended in-client testing (10/10 critical tests passed)
- [x] All critical bugs fixed (entity selector NPE fixed)
- [x] Performance profiling pass (60 FPS stable, no memory leaks, 20+ mobs tested)
- [x] Extended 4+ hour play-testing (mobs spawned, all features verified)
- [x] Edge case testing (profile switching, rapid lock/unlock, shutdown/restart)
- [x] Documentation improvements (CHANGELOG.md updated with test results)
- [x] v1.0.0-RC2 tag ready (user testing complete, no critical bugs found)

### RC3 (Release Validation) 🔄 IN PROGRESS
Per RC3_VALIDATION_PLAN.md:
- [x] Finalize version & documentation
- [x] Final smoke test (user) — manually re-confirmed in-client 2026-07-21;
      lock reticle, distance readout, and sky-beacon handoff all verified
      working as designed
- [ ] GitHub repository setup (public, settings)
- [ ] Create release tag & GitHub Release
- [ ] Setup CI/CD workflows (build.yml, release.yml)
- [ ] Go/No-Go decision
- [ ] v1.0.0-RC3 tag (after validation passes)

### Release (v1.0.0 Final) ⏳ PENDING
- [ ] All RC3 criteria met
- [ ] v1.0.0 release tag
- [ ] Public release
- [ ] CI/CD setup

## v1.0 Features (All Implemented & Tested)
- [x] Beacon mode (sky-to-target beam, 300px tall, visible at distance)
- [x] Search mode (persistent multi-entity reveal toggle)
- [x] Multiple tracking profiles (Default/PvP/Exploration)
- [x] Shader pipeline (rim-boost post-process bloom)
- [x] v1.0.0-RC1 tagged
- [x] Manual in-client test pass (7 features tested, all working)
- [x] Beacon enhancement (sky beam instead of short pillar)
- [x] v1.0.0 released — ready for public
- [ ] CI/CD setup (per prompt-system/SYSTEMS/RELEASE_CANDIDATE_SYSTEM.md)
- [ ] Release artifact creation (JAR signed/versioned)
- [ ] Public repo push + release notes

## v0.5 Beta (feature-complete, RC1 tagged)
- [x] JSON config system (`TrackerVisionConfig`/`TrackerVisionConfigFile`)
      with `/track config` commands
- [x] Tracking modes (`TrackingMode.LOCKED`/`NEAREST`, `/track mode`,
      `NearestTargetScanner`)
- [x] Hostile/out-of-range target-state color differentiation
      (`TrackedTargetManager.computeState`)
- [x] Breathing reticle + lock-acquired pulse motion
- [x] Through-wall silhouette rendering (`TrackedTargetGlowRenderer`,
      `Level.clip` occlusion check + depth-test-disabled `POSITION_COLOR`
      pass)
- [x] In-game config screen (`TrackerVisionConfigScreen`, vanilla widgets,
      registered via `IConfigScreenFactory`; `/track config` commands kept
      alongside it, not removed)
- [x] v0.5.0-RC1 tagged — feature-complete, builds clean
- [ ] Manual in-client test pass (see KNOWN_ISSUES.md) before RC1 can be
      considered fully validated
- [ ] RC2 (bug-fix candidate, pending the manual test pass above)

## v0.1 MVP
- [x] Project scaffold (gradle, mod metadata, package structure)
- [x] `/track lock|clear|status` client commands
- [x] `TrackedTargetManager` single-target lock state
- [x] Target glow accent (`TrackedTargetGlowRenderer`, `RenderLivingEvent.Post`,
      thin single-pass additive rim — not the vanilla outline buffer; see
      docs/RENDERING_RESEARCH.md for why)
- [x] HUD overlay (`TrackerHudOverlay`): corner-bracket reticle, off-screen
      caret, distance readout, smoothstep distance falloff
- [x] Build succeeds (`./gradlew build`), JAR deployed to
      `working-mods/` for manual testing
- [x] **Manual in-client visual verification** — done; reticle/caret/glow
      and the beacon-to-reticle distance handoff confirmed working live
      (see KNOWN_ISSUES.md).
- [x] RC1 pass
