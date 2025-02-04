package com.clonz.blastfromthepast.client.renderers;

import com.clonz.blastfromthepast.client.models.BurrelModel;
import com.clonz.blastfromthepast.entity.Burrel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BurrelRenderer extends MobRenderer<Burrel, BurrelModel> {
    private BurrelRenderer BABY_RENDERER;
    private boolean isBabyRenderer = false;

    final ResourceLocation NORMAL_BABY = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/baby_burrel_texture.png");
    final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap.png");
    final ResourceLocation NORMAL_SAP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap.png");

    final ResourceLocation SCRAT = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap_scrat.png");
    final ResourceLocation SCRAT_BABY = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/baby_burrel_scrat.png");
    final ResourceLocation NOT_SAP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_not_sap.png");
    final ResourceLocation NO_SAP_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap_sleep.png");
    final ResourceLocation SCRAT_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_no_sap_scrat_sleep.png");
    final ResourceLocation SAP_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_sleep.png");
    final ResourceLocation EXOTIC = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_exotic.png");
    final ResourceLocation EXOTIC_SLEEP = ResourceLocation.fromNamespaceAndPath("blastfromthepast", "textures/entity/burrel/brrrel_sap_exotic_sleep.png");

    public BurrelRenderer(EntityRendererProvider.Context context, BurrelModel model, float shadowRadius) {
        super(context, model, shadowRadius);
        isBabyRenderer = true;
    }

    public BurrelRenderer(EntityRendererProvider.Context context) {
        super(context, new BurrelModel((Ducling) context.bakeLayer(BurrelModel.LOCATION)), 0.1F);
        BABY_RENDERER = new BurrelRenderer(context, new BurrelModel((Ducling) context.bakeLayer(BurrelModel.BABY_LOCATION)), 0.1F);
    }

    public Direction rotate(Direction attachment) {
        return attachment.getAxis() == Direction.Axis.Y ? Direction.UP : attachment;
    }

    public void render(Burrel entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby() && !isBabyRenderer) BABY_RENDERER.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        else super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void setupRotations(Burrel entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        if (entity.isBesideClimbableBlock()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
            return;
        }
        float prevProg = (entity.prevAttachChangeProgress + (entity.attachChangeProgress - entity.prevAttachChangeProgress) * partialTick);
        float yawMul = 0F;
        float trans = entity.isBaby() ? 0.2F : 0.4F;
        if(entity.prevAttachDir == entity.getAttachmentFacing() && entity.getAttachmentFacing().getAxis() == Direction.Axis.Y){
            yawMul = 1.0F;
        }
        poseStack.mulPose(Axis.YP.rotationDegrees ( (180.0F - yawMul * yBodyRot)));

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

    @Override
    public ResourceLocation getTextureLocation(Burrel burrel) {
        Component name = burrel.getCustomName();
        if (burrel.isBaby()) {
            if (name != null && "scrat".equalsIgnoreCase(name.getString())) return SCRAT_BABY;
            return NORMAL_BABY;
        }
        if (burrel.isSleeping()) {
            if (name != null && "scrat".equalsIgnoreCase(burrel.getCustomName().getString())) {
                return SCRAT_SLEEP;
            }
            if (burrel.getTypes() == 0) {
                return NO_SAP_SLEEP;
            }
            else {
                return SAP_SLEEP;
            }
        }
        if (name != null && "scrat".equalsIgnoreCase(name.getString())) {
            return SCRAT;
        }
        else if (burrel.getTypes() == 1) {
            return NORMAL_SAP;
        } else {
           return NORMAL;
        }
    }
}
