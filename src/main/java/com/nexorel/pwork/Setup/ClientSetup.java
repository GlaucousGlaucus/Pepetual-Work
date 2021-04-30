package com.nexorel.pwork.Setup;

import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.nexorel.pwork.Reference.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void clientSetup(final FMLClientSetupEvent event)
    {
        ScreenManager.register(PRegister.WITHER_FURNACE_CONTAINER.get(), WitherFurnaceScreen::new);
    }

}
