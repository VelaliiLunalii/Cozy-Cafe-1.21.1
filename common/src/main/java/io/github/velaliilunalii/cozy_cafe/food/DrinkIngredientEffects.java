package io.github.velaliilunalii.cozy_cafe.food;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class DrinkIngredientEffects {
    //--------------------------------------------------Liquid----------------------------------------------------------
    public static final DrinkFoodPropertiesBuilder WATER = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);
    public static final DrinkFoodPropertiesBuilder SPARKLING_WATER = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);
    public static final DrinkFoodPropertiesBuilder MILK = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);
    public static final DrinkFoodPropertiesBuilder COCONUT_MILK = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);
    public static final DrinkFoodPropertiesBuilder DRAGON_BREATH = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);
    public static final DrinkFoodPropertiesBuilder CHOCOLATE_MILK = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0F);

    //--------------------------------------------------Sweetener-------------------------------------------------------
    public static final DrinkFoodPropertiesBuilder SUGAR = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.3F);
    public static final DrinkFoodPropertiesBuilder HONEY = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.4F);
    public static final DrinkFoodPropertiesBuilder NETHER_WART = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.1F);
    public static final DrinkFoodPropertiesBuilder GLOW_INK = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.2F);
    public static final DrinkFoodPropertiesBuilder GLOWSTONE = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.2F);
    public static final DrinkFoodPropertiesBuilder GHAST_TEAR = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.2F);
    public static final DrinkFoodPropertiesBuilder MAGMA_CREAM = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.4F);

    //--------------------------------------------------Flavor----------------------------------------------------------
    //Secondary: / 2
    public static final DrinkFoodPropertiesBuilder BAMBOO = new DrinkFoodPropertiesBuilder().nutrition(2).saturationModifier(0.6F); //8
    public static final DrinkFoodPropertiesBuilder CHERRY = new DrinkFoodPropertiesBuilder().nutrition(4).saturationModifier(0.4F); //8
    public static final DrinkFoodPropertiesBuilder CHORUS = new DrinkFoodPropertiesBuilder().nutrition(2).saturationModifier(0.4F); //6
    public static final DrinkFoodPropertiesBuilder GLOW_BERRIES = new DrinkFoodPropertiesBuilder().nutrition(4).saturationModifier(0.4F);   //8
    public static final DrinkFoodPropertiesBuilder SWEET_BERRIES = new DrinkFoodPropertiesBuilder().nutrition(2).saturationModifier(0.2F);  //4
    public static final DrinkFoodPropertiesBuilder PUMPKIN = new DrinkFoodPropertiesBuilder().nutrition(6).saturationModifier(0.2F);    //8
    public static final DrinkFoodPropertiesBuilder MELON = new DrinkFoodPropertiesBuilder().nutrition(2).saturationModifier(0.2F);  //4
    public static final DrinkFoodPropertiesBuilder MATCHA = new DrinkFoodPropertiesBuilder().nutrition(6).saturationModifier(0.4F); //10
    public static final DrinkFoodPropertiesBuilder SPORE_BLOSSOM = new DrinkFoodPropertiesBuilder().nutrition(4).saturationModifier(0.6F);  //10
    public static final DrinkFoodPropertiesBuilder APPLE = new DrinkFoodPropertiesBuilder().nutrition(4).saturationModifier(0.2F);  //6
    public static final DrinkFoodPropertiesBuilder SCULK = new DrinkFoodPropertiesBuilder().nutrition(2).saturationModifier(0.4F);  //6
    public static final DrinkFoodPropertiesBuilder POTION = new DrinkFoodPropertiesBuilder().nutrition(1).saturationModifier(0.1F);  //2

    public static void addEffects() {
        WATER.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.TEA_EFFECT, 1, 0, false, false), 1.0F);
        SPARKLING_WATER.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.TEA_EFFECT, 1, 1, false, false), 1.0F);
        MILK.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.LATTE_EFFECT, 1, 0, false, false), 1.0F);
        COCONUT_MILK.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.LATTE_EFFECT, 1, 1, false, false), 1.0F);
        DRAGON_BREATH.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.SHARE_FOOD_EFFECT, 1, 0, false, false), 1.0F);
        CHOCOLATE_MILK.effect(drinkEffect(), 1.0F).alwaysEdible().effect(
                new MobEffectInstance(CommonClass.SMALL_ABSORPTION_EFFECT,
                600, 1, false, false), 1.0F);

        GLOW_INK.effect(new MobEffectInstance(CommonClass.REVEALING_EFFECT, 20, 1, false, false), 1.0F);
        GLOWSTONE.effect(new MobEffectInstance(CommonClass.REVEALING_EFFECT, 20, 0, false, false), 1.0F);

        CHORUS.effect(new MobEffectInstance(CommonClass.WARPING_EFFECT, 20, 0, false, false), 1.0F);
    }

    public static MobEffectInstance drinkEffect(){
        return new MobEffectInstance(CommonClass.REFRESHED_EFFECT, 1200, 0, false, false);
    }
}
