package com.nexorel.pwork.content.items;

import com.nexorel.pwork.PRegister;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nullable;
import java.util.List;

public class NexorelsStaff extends Item {

    public NexorelsStaff(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    private Entity e;

    private void hyperion(World world, PlayerEntity player, Hand hand) {
        /** AxisAlignedBB b = new AxisAlignedBB(player.getX() - 5, player.getY() - 5 , player.getZ() - 5, player.getX() + 5, player.getY() + 5, player.getZ() + 5);
         List<Entity> entities = world.getEntities(e, b);
         for (int i = 0; i <= entities.size(); i++) {
         Entity entity = entities.get(i);
         if (entity instanceof ItemEntity) {
         continue;
         } else {
         entity.hurt(DamageSource.MAGIC, 100);
         }
         }*/

        ((ServerPlayerEntity) player).connection.teleport(player.getX() + 5, player.getY() + 5, player.getZ() + 0, 0, 0);

    }

    /**

     Yaw = 0 when the head is looking straight.

     X = cos(yaw)sin(pitch)

     Y = cos(pitch)

     Z = sin(yaw)sin(pitch)

     ( -cos(x) = -cos(-x) = cos(-x+π), even though none of those should be necessary for such formulas), but the only error should be a switch in X and Z.

     player.moveEntity(distance*sin(player.pitch)*sin(player.yaw),distance*cos(player.pitch),distance*sin(player.pitch)*sin(player.yaw));

     If it's moving at odd angles try switching x and z.

     int distance = 5;
     float f1 = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float)Math.PI);
     float f2 = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float)Math.PI);
     float f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
     float f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
     double i = this.posX;
     double j = this.posY;
     double k = this.posZ;
     this.moveEntity(distance*f2*f3,
     distance*f4,
     distance*f1*f3);

     try{
     // Trace for blocks
     MovingObjectPosition eyeTrace = player.rayTrace(distance, 1.0F);
     // If no blocks are traced, then eyeTrace is null.
     if(eyeTrace.hitVec != null){
     //If a block is found, call the moveEntity below
     player.moveEntity(eyeTrace.hitVec.xCoord-player.posX, eyeTrace.hitVec.yCoord-player.posY + 1.1, eyeTrace.hitVec.zCoord-player.posZ );
     }
     }
     catch(NullPointerException npe){
     // Lolz at your NPE, going to throw you whatever set distance forward in the hope to ignore your NPE
     player.moveEntity(-distance*Math.sin(Math.toRadians(player.rotationYawHead))*Math.cos(Math.toRadians(player.rotationPitch)),-distance*Math.sin(Math.toRadians(player.rotationPitch)), distance*Math.cos(Math.toRadians(player.rotationYawHead))*Math.cos(Math.toRadians(player.rotationPitch)));
     }

     **/


    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();
        AxisAlignedBB b = new AxisAlignedBB(player.getX() - 5, player.getY() - 5, player.getZ() - 5, player.getX() + 5, player.getY() + 5, player.getZ() + 5);
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            List<Entity> entities = world.getEntities(e, b);
            for (int i = 0; i <= entities.size(); i++) {
                Entity entity = entities.get(i);
                if (entity instanceof ItemEntity) {
                    continue;
                } else {
                    entity.hurt(DamageSource.MAGIC, 100);
                }
            }

            float m = MathHelper.cos(MathHelper.wrapDegrees(player.xRot)) * 5;
            float n = (90 - MathHelper.cos(MathHelper.wrapDegrees(player.xRot))) * 5;
            float A = (float) player.getX() + m;
            float B = (float) player.getZ() + n;

            ((ServerPlayerEntity) player).connection.teleport(A, player.getY() + 5, B, MathHelper.wrapDegrees(player.yRot), 0);

            player.addEffect(new EffectInstance(Effects.ABSORPTION, 30, 3, false, false));
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 2, 1, false, false));
            player.heal((float) (player.getMaxHealth() * 0.01));
            player.getCooldowns().addCooldown(this, 20);
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent(TextFormatting.GOLD + "The staff that its creator made cus he wasn't able to afford a hyperion on hypixel skyblock :("));
        }
    }
}
