package eu.bsuu.curiosmunchies.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import eu.bsuu.curiosmunchies.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SnackItem extends Item implements INBTSerializable<CompoundTag> {

    private ISlotType slot;

    public static final String SNACK_NBT_ID = "snack_slot";

    private static final FoodProperties properties = new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(1)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 40), 1)
            .effect(() -> new MobEffectInstance(MobEffects.UNLUCK, 1000), .5f)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 1000), .5f)
            .alwaysEat()
            .build();

    public SnackItem() {
        super(new Item.Properties().food(properties));
    }

    @Override
    @NotNull
    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity) {

        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof ServerPlayer) {
            if (itemStack.getTag() == null) {
                return super.finishUsingItem(itemStack, level, entity);
            }
            String slotTag = itemStack.getTag().getString(SNACK_NBT_ID);

            Multimap<String, AttributeModifier> modifiers = HashMultimap.create();
            modifiers.put(slotTag, new AttributeModifier(
                    UUID.randomUUID(),
                    "curios_munchies",
                    1,
                    AttributeModifier.Operation.ADDITION)
            );

            LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
            curiosInventory.ifPresent( inv -> {
                int currentSlotSize = inv.getCurios().get(slotTag).getSlots();
                if (currentSlotSize >= Config.numberOfCurios) {
                    player.sendSystemMessage(Component.translatable("item.curios_munchies.snack_item.max_slots"));
                    return;
                }
                inv.addPermanentSlotModifiers(modifiers);
            });
        }
        return super.finishUsingItem(itemStack, level, entity);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 16;
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack _p) {
        return Rarity.EPIC;
    }


    private boolean isHoldingShift() {
        long WINDOW = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, @NotNull TooltipFlag flag) {
        Component baseTooltip = Component
                .translatable("item.curios_munchies.snack_item.unlock")
                .withStyle(ChatFormatting.GOLD);
        components.add(baseTooltip);

        CompoundTag nbt = itemStack.getTag();
        List<Component> additionalTooltip = new ArrayList<>();

        MutableComponent tooltipComponent = Component
                .translatable("curios.tooltip.slot")
                .append(" ")
                .withStyle(ChatFormatting.GOLD);

        MutableComponent sloTypeComponent = null;
        if (nbt != null && nbt.contains(SNACK_NBT_ID)) {
            String slotId = nbt.getString(SNACK_NBT_ID);
            sloTypeComponent = Component
                    .translatable("curios.identifier." + slotId)
                    .withStyle(ChatFormatting.YELLOW);
        } else {
            sloTypeComponent = Component
                    .literal("POLSKAGUROM")
                    .withStyle(ChatFormatting.YELLOW)
                    .withStyle(ChatFormatting.OBFUSCATED);
        }

        tooltipComponent.append(sloTypeComponent);
        additionalTooltip.add(tooltipComponent);
        components.addAll(additionalTooltip);

        Component infoTooltip = Component.translatable(
                        isHoldingShift() ?
                                "item.curios_munchies.snack_item.info" : "item.curios_munchies.snack_item.shift")
                .withStyle(ChatFormatting.DARK_GRAY);

        components.add(infoTooltip);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString(SNACK_NBT_ID, this.slot.getIdentifier());
        return tag;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(SNACK_NBT_ID)) {
            this.slot = CuriosApi.getSlot(nbt.getString(SNACK_NBT_ID)).orElse(null);
        }
    }
}
