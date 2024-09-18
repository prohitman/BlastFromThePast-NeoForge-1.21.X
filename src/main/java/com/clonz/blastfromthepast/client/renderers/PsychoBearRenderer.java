package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.client.models.PsychoBearModel;
import com.clonz.blastfromthepast.entity.PsychoBearEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PsychoBearRenderer extends MobRenderer<PsychoBearEntity, PsychoBearModel> {

    private static final ResourceLocation NORMAL_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.PSYCHO_BEAR.getId());
    private static final ResourceLocation BERRY_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry");
    private static final ResourceLocation SLEEP_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_sleep");
    private static final ResourceLocation BERRY_SLEEP_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.PSYCHO_BEAR.getId(), "_berry_sleep");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.PSYCHO_BEAR.getId(), "baby_");

    private final PsychoBearModel adult;
    private final PsychoBearModel baby;

    public PsychoBearRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PsychoBearModel((Ducling) pContext.bakeLayer(PsychoBearModel.ADULT_LAYER_LOCATION)), 0.8F);
        this.addLayer(new BearHoldsItemLayer<>(this, pContext.getItemInHandRenderer()));
        this.adult = this.getModel();
        this.baby = new PsychoBearModel((Ducling) pContext.bakeLayer(PsychoBearModel.BABY_LAYER_LOCATION));
    }

    @Override
    public void render(PsychoBearEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            this.model = this.baby;
        } else {
            this.model = this.adult;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PsychoBearEntity entity) {
        if(entity.isBaby()){
            return BABY_TEXTURE;
        } else{
            if(entity.isPacified()){
                if(entity.isSleeping()){
                    return BERRY_SLEEP_TEXTURE;
                } else{
                    return BERRY_TEXTURE;
                }
            } else{
                if(entity.isSleeping()){
                    return SLEEP_TEXTURE;
                } else{
                    return NORMAL_TEXTURE;
                }
            }
        }
    }
}