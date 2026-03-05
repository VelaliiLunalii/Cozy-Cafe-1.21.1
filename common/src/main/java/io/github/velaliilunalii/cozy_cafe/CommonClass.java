package io.github.velaliilunalii.cozy_cafe;

import io.github.velaliilunalii.cozy_cafe.block.IngredientShelfBlock;
import io.github.velaliilunalii.cozy_cafe.block.entity.IngredientShelfBlockEntity;
import io.github.velaliilunalii.cozy_cafe.component.DrinkIngredientsSerializer;
import io.github.velaliilunalii.cozy_cafe.effect.*;
import io.github.velaliilunalii.cozy_cafe.entity.CoconutEntity;
import io.github.velaliilunalii.cozy_cafe.entity.DyeVatEntity;
import io.github.velaliilunalii.cozy_cafe.entity.JuicerEntity;
import io.github.velaliilunalii.cozy_cafe.entity.MixingCupEntity;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.BambooCoasterEntity;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.WoodenCoasterEntity;
import io.github.velaliilunalii.cozy_cafe.food.DrinkFoodPropertiesBuilder;
import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredientEffects;
import io.github.velaliilunalii.cozy_cafe.item.*;
import io.github.velaliilunalii.cozy_cafe.item.coaster.BambooCoasterItem;
import io.github.velaliilunalii.cozy_cafe.component.CupColorComponent;
import io.github.velaliilunalii.cozy_cafe.item.coaster.WoodenCoasterItem;
import io.github.velaliilunalii.cozy_cafe.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static io.github.velaliilunalii.cozy_cafe.food.DrinkIngredientEffects.*;

public class CommonClass {
    public static ColoredItem GLASS_TUMBLER;
    public static Supplier<ColoredItem> GLASS_TUMBLER_SUPPLIER = () -> new ColoredItem(new Item.Properties());
    public static ColoredItem SPLIT_COCONUT;
    public static Supplier<ColoredItem> SPLIT_COCONUT_SUPPLIER = () -> new ColoredItem(new Item.Properties());
    public static SodaBottleItem SODA_BOTTLE;
    public static Supplier<SodaBottleItem> SODA_BOTTLE_SUPPLIER = () -> new SodaBottleItem(new Item.Properties());
    public static ColoredItem GLASS_CAN;
    public static Supplier<ColoredItem> GLASS_CAN_SUPPLIER = () -> new ColoredItem(new Item.Properties());
    public static ColoredItem COCKTAIL_GLASS;
    public static Supplier<ColoredItem> COCKTAIL_GLASS_SUPPLIER = () -> new ColoredItem(new Item.Properties());
    public static ColoredItem GLASS_CUP;
    public static Supplier<ColoredItem> GLASS_CUP_SUPPLIER = () -> new ColoredItem(new Item.Properties());
    public static ColoredItem CHOCOLATE_MILK;
    public static Supplier<ColoredItem> CHOCOLATE_MILK_SUPPLIER = () -> new ColoredItem(new Item.Properties()
            .food(new DrinkFoodPropertiesBuilder().nutrition(3).saturationModifier(1.0F).fast().build()));
    public static EmptyBottleItem EMPTY_BOTTLE;
    public static Supplier<EmptyBottleItem> EMPTY_BOTTLE_SUPPLIER = () -> new EmptyBottleItem(new Item.Properties());
    public static Item WATER_BOTTLE;
    public static Supplier<Item> WATER_BOTTLE_SUPPLIER = () -> new Item(new Item.Properties());
    public static Item MILK_BOTTLE;
    public static Supplier<Item> MILK_BOTTLE_SUPPLIER = () -> new Item(new Item.Properties());
    public static Item COCONUT_MILK_BOTTLE;
    public static Supplier<Item> COCONUT_MILK_BOTTLE_SUPPLIER = () -> new Item(new Item.Properties());
    public static Item SPARKLING_WATER;
    public static Supplier<Item> SPARKLING_WATER_SUPPLIER = () -> new Item(new Item.Properties());
    public static Item CHERRY_JUICE;
    public static Supplier<Item> CHERRY_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(CHERRY.fast().build()));
    public static Item BAMBOO_MILK;
    public static Supplier<Item> BAMBOO_MILK_SUPPLIER = () -> new Item(new Item.Properties().food(BAMBOO.fast().build()));
    public static Item CHORUS_JUICE;
    public static Supplier<Item> CHORUS_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(CHORUS.fast().build()));
    public static Item GLOW_BERRIES_JUICE;
    public static Supplier<Item> GLOW_BERRIES_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(GLOW_BERRIES.fast().build()));
    public static Item SCULK_JUICE;
    public static Supplier<Item> SCULK_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(SCULK.fast().build()));
    public static Item PUMPKIN_JUICE;
    public static Supplier<Item> PUMPKIN_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(PUMPKIN.fast().build()));
    public static Item MELON_JUICE;
    public static Supplier<Item> MELON_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(MELON.fast().build()));
    public static Item SWEET_BERRIES_JUICE;
    public static Supplier<Item> SWEET_BERRIES_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(SWEET_BERRIES.fast().build()));
    public static Item APPLE_JUICE;
    public static Supplier<Item> APPLE_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(APPLE.fast().build()));
    public static Item SPORE_BLOSSOM_JUICE;
    public static Supplier<Item> SPORE_BLOSSOM_JUICE_SUPPLIER = () -> new Item(new Item.Properties().food(SPORE_BLOSSOM.fast().build()));
    public static Item MATCHA_TEA_BAG;
    public static Supplier<Item> MATCHA_TEA_BAG_SUPPLIER = () -> new Item(new Item.Properties());
    public static CoconutItem COCONUT_ITEM;
    public static Supplier<CoconutItem> COCONUT_ITEM_SUPPLIER = () -> new CoconutItem(new Item.Properties().food(new DrinkFoodPropertiesBuilder().nutrition(3).saturationModifier(0.2F).fast().build()));

    public static WoodenCoasterItem WOODEN_COASTER_ITEM;
    public static Supplier<WoodenCoasterItem> WOODEN_COASTER_ITEM_SUPPLIER = () -> new WoodenCoasterItem(new Item.Properties());
    public static BambooCoasterItem BAMBOO_COASTER_ITEM;
    public static Supplier<BambooCoasterItem> BAMBOO_COASTER_ITEM_SUPPLIER = () -> new BambooCoasterItem(new Item.Properties());

    public static MixingCupItem MIXING_CUP_ITEM;
    public static Supplier<MixingCupItem> MIXING_CUP_ITEM_SUPPLIER = () -> new MixingCupItem(new Item.Properties());

    public static DyeVatItem DYE_VAT_ITEM;
    public static Supplier<DyeVatItem> DYE_VAT_ITEM_SUPPLIER = () -> new DyeVatItem(new Item.Properties());

    public static JuicerItem JUICER_ITEM;
    public static Supplier<JuicerItem> JUICER_ITEM_SUPPLIER = () -> new JuicerItem(new Item.Properties());

    public static final int GLASS_TUMBLER_MAX_SIPS = 9;
    public static final int SPLIT_COCONUT_MAX_SIPS = 3;
    public static final int SODA_BOTTLE_MAX_SIPS = 6;
    public static final int GLASS_CAN_MAX_SIPS = 9;
    public static final int COCKTAIL_GLASS_MAX_SIPS = 3;
    public static final int GLASS_CUP_MAX_SIPS = 6;

    public static DrinkItem BAMBOO_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> BAMBOO_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> CHERRY_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> CHORUS_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem MELON_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> MELON_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> SCULK_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> MATCHA_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> APPLE_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.slow().build()), GLASS_TUMBLER_MAX_SIPS);
    public static DrinkItem POTION_FILLED_GLASS_TUMBLER;
    public static Supplier<DrinkItem> POTION_FILLED_GLASS_TUMBLER_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.slow().build()), GLASS_TUMBLER_MAX_SIPS);

    public static DrinkItem BAMBOO_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> BAMBOO_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> CHERRY_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> CHORUS_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem MELON_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> MELON_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> SCULK_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> MATCHA_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> APPLE_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.fast().build()), SPLIT_COCONUT_MAX_SIPS);
    public static DrinkItem POTION_FILLED_SPLIT_COCONUT;
    public static Supplier<DrinkItem> POTION_FILLED_SPLIT_COCONUT_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.fast().build()), SPLIT_COCONUT_MAX_SIPS);

    public static DrinkItem BAMBOO_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> BAMBOO_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> CHERRY_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> CHORUS_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem MELON_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> MELON_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> SCULK_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> MATCHA_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> APPLE_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.average().build()), SODA_BOTTLE_MAX_SIPS);
    public static DrinkItem POTION_FILLED_SODA_BOTTLE;
    public static Supplier<DrinkItem> POTION_FILLED_SODA_BOTTLE_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.average().build()), SODA_BOTTLE_MAX_SIPS);

    public static DrinkItem BAMBOO_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> BAMBOO_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> CHERRY_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> CHORUS_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem MELON_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> MELON_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> SCULK_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> MATCHA_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> APPLE_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.average().build()), GLASS_CAN_MAX_SIPS);
    public static DrinkItem POTION_FILLED_GLASS_CAN;
    public static Supplier<DrinkItem> POTION_FILLED_GLASS_CAN_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.average().build()), GLASS_CAN_MAX_SIPS);

    public static DrinkItem BAMBOO_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> BAMBOO_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> CHERRY_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> CHORUS_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem MELON_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> MELON_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> SCULK_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> MATCHA_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> APPLE_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);
    public static DrinkItem POTION_FILLED_COCKTAIL_GLASS;
    public static Supplier<DrinkItem> POTION_FILLED_COCKTAIL_GLASS_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.veryFast().build()), COCKTAIL_GLASS_MAX_SIPS);

    public static DrinkItem BAMBOO_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> BAMBOO_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(BAMBOO.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem CHERRY_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> CHERRY_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHERRY.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem CHORUS_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> CHORUS_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(CHORUS.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem GLOW_BERRIES_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> GLOW_BERRIES_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(GLOW_BERRIES.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem PUMPKIN_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> PUMPKIN_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(PUMPKIN.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem MELON_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> MELON_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MELON.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem SCULK_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> SCULK_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SCULK.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem MATCHA_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> MATCHA_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(MATCHA.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem SWEET_BERRIES_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> SWEET_BERRIES_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SWEET_BERRIES.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem APPLE_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> APPLE_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(APPLE.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem SPORE_BLOSSOM_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> SPORE_BLOSSOM_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(SPORE_BLOSSOM.fast().build()), GLASS_CUP_MAX_SIPS);
    public static DrinkItem POTION_FILLED_GLASS_CUP;
    public static Supplier<DrinkItem> POTION_FILLED_GLASS_CUP_SUPPLIER = () -> new DrinkItem(new Item.Properties().food(POTION.fast().build()), GLASS_CUP_MAX_SIPS);

    public static DataComponentType<CupColorComponent> CUP_COLOR;
    public static final Supplier<DataComponentType<CupColorComponent>> CUP_COLOR_SUPPLIER = () ->
            DataComponentType.<CupColorComponent>builder()
                    .persistent(CupColorComponent.CODEC)
                    .networkSynchronized(CupColorComponent.STREAM_CODEC)
                    .build();

    public static EntityType<WoodenCoasterEntity> WOODEN_COASTER;
    public static final Supplier<EntityType<WoodenCoasterEntity>> WOODEN_COASTER_SUPPLIER = () ->
            EntityType.Builder.<WoodenCoasterEntity>of(WoodenCoasterEntity::new, MobCategory.MISC)
                    .sized(4.0F / 16.0F, 1.0F / 16.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("wooden_coaster");
    public static EntityType<BambooCoasterEntity> BAMBOO_COASTER;
    public static final Supplier<EntityType<BambooCoasterEntity>> BAMBOO_COASTER_SUPPLIER = () ->
            EntityType.Builder.<BambooCoasterEntity>of(BambooCoasterEntity::new, MobCategory.MISC)
                    .sized(4.0F / 16.0F, 1.0F / 16.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("bamboo_coaster");

    public static EntityType<MixingCupEntity> MIXING_CUP;
    public static final Supplier<EntityType<MixingCupEntity>> MIXING_CUP_SUPPLIER = () ->
            EntityType.Builder.<MixingCupEntity>of(MixingCupEntity::new, MobCategory.MISC)
                    .sized(5.0F / 16.0F, 8.0F / 16.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("mixing_cup");

    public static EntityType<DyeVatEntity> DYE_VAT;
    public static final Supplier<EntityType<DyeVatEntity>> DYE_VAT_SUPPLIER = () ->
            EntityType.Builder.<DyeVatEntity>of(DyeVatEntity::new, MobCategory.MISC)
                    .sized(0.5F, 1F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("dye_vat");

    public static EntityType<JuicerEntity> JUICER;
    public static final Supplier<EntityType<JuicerEntity>> JUICER_SUPPLIER = () ->
            EntityType.Builder.<JuicerEntity>of(JuicerEntity::new, MobCategory.MISC)
                    .sized(5.0F / 16.0F, 12.0F / 16.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("juicer");

    public static EntityType<CoconutEntity> COCONUT;
    public static final Supplier<EntityType<CoconutEntity>> COCONUT_SUPPLIER = () ->
            EntityType.Builder.<CoconutEntity>of(CoconutEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("coconut");

    public static Block DECORATIVE_BAMBOO;
    public static Supplier<Block> DECORATIVE_BAMBOO_SUPPLIER = () -> new Block(BlockBehaviour.Properties.of().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion());

    public static IngredientShelfBlock INGREDIENT_SHELF;
    public static Supplier<IngredientShelfBlock> INGREDIENT_SHELF_SUPPLIER = () -> new IngredientShelfBlock(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOD).noOcclusion());
    public static BlockEntityType<IngredientShelfBlockEntity> INGREDIENT_SHELF_BLOCK_ENTITY;

    public static Holder<MobEffect> REFRESHED_EFFECT;
    public static final Supplier<MobEffect> REFRESHED_EFFECT_SUPPLIER = RefreshedEffect::new;
    public static Holder<MobEffect> SMALL_ABSORPTION_EFFECT;
    public static final Supplier<MobEffect> SMALL_ABSORPTION_EFFECT_SUPPLIER = () -> new SmallAbsorptionEffect().addAttributeModifier(Attributes.MAX_ABSORPTION, ResourceLocation.withDefaultNamespace("effect.absorption"), 1.0, AttributeModifier.Operation.ADD_VALUE);
    public static Holder<MobEffect> LATTE_EFFECT;
    public static final Supplier<MobEffect> LATTE_EFFECT_SUPPLIER = LatteEffect::new;
    public static Holder<MobEffect> TEA_EFFECT;
    public static final Supplier<MobEffect> TEA_EFFECT_SUPPLIER = TeaEffect::new;
    public static Holder<MobEffect> SHARE_FOOD_EFFECT;
    public static final Supplier<MobEffect> SHARE_FOOD_EFFECT_SUPPLIER = ShareFoodEffect::new;
    public static Holder<MobEffect> REVEALING_EFFECT;
    public static final Supplier<MobEffect> REVEALING_EFFECT_SUPPLIER = RevealingEffect::new;
    public static Holder<MobEffect> WARPING_EFFECT;
    public static final Supplier<MobEffect> WARPING_EFFECT_SUPPLIER = WarpingEffect::new;


    public static SimpleParticleType BLUE_MIXING_PARTICLE;
    public static SimpleParticleType GREEN_MIXING_PARTICLE;
    public static SimpleParticleType RED_MIXING_PARTICLE;

    public static SoundEvent MIXING;
    public static SoundEvent POURING;
    public static SoundEvent DRINKING;

    public static final EntityDataSerializer<DrinkItem.DrinkItemIngredients> DRINK_INGREDIENTS_SERIALIZER =
            new DrinkIngredientsSerializer();

    public static void init() {
        EntityDataSerializers.registerSerializer(DRINK_INGREDIENTS_SERIALIZER);

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        if (Services.PLATFORM.isModLoaded("examplemod")) {

            Constants.LOG.info("Hello to examplemod");
        }
    }
}