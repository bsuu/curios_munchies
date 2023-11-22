package eu.bsuu.curiosmunchies.recipe;

import com.google.gson.JsonObject;
import eu.bsuu.curiosmunchies.Config;
import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.init.ModItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;

import java.util.Map;

public class SnackRecipe implements SmithingRecipe {

    public static RecipeSerializer<SnackRecipe> SERIALIZER;

    private final ResourceLocation id;
    public final Ingredient addition;
    public final Ingredient base;

    public SnackRecipe(ResourceLocation id, Ingredient base, Ingredient addition) {
        this.id = id;
        this.base = base;
        this.addition = addition;
    }

        @Override
    public boolean matches(Container container, Level level) {
        return this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess p_267165_) {
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
    public boolean isBaseIngredient(ItemStack base) {
        return this.base.test(base) && !Config.blockedCurios.contains(base.getItem());
    }
    public boolean isAdditionIngredient(@NotNull ItemStack addition) {
        return this.addition.test(addition);
    }
    public @NotNull ResourceLocation getId() { return this.id; }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class SnackRecipeSerializer implements RecipeSerializer<SnackRecipe> {
        private final SnackRecipeSerializer.Factory<SnackRecipe> constructor;

        public SnackRecipeSerializer(SnackRecipeSerializer.Factory<SnackRecipe> recipeFactory) {
            this.constructor = recipeFactory;
        }

        public SnackRecipe fromJson(ResourceLocation id, JsonObject object) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(object, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(object, "addition"));
            return this.constructor.create(id, base, addition);
        }

        public SnackRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf friendlyByteBuf) {
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient addition = Ingredient.fromNetwork(friendlyByteBuf);
            return this.constructor.create(id, base, addition);
        }

        public void toNetwork(FriendlyByteBuf friendlyByteBuf, SnackRecipe recipe) {
            recipe.base.toNetwork(friendlyByteBuf);
            recipe.addition.toNetwork(friendlyByteBuf);
        }

        @FunctionalInterface
        public interface Factory<T extends SnackRecipe> {
            T create(ResourceLocation p_250892_, Ingredient base, Ingredient addition);
        }
    }
}
