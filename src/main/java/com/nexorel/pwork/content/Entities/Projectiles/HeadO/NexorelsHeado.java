package com.nexorel.pwork.content.Entities.Projectiles.HeadO;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.Entities.boss.Necron.NecronEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class NexorelsHeado extends DamagingProjectileEntity {

    //TODO: Model.this

    private static final Set<Block> CONVERTIBLE = Sets.newHashSet(Blocks.GRASS);
    private World world = this.level;
    private Entity target;
    private Entity secondaryTarget;

    public NexorelsHeado(EntityType<? extends NexorelsHeado> type, World world) {
        super(type, world);
        this.noPhysics = true;
        this.onGround = false;
    }

    public NexorelsHeado(LivingEntity owner, World world, Entity target) {
        this(PRegister.NEXORELS_HEADO.get(), world);
        this.setOwner(owner);
        this.setRot(owner.xRot, owner.yRot);
        this.target = target;

        this.moveTo(owner.getX(), owner.getY(), owner.getZ(), this.yRot, this.xRot);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.target != null) {

            Vector3d vector3d = this.getDeltaMovement();
            double d = this.getX();
            double d1 = this.getY() + 1.0D;
            double d2 = this.getZ();
            double accX = this.target.getX() - d;
            double accY = (this.target.getY() + (double)this.target.getEyeHeight() * 0.75D) - d1;
            double accZ = this.target.getZ() - d2;

            double DIST = MathHelper.sqrt(accX * accX + accY * accY + accZ * accZ);

            double x = accX / DIST * 0.1;
            double y = accY / DIST * 0.1;
            double z = accZ / DIST * 0.1;


            AxisAlignedBB bb = new AxisAlignedBB(this.getX() - 5, this.getY() - 5, this.getZ() - 5, this.getX() + 5, this.getY() + 5, this.getZ() + 5);
            List<Entity> secondaryTargets = world.getEntities(secondaryTarget, bb);

            for (Entity secTarget : secondaryTargets) {
                if (secTarget instanceof ItemEntity) {

                } else if (secTarget instanceof PlayerEntity) {
                    ((PlayerEntity) secTarget).addEffect(new EffectInstance(Effects.CONFUSION, 5, 5));
                }  else if (secTarget instanceof NecronEntity || secTarget instanceof NexorelsHeado){

                } else {
                    secTarget.hurt(DamageSource.CRAMMING, 0.5F);
                }
            }
            this.setDeltaMovement(vector3d.add(x, y, z).scale(this.getInertia()));
        }
    }

    @Override
    protected float getInertia() {
        return 0.9F;
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        this.remove();
    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticleTypes.ASH;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        Entity shooter = this.getOwner();
        if (!world.isClientSide) {
            if (shooter instanceof LivingEntity) {
                LivingEntity shooterLiving = (LivingEntity)shooter;

                if (target instanceof LivingEntity) {
                    LivingEntity livingTarget = (LivingEntity)target;
                    if (livingTarget.hurt(DamageSource.WITHER, 4.0F)) {
                        if (livingTarget.isAlive()) {

                            if (world.getDifficulty() == Difficulty.EASY) {
                                livingTarget.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 1));
                            }
                            if (world.getDifficulty() == Difficulty.NORMAL) {
                                livingTarget.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 1));
                                livingTarget.addEffect(new EffectInstance(Effects.WEAKNESS, 20, 1));
                                livingTarget.addEffect(new EffectInstance(Effects.HARM, 1, 1));
                            }
                            if (world.getDifficulty() == Difficulty.HARD) {
                                livingTarget.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 2));
                                livingTarget.addEffect(new EffectInstance(Effects.WEAKNESS, 20, 2));
                                livingTarget.addEffect(new EffectInstance(Effects.CONFUSION, 20, 2));
                                livingTarget.addEffect(new EffectInstance(Effects.HARM, 1, 1));
                            }
                        } else {
                            shooterLiving.heal(15.0F);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        if (world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
            /** world.explode(entity, x,y,z, radius,Mode);
             *
             * Explosion Modes:
             *
             * NONE:
             *
             * BREAK:
             *
             * DESTROY:
             *
             * */
            world.explode(this, pos.getX(), pos.getY(), pos.getZ(), 3,Explosion.Mode.NONE);
        } else {
            BlockState state = Blocks.BLACK_GLAZED_TERRACOTTA.defaultBlockState();
            world.setBlockAndUpdate(pos, state);
            world.explode(this, pos.getX(), pos.getY(), pos.getZ(), 3,Explosion.Mode.NONE);
        }
        this.remove();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float strength) {
        return false;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return super.getOwner();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
