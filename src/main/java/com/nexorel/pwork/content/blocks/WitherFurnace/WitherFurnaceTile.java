package com.nexorel.pwork.content.blocks.WitherFurnace;


import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.Recipes.WitheringRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class WitherFurnaceTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private RecipeWrapper wrapper = new RecipeWrapper(itemHandler);

    Inventory inventory = new Inventory(wrapper.getItem(0), wrapper.getItem(1), wrapper.getItem(2));

    public int currentProgress;
    public int MaxProgressTime = 100;

    private final IRecipeType<? extends WitheringRecipe> recipeType;

    public WitherFurnaceTile() {
        super(PRegister.WITHER_FURNACE_TILE.get());
        this.recipeType = PRegister.WITHERING_TYPE;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide) {

            ItemStack output = this.itemHandler.getStackInSlot(2);

            if (this.getRecipe(this.itemHandler.getStackInSlot(0)) != null) {
                ItemStack Result = this.getRecipe(this.itemHandler.getStackInSlot(0)).getResultItem();
                if (output.getStack().getCount() < 64) {
                    if (output.sameItem(Result) || output.isEmpty()) {
                        if (this.currentProgress != this.MaxProgressTime) {
                            this.currentProgress++;
                            this.setChanged();
                        } else {
                            this.currentProgress = 0;
                            itemHandler.insertItem(2, Result.copy(), false);
                            itemHandler.extractItem(0, 1, false);
                            setChanged();
                        }
                    }
                }
            }
        }
        this.setChanged();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nullable
    private WitheringRecipe getRecipe(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        Set<IRecipe<?>> recipes = findRecipesByType(PRegister.WITHERING_TYPE, this.level);
        for (IRecipe<?> iRecipe : recipes) {
            WitheringRecipe recipe = (WitheringRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(itemHandler), this.level)) {
                return recipe;
            }
        }

        return null;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        currentProgress = tag.getInt("currentProgress");
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("currentProgress", currentProgress);
        return super.save(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }
}