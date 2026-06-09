# Plugin Template - Bukkit/Spigot Plugin

Complete template for creating a new Bukkit/Spigot plugin.

## Directory Structure

```
plugin-name/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/minecraft/plugin/
│   │   │       ├── PluginName.java
│   │   │       ├── commands/
│   │   │       ├── events/
│   │   │       ├── listeners/
│   │   │       ├── utils/
│   │   │       └── config/
│   │   └── resources/
│   │       ├── plugin.yml
│   │       ├── config.yml
│   │       └── messages.yml
│   └── test/
│       └── java/
│           └── com/minecraft/plugin/
│               └── *Test.java
├── docs/
│   ├── FEATURES.md
│   ├── COMMANDS.md
│   ├── API.md
│   └── CONFIG.md
├── pom.xml
└── README.md
```

## Essential Files

### 1. pom.xml (Module Configuration)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.minecraft.addons</groupId>
        <artifactId>minecraft-addons</artifactId>
        <version>1.0.0</version>
        <relativePath>../../pom.xml</relativePath>
    </parent>

    <artifactId>plugin-name</artifactId>
    <version>1.0.0</version>
    <name>Plugin Name</name>
    <description>Brief description of plugin functionality</description>

    <dependencies>
        <!-- Spigot API -->
        <dependency>
            <groupId>org.spigotmc</groupId>
            <artifactId>spigot-api</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- JUnit Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Mockito -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Built-By>${user.name}</Built-By>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>

            <!-- Shade Maven Plugin (if using external libraries) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 2. plugin.yml (Plugin Configuration)

```yaml
# Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
# Licensed under MIT License

name: PluginName
version: 1.0.0
main: com.minecraft.plugin.PluginName
description: Brief description of what the plugin does
author: nobody174
website: https://github.com/nobody174/minecraft-addons

# Minimum Bukkit/Spigot API version
api-version: '1.20'

# Commands
commands:
  command-name:
    description: Description of what the command does
    usage: /command-name [args]
    aliases:
      - alias1
      - alias2
    permission: plugin-name.command.name

# Permissions
permissions:
  plugin-name.*:
    description: Grants all plugin permissions
    default: op
    children:
      plugin-name.admin: true
      plugin-name.user: true
  plugin-name.admin:
    description: Admin commands
    default: op
  plugin-name.user:
    description: User commands
    default: true
```

### 3. Main Plugin Class

```java
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under MIT License

package com.minecraft.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginName extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("PluginName enabled!");
        
        // Save default config if not exists
        saveDefaultConfig();
        
        // Register commands
        registerCommands();
        
        // Register listeners
        registerListeners();
        
        // Load configurations
        loadConfiguration();
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginName disabled!");
    }

    private void registerCommands() {
        // Register your commands here
    }

    private void registerListeners() {
        // Register your event listeners here
    }

    private void loadConfiguration() {
        // Load configuration from config.yml
    }
}
```

## Documentation Templates

### FEATURES.md
- List all features
- Include usage examples
- Explain mechanics
- Link to detailed docs

### COMMANDS.md
- All commands and aliases
- Usage syntax
- Permission requirements
- Examples

### API.md
- Public API classes/methods
- Event handling examples
- Custom event documentation
- Integration guide

### CONFIG.md
- All configuration options
- Default values
- Explanation of each setting
- Examples of common configurations

## Testing Template

```java
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under MIT License

package com.minecraft.plugin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PluginNameTest {
    private PluginName plugin;

    @BeforeEach
    void setUp() {
        plugin = new PluginName();
    }

    @Test
    void testPluginEnables() {
        assertNotNull(plugin);
    }

    @Test
    void testConfiguration() {
        // Test configuration loading
    }
}
```

## Building the Plugin

```bash
# Build the plugin JAR
mvn clean package

# JAR will be at: target/plugin-name-1.0.0.jar

# Deploy to test server
cp target/plugin-name-1.0.0.jar ~/minecraft-server/plugins/

# Restart server
cd ~/minecraft-server && ./stop.sh && ./start.sh
```

## Distribution

Once ready for release:
1. Tag version in git: `git tag v1.0.0`
2. Create release on GitHub with JAR artifact
3. Post to SpigotMC if desired
4. Update IDEAS.md with release status

## Resources

- [Bukkit API Documentation](https://hub.spigotmc.org/javadocs/bukkit/)
- [Spigot Plugin Development Wiki](https://www.spigotmc.org/wiki/spigot-plugin-creators-guide/)
- [Paper Documentation](https://docs.papermc.io/)
