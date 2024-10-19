package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.burrel.Burrel;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BurrelModel extends AnimatableDucModel<Burrel> {

    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "burrel"), "burrel");

    public BurrelModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.burrel.walk");
    }

    @Override
    public void setupAnim(@NotNull Burrel pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.burrel.walk").animation(), pLimbSwing, pLimbSwingAmount, 2, 1);
        this.animate(pEntity.idleState, pEntity.getAnimation().getAnimations().get("animation.burrel.idle").animation(), pAgeInTicks);
    }
}
