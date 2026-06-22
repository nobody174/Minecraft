# Technical Debt

## Overview

This document tracks intentional shortcuts and deferred work.

## Current Debt

```
Debt: BuddyBeastRenderer uses vanilla CowModel as a placeholder
  Why: Custom Blockbench model not yet created; CowModel + MobRenderer compiles and renders correctly
  When: Before v0.1.0 release, or defer to v0.2.0 if visuals are acceptable for initial testing
  Cost: 3-4 hours (model creation, texture, renderer update)
  Status: MONITORING

Debt: BuddySyncPacket only carries entityId + health
  Why: StreamCodec.composite() has a fixed arity ceiling; UUID has no built-in ByteBufCodecs entry
  Why (cont.): position/rotation sync deferred since entity position already syncs via vanilla's
  default tracking; only custom data (health) needed a dedicated packet for now
  When: v0.2.0 if additional custom synced state is needed (e.g. taming status, custom stats)
  Cost: 1-2 hours to add a second packet or split into multiple smaller composites
  Status: MONITORING

Debt: gradlew.bat has a cosmetic "cannot find batch label mainEnd" error on every build
  Why: Wrapper script copied from template is missing the :mainEnd label; BUILD SUCCESSFUL
  still prints correctly and the JAR is produced, so it doesn't block development
  When: Low priority, fix whenever touching build tooling
  Cost: 10 minutes
  Status: MONITORING
```

## When to Add Entries

When you take a shortcut:

1. **Clearly document it** with:
   - **What:** Description of the shortcut
   - **Why:** Reason it was necessary (time, complexity, blocking other work)
   - **When:** Estimated timeline for fixing
   - **Cost:** Estimated effort to resolve

2. **Example:**

```
Debt: EntityAI uses linear search for nearby buddies
  Why: Spatial index would add complexity, not needed for v0.1.0
  When: v0.2.0 if performance becomes issue
  Cost: 2-3 hours to implement quadtree and test
  Status: MONITORING
```

## Debt Resolution Rules

- **Before v0.1.0 release:** Evaluate which debt is critical
- **Must fix:** Anything causing crashes, data loss, or severe performance issues
- **Can defer:** Optimization shortcuts that don't impact stability
- **Never defer:** Security issues, save file corruption, multiplayer desync

## Tracking

Debt is reviewed at each milestone:
- After Week 1
- After Week 2
- After Week 3
- Before RC1
