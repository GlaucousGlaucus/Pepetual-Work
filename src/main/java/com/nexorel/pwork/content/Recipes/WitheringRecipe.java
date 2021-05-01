package com.nexorel.pwork.content.Recipes;

import com.google.gson.JsonObject;
import com.nexorel.pwork.PRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class WitheringRecipe implements IRecipe<IInventory> {

    protected final Ingredient ingredient;
    protected final ItemStack result;
    private final IRecipeType<?> type;
    private final IRecipeSerializer<?> serializer;
    protected final ResourceLocation id;

    public WitheringRecipe( ResourceLocation Recipeid, Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
        this.type = PRegister.WITHERING_TYPE;
        this.serializer = PRegister.WITHERING.get();
        this.id = Recipeid;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return PRegister.WITHERING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return PRegister.WITHERING_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<WitheringRecipe> {

        @Override
        public WitheringRecipe fromJson(ResourceLocation RecipeID, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            ResourceLocation itemID = new ResourceLocation(JSONUtils.getAsString(json, "result"));
            int count= JSONUtils.getAsInt(json, "count", 1);

            ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemID), count);

            return new WitheringRecipe(RecipeID, ingredient, result);
        }

        @Nullable
        @Override
        public WitheringRecipe fromNetwork(ResourceLocation RecipeID, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new WitheringRecipe(RecipeID, ingredient, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, WitheringRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
