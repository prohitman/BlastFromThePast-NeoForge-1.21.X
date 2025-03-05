package com.clonz.blastfromthepast.client.models.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.HollowEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class HollowModel extends DefaultedEntityGeoModel<HollowEntity> {
    public HollowModel() {
        super(BlastFromThePast.location("hollow"));
    }

    public float getMotionAnimThreshold(HollowModel animatable) {
        return 1.0E-6F;
    }
}