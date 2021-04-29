package com.nexorel.pwork;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pwork")
public class PerpetualsWork
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public PerpetualsWork() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        /**For registration of stuff**/
        PRegister.initialization();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
       //oregen stup here
    }


    private void clientSetup(final FMLClientSetupEvent event)
    {
       // RenderingRegistry.registerEntityRenderingHandler(PRegister.NEXORELS_HEADO.get(), NexorelsHeadoRenderer::new);
    }

    public static final ItemGroup PERPETUALS_WORK = new ItemGroup("perpetuals_work") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.BARREL);
        }

    };

}
