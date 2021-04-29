package com.nexorel.pwork.content.Entities;

import com.nexorel.pwork.PRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class NexorelsHeado extends ProjectileItemEntity {

    public NexorelsHeado(EntityType<? extends ProjectileItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        World world = this.level;
        if (!world.isClientSide) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity1;
                entity.hurt(DamageSource.ANVIL, 15.0F);
                livingEntity.heal( (float) livingEntity.getMaxHealth() * 0.1F);
            }
        }
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 5.0F, Explosion.Mode.BREAK);
        this.remove();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return PRegister.NEXORELS_STAFF.get();
    }
}
