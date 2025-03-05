package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GlacerosModel extends GeoModel<GlacerosEntity> {
    public static final ResourceLocation MODEL = BlastFromThePast.location("geo/entity/glaceros.geo.json");
    public static final ResourceLocation BABY_MODEL = BlastFromThePast.location("geo/entity/baby_glaceros.geo.json");
    public static final ResourceLocation ANIMATION = BlastFromThePast.location("animations/entity/glaceros.animation.json");
    public static final ResourceLocation BABY_ANIMATION = BlastFromThePast.location("animations/entity/baby_glaceros.animation.json");

    private static final ResourceLocation NORMAL = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.GLACEROS.getId());
    private static final ResourceLocation BROAD = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_broad");
    private static final ResourceLocation CURLY = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_curly");
    private static final ResourceLocation SPIKEY = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_spikey");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.GLACEROS.getId(), "baby_");

    public GlacerosModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(GlacerosEntity animatable) {
        if (animatable.isBaby()) return BABY_MODEL;
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GlacerosEntity animatable) {
        if(animatable.isBaby()){
            return BABY_TEXTURE;
        }
        return switch (animatable.getVariant()) {
            case STRAIGHT -> NORMAL;
            case BROAD -> BROAD;
            case CURLY -> CURLY;
            case SPIKEY -> SPIKEY;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(GlacerosEntity animatable) {
        if (animatable.isBaby()) return BABY_ANIMATION;
        return ANIMATION;
    }

    public float getMotionAnimThreshold(GlacerosEntity animatable) {
        return 1.0E-6F;
    }
}