package io.github.velaliilunalii.cozy_cafe.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CupColorComponent(ColoredItem.Color color) {

    public static final Codec<CupColorComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ColoredItem.Color.CODEC.fieldOf("color").forGetter(CupColorComponent::color)
            ).apply(instance, CupColorComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CupColorComponent> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.fromCodec(ColoredItem.Color.CODEC),
                    CupColorComponent::color,
                    CupColorComponent::new
            );

    public static final CupColorComponent DEFAULT = new CupColorComponent(ColoredItem.Color.DEFAULT);

    public CupColorComponent withColor(ColoredItem.Color newColor) {
        return new CupColorComponent(newColor);
    }
}