---
name: ArmorAura v1.0.0 - Critical Fixes & Learnings
description: Complete record of fixes applied during ArmorAura development for future reference
---

# ArmorAura v1.0.0 - Critical Fixes & Learnings

## Issues Encountered & Fixes

### 1. **File Naming: `mods.toml` vs `neoforge.mods.toml`** ✅ FIXED
- **Problem**: JAR loaded but NeoForge rejected it saying "is for Minecraft Forge or an older version of NeoForge"
- **Root Cause**: File was named `META-INF/mods.toml` — NeoForge specifically looks for `neoforge.mods.toml` to distinguish from legacy Forge
- **Fix**: Renamed file to `neoforge.mods.toml`
- **Status**: Applied to MASTER_PROMPT Phase 1 initialization
- **Applies to**: All future NeoForge 1.21.1 mods

### 2. **Static Method Registration Mismatch** ✅ FIXED
- **Problem**: `IllegalArgumentException: Expected @SubscribeEvent method... to NOT be static`
- **Root Cause**: `ArmorDetectionManager.onServerTick()` was `static` but registered as instance (`new ArmorDetectionManager()`)
- **Fix**: Changed method signature from `public static void onServerTick(...)` to `public void onServerTick(...)`
- **Pattern**: 
  - Instance registration → non-static methods
  - Class registration → static methods
- **Status**: Applied to MASTER_PROMPT NeoForge Event Bus Registration Pattern
- **Applies to**: All future mods using event listeners

### 3. **Client/Server JAR Version Mismatch** ✅ IDENTIFIED
- **Problem**: Network channel `armoraura:aura_state` rejected: "missing on the server side"
- **Root Cause**: Client had fresh JAR but server mods folder wasn't updated
- **Fix**: Deploy same JAR to both folders with timestamp verification
- **Status**: Applied to MASTER_PROMPT JAR Build & Deployment Workflow (step 4-6)
- **Applies to**: All mods with networking channels

### 4. **Gradle Cache Corruption** ✅ FIXED
- **Problem**: Build lockouts due to multiple Gradle daemon instances
- **Fix**: 
  ```bash
  ./gradlew --stop
  Remove-Item .gradle -Recurse -Force
  ./gradlew clean build --refresh-dependencies
  ```
- **Status**: Documented as critical recovery step
- **Applies to**: All future builds with persistent issues

## Critical Files for Future Mods

### `neoforge.mods.toml` Template
```toml
modLoader="javafml"
loaderVersion="[1,)"
license="All rights reserved"
authors="nobody174"
description="Your mod description"

[[mods]]
modId="yourmodid"
version="1.0.0"
displayName="YourModName"
logoFile="assets/yourmodid/icon.png"
credits=""
authors="nobody174"
description="Your mod description"

[[dependencies.yourmodid]]
    modId="minecraft"
    mandatory=true
    versionRange="[1.21.1,)"
    ordering="NONE"
    side="BOTH"

[[dependencies.yourmodid]]
    modId="neoforge"
    mandatory=true
    versionRange="[21.1.233,)"
    ordering="NONE"
    side="BOTH"
```

**Key Points:**
- Filename MUST be `neoforge.mods.toml` (not `mods.toml`)
- `modLoader` must be `"javafml"` (case-sensitive)
- `loaderVersion` must be version range like `"[1,)"` (not plain number)
- Dependencies must be indented with spaces (not tabs)

## Deployment Checklist

For every build → test cycle:

1. ✅ Run `./gradlew clean build`
2. ✅ Remove old JAR from `C:\Users\Vartd\AppData\Roaming\.minecraft\mods\`
3. ✅ Remove old JAR from `D:\Claude AI Projects\Minecraft-Test-Server-1.21.1\mods\`
4. ✅ Copy fresh JAR to client mods folder
5. ✅ Copy fresh JAR to server mods folder
6. ✅ Verify timestamps match in all three locations (build, client, server)
7. ✅ Launch Minecraft for testing

## Reference

- **MASTER_PROMPT.md**: Phase 1 Initialization, NeoForge Event Bus Pattern, JAR Deployment
- **NEOFORGE_GRADLE_TEMPLATE.md**: Metadata Files section
- **Server Mods Folder**: `D:\Claude AI Projects\Minecraft-Test-Server-1.21.1\mods\`
- **Client Mods Folder**: `C:\Users\Vartd\AppData\Roaming\.minecraft\mods\`

---

**Summary**: ArmorAura v1.0.0 successfully loads on client and server. Mod recognition required exact filename (`neoforge.mods.toml`), proper event registration patterns (instance vs static), and synchronized JAR deployment to both environments. All fixes integrated into MASTER_PROMPT for future mods.
