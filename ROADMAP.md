# Roadmap

## v0.1.0 — Core System (3-4 weeks)

**Status:** In Development

### Week 1: Entity Foundation
- [x] Project structure initialization
- [ ] Research NeoForge entity and AI APIs
- [ ] Create custom BuddyBeast entity type
- [ ] Implement entity model and texture
- [ ] Basic entity spawning and rendering

### Week 2: AI and Taming
- [ ] Implement goal-based AI system
- [ ] Follow Player goal
- [ ] Stay in Place goal
- [ ] Idle goal
- [ ] Taming mechanic (right-click)
- [ ] Health and animation system

### Week 3: Persistence and Sync
- [ ] NBT data persistence (save/load)
- [ ] Network packet synchronization
- [ ] Multiplayer state tracking
- [ ] Bug fixes and stability

### Week 4: Testing and Polish
- [ ] Edge case testing
- [ ] Performance profiling
- [ ] Documentation completion
- [ ] Release as v0.1.0

## v0.2.0 — Expansion (2-3 weeks)

**Status:** Planned

- Advanced AI goals (hunt, explore, defend)
- Chat commands (/buddy follow, /buddy stay, /buddy list)
- Buddy customization (colors, names, size variants)
- Improved multiplayer synchronization
- Status effects system
- Equippable armor for buddies (defense stat boost, visible on model)
- Rideable saddle support for buddy variants large enough to ride (e.g. horse-based buddy), reusing vanilla saddle item/interaction

## v0.3.0 — Progression (Future)

- Leveling and experience system
- Combat system (attack, defense stats)
- Skill training
- Multi-buddy parties
- Breeding mechanics

## Known Constraints

- **Performance ceiling:** ~50 buddies per world (needs optimization for 100+)
- **Multiplayer:** May experience jitter in high-latency environments
- **Chunk unloading:** Buddies despawn when chunks unload (architectural limitation)

## Release Criteria

Each version is released when:
- ✅ All features tested and stable
- ✅ No critical bugs
- ✅ Documentation complete
- ✅ Community feedback incorporated
- ✅ RC1 → RC2 → RC3 progression complete
