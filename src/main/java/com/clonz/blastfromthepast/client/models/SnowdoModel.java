package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SnowdoModel extends AnimatableDucModel<SnowdoEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "snowdo"), "main");
    public static final ModelLayerLocation BABY_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "baby_snowdo"), "main");

    public SnowdoModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of();
    }

    @Override
    public void setupAnim(@NotNull SnowdoEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

        this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.snowdo.walk").animation(), pLimbSwing, pLimbSwingAmount, 10, 4);

        if(!pEntity.isTripped()){
            if(!pEntity.isBaby()){
                ((Ducling) getAnyDescendantWithName("neck").orElseThrow()).xRot = pHeadPitch * ((float)Math.PI / 180F);
                ((Ducling) getAnyDescendantWithName("neck").orElseThrow()).yRot = pNetHeadYaw * ((float)Math.PI / 180F);
            } else {
                ((Ducling) getAnyDescendantWithName("Body").orElseThrow()).xRot = pHeadPitch * ((float)Math.PI / 180F);
                ((Ducling) getAnyDescendantWithName("Body").orElseThrow()).yRot = pNetHeadYaw * ((float)Math.PI / 180F);
            }
        }
    }
}
