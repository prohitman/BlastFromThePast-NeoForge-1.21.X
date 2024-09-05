package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothTiger;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SpeartoothTigerModel extends AnimatableDucModel<SpeartoothTiger> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "speartooth_tiger"), "main");

    public SpeartoothTigerModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.speartooth.lunge", "animation.speartooth.walk");
    }

    @Override
    public void setupAnim(@NotNull SpeartoothTiger pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (pEntity.shouldRun()) {
            this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.speartooth.run").animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
        } else if (pEntity.shouldStalk()) {
            this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.speartooth.stalk").animation(), pLimbSwing, pLimbSwingAmount, 2, 2);
        } else {
            this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.speartooth.walk").animation(), pLimbSwing, pLimbSwingAmount, 1, 2);
        }
        this.animate(pEntity.idleState, pEntity.getAnimation().getAnimations().get("animation.speartooth.idle").animation(), pAgeInTicks);
        this.animate(pEntity.danceState, pEntity.getAnimation().getAnimations().get("animation.speartooth.dance").animation(), pAgeInTicks);
        this.animate(pEntity.noiseState, pEntity.getAnimation().getAnimations().get("animation.speartooth.noise").animation(), pAgeInTicks);
        this.animate(pEntity.earState, pEntity.getAnimation().getAnimations().get("animation.speartooth.ear").animation(), pAgeInTicks);
        this.animate(pEntity.stretchState, pEntity.getAnimation().getAnimations().get("animation.speartooth.stretch").animation(), pAgeInTicks);
        this.animate(pEntity.attackState, pEntity.getAnimation().getAnimations().get("animation.speartooth.attack").animation(), pAgeInTicks);
        ((Ducling) getAnyDescendantWithName("mane").orElseThrow()).xRot = pHeadPitch * ((float) Math.PI / 180F);
        ((Ducling) getAnyDescendantWithName("mane").orElseThrow()).yRot = pNetHeadYaw * ((float) Math.PI / 180F);
    }
}
