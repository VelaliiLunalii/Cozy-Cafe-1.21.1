package io.github.velaliilunalii.cozy_cafe.entity;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

import static io.github.velaliilunalii.cozy_cafe.item.ColoredItem.setColor;

public class DyeVatEntity extends Entity {
    private static final EntityDataAccessor<ItemStack> DATA_INPUT =
            SynchedEntityData.defineId(DyeVatEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_OUTPUT =
            SynchedEntityData.defineId(DyeVatEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_COLOR =
            SynchedEntityData.defineId(DyeVatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_PROGRESS =
            SynchedEntityData.defineId(DyeVatEntity.class, EntityDataSerializers.INT);

    public static final int MAX_PROGRESS = 40;

    public DyeVatEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public DyeVatEntity(EntityType<?> entityType, Level level, Vec3 pos, float angle, ColoredItem.Color color) {
        this(entityType, level);
        this.setPos(pos);
        this.setYRot(angle);
        this.setDataColor(color);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_INPUT, ItemStack.EMPTY);
        builder.define(DATA_OUTPUT, ItemStack.EMPTY);
        builder.define(DATA_COLOR, 0);
        builder.define(DATA_PROGRESS, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.xOld = this.getX();
            this.yOld = this.getY();
            this.zOld = this.getZ();

            Vec3 motion = this.getDeltaMovement();

            if (!this.isNoGravity()) {
                motion = motion.add(0.0, -0.04, 0.0);
            }

            this.setDeltaMovement(motion);
            this.move(MoverType.SELF, this.getDeltaMovement());

            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 0.0, 0.9));
            }

            if(getProgress()>0){
                incrementProgress(-1);
                if (this.level() instanceof ServerLevel serverLevel && this.level().getGameTime() % 10 == 0) {
                    serverLevel.sendParticles(new DustParticleOptions(getRGBVector(getDataColor()).toVector3f(), 1.0F),
                            this.getX(), this.getY() + 1.1, this.getZ(), 1, 0.1, 0.1, 0.1, 0.05
                    );
                }
            }
            else if(!getInputItem().isEmpty()){
                if(getInputItem().is(ItemTags.WOOL) || getInputItem().is(ItemTags.WOOL_CARPETS)){
                    Optional<Item> item = getInputItem().is(ItemTags.WOOL) ? Dyeable.getWool(this.getDataColor()) :
                            Dyeable.getCarpet(this.getDataColor());
                    if (item.isPresent()){
                        int count = getOutputItem().is(item.get()) ? getOutputItem().getCount() + 1 : 1;
                        if (getOutputItem().isEmpty() || (getOutputItem().is(item.get()) && getOutputItem().getCount() < 64)){
                            setOutputItem(new ItemStack(item.get(), count));
                            int newCount = getInputItem().getCount() - 1;
                            if (newCount <= 0) setInputItem(ItemStack.EMPTY);
                            else setInputItem(new ItemStack(getInputItem().getItem(), getInputItem().getCount() - 1));
                            setProgress(MAX_PROGRESS);
                        }
                    }
                }
                if(getInputItem().getItem() instanceof ColoredItem){
                    int count = getOutputItem().is(getInputItem().getItem()) ? getOutputItem().getCount() + 1 : 1;
                    if (getOutputItem().isEmpty() || (getOutputItem().is(getInputItem().getItem()) && getOutputItem().getCount() < 64)){
                        ItemStack newStack = new ItemStack(getInputItem().getItem(), count);
                        setColor(newStack, this.getDataColor());
                        newStack.set(DataComponents.ITEM_NAME, getInputItem().get(DataComponents.ITEM_NAME));
                        newStack.set(DataComponents.FOOD, getInputItem().get(DataComponents.FOOD));
                        setOutputItem(newStack);
                        int newCount = getInputItem().getCount() - 1;
                        if (newCount <= 0) setInputItem(ItemStack.EMPTY);
                        else setInputItem(getInputItem().consumeAndReturn(newCount, null));
                        setProgress(MAX_PROGRESS);
                    }
                }
            }
        }
    }

    public static Vec3 getRGBVector(ColoredItem.Color color) {
        return switch (color) {
            case BLACK -> new Vec3(25, 25, 25).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case BLUE -> new Vec3(51, 76, 200).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case BROWN -> new Vec3(102, 76, 51).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case CYAN -> new Vec3(76, 153, 200).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case GRAY -> new Vec3(76, 76, 76).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case GREEN -> new Vec3(102, 175, 51).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case LIGHT_BLUE -> new Vec3(102, 153, 216).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case LIGHT_GRAY -> new Vec3(153, 153, 153).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case LIME -> new Vec3(127, 204, 25).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case MAGENTA -> new Vec3(178, 76, 216).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case ORANGE -> new Vec3(216, 127, 51).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case PINK -> new Vec3(242, 127, 165).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case PURPLE -> new Vec3(150, 63, 200).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case RED -> new Vec3(200, 51, 51).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case WHITE -> new Vec3(242, 242, 242).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            case YELLOW -> new Vec3(229, 229, 51).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
            default -> new Vec3(82, 82, 82).multiply((double) 1 /255, (double) 1 /255, (double) 1 /255);
        };
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(0.5F, 1F);
    }

    public ItemStack getInputItem() {
        return this.entityData.get(DATA_INPUT);
    }

    public void setInputItem(ItemStack stack) {
        this.entityData.set(DATA_INPUT, stack.copy());
    }

    public ItemStack getOutputItem() {
        return this.entityData.get(DATA_OUTPUT);
    }

    public void setOutputItem(ItemStack stack) {
        this.entityData.set(DATA_OUTPUT, stack.copy());
    }

    public ColoredItem.Color getDataColor() {
        int intColor = this.entityData.get(DATA_COLOR);
        if (intColor < 0 || intColor >= ColoredItem.Color.values().length) {
            return ColoredItem.Color.DEFAULT;
        }
        return ColoredItem.Color.values()[intColor];
    }

    public void setDataColor(ColoredItem.Color color) {
        this.entityData.set(DATA_COLOR, color.ordinal());
    }

    public int getProgress() {
        return this.entityData.get(DATA_PROGRESS);
    }
    public void setProgress(int progress) {
        this.entityData.set(DATA_PROGRESS, progress);
    }
    public void incrementProgress(int increment) {
        this.entityData.set(DATA_PROGRESS, this.entityData.get(DATA_PROGRESS) + increment);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Input", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Input"))
                    .orElse(ItemStack.EMPTY);
            this.setInputItem(stack);
        }
        if (tag.contains("Output", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Output"))
                    .orElse(ItemStack.EMPTY);
            this.setInputItem(stack);
        }
        if (tag.contains("Color", 8)) {
            String colorName = tag.getString("Color");
            try {
                ColoredItem.Color color = ColoredItem.Color.valueOf(colorName);
                this.setDataColor(color);
            } catch (IllegalArgumentException e) {
                this.setDataColor(ColoredItem.Color.DEFAULT);
            }
        }
        if(tag.contains("Progress", 8)){
            int progress = tag.getInt("Progress");
            this.setProgress(progress);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!this.getInputItem().isEmpty()) {
            tag.put("Input", this.getInputItem().save(this.registryAccess()));
        }
        if (!this.getInputItem().isEmpty()) {
            tag.put("Output", this.getInputItem().save(this.registryAccess()));
        }
        tag.putString("Color", this.getDataColor().name());
        tag.putInt("Progress", this.getProgress());
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isRemoved();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean skipAttackInteraction(Entity attacker) {
        if (attacker instanceof Player player) {
            return !this.level().mayInteract(player, this.blockPosition());
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        remove(source);
        return false;
    }

    public boolean remove(DamageSource source){
        if (this.isInvulnerableTo(source) || this.isRemoved()) {
            return false;
        }

        if (!this.level().isClientSide) {
            this.playSound(SoundEvents.METAL_BREAK, 0.5F, 1.0F);

            ItemStack inputItem = this.getInputItem();
            if (!inputItem.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(
                        this.level(),
                        this.getX(),
                        this.getY() + 0.2,
                        this.getZ(),
                        inputItem.copy()
                );
                itemEntity.setDefaultPickUpDelay();
                this.level().addFreshEntity(itemEntity);
            }
            ItemStack outputItem = this.getOutputItem();
            if (!outputItem.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(
                        this.level(),
                        this.getX(),
                        this.getY() + 0.2,
                        this.getZ(),
                        outputItem.copy()
                );
                itemEntity.setDefaultPickUpDelay();
                this.level().addFreshEntity(itemEntity);
            }


            this.dropDyeVatItem();

            this.markHurt();
            this.discard();
        }

        return true;
    }

    public void dropDyeVatItem() {
        ItemStack dyeVatStack = new ItemStack(CommonClass.DYE_VAT_ITEM);
        setColor(dyeVatStack, this.getDataColor());
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), dyeVatStack);
        itemEntity.setDefaultPickUpDelay();
        this.level().addFreshEntity(itemEntity);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        ItemStack inputItem = this.getInputItem();
        ItemStack outputItem = this.getOutputItem();

        if (!this.level().isClientSide) {

            Optional<ColoredItem.Color> optionalColor = ColoredItem.Color.getColorFromValues(handStack);
            if(optionalColor.isPresent() && !optionalColor.get().equals(ColoredItem.Color.DEFAULT)){
                ColoredItem.Color color = optionalColor.get();
                ColoredItem.Color current = getDataColor();
                if(!current.equals(color)) {
                    setDataColor(color);
                    if (!player.isCreative()) {
                        handStack.shrink(1);
                    }
                }
            }else if(handStack.isEmpty() && !outputItem.isEmpty()){
                player.addItem(outputItem.copy());
                this.setOutputItem(ItemStack.EMPTY);

                this.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else if (handStack.isEmpty() && !inputItem.isEmpty()) {
                player.addItem(inputItem.copy());
                this.setInputItem(ItemStack.EMPTY);

                this.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }if (!handStack.isEmpty() && (handStack.is(ItemTags.WOOL) ||
                    handStack.is(ItemTags.WOOL_CARPETS) || handStack.getItem() instanceof ColoredItem)) {
                ItemStack temp = inputItem.copy();
                ItemStack toInput = handStack.copy();
                this.setInputItem(toInput);
                this.setProgress(MAX_PROGRESS);

                if (!player.isCreative()) {
                    handStack.shrink(toInput.getCount());
                }

                player.addItem(temp);

                this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }else if(handStack.isEmpty() && outputItem.isEmpty() && inputItem.isEmpty()){
                setDataColor(ColoredItem.Color.DEFAULT);
            }
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public void push(double x, double y, double z) {}

    @Override
    protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {}

    public enum Dyeable{
        BLACK(ColoredItem.Color.BLACK, Items.BLACK_WOOL, Items.BLACK_CARPET),
        BLUE(ColoredItem.Color.BLUE, Items.BLUE_WOOL, Items.BLUE_CARPET),
        BROWN(ColoredItem.Color.BROWN, Items.BROWN_WOOL, Items.BROWN_CARPET),
        CYAN(ColoredItem.Color.CYAN, Items.CYAN_WOOL, Items.CYAN_CARPET),
        GRAY(ColoredItem.Color.GRAY, Items.GRAY_WOOL, Items.GRAY_CARPET),
        GREEN(ColoredItem.Color.GREEN, Items.GREEN_WOOL, Items.GREEN_CARPET),
        LIGHT_BLUE(ColoredItem.Color.LIGHT_BLUE, Items.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_CARPET),
        LIGHT_GRAY(ColoredItem.Color.LIGHT_GRAY, Items.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_CARPET),
        LIME(ColoredItem.Color.LIME, Items.LIME_WOOL, Items.LIME_CARPET),
        MAGENTA(ColoredItem.Color.MAGENTA, Items.MAGENTA_WOOL, Items.MAGENTA_CARPET),
        ORANGE(ColoredItem.Color.ORANGE, Items.ORANGE_WOOL, Items.ORANGE_CARPET),
        PINK(ColoredItem.Color.PINK, Items.PINK_WOOL, Items.PINK_CARPET),
        PURPLE(ColoredItem.Color.PURPLE, Items.PURPLE_WOOL, Items.PURPLE_CARPET),
        RED(ColoredItem.Color.RED, Items.RED_WOOL, Items.RED_CARPET),
        WHITE(ColoredItem.Color.WHITE, Items.WHITE_WOOL, Items.WHITE_CARPET),
        YELLOW(ColoredItem.Color.YELLOW, Items.YELLOW_WOOL, Items.YELLOW_CARPET);

        private final ColoredItem.Color color;
        private final Item wool;
        private final Item carpet;

        Dyeable(ColoredItem.Color color, Item wool, Item carpet){
            this.color = color;
            this.wool = wool;
            this.carpet = carpet;
        }

        public static Optional<Item> getWool(ColoredItem.Color color){
            for(Dyeable dyeable : values()){
                if(color.equals(dyeable.color)) return Optional.of(dyeable.wool);
            }
            return Optional.empty();
        }

        public static Optional<Item> getCarpet(ColoredItem.Color color){
            for(Dyeable dyeable : values()){
                if(color.equals(dyeable.color)) return Optional.of(dyeable.carpet);
            }
            return Optional.empty();
        }
    }
}