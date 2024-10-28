package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.client.models.FrostomperModel;
import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FrostomperRenderer extends MobRenderer<FrostomperEntity, FrostomperModel> {

    private static final ResourceLocation NORMAL_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.FROSTOMPER.getId());
    private static final ResourceLocation SADDLED_TEXTURE = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.FROSTOMPER.getId(), "_saddled");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.FROSTOMPER.getId(), "baby_");

    private final FrostomperModel adult;
    private final FrostomperModel baby;

    public FrostomperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FrostomperModel((Ducling) pContext.bakeLayer(FrostomperModel.ADULT_LAYER_LOCATION)), 0.8F);
        this.adult = this.getModel();
        this.baby = new FrostomperModel((Ducling) pContext.bakeLayer(FrostomperModel.BABY_LAYER_LOCATION));
    }

    @Override
    public void render(FrostomperEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            this.model = this.baby;
        } else {
            this.model = this.adult;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostomperEntity entity) {
        if(entity.isBaby()){
            return BABY_TEXTURE;
        } else{
            if(entity.isSaddled()){
                return SADDLED_TEXTURE;
            }
            return NORMAL_TEXTURE;
        }
    }
}