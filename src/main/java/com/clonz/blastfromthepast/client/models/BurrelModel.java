package com.clonz.blastfromthepast.client.models;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.Burrel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BurrelModel extends AnimatableDucModel<Burrel> {

    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "burrel"), "burrel");
    public static final ModelLayerLocation BABY_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "baby_burrel"), "baby_burrel");

    public BurrelModel(Ducling ducling) {
        super(ducling, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.burrel.walk");
    }

    @Override
    public void setupAnim(@NotNull Burrel pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.animateWalk(pEntity.getAnimation().getAnimations().get("animation.burrel.walk").animation(), pLimbSwing, pLimbSwingAmount, 2, 1);
        this.animate(pEntity.idleState, pEntity.getAnimation().getAnimations().get("animation.burrel.idle").animation(), pAgeInTicks);
        this.animate(pEntity.lookState, pEntity.getAnimation().getAnimations().get("animation.burrel.look").animation(), pAgeInTicks);
        this.animate(pEntity.climbingState, pEntity.getAnimation().getAnimations().get("animation.burrel.climb").animation(), pAgeInTicks);
        this.animate(pEntity.danceState, pEntity.getAnimation().getAnimations().get("animation.burrel.dance").animation(), pAgeInTicks);
        this.animate(pEntity.eatState, pEntity.getAnimation().getAnimations().get("animation.burrel.eat").animation(), pAgeInTicks);
        this.animate(pEntity.sleepState, pEntity.getAnimation().getAnimations().get("animation.burrel.sleep").animation(), pAgeInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(0.3F, 0.3F, 0.3F);
            poseStack.translate(0F, 1.2F, 0F);
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            poseStack.scale(1, 1, 1);
            poseStack.popPose();
        }
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
