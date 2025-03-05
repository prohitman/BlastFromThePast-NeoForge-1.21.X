package com.clonz.blastfromthepast.client.renderers.entity;

import com.clonz.blastfromthepast.client.models.entity.BurrelModel;
import com.clonz.blastfromthepast.entity.BurrelEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BurrelRenderer extends GeoEntityRenderer<BurrelEntity> {
    public BurrelRenderer(EntityRendererProvider.Context context) {
        super(context, new BurrelModel());
        this.shadowRadius = 0.1F;
    }

    @Override
    protected void applyRotations(BurrelEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        if (entity.isBesideClimbableBlock()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
            return;
        }
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        float prevProg = (entity.prevAttachChangeProgress + (entity.attachChangeProgress - entity.prevAttachChangeProgress) * partialTick);
        float yawMul = 0F;
        float trans = entity.isBaby() ? 0.2F : 0.4F;
        if(entity.prevAttachDir == entity.getAttachmentFacing() && entity.getAttachmentFacing().getAxis() == Direction.Axis.Y){
            yawMul = 1.0F;
        }
        poseStack.mulPose(Axis.YP.rotationDegrees ( (180.0F - yawMul * rotationYaw)));

        if(entity.getAttachmentFacing() == Direction.DOWN){
            poseStack.translate(0.0D, trans, 0.0D);
            if(entity.yo <= entity.getY()){
                poseStack.mulPose(Axis.XP.rotationDegrees(90 * prevProg));
            }else{
                poseStack.mulPose(Axis.XP.rotationDegrees(-90 * prevProg));
            }
            poseStack.translate(0.0D, -trans, 0.0D);
        }
    }
}
