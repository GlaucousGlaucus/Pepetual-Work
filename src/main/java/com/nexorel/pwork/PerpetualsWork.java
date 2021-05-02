package com.nexorel.pwork;

import com.nexorel.pwork.Setup.ClientSetup;
import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceScreen;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.nexorel.pwork.Reference.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pwork")
public class PerpetualsWork
{

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public PerpetualsWork() {

        /**For registration of stuff**/
        PRegister.initialization();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::clientSetup);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
       //oregen stup here
    }

    public static final ItemGroup PERPETUALS_WORK = new ItemGroup("perpetuals_work") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.BARREL);
        }

    };

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
