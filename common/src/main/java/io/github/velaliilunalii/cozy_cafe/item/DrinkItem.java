package io.github.velaliilunalii.cozy_cafe.item;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public class DrinkItem extends ColoredItem {
    private final int maxSips;

    public DrinkItem(Properties properties, int maxSips) {
        super(properties);
        this.maxSips = maxSips;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if(customData.copyTag().getInt("Sips") < 1) {
            customData = customData.update(tag -> {
                tag.putInt("Sips", maxSips);
            });
            stack.set(DataComponents.CUSTOM_DATA, customData);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int currentSips = customData.copyTag().getInt("Sips");
        customData = customData.update(tag -> {
            tag.putInt("Sips", currentSips - 1);
        });
        stack.set(DataComponents.CUSTOM_DATA, customData);

        FoodProperties foodproperties = stack.get(DataComponents.FOOD);
        return foodproperties != null ? livingEntity.eat(level, stack, foodproperties) : stack;
    }

    public SoundEvent getEatingSound() {
        return CommonClass.DRINKING;
    }

    public SoundEvent getBreakingSound() {
        return SoundEvents.ITEM_BREAK;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public static void setIngredients(){

    }

    public static class DrinkItemIngredients {
        @Nullable
        private DrinkIngredient liquid;
        @Nullable
        private DrinkIngredient sweetener;
        @Nullable
        private DrinkIngredient mainFlavor;
        @Nullable
        private DrinkIngredient secondaryFlavor;

        public DrinkItemIngredients() {
            this.liquid = null;
            this.sweetener = null;
            this.mainFlavor = null;
            this.secondaryFlavor = null;
        }

        public DrinkItemIngredients(DrinkIngredient[] ingredients) {
            this();

            int counter = 0;
            for (DrinkIngredient drinkIngredient : ingredients) {
                if (counter > 4) return;

                if (drinkIngredient.isLiquid() && liquid == null) liquid = drinkIngredient;
                else if (drinkIngredient.isSweetener() && sweetener == null) sweetener = drinkIngredient;
                else if (drinkIngredient.isFlavor() && mainFlavor == null) mainFlavor = drinkIngredient;
                else if (drinkIngredient.isFlavor() && secondaryFlavor == null) secondaryFlavor = drinkIngredient;

                counter++;
            }
        }

        @Nullable
        public DrinkIngredient getLiquid() {
            return liquid;
        }

        @Nullable
        public DrinkIngredient getSweetener() {
            return sweetener;
        }

        @Nullable
        public DrinkIngredient getMainFlavor() {
            return mainFlavor;
        }

        @Nullable
        public DrinkIngredient getSecondaryFlavor() {
            return secondaryFlavor;
        }

        public void setLiquid(@Nullable DrinkIngredient liquid) {
            this.liquid = liquid;
        }

        public void setSweetener(@Nullable DrinkIngredient sweetener) {
            this.sweetener = sweetener;
        }

        public void setMainFlavor(@Nullable DrinkIngredient mainFlavor) {
            this.mainFlavor = mainFlavor;
        }

        public void setSecondaryFlavor(@Nullable DrinkIngredient secondaryFlavor) {
            this.secondaryFlavor = secondaryFlavor;
        }

        public static final StreamCodec<ByteBuf, DrinkItemIngredients> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public void encode(ByteBuf buffer, DrinkItemIngredients ingredients) {
                // Encoder chaque DrinkIngredient (ou null)
                encodeNullableDrinkIngredient(buffer, ingredients.liquid);
                encodeNullableDrinkIngredient(buffer, ingredients.sweetener);
                encodeNullableDrinkIngredient(buffer, ingredients.mainFlavor);
                encodeNullableDrinkIngredient(buffer, ingredients.secondaryFlavor);
            }

            @Override
            public DrinkItemIngredients decode(ByteBuf buffer) {
                DrinkItemIngredients ingredients = new DrinkItemIngredients();
                ingredients.liquid = decodeNullableDrinkIngredient(buffer);
                ingredients.sweetener = decodeNullableDrinkIngredient(buffer);
                ingredients.mainFlavor = decodeNullableDrinkIngredient(buffer);
                ingredients.secondaryFlavor = decodeNullableDrinkIngredient(buffer);
                return ingredients;
            }

            private void encodeNullableDrinkIngredient(ByteBuf buffer, @Nullable DrinkIngredient ingredient) {
                if (ingredient == null) {
                    buffer.writeBoolean(false); // null marker
                } else {
                    buffer.writeBoolean(true); // not null marker
                    ByteBufCodecs.STRING_UTF8.encode(buffer, ingredient.name());
                }
            }

            @Nullable
            private DrinkIngredient decodeNullableDrinkIngredient(ByteBuf buffer) {
                boolean isPresent = buffer.readBoolean();
                if (!isPresent) {
                    return null;
                }
                String name = ByteBufCodecs.STRING_UTF8.decode(buffer);
                try {
                    return DrinkIngredient.valueOf(name);
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        };

        public DrinkItemIngredients copy() {
            DrinkItemIngredients copy = new DrinkItemIngredients();
            copy.liquid = this.liquid;
            copy.sweetener = this.sweetener;
            copy.mainFlavor = this.mainFlavor;
            copy.secondaryFlavor = this.secondaryFlavor;
            return copy;
        }
    }
}
