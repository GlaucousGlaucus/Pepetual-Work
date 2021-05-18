package com.nexorel.pwork.Setup;

import com.nexorel.pwork.PRegister;
import com.nexorel.pwork.content.Entities.Projectiles.HeadO.NecronPTRenderer;
import com.nexorel.pwork.content.Entities.boss.Necron.NecronRenderer;
import com.nexorel.pwork.content.blocks.WitherFurnace.WitherFurnaceScreen;
import com.nexorel.pwork.content.blocks.WitheringTable.WitheringTableScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.nexorel.pwork.Reference.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void clientSetup(final FMLClientSetupEvent event)
    {
        ScreenManager.register(PRegister.WITHER_FURNACE_CONTAINER.get(), WitherFurnaceScreen::new);
        ScreenManager.register(PRegister.WITHER_CRAFTING_TABLE_CONTAINER.get(), WitheringTableScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(PRegister.NECRON.get(), NecronRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(PRegister.NEXORELS_HEADO.get(), NecronPTRenderer::new);
    }

}
