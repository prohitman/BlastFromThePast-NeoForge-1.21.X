package com.clonz.blastfromthepast.client.renderers.entity;

import com.clonz.blastfromthepast.client.models.entity.FrostomperModel;
import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostomperRenderer extends GeoEntityRenderer<FrostomperEntity> {
    public FrostomperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FrostomperModel());
        this.shadowRadius = 0.8F;
    }

    @Override
    protected void applyRotations(FrostomperEntity pEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(pEntity, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        if (pEntity.canAnimateLook()) {
            float f = Mth.rotLerp(partialTick, pEntity.yBodyRotO, pEntity.yBodyRot);
            float f1 = Mth.rotLerp(partialTick, pEntity.yHeadRotO, pEntity.yHeadRot);
            this.animateLook(f1 - f, pEntity.getXRot());
        }
    }

    private void animateLook(float pNetHeadYaw, float pHeadPitch) {
        GeoBone head = this.getGeoModel().getBone("head").orElseThrow();
        head.setRotX(pHeadPitch * Mth.DEG_TO_RAD);
        head.setRotY(pNetHeadYaw * Mth.DEG_TO_RAD);
    }
}