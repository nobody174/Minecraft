//
// TeleportShop - Step-by-Step Guide for Beginners
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/minecraft-addons
// License: All rights reserved © 2026 nobody174
//

# Step-by-Step Beginner's Guide

This guide walks you through the entire process from setup to coding, step by step.

---

## Phase 0: Setup (Before Coding)

### Step 1: Install Maven (5-10 minutes)

**Why?** Maven builds your code into the plugin .jar file.

1. Read `SETUP_TOOLS.md` (in your working folder)
2. Follow the installation steps
3. Verify: Open Command Prompt and type: `mvn --version`
4. You should see version info like: `Apache Maven 3.8.1`

**If it doesn't work:** 
- Close VS Code completely
- Open a fresh Command Prompt
- Try again

### Step 2: Set Your Working Folder

**Where you'll be working:**
```
C:\Users\Vartd\OneDrive\Skrivebord\Learning AI\vscode\
   New projects\minecraft-addons\src\java\plugins\teleportshop
```

**In VS Code:**
1. File → Open Folder
2. Navigate to: `New projects/minecraft-addons/src/java/plugins/teleportshop`
3. Click "Select Folder"
4. This is your working folder!

---

## Phase 1: Understanding the Project

### Step 3: Read the Documentation (45 minutes)

Read these files IN ORDER (all in your working folder):

**Quick orientation (10 min):**
1. `START_HERE.md` - Project overview

**Project planning (15 min):**
2. `DEVELOPMENT_STATUS.md` - What's done, what's next
3. `ROADMAP.md` - 10-week plan overview

**What you'll code (15 min):**
4. `TODO.md` - See Phase 1 tasks
5. `docs/DEVELOPMENT.md` - Code patterns and structure

**Tracking progress (5 min):**
6. `PROGRESS_TRACKER.md` - How to track what you've done

### Step 4: Understand the Code Structure

**File Layout** (in your working folder):

```
src/main/java/com/minecraft/teleportshop/
├── TeleportShop.java          ← Main plugin class (already exists)
├── commands/                  ← Command implementations (stubs exist)
│   ├── teleport/
│   │   ├── HomeCommand.java
│   │   ├── WarpCommand.java
│   │   └── ... (more commands)
│   └── shop/
│       ├── ShopCommand.java
│       └── ... (more commands)
├── handlers/                  ← Core logic (stubs exist)
│   ├── TeleportHandler.java   ← Main logic for teleports
│   ├── ShopHandler.java       ← Shop system logic
│   └── ConfigHandler.java     ← Load configuration
├── listeners/                 ← Event handlers (stubs exist)
│   └── PlayerListener.java
├── storage/                   ← Data persistence (empty - you'll create)
└── utils/                     ← Helpers (empty - create as needed)

src/main/resources/
├── config.yml                 ← Settings file
└── plugin.yml                 ← Plugin metadata

src/test/java/                 ← Tests (empty - create in Phase 1.3)
```

---

## Phase 2: First Coding Task (YAML Storage)

### Step 5: Start the First Task

**Task: Implement YAML Storage**

This is the first thing you'll code. Here's what it means:

**What is YAML?**
- A file format for storing data (like `.yml` files)
- Easy to read: `player_name: Steve`
- Used for Minecraft configs

**What you need to create:**

1. New file: `src/main/java/com/minecraft/teleportshop/storage/HomeStorage.java`
   - Saves player homes to a YAML file
   - Loads homes from the file when server starts

2. New file: `src/main/java/com/minecraft/teleportshop/storage/WarpStorage.java`
   - Saves public warps to a YAML file
   - Loads warps when server starts

**How to do it:**

1. In VS Code, right-click on `storage/` folder (or create it)
2. Create new file: `HomeStorage.java`
3. Follow the code pattern from `docs/DEVELOPMENT.md`
4. Write Java code to:
   - Read homes from file
   - Write homes to file
   - Handle file creation

**Step-by-step coding:**
1. Read `docs/DEVELOPMENT.md` section "Storage Implementation"
2. Look at existing handler examples (ConfigHandler, TeleportHandler)
3. Create the HomeStorage class
4. Create the WarpStorage class
5. Test: Run `mvn clean package` to compile
6. If no errors, it works!

### Step 6: Test Your Code Compiles

After writing code:

1. Open Command Prompt (or PowerShell)
2. Navigate to: `New projects/minecraft-addons/src/java/plugins/teleportshop`
3. Type: `mvn clean package`
4. Wait for it to finish
5. Look for: "BUILD SUCCESS" at the end
6. If you see "BUILD SUCCESS" → Your code is correct! ✅
7. If you see errors → Fix them and try again

---

## Step-by-Step Example: Adding a Command

Let me walk you through ONE complete example:

### Example: Implement HomeCommand

**Current state:** `HomeCommand.java` exists but is empty (stub)

**What it should do:**
- Player types `/home` in Minecraft
- Plugin checks if player has a home saved
- If yes: teleport to home
- If no: send message "You don't have a home"

**Step 1: Open the file**
- File: `src/main/java/com/minecraft/teleportshop/commands/teleport/HomeCommand.java`
- It already has the basic structure

**Step 2: Write the code**

Find the `onCommand` method in the file. It looks like:
```java
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    // Your code goes here
    return true;
}
```

Add code to:
```java
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    // 1. Check if sender is a player
    if (!(sender instanceof Player)) {
        sender.sendMessage("Only players can use this!");
        return true;
    }
    
    // 2. Get the player
    Player player = (Player) sender;
    
    // 3. Get home location from handler
    String homeName = args.length > 0 ? args[0] : "home";
    Location home = plugin.getTeleportHandler().getHome(player, homeName);
    
    // 4. Check if home exists
    if (home == null) {
        player.sendMessage("§cHome not found!");
        return true;
    }
    
    // 5. Teleport player
    player.teleport(home);
    player.sendMessage("§a✓ Teleported to home!");
    
    return true;
}
```

**Step 3: Test the code compiles**
- Command Prompt: `mvn clean package`
- Look for "BUILD SUCCESS"
- Done!

---

## Phase 3: Repeat for All Commands

Do the same for each of the 15 commands:

1. Open the command file
2. Add code to make it work
3. Compile with `mvn clean package`
4. If no errors, move to next command

**Commands to implement (in order):**
1. HomeCommand
2. SetHomeCommand
3. DelHomeCommand
4. ListHomesCommand
5. WarpCommand
6. SetWarpCommand
7. DelWarpCommand
8. WarpListCommand
9. BackCommand
10. SpawnCommand
11. SetSpawnCommand
12. TPACommand
13. TPAHereCommand
14. TPAcceptCommand
15. TPDenyCommand

---

## About JavaScript (Just FYI)

You mentioned "not used to JS". Good news:

**This project is 100% JAVA - No JavaScript needed yet!**

- ✅ Phase 1-4: Pure Java (for Minecraft plugin)
- ⏰ Phase 5: Optional Node.js tools (for admin dashboard)

**For Phase 1-4, you only need to know Java.**

If we get to Phase 5 and need JavaScript:
- I'll give you step-by-step guides
- JavaScript is simpler than Java
- We'll go slow and explain everything

---

## Quick Reference

### Common Commands

**Build the project:**
```bash
mvn clean package
```

**Build without tests (faster):**
```bash
mvn clean package -DskipTests
```

**Check for compilation errors:**
```bash
mvn compile
```

**Run tests:**
```bash
mvn test
```

### Common Java Code Patterns

**Check if object is a Player:**
```java
if (!(sender instanceof Player)) {
    return true;
}
Player player = (Player) sender;
```

**Send message to player:**
```java
player.sendMessage("§aMessage in green");
player.sendMessage("§cMessage in red");
player.sendMessage("§bMessage in blue");
```

**Get permission:**
```java
if (!player.hasPermission("teleportshop.home.use")) {
    player.sendMessage("§cNo permission!");
    return true;
}
```

**Call handler method:**
```java
Location home = plugin.getTeleportHandler().getHome(player, "home");
```

---

## Troubleshooting

### Error: "Cannot find symbol"
- A variable name is wrong
- Check the spelling
- Look at existing examples

### Error: "Method not found"
- Method doesn't exist
- Check `TeleportHandler` for available methods
- Check `docs/DEVELOPMENT.md` for patterns

### Build succeeds but nothing happens
- Code is correct but logic might be wrong
- Test manually on Minecraft server
- Check console output for errors

### Still stuck?
- Read `docs/DEVELOPMENT.md` again (it has all patterns)
- Look at a similar command that's already done
- Check the error message - it usually tells you what's wrong

---

## Your Daily Workflow

Each day:

1. **Morning:** Read what you need to do (from TODO.md)
2. **Code:** Write code for one command or feature
3. **Compile:** `mvn clean package`
4. **Test:** Check for "BUILD SUCCESS"
5. **Commit:** Save your work to git
6. **Track:** Update PROGRESS_TRACKER.md
7. **Move on:** Pick next task from TODO.md

---

## Summary

**You are:**
- 100% ready to code
- Just need to follow the step-by-step guide
- Will write Java code (not JavaScript)
- Building a Minecraft plugin one task at a time

**What to do now:**
1. Install Maven (follow SETUP_TOOLS.md)
2. Read START_HERE.md → TODO.md
3. Pick the first task (YAML Storage)
4. Start coding!

**You've got this! 🚀**

---

**Questions?** Email: nobodylearn174@gmail.com
