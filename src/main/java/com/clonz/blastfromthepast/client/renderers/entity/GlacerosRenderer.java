package com.clonz.blastfromthepast.client.renderers.entity;

import com.clonz.blastfromthepast.client.models.entity.GlacerosModel;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GlacerosRenderer extends GeoEntityRenderer<GlacerosEntity> {
    public GlacerosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlacerosModel());
        this.shadowRadius = 0.8F;
    }

    @Override
    protected void applyRotations(GlacerosEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        if(!animatable.isBaby()){
            model.getBone("Antlers1").orElseThrow().setHidden(animatable.isSheared());
        }
        float f = Mth.rotLerp(partialTick, animatable.yBodyRotO, animatable.yBodyRot);
        float f1 = Mth.rotLerp(partialTick, animatable.yHeadRotO, animatable.yHeadRot);
        model.getBone("neck").orElseThrow().setRotX((animatable.getXRot() + (animatable.isSparring() ? 90 : 0)) * ((float)Math.PI / 180F));
        model.getBone("neck").orElseThrow().setRotY((f1 - f) * ((float)Math.PI / 180F));
    }
}
