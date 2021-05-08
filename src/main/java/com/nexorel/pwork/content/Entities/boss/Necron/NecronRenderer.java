package com.nexorel.pwork.content.Entities.boss.Necron;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class NecronRenderer extends MobRenderer<NecronEntity, NecronModel> {

    private static final ResourceLocation LOC = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");

    public NecronRenderer(EntityRendererManager manager) {
        super(manager, new NecronModel(), 0.5f);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(NecronEntity p_110775_1_) {
        return LOC;
    }
}
