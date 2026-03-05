package io.github.velaliilunalii.cozy_cafe.food;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.item.DrinkItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Optional;

public enum DrinkIngredient {
    WATER(CommonClass.WATER_BOTTLE, DrinkIngredientEffects.WATER, IngredientType.LIQUID, "Tea ", CommonClass.EMPTY_BOTTLE),
    SPARKLING_WATER(CommonClass.SPARKLING_WATER, DrinkIngredientEffects.SPARKLING_WATER, IngredientType.LIQUID, "Soda ", CommonClass.SODA_BOTTLE),
    DRAGON_BREATH(Items.DRAGON_BREATH, DrinkIngredientEffects.DRAGON_BREATH, IngredientType.LIQUID, "Draconic Soda ", Items.GLASS_BOTTLE),
    MILK(CommonClass.MILK_BOTTLE, DrinkIngredientEffects.MILK, IngredientType.LIQUID, "Latte ", CommonClass.EMPTY_BOTTLE),
    COCONUT_MILK(CommonClass.COCONUT_MILK_BOTTLE, DrinkIngredientEffects.COCONUT_MILK, IngredientType.LIQUID, "Tropical Latte ", CommonClass.EMPTY_BOTTLE),
    CHOCOLATE_MILK(CommonClass.CHOCOLATE_MILK, DrinkIngredientEffects.CHOCOLATE_MILK, IngredientType.LIQUID, "Chocolate ", CommonClass.GLASS_CUP),

    SUGAR(Items.SUGAR, DrinkIngredientEffects.SUGAR, IngredientType.SWEETENER, "Sweet ", Items.AIR),
    HONEY(Items.HONEY_BOTTLE, DrinkIngredientEffects.HONEY, IngredientType.SWEETENER, "Honeyed ", Items.GLASS_BOTTLE),
    NETHER_WART(Items.NETHER_WART, DrinkIngredientEffects.NETHER_WART, IngredientType.SWEETENER, "Alchemical ", Items.AIR),
    GLOW_INK(Items.GLOW_INK_SAC, DrinkIngredientEffects.GLOW_INK, IngredientType.SWEETENER, "Glowing ", Items.AIR),
    GLOWSTONE(Items.GLOWSTONE_DUST, DrinkIngredientEffects.GLOWSTONE, IngredientType.SWEETENER, "Sparkling ", Items.AIR),
    GHAST_TEAR(Items.GHAST_TEAR, DrinkIngredientEffects.GHAST_TEAR, IngredientType.SWEETENER, "Haunted ", Items.AIR),
    MAGMA_CREAM(Items.MAGMA_CREAM, DrinkIngredientEffects.MAGMA_CREAM, IngredientType.SWEETENER, "Creamy ", Items.AIR),

    BAMBOO(CommonClass.BAMBOO_MILK, DrinkIngredientEffects.BAMBOO, IngredientType.FLAVOR, "Bamboo ", CommonClass.EMPTY_BOTTLE),
    CHERRY(CommonClass.CHERRY_JUICE, DrinkIngredientEffects.CHERRY, IngredientType.FLAVOR, "Cherry ", CommonClass.EMPTY_BOTTLE),
    CHORUS(CommonClass.CHORUS_JUICE, DrinkIngredientEffects.CHORUS, IngredientType.FLAVOR, "Chorus ", CommonClass.EMPTY_BOTTLE),
    GLOW_BERRIES(CommonClass.GLOW_BERRIES_JUICE, DrinkIngredientEffects.GLOW_BERRIES, IngredientType.FLAVOR, "Glow Berries ", CommonClass.EMPTY_BOTTLE),
    SWEET_BERRIES(CommonClass.SWEET_BERRIES_JUICE, DrinkIngredientEffects.SWEET_BERRIES, IngredientType.FLAVOR, "Sweet Berries ", CommonClass.EMPTY_BOTTLE),
    PUMPKIN(CommonClass.PUMPKIN_JUICE, DrinkIngredientEffects.PUMPKIN, IngredientType.FLAVOR, "Pumpkin Spice ", CommonClass.EMPTY_BOTTLE),
    MELON(CommonClass.MELON_JUICE, DrinkIngredientEffects.MELON, IngredientType.FLAVOR, "Melon ", CommonClass.EMPTY_BOTTLE),
    MATCHA(CommonClass.MATCHA_TEA_BAG, DrinkIngredientEffects.MATCHA, IngredientType.FLAVOR, "Matcha ", Items.AIR),
    SPORE_BLOSSOM(CommonClass.SPORE_BLOSSOM_JUICE, DrinkIngredientEffects.SPORE_BLOSSOM, IngredientType.FLAVOR, "Spore Blossom ", CommonClass.EMPTY_BOTTLE),
    APPLE(CommonClass.APPLE_JUICE, DrinkIngredientEffects.APPLE, IngredientType.FLAVOR, "Apple ", CommonClass.EMPTY_BOTTLE),
    SCULK(CommonClass.SCULK_JUICE, DrinkIngredientEffects.SCULK, IngredientType.FLAVOR, "Sculk ", CommonClass.EMPTY_BOTTLE),
    POTION(Items.POTION, DrinkIngredientEffects.POTION, IngredientType.FLAVOR, "of ", Items.GLASS_BOTTLE);

    private final Item item;
    private final DrinkFoodPropertiesBuilder drinkFoodPropertiesBuilder;
    private final IngredientType ingredientType;
    private final String name;
    private final Item remainder;


    DrinkIngredient(Item item, DrinkFoodPropertiesBuilder drinkFoodPropertiesBuilder, IngredientType ingredientType, String name,Item remainder){
        this.item = item;
        this.drinkFoodPropertiesBuilder = drinkFoodPropertiesBuilder;
        this.ingredientType = ingredientType;
        this.name = name;
        this.remainder = remainder;
    }

    public Item item(){return item;}

    public static Optional<DrinkIngredient> getDrinkIngredient(ItemStack item){
        for(DrinkIngredient drinkIngredient : DrinkIngredient.values()){
            if(item.is(drinkIngredient.item)) return Optional.of(drinkIngredient);
        }
        return Optional.empty();
    }

    public static Optional<Item> getRemainder(Item item){
        for(DrinkIngredient drinkIngredient : DrinkIngredient.values()){
            if(item.equals(drinkIngredient.item)) return Optional.of(drinkIngredient.remainder);
        }
        return Optional.empty();
    }

    public static FoodProperties getFoodProperties(DrinkIngredient drinkIngredient){
        return drinkIngredient.drinkFoodPropertiesBuilder.build();
    }

    public static DrinkFoodPropertiesBuilder getFoodPropertiesBuilder(DrinkIngredient drinkIngredient){
        return drinkIngredient.drinkFoodPropertiesBuilder;
    }

    public static String getName(DrinkIngredient drinkIngredient){
        return drinkIngredient.name;
    }

    public boolean isLiquid(){
        return liquids().contains(this);
    }

    public boolean isSweetener(){
        return sweeteners().contains(this);
    }

    public boolean isFlavor(){
        return flavors().contains(this);
    }

    public static boolean isLiquid(ItemStack item){
        Optional<DrinkIngredient> drinkIngredient = getDrinkIngredient(item);
        return drinkIngredient.isEmpty() ? false : liquids().contains(drinkIngredient.get());
    }

    public static boolean isSweetener(ItemStack item){
        Optional<DrinkIngredient> drinkIngredient = getDrinkIngredient(item);
        return drinkIngredient.isEmpty() ? false : sweeteners().contains(drinkIngredient.get());
    }

    public static boolean isFlavor(ItemStack item){
        Optional<DrinkIngredient> drinkIngredient = getDrinkIngredient(item);
        return drinkIngredient.isEmpty() ? false : flavors().contains(drinkIngredient.get());
    }

    public static ArrayList<DrinkIngredient> liquids(){
        ArrayList<DrinkIngredient> liquids = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : DrinkIngredient.values()){
            if(drinkIngredient.ingredientType.equals(IngredientType.LIQUID))
                liquids.add(drinkIngredient);
        }
        return liquids;
    }

    public static ArrayList<DrinkIngredient> sweeteners(){
        ArrayList<DrinkIngredient> sweeteners = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : DrinkIngredient.values()){
            if(drinkIngredient.ingredientType.equals(IngredientType.SWEETENER))
                sweeteners.add(drinkIngredient);
        }
        return sweeteners;
    }

    public static ArrayList<DrinkIngredient> flavors(){
        ArrayList<DrinkIngredient> flavors = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : DrinkIngredient.values()){
            if(drinkIngredient.ingredientType.equals(IngredientType.FLAVOR))
                flavors.add(drinkIngredient);
        }
        return flavors;
    }

    public static ArrayList<Item> liquidsAsItems(){
        ArrayList<Item> liquidsAsItems = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : liquids()){
            liquidsAsItems.add(drinkIngredient.item);
        }
        return liquidsAsItems;
    }

    public static ArrayList<Item> sweetenersAsItems(){
        ArrayList<Item> sweetenersAsItems = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : sweeteners()){
            sweetenersAsItems.add(drinkIngredient.item);
        }
        return sweetenersAsItems;
    }

    public static ArrayList<Item> flavorsAsItems(){
        ArrayList<Item> flavorsAsItems = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : flavors()){
            flavorsAsItems.add(drinkIngredient.item);
        }
        return flavorsAsItems;
    }

    public enum IngredientType {
        LIQUID,
        SWEETENER,
        FLAVOR;
    }

    public enum ContainerType{
        GLASS_TUMBLER(CommonClass.GLASS_TUMBLER, "filled Glass Tumbler", 2),
        SPLIT_COCONUT(CommonClass.SPLIT_COCONUT, "filled Split Coconut", 2),
        SODA_BOTTLE(CommonClass.SODA_BOTTLE, "filled Soda Bottle", 2),
        GLASS_CAN(CommonClass.GLASS_CAN, "filled Glass Can", 1),
        COCKTAIL_GLASS(CommonClass.COCKTAIL_GLASS, "filled Cocktail Glass", 1),
        GLASS_CUP(CommonClass.GLASS_CUP, "filled Glass Cup", 1);

        private final Item item;
        private final String name;
        private final int size;

        ContainerType(Item item, String name, int size){
            this.item = item;
            this.name = name;
            this.size = size;
        }

        public static Optional<ContainerType> getContainerType(ItemStack item){
            for(ContainerType containerType : ContainerType.values()){
                if(item.is(containerType.item)) return Optional.of(containerType);
            }
            return Optional.empty();
        }

        public static String getName(ContainerType containerType){
            return containerType.name;
        }
        public static boolean isSmall(ContainerType containerType){
            return containerType.size == 1;
        }
    }

    public enum FilledContainer {
        BAMBOO_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_GLASS_TUMBLER),
        CHERRY_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_GLASS_TUMBLER),
        CHORUS_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_GLASS_TUMBLER),
        GLOW_BERRIES_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_GLASS_TUMBLER),
        PUMPKIN_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_GLASS_TUMBLER),
        MELON_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.MELON, CommonClass.MELON_FILLED_GLASS_TUMBLER),
        SCULK_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_GLASS_TUMBLER),
        MATCHA_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_GLASS_TUMBLER),
        SWEET_BERRIES_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_GLASS_TUMBLER),
        APPLE_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_GLASS_TUMBLER),
        SPORE_BLOSSOM_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_GLASS_TUMBLER),
        POTION_FILLED_GLASS_TUMBLER(ContainerType.GLASS_TUMBLER, DrinkIngredient.POTION, CommonClass.POTION_FILLED_GLASS_TUMBLER),

        BAMBOO_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_SPLIT_COCONUT),
        CHERRY_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_SPLIT_COCONUT),
        CHORUS_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_SPLIT_COCONUT),
        GLOW_BERRIES_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_SPLIT_COCONUT),
        PUMPKIN_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_SPLIT_COCONUT),
        MELON_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.MELON, CommonClass.MELON_FILLED_SPLIT_COCONUT),
        SCULK_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_SPLIT_COCONUT),
        MATCHA_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_SPLIT_COCONUT),
        SWEET_BERRIES_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_SPLIT_COCONUT),
        APPLE_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_SPLIT_COCONUT),
        SPORE_BLOSSOM_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_SPLIT_COCONUT),
        POTION_FILLED_SPLIT_COCONUT(ContainerType.SPLIT_COCONUT, DrinkIngredient.POTION, CommonClass.POTION_FILLED_SPLIT_COCONUT),

        BAMBOO_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_SODA_BOTTLE),
        CHERRY_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_SODA_BOTTLE),
        CHORUS_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_SODA_BOTTLE),
        GLOW_BERRIES_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_SODA_BOTTLE),
        PUMPKIN_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_SODA_BOTTLE),
        MELON_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.MELON, CommonClass.MELON_FILLED_SODA_BOTTLE),
        SCULK_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_SODA_BOTTLE),
        MATCHA_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_SODA_BOTTLE),
        SWEET_BERRIES_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_SODA_BOTTLE),
        APPLE_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_SODA_BOTTLE),
        SPORE_BLOSSOM_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_SODA_BOTTLE),
        POTION_FILLED_SODA_BOTTLE(ContainerType.SODA_BOTTLE, DrinkIngredient.POTION, CommonClass.POTION_FILLED_SODA_BOTTLE),

        BAMBOO_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_GLASS_CAN),
        CHERRY_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_GLASS_CAN),
        CHORUS_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_GLASS_CAN),
        GLOW_BERRIES_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_GLASS_CAN),
        PUMPKIN_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_GLASS_CAN),
        MELON_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.MELON, CommonClass.MELON_FILLED_GLASS_CAN),
        SCULK_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_GLASS_CAN),
        MATCHA_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_GLASS_CAN),
        SWEET_BERRIES_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_GLASS_CAN),
        APPLE_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_GLASS_CAN),
        SPORE_BLOSSOM_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CAN),
        POTION_FILLED_GLASS_CAN(ContainerType.GLASS_CAN, DrinkIngredient.POTION, CommonClass.POTION_FILLED_GLASS_CAN),

        BAMBOO_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_COCKTAIL_GLASS),
        CHERRY_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_COCKTAIL_GLASS),
        CHORUS_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_COCKTAIL_GLASS),
        GLOW_BERRIES_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_COCKTAIL_GLASS),
        PUMPKIN_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_COCKTAIL_GLASS),
        MELON_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.MELON, CommonClass.MELON_FILLED_COCKTAIL_GLASS),
        SCULK_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_COCKTAIL_GLASS ),
        MATCHA_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_COCKTAIL_GLASS ),
        SWEET_BERRIES_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_COCKTAIL_GLASS ),
        APPLE_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_COCKTAIL_GLASS ),
        SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS ),
        POTION_FILLED_COCKTAIL_GLASS(ContainerType.COCKTAIL_GLASS, DrinkIngredient.POTION, CommonClass.POTION_FILLED_COCKTAIL_GLASS ),

        BAMBOO_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.BAMBOO, CommonClass.BAMBOO_FILLED_GLASS_CUP),
        CHERRY_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.CHERRY, CommonClass.CHERRY_FILLED_GLASS_CUP),
        CHORUS_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.CHORUS, CommonClass.CHORUS_FILLED_GLASS_CUP),
        GLOW_BERRIES_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.GLOW_BERRIES, CommonClass.GLOW_BERRIES_FILLED_GLASS_CUP),
        PUMPKIN_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.PUMPKIN, CommonClass.PUMPKIN_FILLED_GLASS_CUP),
        MELON_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.MELON, CommonClass.MELON_FILLED_GLASS_CUP),
        SCULK_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.SCULK, CommonClass.SCULK_FILLED_GLASS_CUP),
        MATCHA_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.MATCHA, CommonClass.MATCHA_FILLED_GLASS_CUP),
        SWEET_BERRIES_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.SWEET_BERRIES, CommonClass.SWEET_BERRIES_FILLED_GLASS_CUP),
        APPLE_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.APPLE, CommonClass.APPLE_FILLED_GLASS_CUP),
        SPORE_BLOSSOM_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CUP),
        POTION_FILLED_GLASS_CUP(ContainerType.GLASS_CUP, DrinkIngredient.POTION, CommonClass.POTION_FILLED_GLASS_CUP);

        private final ContainerType containerType;
        private final DrinkIngredient drinkIngredient;
        private final Item item;

        FilledContainer(ContainerType containerType, DrinkIngredient drinkIngredient, Item item){
            this.containerType = containerType;
            this.drinkIngredient = drinkIngredient;
            this.item = item;
        }

        public static ItemStack getFilledContainer(ContainerType containerType, DrinkIngredient drinkIngredient){
            for(FilledContainer filledContainer : FilledContainer.values()){
                if(filledContainer.containerType.equals(containerType) &&
                        filledContainer.drinkIngredient.equals(drinkIngredient))
                    return new ItemStack(filledContainer.item);
            }
            return ItemStack.EMPTY;
        }

        public static Optional<ContainerType> getFilledContainer(ItemStack itemStack){
            for(FilledContainer filledContainer : FilledContainer.values()){
                if(itemStack.is(filledContainer.item))
                    return Optional.of(filledContainer.containerType);
            }
            return Optional.empty();
        }
    }
}
