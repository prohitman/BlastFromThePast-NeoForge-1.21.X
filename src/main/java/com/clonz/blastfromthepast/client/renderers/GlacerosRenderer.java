package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacerosRenderer extends MobRenderer<GlacerosEntity, GlacerosModel> {

    private static final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros/glaceros.png");
    private static final ResourceLocation BROAD = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros/glaceros_broad.png");
    private static final ResourceLocation CURLY = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros/glaceros_curly.png");
    private static final ResourceLocation CURVY = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros/glaceros_curvy.png");
    private static final ResourceLocation SPIKEY = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/glaceros/glaceros_spikey.png");

    public GlacerosRenderer(EntityRendererProvider.Context context, GlacerosModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public GlacerosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GlacerosModel((Ducling) pContext.bakeLayer(GlacerosModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(GlacerosEntity entity) {
        return switch (entity.getVariant()) {
            case NOMRAL -> NORMAL;
            case BROAD -> BROAD;
            case CURLY -> CURLY;
            case CURVY -> CURVY;
            case SPIKEY -> SPIKEY;
        };
    }
}
