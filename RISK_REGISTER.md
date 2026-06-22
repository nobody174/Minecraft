# Risk Register

## Risk: Data Component persistence/sync semantics unverified

**Impact:** High (core system depends on this)
**Likelihood:** Medium
**Mitigation:** Research phase (RESEARCH_FIRST.md) before implementation; verify via build + in-game test before building stats/evolution on top.
**Status:** Researching (agent dispatched)

## Risk: Capturing entities with passengers/leashes/boss flags may behave unexpectedly

**Impact:** Medium (edge-case crashes or duplication bugs)
**Likelihood:** Medium
**Mitigation:** Restrict capture to standard hostile/passive mobs in v0.1.0; document restriction in KNOWN_ISSUES if encountered.
**Status:** Researching (agent dispatched)
