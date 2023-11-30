package eu.bsuu.curiosmunchies.recipe;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SnackRecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final RecipeSerializer<?> type;

    public SnackRecipeBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, RecipeCategory category) {
        this.category = category;
        this.type = serializer;
        this.base = base;
        this.addition = addition;
    }

    public static SnackRecipeBuilder build(Ingredient base, Ingredient addition, RecipeCategory category) {
        return new SnackRecipeBuilder(SnackRecipe.SERIALIZER, base, addition, category);
    }


    public void save(Consumer<FinishedRecipe> p_267089_, ResourceLocation p_267287_) {
        p_267089_.accept(new SnackRecipeBuilder.Result(p_267287_, this.type, this.base, this.addition));
    }

    public static record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base, Ingredient addition) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject p_266713_) {
            p_266713_.add("base", this.base.toJson());
            p_266713_.add("addition", this.addition.toJson());
        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        public @NotNull RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
