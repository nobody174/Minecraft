# Todo

## v0.1.0 Progress

### Week 1: Entity Foundation - ✅ COMPLETE
- [x] **RESEARCH:** NeoForge entity and goal APIs (RESEARCH_FIRST.md)
- [x] Create `BuddyBeastEntity` class extending `Mob`
- [x] Register entity type in registry (ModEntities)
- [x] Implement goal selector and AI goals (FollowOwnerGoal, StayGoal, IdleGoal)
- [x] Network packet system (BuddySyncPacket, BuddyNetworkHandler)
- [x] NBT persistence methods (readAdditionalSaveData, addAdditionalSaveData)
- [x] Implement right-click taming interaction (BuddyInteractionHandler)
- [x] Entity spawn handler (BuddySpawnHandler)
- [x] Client-side rendering (BuddyBeastRenderer with CowModel placeholder)
- [x] Entity and spawn registration in main mod class
- [x] Build successfully compiling with no errors

**Week 1 Status:** All core systems implemented and building successfully!

### Week 2: Health Bar & Rendering - ⏳ IN PROGRESS
- [x] Goal selector with priority system (DONE in Week 1)
- [x] FollowOwnerGoal (DONE in Week 1)
- [x] StayGoal (DONE in Week 1)
- [x] IdleGoal (DONE in Week 1)
- [x] Right-click taming interaction (DONE in Week 1)
- [ ] **Add health bar rendering on client**
- [ ] **Improve entity animations (setupAnim)**
- [ ] **Add status effect indicators**
- [ ] **Test AI goal switching in-game**

### Week 3: Testing & Network Verification - ⏳ PLANNED
- [x] NBT serialization (DONE in Week 1)
- [x] NBT deserialization (DONE in Week 1)
- [x] CustomPacketPayload structure (DONE in Week 1)
- [x] Packet registration (DONE in Week 1)
- [ ] **Implement packet sending (every 10 ticks server-side)**
- [ ] **Implement packet handling (client-side)**
- [ ] **Test multiplayer sync in actual game**
- [ ] **Verify save/load works across server restarts**

### Week 4: In-Game Testing & Polish - ⏳ PLANNED
- [ ] **Single-player spawn testing** (/summon buddybeast:buddy_beast)
- [ ] **Taming interaction testing** (right-click)
- [ ] **Following behavior testing** (does it actually follow?)
- [ ] **Multiplayer sync testing** (2+ players)
- [ ] **Save/load persistence testing** (world reload)
- [ ] **Performance profiling** (20+ buddies simultaneously)
- [ ] **Bug fixes and edge cases**
- [ ] **Final documentation and release prep**

## Blocking Tasks

**CRITICAL:** Research entity and AI APIs before coding AI system.

See `prompt-system/SIDE_PROMPTS/RESEARCH_FIRST.md`

## Known Risks

| Risk | Impact | Likelihood | Status |
|------|--------|------------|--------|
| AI tick cost scales poorly | High | High | MONITORING |
| Multiplayer desync issues | High | Medium | PLANNING |
| Data migration breaks saves | High | Low | PLANNING |
| Custom entity rendering fails | Medium | Low | PLANNING |

See RISK_REGISTER.md for details.

## Technical Debt

None yet (project just started).

See TECHNICAL_DEBT.md when shortcuts are taken.
