/**RayTraceResult result = player.pick(20.0F,2,false);*/

package com.nexorel.pwork.content.items;

import com.nexorel.pwork.content.Entities.Projectiles.HeadO.NexorelsHeado;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class NexorelsStaff extends Item {

    private String Mode = "";
    private Entity e;

    public NexorelsStaff(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        RayTraceResult result = player.pick(20.0F/**Range*/,2/**Eye Height*/,false /**eh?*/);
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            if (!player.isCrouching()) {

                if (result.getType() == RayTraceResult.Type.BLOCK) {
                    BlockPos blockpos = ((BlockRayTraceResult)result).getBlockPos();
                    BlockState blockstate = world.getBlockState(blockpos);

                    player.moveTo(result.getLocation().x, blockpos.getY() + 2.5F, result.getLocation().z);

                    AxisAlignedBB b = new AxisAlignedBB(player.getX() - 5, player.getY() - 5, player.getZ() - 5, player.getX() + 5, player.getY() + 5, player.getZ() + 5);
                    List<Entity> entities = world.getEntities(e, b);

                    for (Entity entity : entities) {
                        if (entity instanceof ItemEntity) {
                            continue;

                        } else if (entity instanceof PlayerEntity) {
                            continue;
                        } else {
                            entity.hurt(DamageSource.MAGIC, 100.0F);
                            //world.addFreshEntity(new NexorelsHeado(world, player, entity, player.getDirection().getAxis()));
                        }
                    }

                    world.addParticle(ParticleTypes.EXPLOSION, player.getX(), player.getY(), player.getZ(), 10.0D, 10.0D, 10.0D);

                } else {
                    int distance = 5;
                    double x = -distance*Math.sin(Math.toRadians(player.yRot))*Math.cos(Math.toRadians(player.xRot));
                    double y = -distance*Math.sin(Math.toRadians(player.xRot));
                    double z = distance*Math.cos(Math.toRadians(player.yRot))*Math.cos(Math.toRadians(player.xRot));

                    player.moveTo(player.getX() + x,
                            player.getY() + y,
                            player.getZ() + z);

                    AxisAlignedBB b = new AxisAlignedBB(player.getX() - 5, player.getY() - 5, player.getZ() - 5, player.getX() + 5, player.getY() + 5, player.getZ() + 5);
                    List<Entity> entities = world.getEntities(e, b);

                    for (Entity entity : entities) {
                        if (entity instanceof ItemEntity) {
                            continue;
                        } else if (entity instanceof PlayerEntity) {
                            continue;
                        } else {
                            entity.hurt(DamageSource.MAGIC, 100.0F);
                           // world.addFreshEntity(new NexorelsHeado(world, player, entity, player.getDirection().getAxis()));
                        }
                    }

                    world.addParticle(ParticleTypes.EXPLOSION, player.getX(), player.getY(), player.getZ(), 0.0D, 0.0D, 0.0D);
                }


            } else {
                player.addEffect(new EffectInstance(Effects.ABSORPTION, 5, 2));
            }

        }


        if (!player.abilities.instabuild) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }
        return ActionResult.success(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 10;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent(TextFormatting.GOLD + Mode));
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent(TextFormatting.GOLD + "The staff that its creator made cus he wasn't able to afford a hyperion on hypixel skyblock :("));
        }
    }
}

/**
 * X = cos(yaw)sin(pitch)
 *
 *      Y = cos(pitch)
 *
 *      Z = sin(yaw)sin(pitch)
 *
 *      xrot = pitch
 *      yrot = yaw
 */