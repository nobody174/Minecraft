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

### Week 3: Network Verification & Persistence - ✅ COMPLETE
- [x] NBT serialization (addAdditionalSaveData)
- [x] NBT deserialization (readAdditionalSaveData)
- [x] CustomPacketPayload record structure
- [x] Packet registration in mod constructor
- [x] **Implement packet sending (every 10 ticks server-side)**
  - Added SYNC_INTERVAL_TICKS check in BuddyBeastEntity.tick()
  - Sends health update via PacketDistributor.sendToPlayersTrackingEntity
- [x] **Implement packet handling on client-side**
  - BuddyNetworkHandler resolves entity by ID via ClientLevel.getEntity()
  - Applies synced health to client-side BuddyBeastEntity
- [x] **Test save/load persistence (spawn → save world → reload)**
  - Verified via RCON against live dedicated test server
- [x] **Verify data survives server restart**
  - Tamed/OwnerName/PersistenceRequired all confirmed intact after full restart

**Week 3 bugs found and fixed during live server testing:**
- Entity attributes were never registered (`EntityAttributeCreationEvent` missing) — caused "Entity has no attributes" error on mod load. Fixed by adding `BuddyBeastEntity.createAttributes()` and registering it.
- `BuddySpawnHandler` redundantly called `finalizeSpawn()` a second time on every `EntityJoinLevelEvent` — vanilla already calls it exactly once per real spawn flow (natural spawn, `/summon`, spawn egg) with the correct `MobSpawnType`. Removed the handler entirely.
- Tamed buddies didn't persist through world reload — `setTamed()` never set `setPersistenceRequired()`, so an untamed-looking entity could be reaped on chunk unload. Fixed in both `setTamed()` and `readAdditionalSaveData()`.
- `HealthBarRenderer` existed but was never registered on any event bus — wired into `ClientSetup.registerEntityRenderers` (client-only, avoids crashing dedicated servers) and implemented real billboard text rendering via `Font.drawInBatch` (previous code was a non-functional stub).

### Week 4: In-Game Testing & Polish - ⏳ IN PROGRESS
- [x] **Single-player/server spawn testing** (`/summon buddybeast:buddy_beast`) — verified via RCON, no crash, correct attributes/health
- [ ] **Taming interaction testing** (right-click to tame) — NBT-level taming verified; actual player right-click interaction not yet tested in a real client session
- [ ] **Following behavior testing** (does pathfinding work?)
- [ ] **Multiplayer sync testing** (2+ players, buddy appears for all)
- [x] **Save/load persistence testing** (world reload preserves buddy) — verified via full server restart
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

## v0.2.0 Planned Features

- [ ] **Armor for buddies** — equippable armor item, defense stat boost, rendered on model
- [ ] **Saddle/riding for large buddy variants** — reuse vanilla saddle item/interaction for buddies big enough to ride (e.g. horse-based buddy)

See ROADMAP.md v0.2.0 section.

## Technical Debt

None yet (project just started).

See TECHNICAL_DEBT.md when shortcuts are taken.
