# Technical Debt

## Overview

This document tracks intentional shortcuts and deferred work.

## Current Debt

None yet. Project just started.

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
