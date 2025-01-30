package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.HollowEntity;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class HollowModel extends AnimatableDucModel<HollowEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "hollow"), "main");

    public HollowModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    public void setupAnim(@NotNull HollowEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return super.excludeAnimations();
    }
}