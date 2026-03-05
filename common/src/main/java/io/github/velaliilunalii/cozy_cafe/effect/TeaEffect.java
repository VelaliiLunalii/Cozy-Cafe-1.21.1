package io.github.velaliilunalii.cozy_cafe.effect;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class TeaEffect extends InstantenousMobEffect {
    public TeaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(!entity.level().isClientSide) {
            List<MobEffectInstance> currentEffects = new ArrayList<>(entity.getActiveEffects());

            for (MobEffectInstance mobEffectInstance : currentEffects) {
                MobEffect mobEffect = mobEffectInstance.getEffect().value();

                if (mobEffect.isBeneficial() && !mobEffectInstance.is(CommonClass.TEA_EFFECT)) {
                    int duration = mobEffectInstance.getDuration() + (mobEffectInstance.getDuration() < 6000 ? 40 +
                            (entity.getRandom().nextFloat() < 0.2 * (1 + amplifier) ? 40 : 0) : 0);
                    int newAmplifier = mobEffectInstance.getAmplifier();
                    boolean isAmbient = mobEffectInstance.isAmbient();
                    boolean isVisible = mobEffectInstance.isVisible();

                    entity.removeEffect(mobEffectInstance.getEffect());
                    MobEffectInstance newMobEffectInstance = new MobEffectInstance(
                            mobEffectInstance.getEffect(),
                            duration,
                            newAmplifier,
                            isAmbient,
                            isVisible
                    );
                    entity.addEffect(newMobEffectInstance);
                }
            }
        }
        return true;
    }
}
