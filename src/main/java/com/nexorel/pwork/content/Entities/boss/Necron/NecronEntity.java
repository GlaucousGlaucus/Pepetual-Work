package com.nexorel.pwork.content.Entities.boss.Necron;

import com.nexorel.pwork.content.Entities.Projectiles.HeadO.NexorelsHeado;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class NecronEntity extends MonsterEntity implements IRangedAttackMob {

    //TODO: Add sounds
    // make it a little more fight-able
    // better and interesting boss fight (long project maybe)

    private final SpawnMinionsAndActivateShield shield = new SpawnMinionsAndActivateShield(this);

    private final ServerBossInfo bossEvent = new ServerBossInfo(new StringTextComponent(TextFormatting.DARK_RED + "NECRON"), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6);

    public NecronEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(1, new SpawnMinionsAndActivateShield(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true/**check sight*/));
        super.registerGoals();
    }


    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.6F)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ARMOR, 44.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source != DamageSource.DROWN && !(source.getEntity() instanceof NecronEntity)) {
            return super.hurt(source, amount);
        } else if (!shield.canSpawnMore && entity instanceof AbstractArrowEntity) {
                return false;
        } else if (!shield.canSpawnMore) {
            float ReducedDamage = (amount / 4);
            return super.hurt(source, ReducedDamage);
        } else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        double d = this.getX();
        double d1 = this.getY() + 1.0D;
        double d2 = this.getZ();

        NexorelsHeado e = new NexorelsHeado(this, this.level, target);
        e.setOwner(this);
        e.setPosRaw(d, d1, d2);
        this.level.addFreshEntity(e);
    }


    @Override
    public void startSeenByPlayer(ServerPlayerEntity entity) {
        super.startSeenByPlayer(entity);
        this.bossEvent.addPlayer(entity);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity entity) {
        super.stopSeenByPlayer(entity);
        this.bossEvent.removePlayer(entity);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable ITextComponent component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }

    class SpawnMinionsAndActivateShield extends Goal {

        private NecronEntity entity;
        private boolean canSpawnMore = true;

        public SpawnMinionsAndActivateShield(NecronEntity necronEntity) {
            super();
            this.entity = necronEntity;
        }

        @Override
        public void tick() {
            super.tick();
            if (!entity.level.isClientSide) {
                ServerWorld serverworld = (ServerWorld) NecronEntity.this.level;
                DifficultyInstance difficultyinstance = serverworld.getCurrentDifficultyAt(entity.blockPosition());
                SkeletonEntity minion = this.createMinion(difficultyinstance, entity);
                if (this.canSpawnMore) {
                    serverworld.addFreshEntity(minion);
                    this.canSpawnMore = false;
                }
            }
        }

        private SkeletonEntity createMinion(DifficultyInstance instance, NecronEntity entity) {
            SkeletonEntity minion = EntityType.SKELETON.create(entity.level);
            minion.finalizeSpawn((ServerWorld) entity.level, instance, SpawnReason.REINFORCEMENT, null, null);
            minion.setPos(entity.getX(), entity.getY(), entity.getZ());
            minion.invulnerableTime = 100;
            minion.isInvulnerableTo(DamageSource.WITHER);
            minion.isInvulnerableTo(DamageSource.CRAMMING);
            minion.isInvulnerableTo(DamageSource.LIGHTNING_BOLT);
            minion.isInvulnerableTo(DamageSource.LAVA);
            minion.setPersistenceRequired();
            return minion;
        }

        @Override
        public boolean canUse() {
            return entity.getHealth() <= entity.getMaxHealth() * 0.5 && entity.isAlive() && this.canSpawnMore;
        }
    }
}
