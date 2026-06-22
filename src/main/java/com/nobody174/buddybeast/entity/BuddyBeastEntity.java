//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.Level;

import com.nobody174.buddybeast.ai.FollowOwnerGoal;
import com.nobody174.buddybeast.ai.StayGoal;
import com.nobody174.buddybeast.ai.IdleGoal;
import com.nobody174.buddybeast.ai.LookAtOwnerGoal;
import com.nobody174.buddybeast.network.BuddySyncPacket;

import net.neoforged.neoforge.network.PacketDistributor;

import java.util.UUID;

public class BuddyBeastEntity extends Mob {
    private static final EntityDataAccessor<String> DATA_OWNER_NAME =
        SynchedEntityData.defineId(BuddyBeastEntity.class, EntityDataSerializers.STRING);

    private UUID ownerUUID;
    private String ownerName = "Unknown";
    private boolean isTamed = false;

    public BuddyBeastEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Priority-based goal execution (lower number = higher priority)
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1.0));
        this.goalSelector.addGoal(1, new LookAtOwnerGoal(this));
        this.goalSelector.addGoal(2, new StayGoal(this));
        this.goalSelector.addGoal(3, new IdleGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_OWNER_NAME, "Unknown");
    }

    private static final int SYNC_INTERVAL_TICKS = 10;

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.tickCount % SYNC_INTERVAL_TICKS == 0) {
            PacketDistributor.sendToPlayersTrackingEntity(this, new BuddySyncPacket(this.getId(), this.getHealth()));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putBoolean("Tamed", this.isTamed);
        tag.putString("OwnerName", this.ownerName);

        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        this.isTamed = tag.getBoolean("Tamed");
        this.ownerName = tag.getString("OwnerName");

        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        }
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public net.minecraft.world.InteractionResult mobInteract(net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand) {
        return BuddyInteractionHandler.handleRightClick(this, player, hand);
    }

    // Animation and visual state tracking
    public float getAnimationProgress() {
        // For use in animations - based on age
        return (float) this.tickCount / 20.0f;
    }

    public boolean isFollowing() {
        return this.isTamed();
    }

    public float getHealthPercent() {
        return this.getHealth() / this.getMaxHealth();
    }

    // Getter/Setter methods
    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwnerUUID(UUID uuid) {
        this.ownerUUID = uuid;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
        this.entityData.set(DATA_OWNER_NAME, name);
    }

    public LivingEntity findOwner() {
        if (this.ownerUUID == null) {
            return null;
        }
        // Try to find owner in nearby players
        if (this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            return serverLevel.getEntity(this.ownerUUID) instanceof LivingEntity ? (LivingEntity) serverLevel.getEntity(this.ownerUUID) : null;
        }
        return null;
    }
}

// Built with assistance from Claude Code by Anthropic.
