package eu.bsuu.curiosmunchies.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.bsuu.curiosmunchies.Config;
import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.init.ModItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.conditions.ConditionCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;

import java.util.Map;

public class SnackRecipe implements SmithingRecipe {

    public static RecipeSerializer<SnackRecipe> SERIALIZER;

    public final Ingredient addition;
    public final Ingredient base;

    public SnackRecipe(Ingredient base, Ingredient addition) {
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean matches(Container container, @NotNull Level level) {
        return this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    @NotNull
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    @NotNull
    public ItemStack assemble(Container container, @NotNull RegistryAccess p_267165_) {
        ItemStack result = new ItemStack(ModItems.SNACK_ITEM.get());
        result.setCount(1);
        Map<String, ISlotType> map = CuriosApi.getItemStackSlots(container.getItem(1));
        Map.Entry<String, ISlotType> entry = map.entrySet().iterator().next();
        result.getOrCreateTag().putString("snack_slot", entry.getValue().getIdentifier());
        return result;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess p_267052_) {
        ItemStack result = new ItemStack(ModItems.SNACK_ITEM.get());
        result.setCount(1);
        return result;
    }

    public boolean isTemplateIngredient(@NotNull ItemStack template) { return false;}
    public boolean isBaseIngredient(@NotNull ItemStack base) {
        return this.base.test(base) && !Config.blockedCurios.contains(base.getItem());
    }
    public boolean isAdditionIngredient(@NotNull ItemStack addition) {
        return this.addition.test(addition);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Type implements RecipeType<SnackRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "snack_recipe";
    }

    public static class SnackRecipeSerializer implements RecipeSerializer<SnackRecipe> {
        private final SnackRecipeSerializer.Factory<SnackRecipe> constructor;

        private static final Codec<SnackRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("base").forGetter((recipe) -> recipe.base),
                Ingredient.CODEC.fieldOf("addition").forGetter((recipe) -> recipe.addition)
        ).apply(instance, SnackRecipe::new));

        public SnackRecipeSerializer(Factory<SnackRecipe> recipeFactory) {
            this.constructor = recipeFactory;
        }

        @Override
        @NotNull
        public Codec<SnackRecipe> codec() {
            return CODEC;
        }

        @Override
        @Nullable
        public SnackRecipe fromNetwork(@NotNull FriendlyByteBuf friendlyByteBuf) {
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient addition = Ingredient.fromNetwork(friendlyByteBuf);
            return this.constructor.create(base, addition);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, SnackRecipe recipe) {
            recipe.base.toNetwork(friendlyByteBuf);
            recipe.addition.toNetwork(friendlyByteBuf);
        }

        @FunctionalInterface
        public interface Factory<T extends SnackRecipe> {
            T create(Ingredient base, Ingredient addition);
        }
    }
}
