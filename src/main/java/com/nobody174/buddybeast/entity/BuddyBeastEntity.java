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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class BuddyBeastEntity extends LivingEntity {
    private static final EntityDataAccessor<Integer> DATA_HEALTH = SynchedEntityData.defineId(BuddyBeastEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_OWNER_NAME = SynchedEntityData.defineId(BuddyBeastEntity.class, EntityDataSerializers.STRING);

    private UUID ownerUUID;
    private String ownerName = "Unknown";
    private boolean isTamed = false;

    public BuddyBeastEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSyncedData(SynchedEntityData.Builder builder) {
        super.defineSyncedData(builder);
        builder.define(DATA_HEALTH, 20);
        builder.define(DATA_OWNER_NAME, "Unknown");
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // Server-side tick logic
            // AI will be evaluated here
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

    @Override
    public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }
}

// Built with assistance from Claude Code by Anthropic.
