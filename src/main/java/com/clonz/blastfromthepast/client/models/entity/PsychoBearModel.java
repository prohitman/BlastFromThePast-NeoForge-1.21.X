package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.entity.PsychoBearEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PsychoBearModel extends GeoModel<PsychoBearEntity> {
    public static final ResourceLocation MODEL = BlastFromThePast.location("geo/entity/psycho_bear.geo.json");
    public static final ResourceLocation BABY_MODEL = BlastFromThePast.location("geo/entity/baby_psycho_bear.geo.json");
    public static final ResourceLocation ANIMATION = BlastFromThePast.location("animations/entity/psycho_bear.animation.json");
    public static final ResourceLocation BABY_ANIMATION = BlastFromThePast.location("animations/entity/baby_psycho_bear.animation.json");
    
    private static final ResourceLocation BERRY_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry");
    private static final ResourceLocation BERRY_SLEEP_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry_sleep");
    private static final ResourceLocation BERRY_PACIFIED_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry_pacified");
    private static final ResourceLocation BERRY_PACIFIED_SLEEP_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry_pacified_sleep");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.PSYCHO_BEAR.getId(), "baby_");
    
    public PsychoBearModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(PsychoBearEntity animatable) {
        if (animatable.isBaby()) return BABY_MODEL;
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(PsychoBearEntity animatable) {
        if(animatable.isBaby()){
            return BABY_TEXTURE;
        } else{
            if(animatable.isPacified()){
                if(animatable.isSleeping()){
                    return BERRY_PACIFIED_SLEEP_TEXTURE;
                } else{
                    return BERRY_PACIFIED_TEXTURE;
                }
            } else{
                if(animatable.isSleeping()){
                    return BERRY_SLEEP_TEXTURE;
                } else{
                    return BERRY_TEXTURE;
                }
            }
        }
    }

    @Override
    public ResourceLocation getAnimationResource(PsychoBearEntity animatable) {
        if (animatable.isBaby()) return BABY_ANIMATION;
        return ANIMATION;
    }
}