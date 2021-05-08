package com.nexorel.pwork.content.Entities.boss.Necron;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NecronEntity extends MonsterEntity implements IRangedAttackMob {

    public NecronEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true/**check sight*/));
        super.registerGoals();
    }


    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.6F)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source != DamageSource.DROWN && !(source.getEntity() instanceof NecronEntity)) {
                return super.hurt(source, amount);
        } else {
            return false;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        int i = 2;
        double d  = this.getX() + (double)MathHelper.cos((this.yBodyRot + (float)(180 * (i - 1))) * ((float)Math.PI / 180F)) * 1.3D;
        double d1 = this.getY() + 1.0D;
        double d2 = this.getZ() + (double)MathHelper.sin((this.yBodyRot + (float)(180 * (i - 1))) * ((float)Math.PI / 180F)) * 1.3D;
        double accX = target.getX() - d;
        double accY = target.getY() - d1;
        double accZ = target.getZ() - d2;
        WitherSkullEntity e = new WitherSkullEntity(this.level, this, accX, accY, accZ);
        e.setOwner(this);
        e.setDangerous(true);
        e.setPosRaw(d, d1, d2);
        this.level.addFreshEntity(e);
    }
}
