package io.github.velaliilunalii.cozy_cafe.food;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DrinkFoodPropertiesBuilder extends FoodProperties.Builder{
    private int nutrition;
    private float saturationModifier;
    private boolean canAlwaysEat;
    private float eatSeconds = 1.6F;
    private Optional<ItemStack> usingConvertsTo = Optional.empty();
    private final ImmutableList.Builder<FoodProperties.PossibleEffect> effects = ImmutableList.builder();

    public DrinkFoodPropertiesBuilder nutrition(int nutrition) {
        this.nutrition = nutrition;
        return this;
    }

    public DrinkFoodPropertiesBuilder saturationModifier(float saturationModifier) {
        this.saturationModifier = saturationModifier;
        return this;
    }

    public DrinkFoodPropertiesBuilder canAlwaysEat(boolean canAlwaysEat) {
        this.canAlwaysEat = canAlwaysEat;
        return this;
    }

    public DrinkFoodPropertiesBuilder alwaysEdible() {
        this.canAlwaysEat = true;
        return this;
    }

    public DrinkFoodPropertiesBuilder speed(float speed) {
        this.eatSeconds = speed;
        return this;
    }

    public DrinkFoodPropertiesBuilder veryFast() {
        this.eatSeconds = 0.4F;
        return this;
    }

    public DrinkFoodPropertiesBuilder fast() {
        this.eatSeconds = 0.8F;
        return this;
    }

    public DrinkFoodPropertiesBuilder average() {
        this.eatSeconds = 1.2F;
        return this;
    }

    public DrinkFoodPropertiesBuilder slow() {
        this.eatSeconds = 1.6F;
        return this;
    }

    public DrinkFoodPropertiesBuilder verySlow() {
        this.eatSeconds = 2.0F;
        return this;
    }

    public DrinkFoodPropertiesBuilder effect(MobEffectInstance effect, float probability) {
        this.effects.add(new FoodProperties.PossibleEffect(effect, probability));
        return this;
    }

    public DrinkFoodPropertiesBuilder effects(List<FoodProperties.PossibleEffect> effects) {
        for (FoodProperties.PossibleEffect possibleEffect : effects){
            this.effects.add(new FoodProperties.PossibleEffect(possibleEffect.effect(), possibleEffect.probability()));
        }
        return this;
    }

    public DrinkFoodPropertiesBuilder effects(Iterable<MobEffectInstance> effects) {
        for (MobEffectInstance mobEffectInstance : effects) {
            this.effects.add(new FoodProperties.PossibleEffect(mobEffectInstance, 1));
        }
        return this;
    }

    public DrinkFoodPropertiesBuilder usingConvertsTo(Optional<ItemStack> optionalItemStack) {
        this.usingConvertsTo = optionalItemStack;
        return this;
    }

    public DrinkFoodPropertiesBuilder usingConvertsTo(ItemLike item) {
        this.usingConvertsTo = Optional.of(new ItemStack(item));
        return this;
    }

    public FoodProperties build() {
        float f = FoodConstants.saturationByModifier(this.nutrition, this.saturationModifier);
        return new FoodProperties(this.nutrition, f, this.canAlwaysEat, this.eatSeconds, this.usingConvertsTo, this.effects.build());
    }

    public static FoodProperties addSecondaryFlavorFoodProperties(FoodProperties foodproperties, FoodProperties additionalFoodproperties){
        return addFoodProperties(foodproperties, additionalFoodproperties, true);
    }

    public static FoodProperties addFoodProperties(FoodProperties foodproperties, FoodProperties additionalFoodproperties){
        return addFoodProperties(foodproperties, additionalFoodproperties, false);
    }

    public static FoodProperties addFoodProperties(FoodProperties foodproperties, FoodProperties additionalFoodproperties, boolean secondaryFlavor){
        int nutrition = foodproperties.nutrition() + (additionalFoodproperties.nutrition() / (secondaryFlavor ? 2 : 1));
        float saturationModifier = (foodproperties.saturation() / (2 * foodproperties.nutrition())) + ((additionalFoodproperties.saturation() / (2 * additionalFoodproperties.nutrition())) / (secondaryFlavor ? 2 : 1));
        float speed = foodproperties.eatSeconds();
        boolean canAlwaysEat = foodproperties.canAlwaysEat() || additionalFoodproperties.canAlwaysEat();
        List<FoodProperties.PossibleEffect> effects = foodproperties.effects();
        List<FoodProperties.PossibleEffect> additionalEffects = additionalFoodproperties.effects();
        Optional<ItemStack> usingConvertsTo = foodproperties.usingConvertsTo();

        DrinkFoodPropertiesBuilder drinkProperties = new DrinkFoodPropertiesBuilder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .speed(speed)
                .canAlwaysEat(canAlwaysEat)
                .effects(effects)
                .effects(additionalEffects)
                .usingConvertsTo(usingConvertsTo);

        return drinkProperties.build();
    }

    public static FoodProperties addPotionProperties(FoodProperties foodproperties, PotionContents potionContents){
        int nutrition = foodproperties.nutrition();
        float saturationModifier = (foodproperties.saturation() / (2 * foodproperties.nutrition()));
        float speed = foodproperties.eatSeconds();
        boolean canAlwaysEat = true;
        List<FoodProperties.PossibleEffect> effects = foodproperties.effects();
        Iterable<MobEffectInstance> additionalEffects = potionContents.getAllEffects();
        Optional<ItemStack> usingConvertsTo = foodproperties.usingConvertsTo();

        DrinkFoodPropertiesBuilder drinkProperties = new DrinkFoodPropertiesBuilder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .speed(speed)
                .canAlwaysEat(canAlwaysEat)
                .effects(effects)
                .effects(additionalEffects)
                .usingConvertsTo(usingConvertsTo);

        return drinkProperties.build();
    }

    public static FoodProperties reduceFoodProperties(FoodProperties foodproperties, int amplifier){
        float reductionFactor = 1F / (4 * (1 + amplifier));
        int nutrition = (int) (foodproperties.nutrition() * reductionFactor);
        float saturationModifier = (foodproperties.saturation() / (2 * foodproperties.nutrition())) * reductionFactor;
        float speed = foodproperties.eatSeconds();
        boolean canAlwaysEat = foodproperties.canAlwaysEat();
        List<FoodProperties.PossibleEffect> effects = foodproperties.effects();
        Optional<ItemStack> usingConvertsTo = foodproperties.usingConvertsTo();

        DrinkFoodPropertiesBuilder drinkProperties = new DrinkFoodPropertiesBuilder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .speed(speed)
                .canAlwaysEat(canAlwaysEat)
                .effects(effects)
                .usingConvertsTo(usingConvertsTo);

        for (FoodProperties.PossibleEffect possibleEffect : foodproperties.effects()){
            MobEffectInstance mobEffectInstance = possibleEffect.effect();
            Holder<MobEffect> mobEffect = mobEffectInstance.getEffect();
            int duration = (int) (mobEffectInstance.getDuration() * reductionFactor);
            int newAmplifier = mobEffectInstance.getAmplifier();
            boolean isAmbient = mobEffectInstance.isAmbient();
            boolean isVisible = mobEffectInstance.isVisible();
            drinkProperties.effect(new MobEffectInstance(mobEffect, duration, newAmplifier, isAmbient, isVisible),
                    possibleEffect.probability());
        }

        return drinkProperties.build();
    }
}
