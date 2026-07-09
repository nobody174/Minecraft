# Contributing Guidelines

Thank you for contributing to the Minecraft Addons project! This guide explains our development workflow, coding standards, and contribution process.

## Code Style Guidelines

### Java Code

**Naming Conventions:**
- Classes: `PascalCase` (e.g., `CustomEventHandler`)
- Methods: `camelCase` (e.g., `handlePlayerDamage()`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_PLAYERS = 50`)
- Variables: `camelCase` (e.g., `playerCount`)

**Formatting:**
- Indentation: 4 spaces (no tabs)
- Line length: 120 characters max
- Braces: Opening brace on same line (Java style)
- Import statements: Group by package, alphabetical order

**Example:**
```java
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE

package com.example.minecraft.addon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class CustomEventHandler {
    private static final int MAX_PLAYERS = 50;
    private int activePlayerCount = 0;

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        activePlayerCount++;
        if (activePlayerCount > MAX_PLAYERS) {
            event.setJoinMessage(null);
        }
    }

    public int getActivePlayerCount() {
        return activePlayerCount;
    }
}
```

**Documentation:**
- JavaDoc for public APIs
- Brief method descriptions (1-2 lines)
- Include parameter and return descriptions

```java
/**
 * Registers a new custom event handler.
 * 
 * @param handler the event handler to register
 * @return true if registration successful, false otherwise
 */
public boolean registerHandler(EventHandler handler) {
    // implementation
}
```

### JavaScript/TypeScript

**Naming Conventions:**
- Variables/Functions: `camelCase`
- Classes/Interfaces: `PascalCase`
- Constants: `UPPER_SNAKE_CASE`
- Files: `kebab-case.ts` or `kebab-case.js`

**Formatting:**
- Indentation: 2 spaces
- Line length: 100 characters max
- Semicolons: Always use
- Quotes: Double quotes for strings
- Type annotations: Required for TypeScript

**Example:**
```typescript
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE

interface MinecraftPlayer {
  uuid: string;
  name: string;
  joinTime: Date;
}

const MAX_PLAYER_NAME_LENGTH = 16;

export class PlayerManager {
  private players: Map<string, MinecraftPlayer> = new Map();

  public addPlayer(player: MinecraftPlayer): void {
    if (player.name.length > MAX_PLAYER_NAME_LENGTH) {
      throw new Error("Player name too long");
    }
    this.players.set(player.uuid, player);
  }

  public getPlayer(uuid: string): MinecraftPlayer | undefined {
    return this.players.get(uuid);
  }
}
```

### YAML (Data Packs, Config Files)

**Formatting:**
- Indentation: 2 spaces
- Keys in lowercase with underscores
- Comments for complex sections

**Example:**
```yaml
# Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
# Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE

# Data pack configuration
custom_recipes:
  iron_hammer:
    result:
      item: custom:iron_hammer
      count: 1
    ingredients:
      - item: minecraft:iron_ingot
        count: 4
      - item: minecraft:stick
        count: 2
```

## File Headers

All source files must include a copyright header:

**Java:**
```java
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE
```

**JavaScript/TypeScript:**
```javascript
// Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
// Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE
```

**Python:**
```python
# Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
# Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE
```

**YAML:**
```yaml
# Copyright (c) 2026 nobody174 - nobodylearn174@gmail.com
# Licensed under PolyForm Noncommercial License 1.0.0 - see LICENSE
```

## Commit Message Conventions

Follow conventional commits format:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation update
- `style`: Code style (formatting, missing semicolons)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance, dependencies
- `ci`: CI/CD changes

**Example Commits:**
```
feat(bukkit-plugin): add custom event system

- Implements custom event handler interface
- Adds event priority system
- Includes unit tests for all event types

Closes #42
```

```
fix(data-pack): correct loot table condition

The entity property condition was not being evaluated correctly.
Updated to use proper JSON structure.

Fixes #128
```

## Pull Request Process

### Before Creating a PR

1. **Update your branch:**
   ```bash
   git fetch origin
   git rebase origin/main
   ```

2. **Run tests locally:**
   ```bash
   mvn clean test
   npm test
   ```

3. **Check code formatting:**
   ```bash
   mvn spotless:check  # For Java
   npm run lint        # For JavaScript
   ```

4. **Build the project:**
   ```bash
   mvn clean package
   npm run build
   ```

### PR Title and Description

**Title Format:** `[Type] Brief description (scope)`

**Description Template:**
```markdown
## Description
Brief explanation of the changes.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
How to test these changes:
1. Step 1
2. Step 2

## Checklist
- [ ] Tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
```

## Testing Requirements

### Unit Tests

**Java (JUnit 5):**
```bash
mvn test
```

**JavaScript (Jest):**
```bash
npm test
```

**Coverage:**
- Minimum 80% code coverage for new code
- All public APIs must have tests
- Test edge cases and error conditions

### Integration Tests

```bash
# Run server integration tests
mvn integration-test

# Or with live server
mvn -Dminecraft.server.running=true integration-test
```

### Manual Testing

1. Deploy addon to test server
2. Test core functionality
3. Test edge cases mentioned in PR
4. Check for console errors
5. Verify multiplayer behavior

## Addon Development Standards

### Structure
```
addon-name/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── config.yml
│   └── test/
├── docs/
│   ├── FEATURES.md
│   ├── COMMANDS.md
│   └── API.md
├── README.md
└── pom.xml
```

### Documentation Requirements

Each addon must include:
- **README.md**: Overview and features
- **SETUP.md**: Installation instructions
- **FEATURES.md**: Detailed feature list
- **COMMANDS.md**: Command reference (if applicable)
- **API.md**: Public API documentation
- **CHANGELOG.md**: Version history

### Version Format

Use semantic versioning: `MAJOR.MINOR.PATCH`

Example: `1.0.0`, `1.2.5`, `2.0.0`

## CI/CD Pipeline

GitHub Actions automatically:
- Runs tests on all commits
- Checks code formatting
- Builds JAR/ZIP files
- Generates coverage reports
- Deploys to appropriate environment

**View Results:**
- Check GitHub Actions tab in repository
- Review workflow logs for failures
- Coverage reports available as artifacts

## Issue Tracking

### Creating Issues

Use these templates:
- **Bug Report**: Clear steps to reproduce, expected vs actual behavior
- **Feature Request**: Description, use case, and potential implementation
- **Documentation**: Missing docs or unclear sections

### Ideas.md Format

All planned features go in [IDEAS.md](IDEAS.md):

```markdown
## Feature Name
- **Status**: Idea | In Development | Testing | Ready
- **Type**: Mod | Plugin | Data Pack | Tool
- **Purpose**: What problem it solves
- **Description**: Technical details
- **Notes**: Dependencies, version requirements
```

## Questions or Need Help?

- Check existing documentation in `docs/`
- Review similar addons in the project
- Open a discussion on GitHub Issues
- Email: nobodylearn174@gmail.com

Thank you for helping improve the Minecraft Addons project!
