package io.github.velaliilunalii.cozy_cafe.mixin;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.velaliilunalii.cozy_cafe.food.DrinkFoodPropertiesBuilder.reduceFoodProperties;

@Mixin(Player.class)
public class PlayerEatingMixin {
    @Inject(at = @At("HEAD"), method = "eat")
    public void eat(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir){
        Player player = (Player) (Object) this;
        if(player.hasEffect(CommonClass.REFRESHED_EFFECT)){
            int refreshedLevel = player.getEffect(CommonClass.REFRESHED_EFFECT).getAmplifier() + 1;
            int nutrition = foodProperties.nutrition();
            float saturation = (foodProperties.saturation() / (2 * foodProperties.nutrition()));
            FoodProperties newFoodProperties = new FoodProperties.Builder()
                    .nutrition(1 + (int)Math.floor(nutrition * ((double) refreshedLevel /10)))
                    .saturationModifier(0.25f + saturation * ((float) refreshedLevel /10))
                    .build();
            player.getFoodData().eat(newFoodProperties);
        }
        if (foodProperties.effects().contains(new FoodProperties.PossibleEffect(
                new MobEffectInstance(CommonClass.SHARE_FOOD_EFFECT, 1, 0, false, false),
                1.0F))) {
            FoodProperties sharedFoodProperties = reduceFoodProperties(foodProperties, 0);
            System.out.println(sharedFoodProperties);
            for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(5), entity -> entity instanceof Player)) {
                ((Player) entity).getFoodData().eat(foodProperties);
            }
        }
    }
}
