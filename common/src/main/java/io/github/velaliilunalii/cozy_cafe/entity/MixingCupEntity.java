package io.github.velaliilunalii.cozy_cafe.entity;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.block.entity.IngredientShelfBlockEntity;
import io.github.velaliilunalii.cozy_cafe.food.DrinkFoodPropertiesBuilder;
import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import io.github.velaliilunalii.cozy_cafe.item.DrinkItem;
import io.github.velaliilunalii.cozy_cafe.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

import static io.github.velaliilunalii.cozy_cafe.food.DrinkFoodPropertiesBuilder.*;
import static io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient.*;
import static io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient.ContainerType.getContainerType;

public class MixingCupEntity extends Entity {
    private static final EntityDataAccessor<ItemStack> DATA_LIQUID =
            SynchedEntityData.defineId(MixingCupEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_SWEETENER =
            SynchedEntityData.defineId(MixingCupEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_FLAVOR_1 =
            SynchedEntityData.defineId(MixingCupEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_FLAVOR_2 =
            SynchedEntityData.defineId(MixingCupEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> DATA_IS_MIXING =
            SynchedEntityData.defineId(MixingCupEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<DrinkItem.DrinkItemIngredients> DATA_INGREDIENTS =
            SynchedEntityData.defineId(MixingCupEntity.class, CommonClass.DRINK_INGREDIENTS_SERIALIZER);
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    private int lerpSteps;

    private int useCooldown = 0;
    private int mixingProgress = 0;
    private int mixingSuccess = 50;
    private boolean complete = false;
    private int soundCooldown = 0;

    public MixingCupEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public MixingCupEntity(EntityType<?> entityType, Level level, Vec3 pos, float angle) {
        this(entityType, level);
        this.setPos(pos);
        this.setYRot(angle);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpSteps = steps;
    }

    @Override
    protected void lerpPositionAndRotationStep(int steps, double x, double y, double z, double yRot, double xRot) {
        double invSteps = 1.0 / (double) steps;

        double newX = Mth.lerp(invSteps, this.getX(), x);
        double newY = Mth.lerp(invSteps, this.getY(), y);
        double newZ = Mth.lerp(invSteps, this.getZ(), z);

        float newYRot = (float) Mth.rotLerp(invSteps, this.getYRot(), yRot);
        float newXRot = (float) Mth.lerp(invSteps, this.getXRot(), xRot);

        this.setPos(newX, newY, newZ);
        this.setRot(newYRot, newXRot);
    }

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
        builder.define(DATA_INGREDIENTS, new DrinkItem.DrinkItemIngredients());
        builder.define(DATA_LIQUID, ItemStack.EMPTY);
        builder.define(DATA_SWEETENER, ItemStack.EMPTY);
        builder.define(DATA_FLAVOR_1, ItemStack.EMPTY);
        builder.define(DATA_FLAVOR_2, ItemStack.EMPTY);
        builder.define(DATA_IS_MIXING, false);
    }

    public DrinkItem.DrinkItemIngredients getIngredients() {
        return this.entityData.get(DATA_INGREDIENTS);
    }

    public void setIngredients(DrinkItem.DrinkItemIngredients ingredients) {
        this.entityData.set(DATA_INGREDIENTS, ingredients.copy());
    }

    public boolean isMixing() {
        return this.entityData.get(DATA_IS_MIXING);
    }
    private void setMixing(boolean mixing) {
        this.entityData.set(DATA_IS_MIXING, mixing);
    }

    public ItemStack getLiquid() {
        return this.entityData.get(DATA_LIQUID);
    }
    public void setLiquid(ItemStack stack) {
        this.entityData.set(DATA_LIQUID, stack.copy());
    }
    public ItemStack getSweetener() {
        return this.entityData.get(DATA_SWEETENER);
    }
    public void setSweetener(ItemStack stack) {
        this.entityData.set(DATA_SWEETENER, stack.copy());
    }
    public ItemStack getFlavor1() {
        return this.entityData.get(DATA_FLAVOR_1);
    }
    public void setFlavor1(ItemStack stack) {
        this.entityData.set(DATA_FLAVOR_1, stack.copy());
    }
    public ItemStack getFlavor2() {
        return this.entityData.get(DATA_FLAVOR_2);
    }
    public void setFlavor2(ItemStack stack) {
        this.entityData.set(DATA_FLAVOR_2, stack.copy());
    }

//    public void setLiquid(DrinkIngredient liquid) {
//        DrinkItem.DrinkItemIngredients current = getIngredients();
//        current.setLiquid(liquid);
//        setIngredients(current);
//    }
//
//    public DrinkIngredient getLiquid() {
//        return getIngredients().getLiquid();
//    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Liquid", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Liquid"))
                    .orElse(ItemStack.EMPTY);
            this.setLiquid(stack);
        }
        if (tag.contains("Sweetener", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Sweetener"))
                    .orElse(ItemStack.EMPTY);
            this.setSweetener(stack);
        }
        if (tag.contains("Flavor_1", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Flavor_1"))
                    .orElse(ItemStack.EMPTY);
            this.setFlavor1(stack);
        }
        if (tag.contains("Flavor_2", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Flavor_2"))
                    .orElse(ItemStack.EMPTY);
            this.setFlavor2(stack);
        }
        if (tag.contains("MixingProgress")) {
            this.mixingProgress = tag.getInt("MixingProgress");
        }
        if (tag.contains("UseCooldown")) {
            this.useCooldown = tag.getInt("UseCooldown");
        }

        if (tag.contains("Ingredients")) {
            CompoundTag ingredientsTag = tag.getCompound("Ingredients");
            DrinkItem.DrinkItemIngredients ingredients = new DrinkItem.DrinkItemIngredients();

            if (ingredientsTag.contains("Liquid")) {
                ingredients.setLiquid(DrinkIngredient.valueOf(ingredientsTag.getString("Liquid")));
            }
            if (ingredientsTag.contains("Sweetener")) {
                ingredients.setSweetener(DrinkIngredient.valueOf(ingredientsTag.getString("Sweetener")));
            }
            if (ingredientsTag.contains("MainFlavor")) {
                ingredients.setMainFlavor(DrinkIngredient.valueOf(ingredientsTag.getString("MainFlavor")));
            }
            if (ingredientsTag.contains("SecondaryFlavor")) {
                ingredients.setSecondaryFlavor(DrinkIngredient.valueOf(ingredientsTag.getString("SecondaryFlavor")));
            }

            setIngredients(ingredients);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!this.getLiquid().isEmpty()) {
            tag.put("Liquid", this.getLiquid().save(this.registryAccess()));
        }
        if (!this.getSweetener().isEmpty()) {
            tag.put("Sweetener", this.getSweetener().save(this.registryAccess()));
        }
        if (!this.getFlavor1().isEmpty()) {
            tag.put("Flavor_1", this.getFlavor1().save(this.registryAccess()));
        }
        if (!this.getFlavor2().isEmpty()) {
            tag.put("Flavor_2", this.getFlavor2().save(this.registryAccess()));
        }

        tag.putInt("MixingProgress", this.mixingProgress);
        tag.putInt("UseCooldown", this.useCooldown);

        DrinkItem.DrinkItemIngredients ingredients = getIngredients();
        CompoundTag ingredientsTag = new CompoundTag();

        if (ingredients.getLiquid() != null) {
            ingredientsTag.putString("Liquid", ingredients.getLiquid().name());
        }
        if (ingredients.getSweetener() != null) {
            ingredientsTag.putString("Sweetener", ingredients.getSweetener().name());
        }
        if (ingredients.getMainFlavor() != null) {
            ingredientsTag.putString("MainFlavor", ingredients.getMainFlavor().name());
        }
        if (ingredients.getSecondaryFlavor() != null) {
            ingredientsTag.putString("SecondaryFlavor", ingredients.getSecondaryFlavor().name());
        }

        tag.put("Ingredients", ingredientsTag);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(
                    this.lerpSteps,
                    this.lerpX,
                    this.lerpY,
                    this.lerpZ,
                    this.lerpYRot,
                    this.lerpXRot
            );
            this.lerpSteps--;
        }

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

            boolean wasMixing = this.isMixing();

            if(this.useCooldown >= 0){
                this.useCooldown--;
            }else {
                if ((this.mixingProgress >= mixingSuccess && mixingProgress <= mixingSuccess + 10)
                        || (this.mixingProgress >= mixingSuccess - 20 && mixingProgress <= mixingSuccess && this.random.nextFloat() <= 0.5)){
                    level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 1F, 1F);
                    complete = true;
                }
                if (mixingProgress > mixingSuccess + 10) mixingFail(true);
                this.mixingProgress = 0;
            }
            if(this.mixingProgress == 1){
                this.mixingSuccess = this.random.nextInt(21) + 30;
            }

            boolean isNowMixing = this.mixingProgress > 0;
            if (wasMixing != isNowMixing) {
                this.setMixing(isNowMixing);
            }

            if(soundCooldown > 0) soundCooldown --;
            else if(this.isMixing()){
                soundCooldown = 25 + random.nextInt(16);
                level().playSound(null, this.getX(), this.getY(), this.getZ(), CommonClass.MIXING, SoundSource.PLAYERS, 1F, random.nextFloat() * 0.2F + 0.9F);
            }
        }
    }

    private void mixingFail(boolean playSound){
        ItemStack liquid = this.getLiquid();
        if (!liquid.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    liquid.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setLiquid(ItemStack.EMPTY);
        }
        ItemStack sweetener = this.getSweetener();
        if (!sweetener.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    sweetener.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setSweetener(ItemStack.EMPTY);
        }
        ItemStack flavor_1 = this.getFlavor1();
        if (!flavor_1.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    flavor_1.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setFlavor1(ItemStack.EMPTY);
        }
        ItemStack flavor_2 = this.getFlavor2();
        if (!flavor_2.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(
                    this.level(),
                    this.getX(),
                    this.getY() + 0.2,
                    this.getZ(),
                    flavor_2.copy()
            );
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.setFlavor2(ItemStack.EMPTY);
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
            mixingFail(false);
            this.dropMixingCupItem();

            this.markHurt();
            this.discard();
        }

        return true;
    }

    private void emptyCup() {
        if (!this.level().isClientSide) {
            ItemStack liquid = this.getLiquid();
            ItemStack sweetener = this.getSweetener();
            ItemStack flavor_1 = this.getFlavor1();
            ItemStack flavor_2 = this.getFlavor2();

            if (!liquid.isEmpty()) this.setLiquid(ItemStack.EMPTY);
            if (!sweetener.isEmpty()) this.setSweetener(ItemStack.EMPTY);
            if (!flavor_1.isEmpty()) this.setFlavor1(ItemStack.EMPTY);
            if (!flavor_2.isEmpty()) this.setFlavor2(ItemStack.EMPTY);

            this.complete = false;
        }
    }

    public void dropMixingCupItem() {
        ItemStack mixing_cup = new ItemStack(CommonClass.MIXING_CUP_ITEM);
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), mixing_cup);
        itemEntity.setDefaultPickUpDelay();
        this.level().addFreshEntity(itemEntity);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        ItemStack liquid = this.getLiquid();
        ItemStack sweetener = this.getSweetener();
        ItemStack flavor_1 = this.getFlavor1();
        ItemStack flavor_2 = this.getFlavor2();
        boolean isLiquid = DrinkIngredient.isLiquid(handStack);
        boolean isSweetener = DrinkIngredient.isSweetener(handStack);
        boolean isFlavor = DrinkIngredient.isFlavor(handStack);

        if (!this.level().isClientSide) {
            if(!this.complete) {
                if (player.isShiftKeyDown()) {
                    mixingFail(false);
                    return InteractionResult.SUCCESS;
                } else if (!handStack.isEmpty()) {
                    if (isLiquid && liquid.isEmpty()) {
                        ItemStack toAdd = handStack.copy();
                        toAdd.setCount(1);
                        this.setLiquid(toAdd);
                        if (!player.isCreative()) handStack.shrink(1);
                        this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                    }
                    if (isSweetener && sweetener.isEmpty()) {
                        ItemStack toAdd = handStack.copy();
                        toAdd.setCount(1);
                        this.setSweetener(toAdd);
                        if (!player.isCreative()) handStack.shrink(1);
                        this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                    }
                    if (isFlavor) {
                        ItemStack toAdd = handStack.copy();
                        toAdd.setCount(1);
                        if (flavor_1.isEmpty() && (!toAdd.is(this.getFlavor2().getItem()) || (toAdd.is(Items.POTION) &&
                                !toAdd.get(DataComponents.POTION_CONTENTS).equals(this.getFlavor2().get(DataComponents.POTION_CONTENTS))))) {
                            this.setFlavor1(toAdd);
                            if (!player.isCreative()) handStack.shrink(1);
                            this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                        }
                        else if (flavor_2.isEmpty() && (!toAdd.is(this.getFlavor1().getItem()) || (toAdd.is(Items.POTION) &&
                                !toAdd.get(DataComponents.POTION_CONTENTS).equals(this.getFlavor1().get(DataComponents.POTION_CONTENTS))))) {
                            this.setFlavor2(toAdd);
                            if (!player.isCreative()) handStack.shrink(1);
                            this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                        }
                    }
                } else {
                    if (liquid.isEmpty() || sweetener.isEmpty() || flavor_1.isEmpty() || flavor_2.isEmpty()) {
                        BlockPos startPos = this.blockPosition().offset(-2, -2, -2);
                        BlockPos endPos = this.blockPosition().offset(2, 2, 2);
                        for (BlockPos pos : BlockPos.betweenClosed(startPos, endPos)) {
                            if (level().getBlockEntity(pos) instanceof IngredientShelfBlockEntity ingredientShelfBlockEntity) {
                                if (liquid.isEmpty()) {
                                    this.setLiquid(ingredientShelfBlockEntity.removeFirstCorresponding(liquidsAsItems(), ItemStack.EMPTY));
                                    liquid = this.getLiquid();
                                    this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                                }
                                if (sweetener.isEmpty()) {
                                    this.setSweetener(ingredientShelfBlockEntity.removeFirstCorresponding(sweetenersAsItems(), ItemStack.EMPTY));
                                    sweetener = this.getSweetener();
                                    this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                                }
                                if (flavor_1.isEmpty()) {
                                    this.setFlavor1(ingredientShelfBlockEntity.removeFirstCorresponding(flavorsAsItems(), flavor_2));
                                    flavor_1 = this.getFlavor1();
                                    this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                                }
                                if (flavor_2.isEmpty()) {
                                    this.setFlavor2(ingredientShelfBlockEntity.removeFirstCorresponding(flavorsAsItems(), flavor_1));
                                    flavor_2 = this.getFlavor2();
                                    this.playSound(SoundEvents.GENERIC_SPLASH, 0.4F, 1.0F);
                                }
                            }
                            if (!liquid.isEmpty() && !sweetener.isEmpty() && !flavor_1.isEmpty() && !flavor_2.isEmpty())
                                break;
                        }
                    }
                }

                if (!this.getLiquid().isEmpty() && !this.getFlavor1().isEmpty()) {
                    this.useCooldown = 5;
                    this.mixingProgress++;

                    this.setMixing(true);

                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(mixingProgress < mixingSuccess - 20 ? ParticleTypes.SPLASH :
                                        mixingProgress < mixingSuccess ? CommonClass.BLUE_MIXING_PARTICLE :
                                                mixingProgress <= mixingSuccess + 10 ? CommonClass.GREEN_MIXING_PARTICLE : CommonClass.RED_MIXING_PARTICLE,
                                this.getX(), this.getY() + 0.5, this.getZ(), 1, 0.1, 0.1, 0.1, 0.05
                        );
                    }
                }
            }else {
                if (!fillGlass(player, handStack) && level() instanceof ServerLevel serverLevel)
                    serverLevel.sendParticles(CommonClass.GREEN_MIXING_PARTICLE, this.getX(), this.getY() + 0.5,
                            this.getZ(), 1, 0.1, 0.1, 0.1, 0.05);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    private boolean fillGlass(Player player, ItemStack handStack){
        Optional<ContainerType> containerType = getContainerType(handStack);
        Optional<DrinkIngredient> mainFlavorIngredient = getDrinkIngredient(getFlavor1());
        Optional<DrinkIngredient> liquidIngredient = getDrinkIngredient(getLiquid());
        Optional<DrinkIngredient> sweetenerIngredient = getDrinkIngredient(getSweetener());
        Optional<DrinkIngredient> secondaryFlavorIngredient = getDrinkIngredient(getFlavor2());

        if(containerType.isEmpty() || mainFlavorIngredient.isEmpty()) return false;
        if(secondaryFlavorIngredient.isPresent() && ContainerType.isSmall(containerType.get())){
            player.displayClientMessage(Component.literal("This container cannot hold 2 flavors"), true);
            return false;
        }
        if((secondaryFlavorIngredient.isPresent() && secondaryFlavorIngredient.get().equals(POTION)
                || mainFlavorIngredient.get().equals(POTION)) &&
                (sweetenerIngredient.isEmpty() || !sweetenerIngredient.get().equals(NETHER_WART))){
            player.displayClientMessage(Component.literal("A nether wart is required as a sweetener to add potions"), true);
            return false;
        }

        ItemStack itemStack = FilledContainer.getFilledContainer(containerType.get(), mainFlavorIngredient.get());
        FoodProperties foodproperties = itemStack.get(DataComponents.FOOD);

        if(foodproperties == null) return false;

        itemStack.set(DataComponents.ITEM_NAME, Component.literal(ContainerType.getName(containerType.get())));
        if(secondaryFlavorIngredient.isPresent() && secondaryFlavorIngredient.get().equals(POTION)){
            addPotionName(itemStack, getFlavor2());
            foodproperties = addPotionProperties(foodproperties, getFlavor2().get(DataComponents.POTION_CONTENTS));
        }
        if(secondaryFlavorIngredient.isPresent() && secondaryFlavorIngredient.get().equals(POTION) && mainFlavorIngredient.get().equals(POTION)){
            itemStack.set(DataComponents.ITEM_NAME,
                    Component.literal("and ")
                            .append(itemStack.get(DataComponents.ITEM_NAME).copy()));
        }
        if(mainFlavorIngredient.isPresent() && mainFlavorIngredient.get().equals(POTION)){
            addPotionName(itemStack, getFlavor1());
            foodproperties = addPotionProperties(foodproperties, getFlavor1().get(DataComponents.POTION_CONTENTS));
        }
        if (mainFlavorIngredient.get().equals(POTION) || (secondaryFlavorIngredient.isPresent() && secondaryFlavorIngredient.get().equals(POTION)))
            addName(itemStack, POTION);
        if(liquidIngredient.isPresent()){
            addName(itemStack, liquidIngredient.get());
            foodproperties = addFoodProperties(foodproperties, getFoodProperties(liquidIngredient.get()));
        }
        if(secondaryFlavorIngredient.isPresent() && !secondaryFlavorIngredient.get().equals(POTION)){
            addName(itemStack, secondaryFlavorIngredient.get());
            foodproperties = addSecondaryFlavorFoodProperties(foodproperties, getFoodProperties(secondaryFlavorIngredient.get()));     //?????????????
        }
        if(mainFlavorIngredient.isPresent() && !mainFlavorIngredient.get().equals(POTION)) {
            addName(itemStack, mainFlavorIngredient.get());
        }
        if(sweetenerIngredient.isPresent()){
            addName(itemStack, sweetenerIngredient.get());
            foodproperties = addFoodProperties(foodproperties, getFoodProperties(sweetenerIngredient.get()));     //?????????????
        }

        itemStack.set(DataComponents.FOOD, foodproperties);
        ColoredItem.setColor(itemStack, ColoredItem.getColor(handStack));

        ItemUtil.smartAddItem(player, itemStack);

        if (!player.isCreative()){
            handStack.consume(1, player);

            giveRemainder(player, liquidIngredient);
            giveRemainder(player, sweetenerIngredient);
            giveRemainder(player, mainFlavorIngredient);
            giveRemainder(player, secondaryFlavorIngredient);

            this.emptyCup();
        }

        level().playSound(null, this.getX(), this.getY(), this.getZ(),
                CommonClass.POURING, SoundSource.PLAYERS, 1F, random.nextFloat() * 0.4F + 0.8F);
        return true;
    }

    public void addName(ItemStack itemStack, DrinkIngredient drinkIngredient){
        itemStack.set(DataComponents.ITEM_NAME,
                Component.literal(DrinkIngredient.getName(drinkIngredient))
                        .append(itemStack.get(DataComponents.ITEM_NAME).copy()));
    }

    public void addPotionName(ItemStack itemStack, ItemStack potion){
        PotionContents contents = potion.get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents.getAllEffects().iterator().hasNext()) {
            MobEffectInstance effect = contents.getAllEffects().iterator().next();
            String effectName = effect.getEffect().value().getDisplayName().getString() + " ";
            itemStack.set(DataComponents.ITEM_NAME,
                    Component.literal(effectName)
                            .append(itemStack.get(DataComponents.ITEM_NAME).copy()));
        }
    }

    public void giveRemainder(Player player, Optional<DrinkIngredient> optionalDrinkIngredient){
        if(optionalDrinkIngredient.isPresent()) {
            DrinkIngredient drinkIngredient = optionalDrinkIngredient.get();
            Optional<Item> optionalItem = DrinkIngredient.getRemainder(drinkIngredient.item());
            if (optionalItem.isPresent() && !optionalItem.get().equals(Items.AIR))
                ItemUtil.smartAddItem(player, new ItemStack(optionalItem.get()));
        }
    }
}