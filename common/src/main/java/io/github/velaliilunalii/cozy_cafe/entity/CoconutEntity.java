package io.github.velaliilunalii.cozy_cafe.entity;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CoconutEntity extends ThrowableItemProjectile {

    public CoconutEntity(EntityType<? extends CoconutEntity> type, Level level) {
        super(type, level);
    }

    public CoconutEntity(EntityType<? extends CoconutEntity> type, Level level, LivingEntity thrower) {
        super(type, thrower, level);
    }

    @Override
    protected Item getDefaultItem() {
        return CommonClass.COCONUT_ITEM;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            ItemEntity drop1 = new ItemEntity(level(), this.getX(), this.getY(), this.getZ(), new ItemStack(CommonClass.SPLIT_COCONUT));
            ItemEntity drop2 = new ItemEntity(level(), this.getX(), this.getY(), this.getZ(), new ItemStack(CommonClass.SPLIT_COCONUT));
            level().addFreshEntity(drop1);
            level().addFreshEntity(drop2);

            HitResult.Type hitresult$type = result.getType();
            if (hitresult$type == HitResult.Type.ENTITY) {
                EntityHitResult entityhitresult = (EntityHitResult) result;
                Entity entity = entityhitresult.getEntity();
                if(entity instanceof LivingEntity livingEntity){
                    livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 2F);
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                }
            }
            this.discard();
        }
    }
}
