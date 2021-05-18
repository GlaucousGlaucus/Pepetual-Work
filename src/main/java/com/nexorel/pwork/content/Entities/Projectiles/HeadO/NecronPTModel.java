package com.nexorel.pwork.content.Entities.Projectiles.HeadO;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NecronPTModel extends EntityModel<NexorelsHeado> {

    public ModelRenderer head;

    public NecronPTModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.head = new ModelRenderer(this, 0, 35);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);
        this.head.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void setupAnim(NexorelsHeado heado, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.head.render(stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
