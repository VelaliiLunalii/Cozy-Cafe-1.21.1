package io.github.velaliilunalii.cozy_cafe.mixin;

import io.github.velaliilunalii.cozy_cafe.item.DrinkItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityEatingMixin {
    @Shadow
    private void addEatEffect(FoodProperties foodProperties) {}

    @Inject(at = @At("HEAD"), method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    public void eatMixing(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir){
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        level.playSound(
                null,
                livingEntity.getX(),
                livingEntity.getY(),
                livingEntity.getZ(),
                livingEntity.getEatingSound(food),
                SoundSource.NEUTRAL,
                1.0F,
                1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F
        );
        this.addEatEffect(foodProperties);
        CustomData data = food.get(DataComponents.CUSTOM_DATA);
        if (!(food.getItem() instanceof DrinkItem) || (data != null && data.copyTag().getInt("Sips") <1)) food.consume(1, livingEntity);
        livingEntity.gameEvent(GameEvent.EAT);
        cir.setReturnValue(food);
    }
}
