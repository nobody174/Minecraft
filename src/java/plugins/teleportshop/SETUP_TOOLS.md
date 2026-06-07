//
// TeleportShop - Tool Installation Guide
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/minecraft-addons
// License: All rights reserved © 2026 nobody174
//

# Tool Installation Guide

This guide helps you install all necessary tools for TeleportShop development.

---

## ✅ Tools You Already Have

### Java (JDK 20)
```
Status: ✅ INSTALLED
Version: 20.0.2
Type: Java SE Runtime Environment
```

Your Java is good! We need JDK 17+ and you have 20, which is perfect.

### Git
```
Status: ✅ INSTALLED
Version: 2.54.0
Purpose: Version control
```

Git is ready for managing your code!

### VS Code
```
Status: ✅ INSTALLED
Version: 1.122.1
Purpose: Code editor (IDE)
```

VS Code is your code editor and is all set!

---

## ❌ Tools You Need to Install

### Maven (REQUIRED!)

**What is Maven?**
- Builds your Java code (compiles it into a .jar file)
- Manages dependencies (libraries your code needs)
- Runs tests
- Creates the plugin file you'll deploy to Minecraft server

**Installation Steps:**

#### Step 1: Download Maven
1. Go to: https://maven.apache.org/download.cgi
2. Download **Binary zip archive** (not source)
3. Save it somewhere like `C:\Program Files\Apache\maven` or `C:\Tools\maven`

#### Step 2: Extract the ZIP
1. Right-click the downloaded ZIP
2. Choose "Extract All..."
3. Extract to `C:\Program Files\Apache` (or your tools folder)

#### Step 3: Add Maven to PATH (Windows)
1. Open **Start Menu** → Search "Environment Variables"
2. Click **"Edit the system environment variables"**
3. Click **"Environment Variables"** button
4. Under "User variables" click **"New"**
5. Variable name: `MAVEN_HOME`
6. Variable value: `C:\Program Files\Apache\maven` (or wherever you extracted it)
7. Click **OK**
8. Now edit the **PATH** variable:
   - Select **PATH** in the list
   - Click **Edit**
   - Click **New**
   - Add: `%MAVEN_HOME%\bin`
   - Click **OK** three times

#### Step 4: Verify Installation
1. Close VS Code completely
2. Open a **new Command Prompt** (Windows)
3. Type: `mvn --version`
4. You should see Maven version info

If you see an error, something went wrong. Try again or contact support.

---

## 🎮 Minecraft Paper Server (For Testing)

**What is it?**
- A Minecraft server where you'll test your plugin
- Must have for testing, but don't need it yet
- We'll set it up before Phase 1 testing

**When to Install:**
- After you finish implementing the first commands
- For now, just know you'll need it later

**Installation (when needed):**
1. Create folder: `C:\Users\Vartd\minecraft-server`
2. Download Paper JAR from: https://papermc.io/downloads/paper
3. Create `eula.txt` with: `eula=true`
4. Run the JAR with: `java -jar paper-1.20.1.jar nogui`
5. Wait for it to create the server

---

## 📚 Optional but Recommended

### IntelliJ IDEA Community Edition
- Better IDE for Java than VS Code (optional)
- Free community edition
- Download: https://www.jetbrains.com/idea/download/
- Not required - VS Code works fine

---

## Checklist Before Starting

After installing Maven, verify everything works:

- [ ] Java installed: `java -version`
- [ ] Maven installed: `mvn --version`
- [ ] Git installed: `git --version`
- [ ] VS Code ready
- [ ] Working folder set: `New projects/minecraft-addons/src/java/plugins/teleportshop/`

---

## Quick Commands You'll Use

Once Maven is installed, you can use these commands:

### Build the project
```bash
mvn clean package
```
This compiles your code and creates a .jar file.

### Run tests
```bash
mvn test
```
This runs all unit tests.

### Build without tests
```bash
mvn clean package -DskipTests
```
This builds faster (skips tests).

---

## Troubleshooting

### "mvn: command not found"
- Maven PATH not added correctly
- Restart VS Code completely
- Try opening new Command Prompt/PowerShell

### "Java version too old"
- You have JDK 20 which is perfect
- Not your issue

### "Cannot find Maven home"
- Double-check the Maven installation path
- Verify `MAVEN_HOME` environment variable is set correctly

---

## Next Steps

1. **Install Maven** (follow steps above)
2. **Verify Maven works** (run `mvn --version`)
3. **Read START_HERE.md** (back in your working folder)
4. **Begin coding!** 🚀

---

**Note**: Don't install anything else right now. We'll add Minecraft Server later when needed for testing.

**Status**: Once Maven is installed, you're 100% ready to start coding! ✅

---

For help: nobodylearn174@gmail.com
