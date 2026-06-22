# Risk Register

## Overview

This document tracks technical, performance, and compatibility risks for Buddy Beast.

## High Impact Risks

### Risk 1: AI Tick Cost Scaling

**Description:** Entity AI evaluation cost grows linearly with buddy count. At 100+ buddies, tick cost becomes prohibitive.

**Impact:** HIGH  
**Likelihood:** HIGH  
**Current Status:** MONITORING

**Mitigation:**
- Implement per-tick throttling (not all buddies every tick)
- Profile early and often
- Document performance targets and limits
- Consider spatial indexing for pathfinding

**Owner:** Pathfinding implementation  
**Target Resolution:** v0.2.0

---

### Risk 2: Multiplayer Desynchronization

**Description:** Clients may fall out of sync with server buddy state due to:
- Network lag
- Packet loss
- Race conditions between client render and server state

**Impact:** HIGH  
**Likelihood:** MEDIUM  
**Current Status:** PLANNING

**Mitigation:**
- Use server-authoritative model (server is source of truth)
- Implement delta compression (only send changed data)
- Add retry logic for sync packets
- Test heavily in multiplayer before release

**Status update (Week 3):** Server-authoritative health sync implemented and verified
via live dedicated server (entity attributes, taming, persistence all confirmed working).
Actual 2+ client multiplayer sync testing still outstanding — requires connected clients,
deferred to Week 4 manual testing session.

**Owner:** Network synchronization  
**Target Resolution:** Week 3 (v0.1.0) — sync logic done, multi-client verification pending

---

### Risk 3: Data Persistence Breaking

**Description:** Changes to NBT format in v0.2.0+ will break saves from v0.1.0.

**Impact:** HIGH  
**Likelihood:** LOW  
**Current Status:** PLANNING

**Mitigation:**
- Version NBT data with format version field
- Implement migration logic for each version bump
- Test save/load with old data
- Document migration path for users

**Owner:** Data persistence layer  
**Target Resolution:** v0.2.0

---

## Medium Impact Risks

### Risk 4: Custom Entity Rendering Complexity

**Description:** Implementing custom model rendering with bone animations may be more complex than anticipated.

**Impact:** MEDIUM  
**Likelihood:** LOW  
**Current Status:** PLANNING

**Mitigation:**
- Research NeoForge model/bone animation API before coding
- Start with simple cube model (iterate to complex)
- Test rendering early and often

**Owner:** Client rendering  
**Target Resolution:** Week 1

---

### Risk 5: Memory Leaks

**Description:** Long-running sessions (1+ hours) may accumulate memory as entities are created/destroyed.

**Impact:** MEDIUM  
**Likelihood:** LOW  
**Current Status:** MONITORING

**Mitigation:**
- Profile memory usage over 2+ hour session
- Ensure entity cleanup on despawn
- Test with many buddy spawn/despawn cycles

**Owner:** Entity lifecycle  
**Target Resolution:** v0.1.0 testing

---

## Low Impact Risks

### Risk 6: Compatibility with Other Mods

**Description:** Custom entity may conflict with other entity-modifying mods.

**Impact:** LOW  
**Likelihood:** LOW  
**Current Status:** INFORMATIONAL

**Mitigation:**
- Use standard entity registration patterns
- Test with common mods (OptiFine, Sodium, etc.)
- Document known incompatibilities

**Owner:** Entity registration  
**Target Resolution:** v0.1.0 testing

---

## Risk Resolution Process

1. **Identify:** When discovered, immediately document here
2. **Assess:** Impact and likelihood evaluation
3. **Monitor:** Check status weekly during development
4. **Mitigate:** Take action during development
5. **Close:** Mark resolved when mitigation complete

## Status Summary

| Risk | Status | Next Review |
|------|--------|-------------|
| AI Tick Cost Scaling | MONITORING | Week 2 (pathfinding research) |
| Multiplayer Desync | PLANNING | Week 3 (network implementation) |
| Data Persistence | PLANNING | v0.2.0 planning |
| Custom Rendering | PLANNING | Week 1 (research phase) |
| Memory Leaks | MONITORING | v0.1.0 testing phase |
| Mod Compatibility | INFORMATIONAL | v0.1.0 release |
