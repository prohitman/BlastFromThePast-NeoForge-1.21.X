package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

import java.util.Set;

public class FrostomperModel extends AnimatableDucModel<FrostomperEntity> {
    public static final ModelLayerLocation ADULT_LAYER_LOCATION = new ModelLayerLocation(ModEntities.FROSTOMPER.getId(), "main");
    public static final ModelLayerLocation BABY_LAYER_LOCATION = new ModelLayerLocation(ModEntities.FROSTOMPER.getId().withPrefix("baby_"), "main");

    public FrostomperModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.frostomper.walk");
    }

    @Override
    public void setupAnim(FrostomperEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        FrostomperEntity.FrostomperAttackType attackType = pEntity.getActiveAttackType();
        if(pEntity.canAnimateWalk()){
            this.animateWalk(pEntity.getAnimation().getAnimations().get(pEntity.getAnimationKey("walk")).animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
        }
        if(pEntity.canAnimateLook()){
            this.animateLook(pNetHeadYaw, pHeadPitch);
        }
    }

    private void animateLook(float pNetHeadYaw, float pHeadPitch) {
        Ducling head = (Ducling) this.getAnyDescendantWithName("Head").or(() -> this.getAnyDescendantWithName("head")).orElseThrow();
        head.xRot = pHeadPitch * Mth.DEG_TO_RAD;
        head.yRot = pNetHeadYaw * Mth.DEG_TO_RAD;
    }
}