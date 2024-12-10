package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.HollowModel;
import com.clonz.blastfromthepast.entity.HollowEntity;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class HollowRenderer extends LivingEntityRenderer<HollowEntity, HollowModel> {
    private static final ResourceLocation LOCATION = BlastFromThePast.location("textures/entity/hollow.png");

    public HollowRenderer(EntityRendererProvider.Context context, HollowModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public HollowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HollowModel((Ducling) pContext.bakeLayer(HollowModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HollowEntity entity) {
        return LOCATION;
    }

    @Override
    protected boolean shouldShowName(HollowEntity entity) {
        return false;
    }
}
