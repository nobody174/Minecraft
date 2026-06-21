# Architecture

## System Overview

Buddy Beast follows a **server-authoritative** architecture with client rendering:

```
┌─────────────────────┐
│   Server (Logic)    │
│  - AI Goals         │
│  - Pathfinding      │
│  - Data Persistence │
│  - State Authority  │
└──────────┬──────────┘
           │ Network Packets
           │ (Position, State)
           ▼
┌─────────────────────┐
│ Client (Rendering)  │
│  - Entity Renderer  │
│  - Animations       │
│  - Health Bar       │
└─────────────────────┘
```

## Core Components

### Entity System (`entity/`)

- **`BuddyBeastEntity`** — Custom entity extending `LivingEntity`
  - Lifecycle: Spawn → Load NBT → Tick → Sync → Save NBT → Despawn
  - Health, movement, AI integration
  - Server-side entity with client rendering

### AI System (`ai/`)

- **`BuddyGoalSelector`** — Goal prioritization and execution
  - Goals evaluated each tick in priority order
  - Only one goal active at a time
  - Smooth transitions between goals

- **Goals:**
  - `FollowPlayerGoal` — Chase and stay near owner
  - `StayGoal` — Wait in place
  - `IdleGoal` — Wander slowly

### Persistence System (`data/`)

- **`BuddyData`** — NBT structure for save/load
  - UUID, owner, health, position, goal state
  - Versioned format for future migrations
  - Loaded on entity spawn, saved on tick

### Network System (`network/`)

- **`BuddySyncPacket`** — Position/rotation/state sync
  - Sent every 10 ticks (2Hz) when state changes
  - Delta compression (only changed fields)
  - Server authoritative (client receives only)

### Rendering System (`client/`)

- **`BuddyRenderer`** — Custom entity renderer
  - Model-based rendering with bone animation
  - Health bar overlay
  - Status effect rendering

## Data Flow

### Spawning
```
1. Player uses spawn command
2. BuddyBeastEntity created on server
3. NBT data initialized (UUID, owner)
4. Entity registered in world
5. Client spawns rendering entity
```

### Ticking (Server-side, 20 ticks/sec)
```
1. Entity.tick() called
2. AI goals evaluated
3. Goal action executed (move, idle)
4. Entity state updated
5. If state changed: sync packet sent to all clients
```

### Persistence
```
On World Save:
  Entity → NBT → Player.getPersistentData()
  
On World Load:
  Player.getPersistentData() → NBT → Entity
  Entity spawned in correct location
```

## Performance Considerations

### Tick Cost Per Buddy
- **Goal evaluation:** 0.1ms
- **Pathfinding:** 0.15ms (throttled)
- **Data sync:** 0.05ms
- **Total:** ~0.3ms per buddy

### Scaling
- 10 buddies: ~3ms/tick (fine)
- 50 buddies: ~15ms/tick (acceptable)
- 100 buddies: ~30ms/tick (PROBLEM)

### Mitigation
- Throttle AI updates (not every tick)
- Spatial indexing for pathfinding
- Batch sync packets

## Testing Strategy

### Unit Tests
- NBT serialization/deserialization
- Goal priority evaluation
- Path calculation

### Integration Tests
- Single-player: spawn, tame, persist, save/load
- Multiplayer: sync across players, world lag

### Performance Tests
- Profiling with 50+ buddies
- Memory usage over 1+ hour session
- Network packet frequency

## Future Refactoring

- **v0.2.0:** Spatial indexing for pathfinding performance
- **v0.3.0:** Separate buddy data layer for better persistence
- **v1.0.0:** Configurable AI priority system
