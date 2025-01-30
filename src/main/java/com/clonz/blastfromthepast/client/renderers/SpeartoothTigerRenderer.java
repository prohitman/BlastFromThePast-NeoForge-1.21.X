/*
package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.models.SpeartoothTigerModel;
import com.clonz.blastfromthepast.entity.speartooth.SpeartoothTiger;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpeartoothTigerRenderer extends MobRenderer<SpeartoothTiger, SpeartoothTigerModel> {

    final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/spear_tooth_tiger.png");
    final ResourceLocation RETRO = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/spear_tooth_tiger_retro.png");

    public SpeartoothTigerRenderer(EntityRendererProvider.Context context, SpeartoothTigerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public SpeartoothTigerRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new SpeartoothTigerModel((Ducling) pContext.bakeLayer(SpeartoothTigerModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public void render(SpeartoothTiger entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SpeartoothTiger speartoothTiger) {
        if (speartoothTiger.getVariant() == SpeartoothTiger.Variant.RETRO) {
            return RETRO;
        } else {
            return NORMAL;
        }
    }
}*/
