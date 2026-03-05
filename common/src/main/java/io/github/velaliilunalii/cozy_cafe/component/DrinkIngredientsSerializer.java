package io.github.velaliilunalii.cozy_cafe.component;

import io.github.velaliilunalii.cozy_cafe.item.DrinkItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DrinkIngredientsSerializer implements EntityDataSerializer<DrinkItem.DrinkItemIngredients> {

    private static final StreamCodec<RegistryFriendlyByteBuf, DrinkItem.DrinkItemIngredients> CODEC =
            DrinkItem.DrinkItemIngredients.STREAM_CODEC.cast();

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, DrinkItem.DrinkItemIngredients> codec() {
        return CODEC;
    }

    @Override
    public DrinkItem.DrinkItemIngredients copy(DrinkItem.DrinkItemIngredients value) {
        return value.copy();
    }
}
