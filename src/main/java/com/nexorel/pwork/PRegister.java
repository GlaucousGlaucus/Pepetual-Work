package com.nexorel.pwork;

import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceContainer;
import com.nexorel.pwork.content.Entities.NexorelsHeado;
import com.nexorel.pwork.content.Recipes.WitheringRecipe;
import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceTile;
import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceBlock;
import com.nexorel.pwork.content.items.NexorelsStaff;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.nexorel.pwork.PerpetualsWork.PERPETUALS_WORK;
import static com.nexorel.pwork.Reference.MOD_ID;

public class PRegister {

    private static Item.Properties properties = new Item.Properties().tab(PERPETUALS_WORK);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static void initialization() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    //Items
    public static final RegistryObject<Item> NEXORELS_STAFF = ITEMS.register("nexorels_staff", () -> new NexorelsStaff(properties.stacksTo(1)));

    //Blocks
    public static final RegistryObject<Block> WITHER_FURNACE = BLOCKS.register("wither_furnace", WitherFurnaceBlock::new);

    //Block Items
    public static final RegistryObject<Item> WITHER_FURNACE_ITEM = ITEMS.register("wither_furnace", () -> new BlockItem(WITHER_FURNACE.get(), properties));

    //Tile Entites
    public static final RegistryObject<TileEntityType<WitherFurnaceTile>> WITHER_FURNACE_TILE = TILE_ENTITIES.register("wither_furnace", () -> TileEntityType.Builder.of(WitherFurnaceTile::new, WITHER_FURNACE.get()).build(null));

    //Tile Entity

    public static final RegistryObject<ContainerType<WitherFurnaceContainer>> WITHER_FURNACE_CONTAINER = CONTAINERS.register("wither_furnace", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new WitherFurnaceContainer(windowId, world, pos, inv, inv.player);
    }));


    //Entities

    public static final RegistryObject<EntityType<NexorelsHeado>> NEXORELS_HEADO = ENTITIES.register("nexorels_heado", () -> EntityType.Builder.of(NexorelsHeado::new, EntityClassification.MISC).clientTrackingRange(4).updateInterval(10).sized(0.3125F, 0.3125F).build("nexorels_heado"));

    //Recipes
    public static final RegistryObject<IRecipeSerializer<?>> WITHERING = RECIPE_SERIALIZER.register("withering", WitheringRecipe.Serializer::new);

    public static final IRecipeType<WitheringRecipe> WITHERING_TYPE = IRecipeType.register(MOD_ID + "withering");

}
