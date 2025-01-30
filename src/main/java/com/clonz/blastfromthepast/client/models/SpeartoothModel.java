package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.entity.speartooth.SpeartoothEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SpeartoothModel extends AnimatableDucModel<SpeartoothEntity> {
    public static final ModelLayerLocation ADULT_LAYER_LOCATION = new ModelLayerLocation(ModEntities.SPEARTOOTH.getId(), "main");
    public static final ModelLayerLocation BABY_LAYER_LOCATION = new ModelLayerLocation(ModEntities.SPEARTOOTH.getId().withPrefix("baby_"), "main");

    public SpeartoothModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.speartooth.lunge", "animation.speartooth.walk");
    }

    @Override
    public void setupAnim(@NotNull SpeartoothEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (!pEntity.isBaby()) {
            if (pEntity.shouldRun()) {
                this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.speartooth.run").animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
            } else {
                this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.speartooth.walk").animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
            }
        } else {
            this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.baby_speartooth.walk").animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
        }

        if (!pEntity.isSleeping()) {
            Ducling mane = ((Ducling) getAnyDescendantWithName("mane").orElse(null));
            if (mane != null) {
                mane.xRot = pHeadPitch * ((float) Math.PI / 180F);
                mane.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
            } else {
                Ducling head = ((Ducling) getAnyDescendantWithName("head").orElse(null));
                if (head != null) {
                    head.xRot = pHeadPitch * ((float) Math.PI / 180F);
                    head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
                }
            }
        }
    }
}
