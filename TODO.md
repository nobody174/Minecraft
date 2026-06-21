# Todo

## v0.1.0 Progress

### Week 1: Entity Foundation
- [x] **RESEARCH:** NeoForge entity and goal APIs (RESEARCH_FIRST.md) - COMPLETE
- [x] Create `BuddyBeastEntity` class extending `Mob` - COMPLETE
- [x] Register entity type in registry (ModEntities) - COMPLETE
- [x] Implement goal selector and AI goals (Follow, Stay, Idle) - COMPLETE
- [x] Network packet system (BuddySyncPacket, handler) - COMPLETE
- [x] NBT persistence methods (readAdditionalSaveData, addAdditionalSaveData) - COMPLETE
- [ ] Create entity model and texture
- [ ] Implement client-side rendering
- [ ] Implement right-click taming interaction
- [ ] Test basic spawning and AI

### Week 2: AI and Taming
- [ ] Implement `BuddyGoalSelector` with goal prioritization
- [ ] Create `FollowPlayerGoal`
- [ ] Create `StayGoal`
- [ ] Create `IdleGoal`
- [ ] Implement right-click taming interaction
- [ ] Add health bar and animation system

### Week 3: Persistence and Sync
- [ ] Implement NBT serialization (readAdditionalSaveData)
- [ ] Implement NBT deserialization (addAdditionalSaveData)
- [ ] Create `BuddySyncPacket`
- [ ] Register packet with NeoForge network
- [ ] Implement sync packet sending (every 10 ticks)
- [ ] Test multiplayer synchronization

### Week 4: Testing and Release
- [ ] Manual testing: single-player spawn/tame/follow
- [ ] Manual testing: save/load persistence
- [ ] Manual testing: multiplayer sync
- [ ] Performance profiling (20+ buddies)
- [ ] Bug fixes
- [ ] Documentation completion

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
