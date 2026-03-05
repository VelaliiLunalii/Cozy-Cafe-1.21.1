package io.github.velaliilunalii.cozy_cafe.effect;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class ShareFoodEffect extends InstantenousMobEffect {
    public ShareFoodEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        return true;
    }
}
