package io.github.velaliilunalii.cozy_cafe.effect;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class LatteEffect extends InstantenousMobEffect {
    public LatteEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(!entity.level().isClientSide) {
            List<MobEffectInstance> currentEffects = new ArrayList<>(entity.getActiveEffects());

            for (MobEffectInstance mobEffectInstance : currentEffects) {
                MobEffect mobEffect = mobEffectInstance.getEffect().value();

                if (mobEffect.isBeneficial() && !mobEffectInstance.is(CommonClass.LATTE_EFFECT)) {
                    int duration = mobEffectInstance.getDuration();
                    int newAmplifier = mobEffectInstance.getAmplifier() + (entity.getRandom().nextFloat() <
                            (0.2 * (1 + amplifier)) / (1 + ((double) mobEffectInstance.getAmplifier() / 3))
                            ? 1 : 0);
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

            currentEffects = new ArrayList<>(entity.getActiveEffects());
            for (MobEffectInstance mobEffectInstance : currentEffects) {
                MobEffect mobEffect = mobEffectInstance.getEffect().value();

                if (!mobEffect.isBeneficial() && !mobEffectInstance.is(CommonClass.LATTE_EFFECT)) {
                    entity.removeEffect(mobEffectInstance.getEffect());
                }
            }
        }
        return true;
    }
}
