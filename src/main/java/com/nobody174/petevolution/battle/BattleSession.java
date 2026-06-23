//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.battle;

import java.util.UUID;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.skills.Skill;
import com.nobody174.petevolution.skills.SkillElement;
import com.nobody174.petevolution.skills.SkillEffectType;

/**
 * A single tick-driven pet-vs-pet battle. Replaces the old instant stat-power
 * comparison with proper turn resolution so skill cooldowns are meaningful.
 *
 * Each "round" lasts {@link #ROUND_TICKS} ticks: the challenger's owner gets an
 * optional override window (see {@link #PLAYER_INPUT_WINDOW_TICKS}) to pick a
 * skill via the battle UI payload; if no input arrives in time, {@link BattleAi}
 * picks automatically. The defender side is always AI-controlled (its owner may
 * not even be online) per the existing battle-trigger design. Both sides' skills
 * resolve simultaneously at the end of the round, in speed order (SPD stat).
 */
public final class BattleSession {

    public static final int ROUND_TICKS = 20 * 2;
    public static final int PLAYER_INPUT_WINDOW_TICKS = 20 * 2;

    private final UUID sessionId = UUID.randomUUID();
    private final UUID challengerOwnerId;
    private final BattleParticipant challenger;
    private final BattleParticipant defender;
    private final MinecraftServer server;

    private int roundTimer;
    private Skill pendingPlayerChoice;
    private boolean finished;

    public BattleSession(MinecraftServer server, ServerPlayer challengerOwner, BattleParticipant challenger, BattleParticipant defender) {
        this.server = server;
        this.challengerOwnerId = challengerOwner.getUUID();
        this.challenger = challenger;
        this.defender = defender;
        this.roundTimer = ROUND_TICKS;
        if (PetEvolution.DEBUG_LOGGING) {
            PetEvolution.LOGGER.info("[Battle] Session {} started: challenger HP {} vs defender HP {}",
                sessionId, challenger.maxHp(), defender.maxHp());
        }
    }

    public UUID sessionId() {
        return sessionId;
    }

    public BattleParticipant challenger() {
        return challenger;
    }

    public BattleParticipant defender() {
        return defender;
    }

    public boolean isFinished() {
        return finished;
    }

    public int roundTimer() {
        return roundTimer;
    }

    /** Called from the battle-input network payload handler when the challenger's owner picks a skill. */
    public void submitPlayerChoice(Skill skill) {
        this.pendingPlayerChoice = skill;
    }

    /** Advances the session by one server tick. Returns true once the battle has concluded. */
    public boolean tick() {
        if (finished) {
            return true;
        }

        roundTimer--;
        if (roundTimer > 0) {
            return false;
        }

        resolveRound();
        roundTimer = ROUND_TICKS;

        if (challenger.isDefeated() || defender.isDefeated()) {
            concludeBattle();
        }

        return finished;
    }

    private void resolveRound() {
        Skill challengerSkill = pendingPlayerChoice != null && challenger.isSkillReady(pendingPlayerChoice)
            ? pendingPlayerChoice
            : BattleAi.chooseSkill(challenger);
        pendingPlayerChoice = null;

        Skill defenderSkill = BattleAi.chooseSkill(defender);

        boolean challengerFirst = challenger.petData().spd() >= defender.petData().spd();
        if (challengerFirst) {
            applySkill(challenger, defender, challengerSkill);
            if (!defender.isDefeated()) {
                applySkill(defender, challenger, defenderSkill);
            }
        } else {
            applySkill(defender, challenger, defenderSkill);
            if (!challenger.isDefeated()) {
                applySkill(challenger, defender, challengerSkill);
            }
        }

        challenger.tickCooldowns();
        defender.tickCooldowns();
        challenger.startCooldown(challengerSkill);
        defender.startCooldown(defenderSkill);

        if (PetEvolution.DEBUG_LOGGING) {
            PetEvolution.LOGGER.info("[Battle] Session {} round resolved: challenger used {} (HP {}), defender used {} (HP {})",
                sessionId, challengerSkill.id(), challenger.currentHp(), defenderSkill.id(), defender.currentHp());
        }
    }

    private void applySkill(BattleParticipant user, BattleParticipant target, Skill skill) {
        switch (skill.effectType()) {
            case HEAL -> user.heal(skill.power() + user.petData().special() / 4);
            case DEFENSE_BUFF -> user.heal(0); // placeholder no-op effect beyond reduced incoming damage next hit is out of scope for v2.0
            case PHYSICAL_DAMAGE -> target.applyDamage(computeDamage(user, target, skill, user.petData().atk()));
            case SPECIAL_DAMAGE -> target.applyDamage(computeDamage(user, target, skill, user.petData().special()));
        }
    }

    private int computeDamage(BattleParticipant user, BattleParticipant target, Skill skill, int offenseStat) {
        SkillElement targetElement = BattleAi.chooseSkill(target).element();
        double advantage = skill.element().advantageMultiplierAgainst(targetElement);
        double raw = (skill.power() + offenseStat) * advantage - target.petData().def() * 0.5;
        return (int) Math.max(1, Math.round(raw));
    }

    private void concludeBattle() {
        finished = true;
        boolean challengerWins = !challenger.isDefeated();

        if (PetEvolution.DEBUG_LOGGING) {
            PetEvolution.LOGGER.info("[Battle] Session {} concluded: {} wins", sessionId, challengerWins ? "challenger" : "defender");
        }

        if (server.getPlayerList().getPlayer(challengerOwnerId) instanceof ServerPlayer challengerOwner) {
            challengerOwner.displayClientMessage(
                Component.literal(challengerWins ? "Your pet won the battle!" : "Your pet lost the battle.")
                    .withStyle(challengerWins ? ChatFormatting.GREEN : ChatFormatting.RED),
                true);
        }
        if (server.getPlayerList().getPlayer(defender.ownerId()) instanceof ServerPlayer defenderOwner) {
            defenderOwner.displayClientMessage(
                Component.literal(challengerWins ? "Your pet lost the battle." : "Your pet won the battle!")
                    .withStyle(challengerWins ? ChatFormatting.RED : ChatFormatting.GREEN),
                true);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
