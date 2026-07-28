# Patreon Post Template — Minecraft Mods

Reusable structure for first-release Patreon posts across all mods in
this repo, so future posts stay consistent without re-deriving the
format each time. Based on the Found Ya! v1.1.0 launch post.

This file holds structure only — actual drafted post content goes in
a gitignored `PATREON_UPDATE_DRAFT.md` inside the specific mod's
folder (e.g. `mods/found-ya/PATREON_UPDATE_DRAFT.md`), reviewed,
posted, then deleted. The template is what persists, not the drafts.

## Voice/tone baseline
- First person, casual-friendly, "Hey everyone!" opener
- Bold the feature name on first mention in a bullet, plain text after
- Emoji section headers, consistent per-topic: ⭐ what it does,
  🎨 visuals, 🎮 how to use, 📥 where to get it, 📊 version status,
  🔮 what's next, 📋 full feature list, 🙏 thank you
- Closing line: "More updates soon — happy `<theme verb>`! `<emoji>`✨"
  (e.g. "happy tracking! 🎯✨", "happy modding! 🛡️✨")

## Template — First Release post

```markdown
## <emoji> <Mod Name> — First Release (v<version>)

Hey everyone! I'm excited to introduce **<Mod Name>**, a new NeoForge
mod for Minecraft 1.21.1 that <1-sentence hook: what it replaces or adds>.

### ⭐ What It Does
<bullets, one feature per line, bold the name, one sentence of player-facing impact each>

### 🎨 Visualization / Mechanics  (rename section to fit the mod — visuals, combat, systems, etc.)
<bullets, same shape>

### 🎮 How to Use
<quick start: menu/keybind path first, then key commands if applicable>

---

### 📥 WHERE TO GET IT
**GitHub:** https://github.com/nobody174/Minecraft/tree/main/mods/<mod-folder>

Includes full documentation and complete command/feature list

**Installation steps:**
- Install NeoForge 21.1.233+
- Drop the JAR into: `%appdata%\.minecraft\mods\`
- Launch Minecraft 1.21.1
- <1 short mod-specific first-action line>

**Requirements:**
- Minecraft 1.21.1
- NeoForge 21.1.233+
- Java 21+

### 📊 Version Status
**Current: v<version> (Stable Release / Initial Release)**

<1-2 sentences: what's included, tested/validated status>

### 🔮 What's Next?
Here's what I'm planning for upcoming versions:

**Near-term** (or v<next>.0 if staged)
- <bullets, direction only, no hard commitments>

**v<future>.0+**
- <bullets, longer-term ideas>

<1-sentence framing of the mod's long-term identity/vision>

### 📋 Full Feature List (v<version>)
✔ <bullet per shipped feature, one line each>

---

🙏 **Thank You**
Huge thanks to everyone supporting this project! Whether you're using
<Mod Name> or one of my other projects like <2-3 other mod names>, I
appreciate you being here.

More updates soon — happy <theme verb>! <emoji>✨
```

## Workflow

1. Draft the actual post content in `PATREON_UPDATE_DRAFT.md` inside
   the mod's own folder (already gitignored at the repo root).
2. Review, edit, post it on Patreon.
3. Delete the draft file once posted — this template is what
   persists, not the one-off content.
