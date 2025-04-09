package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
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

    @Override
    public void setCustomAnimations(GlacerosEntity animatable, long instanceId, AnimationState<GlacerosEntity> animationState) {
        float partialTick = animationState.getPartialTick();
        if (animationState.isCurrentAnimation(GlacerosEntity.IDLE)) this.getBone("neck").orElseThrow().setRotZ(0);
        if(!animatable.isBaby()){
            this.getBone("Antlers1").orElseThrow().setHidden(animatable.isSheared());
        }
        float f = Mth.rotLerp(partialTick, animatable.yBodyRotO, animatable.yBodyRot);
        float f1 = Mth.rotLerp(partialTick, animatable.yHeadRotO, animatable.yHeadRot);
        if (!animatable.isSparring()) {
            this.getBone("neck").orElseThrow().setRotX(animatable.getXRot() * ((float) Math.PI / 180F));
        }
        this.getBone("neck").orElseThrow().setRotY((f1 - f) * ((float)Math.PI / 180F));
    }
}