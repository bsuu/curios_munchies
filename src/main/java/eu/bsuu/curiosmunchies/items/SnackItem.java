package eu.bsuu.curiosmunchies.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mojang.blaze3d.platform.InputConstants;
import eu.bsuu.curiosmunchies.Config;
import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.init.ModRecipes;
import eu.bsuu.curiosmunchies.recipe.SnackRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SnackItem extends Item implements INBTSerializable<CompoundTag> {

    private ISlotType slot;

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
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {

        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof ServerPlayer) {
            if (!itemStack.hasTag()) {
                return super.finishUsingItem(itemStack, level, entity);
            }

            assert itemStack.getTag() != null;
            String slotTag = itemStack.getTag().getString("snack_slot");
            CuriosApi
                    .getSlotHelper()
                    .growSlotType(slotTag, 1, player);
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
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Component tooltip1 = Component.translatable("item.curios_munchies.snack_item.tooltip1").withStyle(ChatFormatting.GOLD);
        components.add(tooltip1);

        if (itemStack.getTag() != null && itemStack.getTag().contains("snack_slot")) {
            Optional<ISlotType> slots = CuriosApi.getSlot(itemStack.getTag().getString("snack_slot"));
            if (slots.isPresent()) {
                ISlotType slotType = CuriosApi.getSlot(itemStack.getTag().getString("snack_slot")).get();
                Component tooltip2 = Component.translatable("item.curios_munchies.snack_item.tooltip2")
                        .withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(StringUtils.capitalize(slotType.getIdentifier()))
                                .withStyle(ChatFormatting.YELLOW));
                components.add(tooltip2);
            }
        } else {
            Component tooltip2 = Component.translatable("item.curios_munchies.snack_item.tooltip2")
                    .withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(StringUtils.capitalize("POLSKAGUROM"))
                            .withStyle(ChatFormatting.YELLOW)
                            .withStyle(ChatFormatting.OBFUSCATED));
            components.add(tooltip2);
        }

        if (level instanceof ClientLevel) {
            if (!isHoldingShift()) {
                Component tooltip = Component.translatable("item.curios_munchies.snack_item.tooltip3").withStyle(ChatFormatting.DARK_GRAY);
                components.add(tooltip);
            } else {
                Component tooltip = Component.translatable("item.curios_munchies.snack_item.tooltip4");
                components.add(tooltip);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("snack_slot", this.slot.getIdentifier());
        return tag;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("snack_slot")) {
            this.slot = CuriosApi.getSlot(nbt.getString("snack_slot")).orElse(null);
        }
    }
}
