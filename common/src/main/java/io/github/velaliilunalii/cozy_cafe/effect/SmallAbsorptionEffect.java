package io.github.velaliilunalii.cozy_cafe.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SmallAbsorptionEffect extends MobEffect {
    public SmallAbsorptionEffect() {
        super(MobEffectCategory.BENEFICIAL, 2445989);
    }

    public boolean applyEffectTick(LivingEntity p_294484_, int p_294672_) {
        return p_294484_.getAbsorptionAmount() > 0.0F || p_294484_.level().isClientSide;
    }

    public boolean shouldApplyEffectTickThisTick(int p_295357_, int p_294523_) {
        return true;
    }

    public void onEffectStarted(LivingEntity p_294820_, int p_295222_) {
        super.onEffectStarted(p_294820_, p_295222_);
        p_294820_.setAbsorptionAmount(Math.max(p_294820_.getAbsorptionAmount(), (float)(1 + p_295222_)));
    }
}
