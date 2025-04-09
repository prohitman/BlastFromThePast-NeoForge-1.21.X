package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class FrostomperModel extends GeoModel<FrostomperEntity> {
    public static final ResourceLocation MODEL = BlastFromThePast.location("geo/entity/frostomper.geo.json");
    public static final ResourceLocation BABY_MODEL = BlastFromThePast.location("geo/entity/baby_frostomper.geo.json");
    public static final ResourceLocation ANIMATION = BlastFromThePast.location("animations/entity/frostomper.animation.json");
    public static final ResourceLocation BABY_ANIMATION = BlastFromThePast.location("animations/entity/baby_frostomper.animation.json");
    
    private static final ResourceLocation NORMAL_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.FROSTOMPER.getId());
    private static final ResourceLocation SADDLED_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.FROSTOMPER.getId(), "_saddled");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.FROSTOMPER.getId(), "baby_");

    public FrostomperModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(FrostomperEntity animatable) {
        if (animatable.isBaby()) return BABY_MODEL;
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(FrostomperEntity animatable) {
        if(animatable.isBaby()){
            return BABY_TEXTURE;
        }
        else {
            if(animatable.isSaddled()) {
                return SADDLED_TEXTURE;
            }
            return NORMAL_TEXTURE;
        }
    }

    @Override
    public ResourceLocation getAnimationResource(FrostomperEntity animatable) {
        if (animatable.isBaby()) return BABY_ANIMATION;
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(FrostomperEntity pEntity, long instanceId, AnimationState<FrostomperEntity> animationState) {
        float partialTick = animationState.getPartialTick();
        if (pEntity.canAnimateLook()) {
            float f = Mth.rotLerp(partialTick, pEntity.yBodyRotO, pEntity.yBodyRot);
            float f1 = Mth.rotLerp(partialTick, pEntity.yHeadRotO, pEntity.yHeadRot);
            this.animateLook(f1 - f, pEntity.getXRot());
        }
    }

    private void animateLook(float pNetHeadYaw, float pHeadPitch) {
        GeoBone head = this.getBone("head").orElseThrow();
        head.setRotX(pHeadPitch * Mth.DEG_TO_RAD);
        head.setRotY(pNetHeadYaw * Mth.DEG_TO_RAD);
    }
}