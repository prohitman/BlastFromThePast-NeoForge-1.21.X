package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacerosRenderer extends MobRenderer<GlacerosEntity, GlacerosModel> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros.png");

    public GlacerosRenderer(EntityRendererProvider.Context context, GlacerosModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public GlacerosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlacerosModel((Ducling) pContext.bakeLayer(GlacerosModel.LAYER_LOCATION)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GlacerosEntity entity) {
        return LOCATION;
    }
}
