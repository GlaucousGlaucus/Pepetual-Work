package com.nexorel.pwork.content.Entities.boss.Necron;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Necron - Nexorel
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class NecronModel extends EntityModel<NecronEntity> {
    public ModelRenderer head_bone;
    public ModelRenderer rib_cage;
    public ModelRenderer tail;
    public ModelRenderer main_head;
    public ModelRenderer right_head;
    public ModelRenderer left_head;

    public NecronModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.head_bone = new ModelRenderer(this, 0, 16);
        this.head_bone.setPos(0.0F, 0.0F, 0.0F);
        this.head_bone.addBox(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.main_head = new ModelRenderer(this, 0, 0);
        this.main_head.setPos(0.0F, 0.0F, 0.0F);
        this.main_head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.rib_cage = new ModelRenderer(this, 0, 22);
        this.rib_cage.setPos(-2.0F, 6.9F, -0.5F);
        this.rib_cage.addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rib_cage.texOffs(24, 0).addBox(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rib_cage.texOffs(24, 0).addBox(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rib_cage.texOffs(24, 0).addBox(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rib_cage, 0.3612831684786316F, 0.0F, 0.0F);
        this.left_head = new ModelRenderer(this, 32, 0);
        this.left_head.setPos(10.0F, 4.0F, 0.0F);
        this.left_head.addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.right_head = new ModelRenderer(this, 32, 0);
        this.right_head.setPos(-8.0F, 4.0F, 0.0F);
        this.right_head.addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 12, 22);
        this.tail.setPos(-2.0F, 16.25F, 3.03F);
        this.tail.addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 1.1466812652970528F, 0.0F, 0.0F);
    }

    @Override
    public void setupAnim(NecronEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.main_head.xRot = headPitch * ((float) Math.PI / 180F);
        this.main_head.yRot = netHeadYaw * ((float) Math.PI / 180F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head_bone, this.main_head, this.rib_cage, this.left_head, this.right_head, this.tail).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
