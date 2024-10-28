package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacerosRenderer extends MobRenderer<GlacerosEntity, GlacerosModel> {

    private static final ResourceLocation NORMAL = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.GLACEROS.getId());
    private static final ResourceLocation BROAD = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_broad");
    private static final ResourceLocation CURLY = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_curly");
    private static final ResourceLocation SPIKEY = ClientResourceHelper.entityTecLocWithTypeSubFolderWithSuffix(ModEntities.GLACEROS.getId(),"_spikey");
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.GLACEROS.getId(), "baby_");

    private final GlacerosModel adult;
    private final GlacerosModel baby;

    public GlacerosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlacerosModel((Ducling) pContext.bakeLayer(GlacerosModel.LAYER_LOCATION)), 0.8f);
        this.adult = this.getModel();
        this.baby = new GlacerosModel((Ducling) pContext.bakeLayer(GlacerosModel.BABY_LAYER_LOCATION));
    }

    @Override
    public void render(GlacerosEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            this.model = baby;
        } else {
            this.model = adult;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GlacerosEntity entity) {
        if(entity.isBaby()){
            return BABY_TEXTURE;
        }
        return switch (entity.getVariant()) {
            case STRAIGHT -> NORMAL;
            case BROAD -> BROAD;
            case CURLY -> CURLY;
            case SPIKEY -> SPIKEY;
        };
    }
}
