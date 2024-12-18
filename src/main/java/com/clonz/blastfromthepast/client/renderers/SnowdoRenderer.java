package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.ClientResourceHelper;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.client.models.SnowdoModel;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import com.clonz.blastfromthepast.init.ModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnowdoRenderer extends MobRenderer<SnowdoEntity, SnowdoModel> {

    private static final ResourceLocation LOCATION = ClientResourceHelper.entityTexLocWithTypeSubFolder(ModEntities.SNOWDO.getId());
    private static final ResourceLocation BABY_TEXTURE = ClientResourceHelper.entityTexLocWithTypeSubFolderWithPrefix(ModEntities.SNOWDO.getId(), "baby_");
    private final SnowdoModel adult;
    private final SnowdoModel baby;
    public SnowdoRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowdoModel((Ducling)context.bakeLayer(SnowdoModel.LAYER_LOCATION)), 0.42f);
        this.adult = this.getModel();
        this.baby = new SnowdoModel((Ducling) context.bakeLayer(SnowdoModel.BABY_LAYER_LOCATION));
    }

    @Override
    public void render(SnowdoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()){
            this.model = baby;
        } else {
            this.model = adult;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SnowdoEntity entity) {
        if(entity.isBaby()){
            return BABY_TEXTURE;
        }
        return LOCATION;
    }
}
