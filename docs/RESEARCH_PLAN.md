# Research Plan - Week 1

Before implementing the entity and AI system, must research and document:

## 1. NeoForge Entity API

**Questions to answer:**
- How to create custom entity type and register it?
- Entity lifecycle: spawn → tick → save/load → despawn
- How to extend LivingEntity properly?
- How to handle entity initialization and constructor?
- NBT serialization: readAdditionalSaveData vs addAdditionalSaveData

**Key files to find:**
- LivingEntity class documentation
- EntityType registry
- Entity event patterns (ServerTickEvent for entities)
- Examples from existing NeoForge docs

**Research Success Criteria:**
- Document with code examples
- Know exact method signatures
- Understand event registration pattern

---

## 2. Goal-Based AI System

**Questions to answer:**
- How does NeoForge Goal system work?
- Goal selector and priority evaluation?
- Can I extend Goal and TargetGoal?
- How are goals evaluated each tick?
- Goal interruption and transition?

**Key files to find:**
- Goal class interface
- GoalSelector implementation
- Examples of custom goals
- Event patterns for AI ticking

**Research Success Criteria:**
- Document goal lifecycle
- Know how to prioritize goals
- Understand goal action execution flow

---

## 3. Entity Rendering

**Questions to answer:**
- How to render custom entity model?
- RenderLayerEvent pattern in NeoForge?
- Custom EntityRenderer implementation?
- Bone animation system (JointTransformer)?
- Health bar rendering overlay?

**Key files to find:**
- RenderLayerEvent documentation
- EntityRenderer base class
- Model/texture loading patterns
- HUD overlay examples

**Research Success Criteria:**
- Know exact rendering event to use
- Understand model coordinate system
- Document texture path convention

---

## 4. Network Packet Synchronization

**Questions to answer:**
- How to define custom packet in NeoForge 1.21.1?
- Record + CustomPacketPayload pattern?
- How to register with RegisterPayloadHandlersEvent?
- How to send to players (PacketDistributor)?
- Server vs client handling?

**Key files to find:**
- CustomPacketPayload documentation
- PayloadRegistrar pattern
- PacketDistributor examples
- Network channel best practices

**Research Success Criteria:**
- Have working packet template
- Know registration pattern
- Understand bidirectional sync

---

## 5. NBT Data Persistence

**Questions to answer:**
- How to serialize entity to NBT?
- How to deserialize from NBT?
- Data versioning for future migrations?
- Player persistent data storage (getPersistentData)?
- Save/load event patterns?

**Key files to find:**
- CompoundTag API
- Entity save/load methods
- Player data persistence
- World save event patterns

**Research Success Criteria:**
- Know NBT structure design
- Understand serialization/deserialization flow
- Document format version strategy

---

## Research Methodology

For each topic:

1. **Read NeoForge official docs** (not tutorials)
   - Check https://docs.neoforged.net/docs/1.21.x/
   - Focus on 1.21.1 specific docs

2. **Search existing mods** (if available)
   - Look for patterns in working mods
   - Note API versions and changes

3. **Document findings** in temporary RESEARCH.md
   - Code examples
   - Method signatures
   - Known limitations
   - Alternative approaches

4. **Validate** before implementation
   - Try small POC if uncertain
   - Confirm with build test

---

## Success Criteria

Research is complete when:
- ✅ All 5 topics have documented findings
- ✅ Code examples for each API
- ✅ Clear implementation path forward
- ✅ No ambiguous design decisions
- ✅ Ready to code without guessing

---

## Timeline

- **Day 1:** Entity API research
- **Day 2:** Goal AI system research
- **Day 3:** Rendering and packet research
- **Day 4:** Persistence research
- **Day 5:** Validation and documentation

Ready to code after research completion (no earlier).
