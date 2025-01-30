package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.models.SpeartoothModel;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpeartoothRenderer extends MobRenderer<SpeartoothEntity, SpeartoothModel> {
    private final SpeartoothModel adult;
    private final SpeartoothModel baby;

    public SpeartoothRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpeartoothModel((Ducling) pContext.bakeLayer(SpeartoothModel.ADULT_LAYER_LOCATION)), 0.3f);
        this.adult = this.getModel();
        this.baby = new SpeartoothModel((Ducling) pContext.bakeLayer(SpeartoothModel.BABY_LAYER_LOCATION));
    }

    @Override
    public void render(SpeartoothEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            this.model = this.baby;
        } else {
            this.model = this.adult;
        }
//        if (entity.isBaby()) {
//            poseStack.scale(0.5F, 0.5F, 0.5F);
//        } else {
//            poseStack.scale(1F, 1F, 1F);
//        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SpeartoothEntity entity) {
        return entity.getTexture().textureId(entity.isBaby());
    }
}
