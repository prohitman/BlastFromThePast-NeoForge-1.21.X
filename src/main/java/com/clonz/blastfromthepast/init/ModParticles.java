package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BlastFromThePast.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROSTSTOMPER_GLINT = PARTICLES.register("froststomper_glint",
            () -> new SimpleParticleType(false));
}
