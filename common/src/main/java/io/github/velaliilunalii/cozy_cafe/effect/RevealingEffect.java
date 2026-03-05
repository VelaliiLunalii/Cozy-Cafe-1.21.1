package io.github.velaliilunalii.cozy_cafe.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RevealingEffect extends MobEffect {
    public RevealingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (!level.isClientSide) {
            int count = 0;
            for (Entity entity : level.getEntities(livingEntity, livingEntity.getBoundingBox().inflate(10 * (1 + amplifier)), entity -> entity instanceof LivingEntity)) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.GLOWING, 50 * (1 + amplifier), 0, false, false));

                count ++;
                if (count > 10) break;
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 5 == 0;
    }
}
