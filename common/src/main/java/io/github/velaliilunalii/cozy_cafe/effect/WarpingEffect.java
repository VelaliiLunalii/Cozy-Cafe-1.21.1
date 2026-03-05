package io.github.velaliilunalii.cozy_cafe.effect;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.sounds.SoundEvents.CHORUS_FRUIT_TELEPORT;
import static net.minecraft.sounds.SoundSource.PLAYERS;

public class WarpingEffect extends MobEffect {
    public WarpingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();

        if (!(level instanceof ServerLevel serverLevel)) {
            return true;
        }

        Vec3 velocity = livingEntity.getKnownMovement();

        if (velocity.x == 0 && velocity.y <= 0 && velocity.z == 0) {
            return true;
        }

        float distance = 2 * (1 + amplifier);
        Vec3 currentPos = livingEntity.position();
        Vec3 newPos = currentPos.add(velocity.normalize().multiply(distance, distance, distance));

        if (!level.getBlockState(BlockPos.containing(newPos)).canOcclude()) {

            for(int i = 0; i < 16; ++i) {
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        currentPos.x,
                        currentPos.y + level.random.nextDouble() * 2.0,
                        currentPos.z,
                        1,
                        level.random.nextGaussian() * 0.2,
                        level.random.nextDouble() * 0.5,
                        level.random.nextGaussian() * 0.2,
                        0.1
                );
            }

            livingEntity.teleportTo(newPos.x, newPos.y, newPos.z);
            livingEntity.resetFallDistance();

            for(int i = 0; i < 16; ++i) {
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        newPos.x,
                        newPos.y + level.random.nextDouble() * 2.0,
                        newPos.z,
                        1,
                        level.random.nextGaussian() * 0.2,
                        level.random.nextDouble() * 0.5,
                        level.random.nextGaussian() * 0.2,
                        0.1
                );
            }

            level.playSound(null, newPos.x, newPos.y, newPos.z, CHORUS_FRUIT_TELEPORT, PLAYERS, 1.0F, 1.0F);

            livingEntity.removeEffect(CommonClass.WARPING_EFFECT);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
