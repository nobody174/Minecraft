# Research Findings - NeoForge 1.21.1 Entity API

## Status: IN PROGRESS (Compilation Errors Guide Research)

Compilation revealed actual API differences that need documenting:

## Confirmed API Mismatches

### Issue 1: Entity Data Syncing
**Problem:** `defineSyncedData(Builder)` method doesn't exist as overridden

**Error:**
```
error: method does not override or implement a method from a supertype
@Override
protected void defineSyncedData(SynchedEntityData.Builder builder) {
```

**Need to Research:**
- What is the correct method signature for NeoForge 1.21.1?
- Is it `onSyncedDataUpdated()`? `defineSyncedData()`? Different parameter?
- Check LivingEntity source in NeoForge 1.21.1

### Issue 2: Navigation API
**Problem:** `getNavigation()` method not found

**Error:**
```
error: cannot find symbol
symbol:   method getNavigation()
location: variable buddy of type BuddyBeastEntity
```

**Need to Research:**
- Does LivingEntity have `getNavigation()` or different method?
- How to access PathNavigation in 1.21.1?
- Is it `getNavi gation()`, `getPathNavigation()`, or inherited method?

### Issue 3: Look Control
**Problem:** `getLookControl()` method not found

**Error:**
```
error: cannot find symbol
symbol:   method getLookControl()
location: variable buddy of type BuddyBeastEntity
```

**Need to Research:**
- What is the correct method to control entity look direction?
- Is it `getXRot()`/`setYRot()`? Different API?
- Check how vanilla mobs control looking

### Issue 4: Abstract Method
**Problem:** Missing `getMainArm()` abstract method

**Error:**
```
error: BuddyBeastEntity is not abstract and does not override abstract method getMainArm() in LivingEntity
```

**Need to Research:**
- What does `getMainArm()` do?
- Return type (HumanoidArm or Arm enum)?
- Default implementation for creatures?

## Code Failures as Research Guide

The MASTER_PROMPT says:
> **VERIFICATION RULE:** Before claiming "X doesn't exist," READ the actual source

This means I need to:
1. Check NeoForge source for LivingEntity in 1.21.1
2. Understand actual method signatures
3. Rewrite based on ACTUAL API, not assumptions

## Next Steps

Research agents should provide:
- [ ] Correct entity data syncing method
- [ ] Correct navigation access method
- [ ] Correct look control method
- [ ] getMainArm() implementation

Once research completes, fix code and recompile.
