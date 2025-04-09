package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

import java.util.Optional;

public class SpeartoothModel extends GeoModel<SpeartoothEntity> {
    public static final ResourceLocation MODEL = BlastFromThePast.location("geo/entity/speartooth.geo.json");
    public static final ResourceLocation BABY_MODEL = BlastFromThePast.location("geo/entity/baby_speartooth.geo.json");
    public static final ResourceLocation ANIMATION = BlastFromThePast.location("animations/entity/speartooth.animation.json");
    public static final ResourceLocation BABY_ANIMATION = BlastFromThePast.location("animations/entity/baby_speartooth.animation.json");
    
    public SpeartoothModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(SpeartoothEntity animatable) {
        if (animatable.isBaby()) return BABY_MODEL;
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(SpeartoothEntity animatable) {
        return animatable.getTexture().textureId(animatable.isBaby());
    }

    @Override
    public ResourceLocation getAnimationResource(SpeartoothEntity animatable) {
        if (animatable.isBaby()) return BABY_ANIMATION;
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(SpeartoothEntity pEntity, long instanceId, AnimationState<SpeartoothEntity> animationState) {
        float partialTick = animationState.getPartialTick();
        if (!pEntity.isSleeping()) {
            Optional<GeoBone> mane = this.getBone("mane");
            float f = Mth.rotLerp(partialTick, pEntity.yBodyRotO, pEntity.yBodyRot);
            float f1 = Mth.rotLerp(partialTick, pEntity.yHeadRotO, pEntity.yHeadRot);
            if (mane.isPresent()) {
                mane.get().setRotX(pEntity.getXRot() * ((float)Math.PI / 180F));
                mane.get().setRotY((f1 - f) * ((float)Math.PI / 180F));
                mane.get().setRotZ(0);
            } else {
                Optional<GeoBone> head = this.getBone("head");
                if (head.isPresent()) {
                    head.get().setRotX(pEntity.getXRot() * ((float)Math.PI / 180F));
                    head.get().setRotY((f1 - f) * ((float)Math.PI / 180F));
                }
            }
        }
    }
}
