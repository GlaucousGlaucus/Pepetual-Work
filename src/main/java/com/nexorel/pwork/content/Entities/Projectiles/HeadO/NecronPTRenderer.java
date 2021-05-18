package com.nexorel.pwork.content.Entities.Projectiles.HeadO;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;


public class NecronPTRenderer extends EntityRenderer<NexorelsHeado> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");
    private final GenericHeadModel model = new GenericHeadModel();

    public NecronPTRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    protected int getBlockLightLevel(NexorelsHeado p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }

    @Override
    public void render(NexorelsHeado heado, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn) {
        stack.pushPose();
        stack.scale(-1.0F, -1.0F, 1.0F);
        float f = MathHelper.rotlerp(heado.yRotO, heado.yRot, partialTicks);
        float f1 = MathHelper.lerp(partialTicks, heado.xRotO, heado.xRot);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(heado)));
        this.model.setupAnim(0.0F, f, f1);
        this.model.renderToBuffer(stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
        super.render(heado, entityYaw, partialTicks, stack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(NexorelsHeado p_110775_1_) {
        return TEXTURE_LOCATION;
    }
}
