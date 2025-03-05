package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

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

    public float getMotionAnimThreshold(SpeartoothEntity animatable) {
        return 1.0E-6F;
    }
}
