# TrackerVision v1.0.0 — Public Release Checklist

**Following RELEASE_CANDIDATE_SYSTEM.md and prompt-system guidelines.**

## Pre-Release Requirements (RC3 Pass Gate)

### Code Quality ✅
- [x] All v1.0.0 features implemented and tested
- [x] Manual in-client testing complete (10/10 critical tests)
- [x] Build succeeds: `./gradlew build`
- [x] JAR artifact generated: `build/libs/trackervision-1.0.0-mc1_21_1.jar`

### Documentation ✅
- [x] README.md complete with usage, installation, features
- [x] CHANGELOG.md complete with v1.0.0 release notes
- [x] ARCHITECTURE.md documented (system design)
- [x] TEST_CHECKLIST.md created (manual test procedures)
- [x] FUTURE_FEATURES.md updated with v1.0.1 roadmap
- [x] Known limitations disclosed in README

### Git State ✅
- [x] .gitignore configured to exclude private/dev files
- [x] All commits pushed to origin/main
- [x] Branch: `main` (consistent with other mods)
- [x] Latest commit tagged: v1.0.0-RC1

### Repository Setup ⏳ (RC3 → Release)
- [ ] Repository visibility: Public on GitHub
- [ ] Repository description set
- [ ] Topics added: minecraft, neoforge, mod, java, 1.21
- [ ] License: Set (All rights reserved)
- [ ] Default branch: main

## Stage 1: RC3 Pass Gate (User + Autonomous)

**Milestone:** 2026-07-03  
Per RC2_VALIDATION_PLAN.md:
- [ ] Performance profiling passed (60 FPS stable, no memory leaks)
- [ ] Extended 4+ hour play-testing passed (all edge cases verified)
- [ ] Documentation polish complete
- [ ] Zero critical bugs found during RC2 testing
- [ ] v1.0.0-RC2 tag created

**Then:** Create RC3_VALIDATION_PLAN.md and proceed to Stage 2

---

## Stage 2: Pre-Release Repository Setup (Autonomous)

**Milestone:** 2026-07-05 (after RC3 validation)  
Before making repo public, ensure GitHub settings are correct:

### Repository Settings
- [ ] Go to https://github.com/nobody174/trackervision/settings
- [ ] **General** tab:
  - [ ] Description: "A modern entity tracking framework for NeoForge 1.21.1"
  - [ ] Topics: Add `minecraft`, `neoforge`, `mod`, `java`, `1.21`
  - [ ] Default branch: Ensure set to `main`
- [ ] **Visibility** section:
  - [ ] Click "Change visibility"
  - [ ] Select "Public"
  - [ ] Confirm

### Verify Git Cleanliness
- [ ] `git status` shows clean (no uncommitted changes)
- [ ] All v1.0.0 changes committed
- [ ] Latest tag: v1.0.0-RC3 (or higher for release)
- [ ] gradle.properties: `mod_version=1.0.0` (no -RC suffix)
- [ ] CHANGELOG.md: v1.0.0 section with final date
- [ ] README.md: Status shows "Released"

---

## Stage 3: Create Release Tag & GitHub Release (Autonomous)

**Milestone:** 2026-07-07  
See GITHUB_RELEASE_WORKFLOW.md for detailed steps:

### Create Annotated Release Tag
- [ ] Update gradle.properties: `mod_version=1.0.0`
- [ ] Update CHANGELOG.md: `## [1.0.0] - 2026-07-10`
- [ ] Update README.md: Status = "**v1.0.0 Released**"
- [ ] Commit: `git commit -m "release: v1.0.0 final"`
- [ ] Tag: `git tag -a v1.0.0 -m "TrackerVision v1.0.0 — Production Release"`
- [ ] Push: `git push origin main && git push origin v1.0.0`

### Create GitHub Release
- [ ] Go to https://github.com/nobody174/trackervision/releases
- [ ] Click "Draft a new release"
- [ ] Tag: `v1.0.0` (should auto-populate)
- [ ] Title: "TrackerVision v1.0.0 — Entity Tracking Framework"
- [ ] Description: Use template from GITHUB_RELEASE_WORKFLOW.md
- [ ] Attach JAR: `build/libs/trackervision-1.0.0-mc1_21_1.jar`
- [ ] Pre-release: Unchecked
- [ ] Latest release: Checked
- [ ] Publish release

---

## Stage 4: Setup CI/CD Workflows (Autonomous)

**Milestone:** 2026-07-08  
See GITHUB_RELEASE_WORKFLOW.md for workflow files:

### Create GitHub Actions Workflows
- [ ] Create `.github/workflows/build.yml` (auto-build on push)
- [ ] Create `.github/workflows/release.yml` (auto-artifact on tag)
- [ ] Commit: `git add .github/ && git commit -m "ci: setup github actions workflows"`
- [ ] Push: `git push origin main`

### Verify Workflows
- [ ] Go to **Actions** tab in GitHub
- [ ] Verify both workflows appear
- [ ] Verify last push triggered build workflow
- [ ] Check that build succeeded (green checkmark)

### Test Release Workflow
- [ ] Push a test tag: `git tag v1.0.0-test && git push origin v1.0.0-test`
- [ ] Verify release workflow runs
- [ ] Verify GitHub Release created automatically
- [ ] Delete test tag if successful: `git tag -d v1.0.0-test && git push origin :v1.0.0-test`

---

## Stage 5: Final Verification (User)

**Milestone:** 2026-07-09  
Verify everything works from a user's perspective:

### Clone & Build from GitHub
- [ ] Clone fresh repo: `git clone https://github.com/nobody174/trackervision`
- [ ] Navigate: `cd trackervision`
- [ ] Build: `./gradlew build`
- [ ] Verify JAR generated: `ls build/libs/trackervision-1.0.0-mc*.jar`

### Install & Test JAR
- [ ] Download JAR from GitHub Release
- [ ] Place in mods folder
- [ ] Launch game
- [ ] Verify mod loads (check mod list)
- [ ] Run quick smoke test: `/track lock @e[limit=1,sort=nearest]`
- [ ] Verify beacon, search mode, profiles all work

### Verify Documentation
- [ ] README.md renders correctly on GitHub
- [ ] All links work (ARCHITECTURE.md, CHANGELOG.md, etc.)
- [ ] Installation instructions are clear
- [ ] Command examples are correct

---

## Stage 6: Announce Release (Autonomous)

**Milestone:** 2026-07-10  
Once verified, share the release:

- [ ] Post announcement on Discord (if you have a community server)
- [ ] Post announcement on Reddit (r/Minecraft or similar)
- [ ] Update any portfolio or project tracking systems
- [ ] Share direct link: https://github.com/nobody174/trackervision/releases/tag/v1.0.0

## Post-Release (v1.0.1 Planning)

- [ ] Monitor issues/bug reports on GitHub
- [ ] Plan v1.0.1 patch (entity-type filtering, per FUTURE_FEATURES.md)
- [ ] Respond to feature requests
- [ ] Update KNOWN_ISSUES.md if edge cases found
- [ ] Setup Patreon/donation page (when ready)

---

## Timeline Summary

| Date | Stage | Owner |
|------|-------|-------|
| 2026-06-26 | RC2 validation plan | Autonomous |
| 2026-06-27–30 | RC2 testing (perf, extended play) | **User** |
| 2026-07-03 | RC2 tag + RC3 plan | Autonomous |
| 2026-07-04–06 | RC3 validation | Autonomous |
| 2026-07-07 | Release tag + GitHub Release | Autonomous |
| 2026-07-08 | CI/CD workflows | Autonomous |
| 2026-07-09 | Final verification | **User** |
| 2026-07-10 | Announcement | Autonomous |

---

## Key Files

- **RC2_VALIDATION_PLAN.md** — Performance & extended testing procedures
- **GITHUB_RELEASE_WORKFLOW.md** — GitHub release & CI/CD setup guide
- **PUBLIC_RELEASE_CHECKLIST.md** — This file, step-by-step release stages
- **CHANGELOG.md** — Version history
- **README.md** — User-facing documentation

---

## For Future Mods

This checklist should be copied to each new mod and adapted:

1. Copy PUBLIC_RELEASE_CHECKLIST.md to new project
2. Copy GITHUB_RELEASE_WORKFLOW.md to new project
3. Update version numbers and repository URLs
4. Follow same stages: RC2 → RC3 → Release
5. Use same CI/CD workflows (GitHub Actions)
6. Maintain consistency across portfolio

**Goal:** Each mod follows the same quality-focused release process, ensuring consistency and reliability across the portfolio.

---

## Reference Links

- **GitHub Repo:** https://github.com/nobody174/trackervision
- **Releases:** https://github.com/nobody174/trackervision/releases
- **Issues:** https://github.com/nobody174/trackervision/issues
- **Settings:** https://github.com/nobody174/trackervision/settings
- **Actions:** https://github.com/nobody174/trackervision/actions
