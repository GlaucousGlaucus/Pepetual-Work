package com.nexorel.pwork.content.blocks.WitherFurnace;


import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.Recipes.WitheringRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.Optional;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public abstract class AbstractWitherFurnaceTile<R extends IRecipe<?>> extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private final IRecipeType<? extends WitheringRecipe> recipeType;

    public AbstractWitherFurnaceTile(TileEntityType<?> type, IRecipeType<? extends WitheringRecipe> recipeType) {
        super(type);
        this.recipeType = recipeType;
    }

    public void encodeExtraData(PacketBuffer buffer) {
        buffer.writeBlockPos(this.worldPosition);
    }

    @Override
    public void tick() {
        World world = this.level;
        if (!world.isClientSide) {
            ItemStack primaryInput = this.items.get(0);
            ItemStack secondaryInput = this.items.get(1);
            ItemStack output = this.items.get(2);
            Optional<WitheringRecipe> optional = this.level.getRecipeManager().getRecipeFor(PRegister.WITHERING_TYPE, this, this.level);
            if (optional.isPresent()) {
                IRecipe<?> recipe = optional.get();
                ItemStack result = recipe.getResultItem();

                if (!primaryInput.isEmpty() && !secondaryInput.isEmpty() && recipe != null) {
                    if (output.isEmpty()) {
                        this.items.set(2, result.copy());
                    } else if (output.getItem() == result.getItem()) {
                        output.grow(result.getCount());
                    }
                    primaryInput.shrink(1);
                    secondaryInput.shrink(1);
                }
            }

        }

    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_DOWN;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_180462_1_, ItemStack p_180462_2_, @Nullable Direction p_180462_3_) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int p_180461_1_, ItemStack p_180461_2_, Direction p_180461_3_) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return ItemStackHelper.removeItem(this.items, i,i1);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return ItemStackHelper.takeItem(this.items, p_70304_1_);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.items.set(i, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr(
                    (double)this.worldPosition.getX() + 0.5D,
                    (double)this.worldPosition.getY() + 0.5D,
                    (double)this.worldPosition.getZ() + 0.5D)
                    <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 0) {
            return true;
        }
        if (slot == 1) {
            return true;
        }
        if (slot == 2) {
            return false;
        }
        return false;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ITEM_HANDLER_CAPABILITY) {

        }
        return super.getCapability(cap, side);
    }
}