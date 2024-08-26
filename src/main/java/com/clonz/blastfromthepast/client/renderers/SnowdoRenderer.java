package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.models.SnowdoModel;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnowdoRenderer extends MobRenderer<SnowdoEntity, SnowdoModel> {

    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath("blastfromthepast","textures/entity/snowdo.png");

    public SnowdoRenderer(EntityRendererProvider.Context context, SnowdoModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public SnowdoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SnowdoModel((Ducling) pContext.bakeLayer(SnowdoModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(SnowdoEntity entity) {
        return LOCATION;
    }
}
