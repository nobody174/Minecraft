//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.world.entity.LivingEntity;

import com.nobody174.petevolution.creature.PetData;
import com.nobody174.petevolution.skills.Skill;
import com.nobody174.petevolution.skills.SkillRegistry;

/**
 * One side of an in-progress {@link BattleSession}. Tracks a battle-local HP pool
 * (separate from the entity's live vanilla health, so a battle doesn't actually
 * damage/kill the released mob in the world) and per-skill cooldowns in ticks.
 *
 * Takes the owner's UUID directly (rather than a {@code ServerPlayer}) so a
 * participant can be constructed even when its owner is currently offline —
 * battle messaging/reward lookups re-resolve the ServerPlayer by UUID later
 * and simply skip messaging if the owner isn't online.
 */
public final class BattleParticipant {

    /** Fraction of incoming damage absorbed by an active DEFENSE_BUFF, consumed by the next hit taken. */
    private static final double DEFENSE_BUFF_DAMAGE_REDUCTION = 0.5;

    private final UUID entityId;
    private final UUID ownerId;
    private final PetData petData;
    private int currentHp;
    private final Map<String, Integer> cooldownRemaining = new HashMap<>();
    private boolean defenseBuffActive;

    public BattleParticipant(LivingEntity entity, UUID ownerId, PetData petData) {
        this.entityId = entity.getUUID();
        this.ownerId = ownerId;
        this.petData = petData;
        this.currentHp = petData.hp();
    }

    public UUID entityId() {
        return entityId;
    }

    public UUID ownerId() {
        return ownerId;
    }

    public PetData petData() {
        return petData;
    }

    public int currentHp() {
        return currentHp;
    }

    public int maxHp() {
        return petData.hp();
    }

    public boolean isDefeated() {
        return currentHp <= 0;
    }

    /** Applies the active defense buff's damage reduction (if any), consuming it, before reducing HP. */
    public void applyDamage(int amount) {
        int reduced = defenseBuffActive ? (int) Math.round(amount * (1 - DEFENSE_BUFF_DAMAGE_REDUCTION)) : amount;
        defenseBuffActive = false;
        currentHp = Math.max(0, currentHp - Math.max(1, reduced));
    }

    public void activateDefenseBuff() {
        defenseBuffActive = true;
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp(), currentHp + amount);
    }

    public List<Skill> unlockedSkills() {
        return SkillRegistry.skillsForLevel(petData.level());
    }

    public boolean isSkillReady(Skill skill) {
        return cooldownRemaining.getOrDefault(skill.id(), 0) <= 0;
    }

    public void startCooldown(Skill skill) {
        cooldownRemaining.put(skill.id(), skill.cooldownTicks());
    }

    /** Advance all cooldowns by one tick. Call once per server tick this participant is in a session. */
    public void tickCooldowns() {
        cooldownRemaining.replaceAll((id, remaining) -> Math.max(0, remaining - 1));
    }
}

// Built with assistance from Claude Code by Anthropic.
