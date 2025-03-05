package com.clonz.blastfromthepast.client.renderers.entity;

import com.clonz.blastfromthepast.client.models.entity.SpeartoothModel;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Optional;

public class SpeartoothRenderer extends GeoEntityRenderer<SpeartoothEntity> {
    public SpeartoothRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpeartoothModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    protected void applyRotations(SpeartoothEntity pEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(pEntity, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        if (!pEntity.isSleeping()) {
            Optional<GeoBone> mane = this.model.getBone("mane");
            float f = Mth.rotLerp(partialTick, pEntity.yBodyRotO, pEntity.yBodyRot);
            float f1 = Mth.rotLerp(partialTick, pEntity.yHeadRotO, pEntity.yHeadRot);
            if (mane.isPresent()) {
                mane.get().setRotX(animatable.getXRot() * ((float)Math.PI / 180F));
                mane.get().setRotY((f1 - f) * ((float)Math.PI / 180F));
            } else {
                Optional<GeoBone> head = this.model.getBone("head");
                if (head.isPresent()) {
                    head.get().setRotX(animatable.getXRot() * ((float)Math.PI / 180F));
                    head.get().setRotY((f1 - f) * ((float)Math.PI / 180F));
                }
            }
        }
    }
}
