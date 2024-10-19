package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.models.BurrelModel;
import com.clonz.blastfromthepast.entity.burrel.Burrel;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BurrelRenderer extends MobRenderer<Burrel, BurrelModel> {

    final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap.png");
    final ResourceLocation NORMAL_SAP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap.png");
    final ResourceLocation SCRAT = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap_scrat.png");
    final ResourceLocation NOT_SAP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_not_sap.png");
    final ResourceLocation NO_SAP_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap_sleep.png");
    final ResourceLocation SAP_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_sleep.png");
    final ResourceLocation EXOTIC = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_exotic.png");
    final ResourceLocation EXOTIC_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_exotic_sleep.png");

    public BurrelRenderer(EntityRendererProvider.Context context, BurrelModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public BurrelRenderer(EntityRendererProvider.Context context) {
        super(context, new BurrelModel((Ducling) context.bakeLayer(BurrelModel.LOCATION)), 0.1F);
    }

    @Override
    public void render(Burrel entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Burrel burrel) {
        return NORMAL;
    }
}
