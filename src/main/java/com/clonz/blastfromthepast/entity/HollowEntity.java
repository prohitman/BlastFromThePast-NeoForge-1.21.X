package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.access.PlayerBFTPDataAccess;
import com.clonz.blastfromthepast.client.models.HollowModel;
import com.clonz.blastfromthepast.entity.misc.TransitioningState;
import com.clonz.blastfromthepast.events.CuriosCompat;
import com.clonz.blastfromthepast.init.ModDataSerializers;
import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.util.DebugFlags;
import com.clonz.blastfromthepast.util.EntityHelper;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class HollowEntity extends LivingEntity implements Animatable<HollowModel> {
    public static final DucAnimation ANIMATION = DucAnimation.create(ModEntities.HOLLOW.getId());
    private final Lazy<Map<String, AnimationState>> ANIMATIONS = Lazy.of(() -> HollowModel.createStateMap(getAnimation()));
    private final String ANIMATION_PREFIX = "animation." + ModEntities.HOLLOW.getId().getPath() + ".";
    private static final EntityDataAccessor<TransitioningState> STATE = SynchedEntityData.defineId(HollowEntity.class, ModDataSerializers.TRANSITIONING_STATE.get());
    @Nullable
    private StoredInventory storedInventory;
    @Nullable
    private Player trackedPlayer;
    private int transitionTicks = -1;

    public HollowEntity(EntityType<? extends HollowEntity> hollowEntityEntityType, Level level) {
        super(hollowEntityEntityType, level);
    }

    public static void init() {}

    @Override
    public ResourceLocation getModelLocation() {
        return null;
    }

    @Override
    public DucAnimation getAnimation() {
        return ANIMATION;
    }

    @Override
    public Lazy<Map<String, AnimationState>> getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public Optional<AnimationState> getAnimationState(String animation) {
        return Optional.ofNullable(getAnimations().get().get(ANIMATION_PREFIX + animation));
    }

    public static HollowEntity create(ServerPlayer player) {
        HollowEntity hollow = ModEntities.HOLLOW.get().create(player.level());
        assert hollow != null;
        // TODO: Find a suitable spawn location
        hollow.setPos(player.position());
        BlockPos safePos = EntityHelper.findSafeSpot(hollow);
        if (safePos != null) {
            hollow.setPos(safePos.getX(), safePos.getY(), safePos.getZ());
        }

        if (DebugFlags.DEBUG_HOLLOW) {
            hollow.addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION, 0, true, false));
            //noinspection DataFlowIssue
            player.getServer().getPlayerList().getPlayers().forEach((p) -> p.sendSystemMessage(Component.literal("Hollow created at " + hollow.position() + " for " + player.getName())));
        }
        // Only save horizontal rotation
        hollow.setRot(player.getYRot(), 0);
        hollow.storeInventory(player);
        return hollow;
    }

    public void storeInventory(ServerPlayer player) {
        ListTag inventoryData = new ListTag();
        player.getInventory().save(inventoryData);
        List<ItemStack> additionalItems = BlastFromThePast.CURIOS_LOADED ? CuriosCompat.getCuriosItems(player) : null;
        this.storedInventory = new StoredInventory(player.getUUID(), inventoryData, Optional.ofNullable(additionalItems));
        ((PlayerBFTPDataAccess) player).bftp$markInventoryStored();
    }

    @Override
    public void tick() {
        super.tick();

        setDeltaMovement(0, 0, 0);

        TransitioningState state = getEntityData().get(STATE);

        if (this.level().isClientSide()) {
            this.animateWhen("idle_orbless", state == TransitioningState.INACTIVE || state == TransitioningState.ACTIVE_TO_INACTIVE);
            this.animateWhen("idle", state == TransitioningState.ACTIVE);
            this.animateWhen("orb_spawn", state == TransitioningState.INACTIVE_TO_ACTIVE);
            this.animateWhen("orb_despawn", state == TransitioningState.ACTIVE_TO_INACTIVE);
        }

        if (transitionTicks > 0) {
            transitionTicks--;
        } else if (transitionTicks == 0) {
            if (state.isTransitional()) {
                switch (state) {
                    case INACTIVE_TO_ACTIVE -> {
                        getEntityData().set(STATE, TransitioningState.ACTIVE);
                        transitionTicks = 40;
                    }
                    case ACTIVE_TO_INACTIVE -> getEntityData().set(STATE, TransitioningState.INACTIVE);
                }
            } else {
                transitionTicks = -1;
            }
        } else if (this.storedInventory != null) {
            Player owner = getOwnerNearby();
            if (state == TransitioningState.INACTIVE && owner != null) {
                this.trackedPlayer = owner;
                getEntityData().set(STATE, TransitioningState.INACTIVE_TO_ACTIVE);
                transitionTicks = 20;
            }
            else if (state == TransitioningState.ACTIVE && owner == null) {
                this.trackedPlayer = null;
                getEntityData().set(STATE, TransitioningState.ACTIVE_TO_INACTIVE);
                transitionTicks = 10;
            }
        }
    }

    @Nullable
    public Player getOwnerNearby() {
        if (this.storedInventory == null) return null;

        List<Player> nearbyPlayer = this.level().getNearbyEntities(
                Player.class,
                TargetingConditions.forNonCombat().selector(
                        (p) -> p.isAlive() && p.getUUID().equals(this.storedInventory.playerUuid)),
                this,
                this.getBoundingBox().inflate(5)
        );
        return nearbyPlayer.isEmpty() ? null : nearbyPlayer.getFirst();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public int tickCount() {
        return tickCount;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, TransitioningState.INACTIVE);
    }

    @NotNull
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return super.interact(player, hand);

        // Restores player inventory using the following strategy:
        // - Try restoring slot-by-slot
        // - If there are items left, try inserting them into the player's inventory
        // - Store remaining items back into the Hollow
        // [After each step, checks if the Hollow is empty]
        // This should cover most cases.
        // Curios items are restored by trying to insert them into the player's inventory

        if (this.storedInventory != null && player.getUUID().equals(this.storedInventory.playerUuid)) {
            // Load the stored inventory into a fake player for easier manipulation
            FakePlayer fakePlayer = EntityHelper.getFakePlayer(serverLevel);
            fakePlayer.getInventory().load(this.storedInventory.inventoryData);

            // Restore slot-by-slot
            for (int i = 0; i < fakePlayer.getInventory().getContainerSize(); i++) {
                ItemStack stack = fakePlayer.getInventory().getItem(i);
                if (stack.isEmpty()) continue;

                if (player.getInventory().getItem(i).isEmpty()) {
                    player.getInventory().setItem(i, stack);
                    // And remove it from the hollow
                    fakePlayer.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }

            List<ItemStack> additionalItems = this.storedInventory.additionalItems.orElse(Collections.emptyList());
            additionalItems.removeIf(stack -> player.getInventory().add(stack));

            // If the hollow's inventory is empty, remove the entity
            if (fakePlayer.getInventory().isEmpty() && additionalItems.isEmpty()) {
                this.storedInventory = null;
                this.discard();
            } else {
                if (!fakePlayer.getInventory().isEmpty()) {
                    // Try inserting the remaining items into the player's inventory
                    for (int i = 0; i < fakePlayer.getInventory().getContainerSize(); i++) {
                        ItemStack stack = fakePlayer.getInventory().getItem(i);
                        if (stack.isEmpty()) continue;

                        // Try to insert the item into the player's inventory
                        if (player.getInventory().add(stack)) {
                            fakePlayer.getInventory().setItem(i, ItemStack.EMPTY);
                        } else {
                            if (stack.getMaxStackSize() <= 1) continue;

                            // If the item is stackable, try merging it with an existing stack
                            int slot = player.getInventory().findSlotMatchingItem(stack);
                            if (slot != -1) {
                                ItemStack existing = player.getInventory().getItem(slot);
                                // Calculate the amount of items that can be merged
                                int count = Math.min(stack.getCount(), existing.getMaxStackSize() - existing.getCount());
                                existing.grow(count);
                                stack.shrink(count);
                                // Put the remainder back
                                fakePlayer.getInventory().setItem(i, stack);
                            }
                        }
                    }
                }

                // If the hollow inventory is empty, remove the entity
                if (fakePlayer.getInventory().isEmpty() && additionalItems.isEmpty()) {
                    this.storedInventory = null;
                    this.discard();
                } else {
                    // Save the remaining items back to the stored inventory
                    ListTag inventoryData = new ListTag();
                    fakePlayer.getInventory().save(inventoryData);
                    this.storedInventory = new StoredInventory(player.getUUID(), inventoryData, Optional.of(additionalItems));
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.interact(player, hand);
    }

    public void restoreAdditionalItems(ServerPlayer player) {
        if (this.storedInventory == null) return;

        List<ItemStack> additionalItems = this.storedInventory.additionalItems.orElse(Collections.emptyList());
        additionalItems.removeIf(stack -> player.getInventory().add(stack));

        this.storedInventory = new StoredInventory(player.getUUID(), this.storedInventory.inventoryData, Optional.of(additionalItems));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypes.GENERIC_KILL);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("stored_inventory")) {
            this.storedInventory = StoredInventory.fromSaveData(registryAccess(), compound.getCompound("stored_inventory"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (storedInventory != null)
            compound.put("stored_inventory", storedInventory.getSaveData(registryAccess()));
    }

    public record StoredInventory(UUID playerUuid, ListTag inventoryData, Optional<List<ItemStack>> additionalItems) {
        public CompoundTag getSaveData(RegistryAccess registryAccess) {
            CompoundTag data = new CompoundTag();
            data.putUUID("player", playerUuid);
            data.put("items", inventoryData);
            ListTag additionalItems = new ListTag();
            this.additionalItems.ifPresent(items -> items.forEach(item -> additionalItems.add(item.saveOptional(registryAccess))));
            if (!additionalItems.isEmpty())
                data.put("additional_items", additionalItems);
            return data;
        }

        public static StoredInventory fromSaveData(RegistryAccess registryAccess, CompoundTag data) {
            List<ItemStack> additionalItems = new ArrayList<>();
            if (data.contains("additional_items")) {
                ListTag additionalItemsData = data.getList("additional_items", 10);
                for (Tag tag : additionalItemsData) {
                    ItemStack.parse(registryAccess, tag).ifPresent(additionalItems::add);
                }
            }

            return new StoredInventory(data.getUUID("player"), data.getList("items", 10), Optional.of(additionalItems));
        }
    }
}