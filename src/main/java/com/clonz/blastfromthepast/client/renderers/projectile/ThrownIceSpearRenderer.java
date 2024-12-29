package com.clonz.blastfromthepast.client.renderers.projectile;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.client.models.item.IceSpearModel;
import com.clonz.blastfromthepast.entity.projectile.ThrownIceSpear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class ThrownIceSpearRenderer extends EntityRenderer<ThrownIceSpear> {
    public IceSpearModel model;
    public ThrownIceSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceSpearModel((Ducling) context.bakeLayer(IceSpearModel.LOCATION));
    }

    @Override
    public void render(ThrownIceSpear entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(this.getTextureLocation(entity)), false, entity.isFoil());
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownIceSpear thrownIceSpear) {
        return ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "textures/item/ice_spear.png");
    }
}
