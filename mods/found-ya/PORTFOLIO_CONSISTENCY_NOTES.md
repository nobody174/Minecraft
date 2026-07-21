# Portfolio Consistency & System Improvements

**Purpose:** Document lessons learned from TrackerVision v1.0.0 release process to improve prompt-system for future mods.

**Date:** 2026-06-26  
**Status:** Complete — Ready for next mod dev cycle

---

## What We Did Right

✅ **Proper RC Progression:** RC1 → RC2 → RC3 → Release  
- Not skipping phases even when tempting
- User testing as integral part of RC2 (not optional)
- Performance profiling documented upfront

✅ **Comprehensive Validation Plans:** RC2_VALIDATION_PLAN.md with acceptance criteria  
- Clear pass/fail conditions
- 4+ hour extended play-testing as baseline
- Edge case coverage defined upfront

✅ **GitHub Release Workflow Documentation:** GITHUB_RELEASE_WORKFLOW.md  
- Pre-release checklist
- Three-stage process (tag → release → CI/CD)
- GitHub Actions workflow templates ready to copy
- Release notes template with feature list

✅ **Portfolio Consistency:** All using `main` branch, same folder structure  
- Consistent with ArmorAura, Boss Radar
- Same Gradle setup (8.9, Java 21)
- Same NeoForge version (1.21.1)
- Same mod.version format in gradle.properties

---

## What to Improve in prompt-system

### 1. Master Prompt Should Reference

**Add to prompt-system/MASTER_PROMPT.md or similar:**

```markdown
## Minecraft Mod Release Process

### Branch Strategy
- Use `main` (not `master`) as default branch across all mods
- Create feature branches for major features
- Tag releases with semantic versioning: v1.0.0, v1.0.1-RC1, etc.

### RC Progression (MANDATORY)
- **RC1:** Feature-complete, builds cleanly, basic testing
- **RC2:** Bug-fix candidate, 4+ hours extended play-testing, performance profiling
- **RC3:** Release validation, final checklist, go/no-go decision
- **Release:** Production-ready, public GitHub, CI/CD active

No skipping phases. User testing required for RC2.

### GitHub Release Requirements
1. Repository must be public before release
2. Create annotated tag: `git tag -a v1.0.0 -m "..."` (not lightweight)
3. Update version in gradle.properties BEFORE tagging
4. Create GitHub Release with JAR artifact + release notes
5. Setup GitHub Actions workflows (build.yml, release.yml)
6. Test workflows before announcing

### Documentation Checklist
Before ANY public release:
- [ ] README.md: Installation + usage + commands + entity selector examples
- [ ] CHANGELOG.md: Comprehensive version history
- [ ] ARCHITECTURE.md: System design + component overview
- [ ] Known limitations: Honest disclosure
- [ ] Installation tested: User can follow instructions
```

---

### 2. Create RELEASE_CANDIDATE_SYSTEM.md Template

**File:** `prompt-system/SYSTEMS/RELEASE_CANDIDATE_SYSTEM.md`

```markdown
# Release Candidate System

Standardized RC progression for all Minecraft mods.

## Phases

### RC1: Feature-Complete Candidate
- All planned features implemented
- Build succeeds
- Basic testing (developer)
- Minimal documentation
- Tag: v1.0.0-RC1

### RC2: Bug-Fix Candidate
- Extended 4+ hour play-testing (user)
- Performance profiling (user)
- 10+ manual test cases
- Edge case verification
- Documentation polished
- Zero new critical bugs found
- Tag: v1.0.0-RC2

### RC3: Release Validation
- Final checklist verified
- Repository setup complete
- GitHub Release created
- CI/CD workflows verified
- One final 2+ hour play-test
- Tag: v1.0.0-RC3

### Release: Production
- v1.0.0 (no -RC suffix)
- Public repository
- GitHub Actions active
- Announced

## User Involvement

- **RC1:** Autonomous only (developer builds, basic verification)
- **RC2:** User testing REQUIRED (4+ hours, edge cases, performance)
- **RC3:** Autonomous with user verification (final smoke test)
- **Release:** Autonomous (announcement, monitoring)

## Tooling

See GITHUB_RELEASE_WORKFLOW.md template for:
- Pre-release checklist
- GitHub repository setup
- Annotated tag creation
- GitHub Release creation
- GitHub Actions workflows
- Release notes template
```

---

### 3. Create GITHUB_RELEASE_TEMPLATE.md

**File:** `prompt-system/TEMPLATES/GITHUB_RELEASE_TEMPLATE.md`

```markdown
# GitHub Release Template

Copy to each mod's repo as GITHUB_RELEASE_WORKFLOW.md

(Full template content from TrackerVision's GITHUB_RELEASE_WORKFLOW.md)

### Customization Per Mod
- Replace "TrackerVision" with mod name
- Replace "entity tracking framework" with mod's one-liner
- Update Minecraft version (1.21.1 is current standard)
- Update topics (add mod-specific ones)
- Update feature list (use mod's CHANGELOG.md v1.0.0 section)
- Copy GitHub Actions workflows (build.yml, release.yml)
```

---

### 4. Create PORTFOLIO_CONSISTENCY_CHECKLIST.md

**File:** `prompt-system/SYSTEMS/PORTFOLIO_CONSISTENCY_CHECKLIST.md`

```markdown
# Portfolio Consistency Checklist

Verify each mod follows same standards:

### Branch & Git
- [ ] Default branch is `main` (not `master`)
- [ ] gradle.properties: `mod_version=X.Y.Z` (semantic versioning)
- [ ] .gitignore excludes: build/, .gradle/, .idea/, run/, logs/, *.jar (except release), .env, secrets.txt

### Documentation
- [ ] README.md: 200+ words, features, installation, commands, examples
- [ ] CHANGELOG.md: v0.1/v0.5/v1.0 section structure
- [ ] ARCHITECTURE.md: Component overview, design decisions
- [ ] FUTURE_FEATURES.md: v1.0.1+ roadmap

### Testing
- [ ] TEST_CHECKLIST.md or RC2_VALIDATION_PLAN.md with 10+ test cases
- [ ] Manual in-client testing required for RC2
- [ ] Performance profiling baseline documented

### GitHub Setup
- [ ] Public repository
- [ ] Description set
- [ ] Topics added: minecraft, neoforge, mod, java, [version]
- [ ] License set (All rights reserved or chosen license)
- [ ] Default branch: main
- [ ] Releases section visible

### CI/CD
- [ ] .github/workflows/build.yml for push builds
- [ ] .github/workflows/release.yml for tag-triggered artifact upload
- [ ] Workflows tested and verified
- [ ] Build badge in README (if desired)

### Release
- [ ] Annotated tag created: `git tag -a vX.Y.Z`
- [ ] GitHub Release created with JAR artifact
- [ ] Release notes copied from CHANGELOG.md
- [ ] Installation from JAR tested
- [ ] Installation from source tested
```

---

### 5. Add to Autonomous Mode Guidelines

**File:** `prompt-system/GUIDELINES/AUTONOMOUS_MOD_DEVELOPMENT.md`

Add section:

```markdown
## Release Phase (RC2 → RC3 → Release)

After user confirms all v1.0 features work in-client:

1. **Demote to RC2** (not Release yet)
   - Create RC2_VALIDATION_PLAN.md with acceptance criteria
   - Update CHANGELOG.md to v1.0.0-RC2 (not v1.0.0)
   - Update README.md status to RC2
   - Commit and push

2. **Wait for user RC2 testing** (4+ hours)
   - User tests performance, edge cases, stress scenarios
   - User follows RC2_VALIDATION_PLAN.md procedures
   - You fix any critical bugs found
   - Re-test after fixes

3. **After RC2 passes:**
   - Create RC3_VALIDATION_PLAN.md
   - Create GITHUB_RELEASE_WORKFLOW.md
   - Update PUBLIC_RELEASE_CHECKLIST.md with 6 stages
   - Tag v1.0.0-RC3

4. **After RC3 validation:**
   - Update gradle.properties: `mod_version=1.0.0` (remove -RC)
   - Update CHANGELOG.md: `## [1.0.0] - 2026-07-10`
   - Create release tag: `git tag -a v1.0.0 -m "..."`
   - Push tag to GitHub
   - Create GitHub Release with JAR artifact
   - Setup GitHub Actions workflows

**Key:** No shortcuts. Quality > Speed. User testing is MANDATORY for RC2.
```

---

## Learning Summary

### What Went Well
1. **RC progression template** works — clear phases, testable criteria
2. **User involvement in RC2** essential — catches edge cases
3. **GitHub Actions CI/CD** saves time after release
4. **Standardization** reduces decisions (same branch, same docs, same format)
5. **Comprehensive checklists** prevent mistakes (easy to verify each step)

### What to Avoid
1. **Skipping RC phases** — tempting but cuts quality
2. **Pushing without user testing** — NPE and client-load errors should have been caught earlier
3. **Ambiguous timelines** — need explicit dates, not "when you're ready"
4. **Inconsistent branch names** — some mods use `master`, some `main` → standardize
5. **Missing pre-release checklist** — easy to forget a step without written list

### For Next Mod
1. Copy TrackerVision's release workflow files (GITHUB_RELEASE_WORKFLOW.md, RC2_VALIDATION_PLAN.md)
2. Use same branch (`main`), same Gradle setup, same structure
3. Follow RC1 → RC2 → RC3 → Release progression
4. User testing for RC2 is non-negotiable
5. Document any new learnings and update prompt-system

---

## Files to Update in prompt-system

1. **MASTER_PROMPT.md** — Add release process section
2. **RELEASE_CANDIDATE_SYSTEM.md** — Create standardized template
3. **GITHUB_RELEASE_TEMPLATE.md** — Create copy-paste workflow
4. **PORTFOLIO_CONSISTENCY_CHECKLIST.md** — Create verification list
5. **AUTONOMOUS_MOD_DEVELOPMENT.md** — Add release phase guidelines

---

## Next Mod Will Use

When starting the next Minecraft mod (v1.0.0):

1. Reference updated prompt-system files
2. Copy GITHUB_RELEASE_WORKFLOW.md from TrackerVision
3. Copy RC2_VALIDATION_PLAN.md and adapt dates
4. Copy PUBLIC_RELEASE_CHECKLIST.md with stages
5. Same branch strategy (`main`)
6. Same RC progression (no shortcuts)
7. Same CI/CD workflows

Result: Each mod release faster, more consistent, higher quality.

---

## Conclusion

TrackerVision v1.0.0 release process is **now a proven template for the portfolio.**

By documenting and standardizing, each future mod can:
- ✅ Release faster (proven process, no rediscovery)
- ✅ Release better (quality gates built in)
- ✅ Release consistently (same standards across all mods)

Update prompt-system with these learnings so next mod dev cycle is even smoother.

