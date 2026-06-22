# GameTest Structures

This directory contains NBT structure files for GameTest tests.

## Required Files

### empty_3x3x3.nbt

A 3×3×3 empty structure filled with air.

**How to create:**

1. **Option A: Use NBTStudio**
   - Download: https://github.com/Howaner/NBTStudio
   - Create new NBT
   - Set dimensions: 3×3×3
   - Fill with air blocks
   - Save as `empty_3x3x3.nbt`

2. **Option B: Use Minecraft**
   - In creative mode, build a 3×3×3 structure of air
   - Use command: `/structure save armoraura:empty_3x3x3`
   - NBT file saved to: `structures/empty_3x3x3.nbt`

3. **Option C: Download template**
   - From a working NeoForge mod with GameTests
   - Copy the structure file format

## Structure Requirements

- **Name**: `empty_3x3x3.nbt`
- **Dimensions**: 3 (X) × 3 (Y) × 3 (Z)
- **Blocks**: All air (block ID 0)
- **Format**: NBT (Minecraft structure format)

## GameTest Template Attributes

Tests reference structures with syntax:
```java
@GameTest(template = "armoraura:empty_3x3x3")
```

This loads: `data/armoraura/gametest/structures/empty_3x3x3.nbt`

## Testing Without Structure Files

If structure files are missing, GameTests will fail with error:
```
Could not find structure armoraura:empty_3x3x3
```

**Temporary workaround**: Comment out GameTest methods in `AuraConfigGameTest.java` and run only JUnit tests:
```bash
./gradlew test
```

Once structure files are in place:
```bash
./gradlew runGameTest
```
