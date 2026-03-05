package io.github.velaliilunalii.cozy_cafe.entity;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

import static io.github.velaliilunalii.cozy_cafe.item.ColoredItem.setColor;

public class JuicerEntity extends Entity {
    private static final EntityDataAccessor<ItemStack> DATA_EMPTY_BOTTLE =
            SynchedEntityData.defineId(JuicerEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_FILLED_BOTTLE =
            SynchedEntityData.defineId(JuicerEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_INGREDIENT =
            SynchedEntityData.defineId(JuicerEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> DATA_IS_JUICING =
            SynchedEntityData.defineId(JuicerEntity.class, EntityDataSerializers.BOOLEAN);

    private int useCooldown = 0;
    private int juicingProgress = 0;
    private int juicingSuccess = 50;
    private int soundCooldown = 0;

    public static final int MAX_JUICING_PROGRESS = 40;

    public JuicerEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public JuicerEntity(EntityType<?> entityType, Level level, Vec3 pos, float angle) {
        this(entityType, level);
        this.setPos(pos);
        this.setYRot(angle);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(4.0F / 16.0F, 1.0F / 16.0F);
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
    public void push(double x, double y, double z) {}

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    //---------------------------------------------------DATA-----------------------------------------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_EMPTY_BOTTLE, ItemStack.EMPTY);
        builder.define(DATA_FILLED_BOTTLE, ItemStack.EMPTY);
        builder.define(DATA_INGREDIENT, ItemStack.EMPTY);
        builder.define(DATA_IS_JUICING, false);
    }

    public boolean isJuicing() {
        return this.entityData.get(DATA_IS_JUICING);
    }
    private void setJuicing(boolean juicing) {
        this.entityData.set(DATA_IS_JUICING, juicing);
    }

    public ItemStack getEmptyBottle() {
        return this.entityData.get(DATA_EMPTY_BOTTLE);
    }
    public void setEmptyBottle(ItemStack stack) {
        this.entityData.set(DATA_EMPTY_BOTTLE, stack.copy());
    }
    public ItemStack getFilledBottle() {
        return this.entityData.get(DATA_FILLED_BOTTLE);
    }
    public void setFilledBottle(ItemStack stack) {
        this.entityData.set(DATA_FILLED_BOTTLE, stack.copy());
    }
    public ItemStack getIngredient() {
        return this.entityData.get(DATA_INGREDIENT);
    }
    public void setIngredient(ItemStack stack) {
        this.entityData.set(DATA_INGREDIENT, stack.copy());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("EmptyBottle", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("EmptyBottle"))
                    .orElse(ItemStack.EMPTY);
            this.setEmptyBottle(stack);
        }
        if (tag.contains("FilledBottle", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("FilledBottle"))
                    .orElse(ItemStack.EMPTY);
            this.setFilledBottle(stack);
        }
        if (tag.contains("Ingredient", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Ingredient"))
                    .orElse(ItemStack.EMPTY);
            this.setIngredient(stack);
        }
        if (tag.contains("JuicingProgress")) {
            this.juicingProgress = tag.getInt("JuicingProgress");
        }
        if (tag.contains("UseCooldown")) {
            this.useCooldown = tag.getInt("UseCooldown");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!this.getEmptyBottle().isEmpty()) {
            tag.put("EmptyBottle", this.getEmptyBottle().save(this.registryAccess()));
        }
        if (!this.getFilledBottle().isEmpty()) {
            tag.put("FilledBottle", this.getFilledBottle().save(this.registryAccess()));
        }
        if (!this.getIngredient().isEmpty()) {
            tag.put("Ingredient", this.getIngredient().save(this.registryAccess()));
        }

        tag.putInt("JuicingProgress", this.juicingProgress);
        tag.putInt("UseCooldown", this.useCooldown);
    }

    //------------------------------------------------------------------------------------------------------------------

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

            if(isJuicing()){
                if(getEmptyBottle().isEmpty() || getIngredient().isEmpty() ||
                        (!getFilledBottle().isEmpty() && !Juice.isIngredientAndOutput(getIngredient(), getFilledBottle()))
                        || getFilledBottle().getCount() >= 64){
                    setJuicing(false);
                    juicingProgress = MAX_JUICING_PROGRESS;
                }
                else {
                    if(juicingProgress > 0) juicingProgress--;
                    else {
                        juicingProgress = MAX_JUICING_PROGRESS;
                        Optional<Item> optionalItem = Juice.getOutput(getIngredient());
                        if (optionalItem.isPresent()){
                            int count = getFilledBottle().isEmpty() ? 0 : getFilledBottle().getCount();
                            if(count < 64){
                                ItemStack newStack = new ItemStack(optionalItem.get(), count + 1);
                                setFilledBottle(newStack);

                                int newEmptyBottleCount = getEmptyBottle().getCount() - 1;
                                if (newEmptyBottleCount <= 0) setEmptyBottle(ItemStack.EMPTY);
                                else setEmptyBottle(getEmptyBottle().consumeAndReturn(newEmptyBottleCount, null));

                                int newIngredientCount = getIngredient().getCount() - 1;
                                if (newIngredientCount <= 0) setIngredient(ItemStack.EMPTY);
                                else setIngredient(getIngredient().consumeAndReturn(newIngredientCount, null));

                                juicingProgress = MAX_JUICING_PROGRESS;
                            }
                        }
                    }
                }
            }
        }
    }

    private void empty(boolean playSound){
        ItemStack emptyBottle = this.getEmptyBottle();
        if (!emptyBottle.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    emptyBottle.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setEmptyBottle(ItemStack.EMPTY);
        }
        ItemStack filledBottle = this.getFilledBottle();
        if (!filledBottle.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    filledBottle.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setFilledBottle(ItemStack.EMPTY);
        }
        ItemStack ingredient = this.getIngredient();
        if (!ingredient.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    ingredient.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setIngredient(ItemStack.EMPTY);
        }

        if (playSound) level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 1F, 0.4F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.remove(source);
        return true;
    }

    public boolean remove(DamageSource source){
        if (this.isInvulnerableTo(source) || this.isRemoved()) {
            return false;
        }

        if (!this.level().isClientSide) {
            empty(false);
            this.dropJuicerItem();

            this.markHurt();
            this.discard();
        }

        return true;
    }

    public void dropJuicerItem() {
        ItemStack juicerItem = new ItemStack(CommonClass.JUICER_ITEM);
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), juicerItem);
        itemEntity.setDefaultPickUpDelay();
        this.level().addFreshEntity(itemEntity);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        ItemStack emptyBottle = this.getEmptyBottle();
        ItemStack filledBottle = this.getFilledBottle();
        ItemStack ingredient = this.getIngredient();

        if (!this.level().isClientSide) {
            if(player.isCrouching()){
                if (!filledBottle.isEmpty()) {
                    ItemUtil.smartAddItem(player, filledBottle.copy());
                    setFilledBottle(ItemStack.EMPTY);
                }else if(!ingredient.isEmpty()){
                    ItemUtil.smartAddItem(player, ingredient.copy());
                    setIngredient(ItemStack.EMPTY);
                }else if(!emptyBottle.isEmpty()){
                    ItemUtil.smartAddItem(player, emptyBottle.copy());
                    setEmptyBottle(ItemStack.EMPTY);
                }
            }else {
                if(handStack.is(CommonClass.EMPTY_BOTTLE) && emptyBottle.isEmpty()){
                    ItemStack toInput = handStack.copy();
                    this.setEmptyBottle(toInput);

                    if (!player.isCreative()) {
                        handStack.shrink(toInput.getCount());
                    }
                    this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
                }else if(Juice.isIngredient(handStack) && ingredient.isEmpty()){
                    ItemStack toInput = handStack.copy();
                    this.setIngredient(toInput);

                    if (!player.isCreative()) {
                        handStack.shrink(toInput.getCount());
                    }
                    this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
                }else {
                    this.juicingProgress = 40;
                    this.setJuicing(true);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public enum Juice {
        COCONUT_MILK(CommonClass.COCONUT_ITEM, CommonClass.COCONUT_MILK_BOTTLE),
        CHERRY_JUICE(Items.CHERRY_LEAVES, CommonClass.CHERRY_JUICE),
        BAMBOO_MILK(Items.BAMBOO, CommonClass.BAMBOO_MILK),
        CHORUS_JUICE(Items.CHORUS_FRUIT, CommonClass.CHORUS_JUICE),
        GLOW_BERRIES_JUICE(Items.GLOW_BERRIES, CommonClass.GLOW_BERRIES_JUICE),
        SCULK_JUICE(Items.SCULK, CommonClass.SCULK_JUICE),
        PUMPKIN_JUICE(Items.PUMPKIN, CommonClass.PUMPKIN_JUICE),
        MELON_JUICE(Items.MELON_SLICE, CommonClass.MELON_JUICE),
        SWEET_BERRIES_JUICE(Items.SWEET_BERRIES, CommonClass.SWEET_BERRIES_JUICE),
        APPLE_JUICE(Items.APPLE, CommonClass.APPLE_JUICE),
        SPORE_BLOSSOM_JUICE(Items.SPORE_BLOSSOM, CommonClass.SPORE_BLOSSOM_JUICE);

        private final Item ingredient;
        private final Item output;

        Juice(Item input, Item output){
            this.ingredient = input;
            this.output = output;
        }

        public static boolean isIngredient(ItemStack stack){
            for(Juice juice : values()){
                if(stack.is(juice.ingredient)) return true;
            }
            return false;
        }

        public static boolean isIngredientAndOutput(ItemStack ingredient, ItemStack output){
            for(Juice juice : values()){
                if(ingredient.is(juice.ingredient) && output.is(juice.output)) return true;
            }
            return false;
        }

        public static Optional<Item> getOutput(ItemStack ingredient){
            for(Juice juice : values()){
                if(ingredient.is(juice.ingredient)) return Optional.of(juice.output);
            }
            return Optional.empty();
        }
    }
}