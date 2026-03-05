package io.github.velaliilunalii.cozy_cafe.block.entity;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.MixingCupEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class IngredientShelfBlockEntity  extends BlockEntity implements Clearable {
    private static final int NUM_SLOTS = 4;
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

    public IngredientShelfBlockEntity(BlockPos p_155301_, BlockState p_155302_) {
        super(CommonClass.INGREDIENT_SHELF_BLOCK_ENTITY, p_155301_, p_155302_);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void loadAdditional(CompoundTag p_333177_, HolderLookup.Provider p_333564_) {
        super.loadAdditional(p_333177_, p_333564_);
        this.items.clear();
        ContainerHelper.loadAllItems(p_333177_, this.items, p_333564_);
    }

    @Override
    protected void saveAdditional(CompoundTag p_187486_, HolderLookup.Provider p_336279_) {
        super.saveAdditional(p_187486_, p_336279_);
        ContainerHelper.saveAllItems(p_187486_, this.items, true, p_336279_);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider p_329092_) {
        CompoundTag compoundtag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundtag, this.items, true, p_329092_);
        return compoundtag;
    }

    public boolean placeAll(LivingEntity entity, ItemStack stack) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (itemstack.isEmpty()) {
                this.items.set(i, stack.consumeAndReturn(stack.getCount(), entity));
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.level.playSound(null, this.getBlockPos(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    public boolean takeAll(LivingEntity entity) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty()) {
                ((Player)entity).addItem(this.items.get(i).copy());
                this.items.set(i, ItemStack.EMPTY);
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.level.playSound(null, this.getBlockPos(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    public boolean takeItem(LivingEntity entity) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty()) {
                ((Player)entity).addItem(itemstack.consumeAndReturn(1, entity));
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.level.playSound(null, this.getBlockPos(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    public ItemStack removeFirstCorresponding(List<Item> ingredientList, ItemStack duplicate) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty() && ingredientList.contains(itemstack.getItem()) && !duplicate.is(itemstack.getItem())) {
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(null, this.getBlockState()));
                this.markUpdated();
                return itemstack.consumeAndReturn(1, null);
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack removeItem(int i) {
        ItemStack itemstack = this.items.get(i);
        if (!itemstack.isEmpty()) {
            this.items.set(i, ItemStack.EMPTY);
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(null, this.getBlockState()));
            this.markUpdated();
        }

        return itemstack;
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput p_333862_) {
        super.applyImplicitComponents(p_333862_);
        p_333862_.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder p_333455_) {
        super.collectImplicitComponents(p_333455_);
        p_333455_.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag p_331425_) {
        p_331425_.remove("Items");
    }
}