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

### Week 2: Health Bar & Rendering - ✅ COMPLETE
- [x] Goal selector with priority system
- [x] FollowOwnerGoal with pathfinding
- [x] StayGoal with navigation stop
- [x] IdleGoal with random wandering
- [x] Right-click taming interaction
- [x] **Add health bar rendering foundation (HealthBarRenderer)**
- [x] **Improve entity animations (LookAtOwnerGoal, animation tracking)**
- [x] **Add status effect indicators (StatusIndicator enum)**
- [x] **Build compiling with all new rendering code**

**Week 2 Status:** All visualization systems implemented!

### Week 3: Network Verification & Persistence - ⏳ IN PROGRESS
- [x] NBT serialization (addAdditionalSaveData)
- [x] NBT deserialization (readAdditionalSaveData)
- [x] CustomPacketPayload record structure
- [x] Packet registration in mod constructor
- [ ] **Implement packet sending (every 10 ticks server-side)**
  - Add scheduled task to BuddyBeastEntity.tick()
  - Send health + position updates periodically
- [ ] **Implement packet handling on client-side**
  - Update BuddyNetworkHandler to actually sync data
  - Apply position/health to client-side entity
- [ ] **Test save/load persistence (spawn → save world → reload)**
- [ ] **Verify data survives server restart**

### Week 4: In-Game Testing & Polish - ⏳ PLANNED
- [ ] **Single-player spawn testing** (/summon buddybeast:buddy_beast)
- [ ] **Taming interaction testing** (right-click to tame)
- [ ] **Following behavior testing** (does pathfinding work?)
- [ ] **Multiplayer sync testing** (2+ players, buddy appears for all)
- [ ] **Save/load persistence testing** (world reload preserves buddy)
- [ ] **Performance profiling** (spawn 20+ buddies, measure tick cost)
- [ ] **Bug fixes and edge cases** (despawn, chunk unload, etc)
- [ ] **Final documentation and v0.1.0 release prep**

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
