package com.nexorel.pwork.content.blocks.WitherFurnace;

import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.Recipes.WitheringRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.Optional;

public class WitherFurnaceTile extends AbstractWitherFurnaceTile<WitheringRecipe> {

    public WitherFurnaceTile() {
        super(PRegister.WITHER_FURNACE_TILE.get(), PRegister.WITHERING_TYPE);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("pwork.container.wither_furnace");
    }

    @Override
    protected Container createMenu(int windowid, PlayerInventory playerInventory) {
        return new WitherFurnaceContainer(windowid, this.level, this.worldPosition, playerInventory, playerInventory.player);
    }

    /**
    @Nullable
    @Override
    protected WitheringRecipe getRecipe() {
        if (this.level == null) {return null;}
        Optional<WitheringRecipe> optional = this.level.getRecipeManager().getRecipeFor(PRegister.WITHERING_TYPE, this, this.level);
        return optional.orElse(null);
    }*/
}
