package com.clonz.blastfromthepast.entity.ai.attacker;

import com.clonz.blastfromthepast.entity.AnimatedAttacker;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class AttackerBodyRotationControl<T extends Mob & AnimatedAttacker<T, A>, A extends AnimatedAttacker.AttackType<T, A>> extends BodyRotationControl {
    private final T attacker;

    public AttackerBodyRotationControl(T attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    @Override
    public void clientTick() {
        A activeAttackType = this.attacker.getActiveAttackType();
        if(activeAttackType == null || !activeAttackType.blocksBodyRotation()){
            super.clientTick();
        }
    }
}