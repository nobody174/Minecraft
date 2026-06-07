# Setup Guide - Minecraft Addons Project

Complete setup instructions for developing Minecraft mods, plugins, and tools.

## Prerequisites Installation

### 1. Java Development Kit (JDK)

**Windows:**
- Download JDK 17+ from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Eclipse Temurin](https://adoptium.net/)
- Install and note the installation path
- Set `JAVA_HOME` environment variable

**Verify Installation:**
```bash
java -version
javac -version
```

### 2. Maven

**Windows:**
- Download from [Apache Maven](https://maven.apache.org/download.cgi)
- Extract to `C:\Program Files\Apache\maven`
- Add to PATH: `C:\Program Files\Apache\maven\bin`

**Verify Installation:**
```bash
mvn --version
```

### 3. Node.js

**Windows:**
- Download LTS from [nodejs.org](https://nodejs.org/)
- Install npm automatically included
- Verify: `node --version` and `npm --version`

### 4. Git

**Windows:**
- Download from [git-scm.com](https://git-scm.com/)
- Use default installation settings
- Verify: `git --version`

### 5. IDE Setup

#### Option A: IntelliJ IDEA (Recommended)
1. Download Community Edition (free) from [jetbrains.com](https://www.jetbrains.com/idea/)
2. Install and open the project
3. IntelliJ auto-detects Maven and configures Minecraft Forge development environment
4. Import `pom.xml` when prompted

#### Option B: Eclipse + Minecraft Forge
1. Download [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/)
2. Install Minecraft Forge MDK plugin
3. Import existing Maven projects

### 6. Minecraft Server (Testing)

**Paper Server (Recommended):**
```bash
# Create a server directory
mkdir minecraft-server
cd minecraft-server

# Download latest Paper JAR
# From https://papermc.io/downloads/paper

# Create run script (Windows batch)
# eula.txt - set eula=true
```

**Spigot/Bukkit Alternative:**
- Build from [Spigot BuildTools](https://www.spigotmc.org/wiki/spigot-plugin-creators-guide/)

## Project Setup

### 1. Clone Repository
```bash
cd "New projects"
git clone https://github.com/yourusername/minecraft-addons.git
cd minecraft-addons
```

### 2. Install Java Dependencies
```bash
mvn clean install
```

### 3. Install Node.js Dependencies
```bash
npm install
```

### 4. Configure Local Properties
Create `local.properties` (not committed to git):
```properties
# Minecraft Server Path
minecraft.server.path=C:/Users/YourUsername/minecraft-server

# JDK Path (if not in PATH)
java.home=C:/Program Files/Java/jdk-17

# Minecraft Version (default: 1.20.1)
minecraft.version=1.20.1

# Build Output
build.output.dir=./build
```

## IDE Configuration

### IntelliJ IDEA

1. **Open Project**
   - File → Open → Select `minecraft-addons` folder
   - Choose "Trust Project" when prompted

2. **Configure JDK**
   - File → Project Structure → Project
   - Set Project SDK to JDK 17+
   - Click "Apply" and "OK"

3. **Configure Minecraft SDK**
   - Right-click `pom.xml` → Add as Maven Project
   - IntelliJ detects Minecraft dependencies automatically

4. **Run Configurations**
   - Run → Edit Configurations
   - Create new "Application" configs for:
     - `Debug Minecraft Client` (if building Forge mods)
     - `Debug Minecraft Server` (if building Bukkit plugins)

### VS Code

1. **Extensions to Install:**
   - Extension Pack for Java
   - Maven for Java
   - Gradle for Java
   - Minecraft Tools (optional)

2. **Settings:**
   - File → Preferences → Settings
   - Search "java.home" and set to JDK 17+ path
   - Search "maven.executable.path" if Maven not in PATH

## Building and Testing

### Build Java Modules
```bash
# Build all Java projects
mvn clean package

# Build specific module
mvn clean package -pl :module-name

# Skip tests
mvn clean package -DskipTests
```

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TestClassName

# Run with coverage report
mvn test jacoco:report
# View report: target/site/jacoco/index.html
```

### Run Node.js Tools
```bash
# Install dependencies
npm install

# Build TypeScript
npm run build

# Run tests
npm test

# Run linting
npm run lint
```

## Server Testing Setup

### 1. Create Test Server Directory
```bash
mkdir ~/minecraft-test-server
cd ~/minecraft-test-server
```

### 2. Download Server JAR
- Paper: [papermc.io](https://papermc.io/downloads/paper)
- Spigot: Build with BuildTools
- Bukkit: [bukkit.org](https://bukkit.org/)

### 3. Accept EULA
```bash
# Create eula.txt with content:
eula=true
```

### 4. Deploy Plugin
```bash
# Copy built JAR to plugins folder
cp target/my-plugin-1.0.jar ~/minecraft-test-server/plugins/

# Restart server to load plugin
```

### 5. Connect for Testing
- Start Minecraft Client
- Multiplayer → Direct Connection
- Server IP: `127.0.0.1:25565` (or your test server IP)

## Troubleshooting

### Maven Issues
```bash
# Clear Maven cache if builds fail
mvn clean dependency:purge-local-repository

# Check Maven is using correct Java version
mvn --version
```

### IDE Not Finding Dependencies
```bash
# Reimport Maven project
# IntelliJ: Right-click pom.xml → Maven → Reimport
# VS Code: Reload Window (Ctrl+Shift+P → Developer: Reload Window)
```

### Server Won't Start
- Verify JDK 17+ installed (`java -version`)
- Check `server.properties` configuration
- Ensure port 25565 is not blocked by firewall
- Review `logs/latest.log` for errors

## Next Steps

1. Read [CONTRIBUTING.md](CONTRIBUTING.md) for code standards
2. Check [IDEAS.md](IDEAS.md) for planned features
3. Review addon-specific documentation in `docs/`
4. Start developing your first addon!

## Environment Variables

```bash
# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export MINECRAFT_SERVER_PATH=$HOME/minecraft-server

# Windows (PowerShell)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:MINECRAFT_SERVER_PATH = "C:\Users\YourUsername\minecraft-server"
```

## Quick Commands Reference

```bash
# Development
mvn clean package          # Build all
npm install               # Install JS deps
npm run build             # Build Node.js tools

# Testing
mvn test                  # Run Java tests
npm test                  # Run JS tests
mvn integration-test      # Run server integration tests

# Deployment
# Copy JAR files to minecraft-server/plugins/
# Restart server and verify in logs
```

For detailed addon-specific setup, see documentation in `docs/mods/`, `docs/plugins/`, and `docs/data-packs/`.
