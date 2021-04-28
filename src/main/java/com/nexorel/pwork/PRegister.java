package com.nexorel.pwork;

import com.nexorel.pwork.content.items.NexorelsStaff;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

    public static void initialization() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    //Items
    public static final RegistryObject<Item> NEXORELS_STAFF = ITEMS.register("nexorels_staff", () -> new NexorelsStaff(properties.stacksTo(1)));

    //Blocks
    //public static final RegistryObject<Block> NEXORELS_SOMETHING = ITEMS.register("nexorels_staff", () -> new Item(properties));

}
