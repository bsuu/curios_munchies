package eu.bsuu.curiosmunchies.data;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.recipe.SnackRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ForgeRecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        SnackRecipeBuilder.build(
                Ingredient.of(ItemTagsGenerator.MUNCHIES_TAG),
                Ingredient.of(Items.NETHER_STAR),
                RecipeCategory.MISC
        ).save(writer::accept, new ResourceLocation(CuriosMunchies.MODID, "snack_recipe"));
    }

//    @Override
//    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
//        SnackRecipeBuilder.build(
//                Ingredient.of(ItemTagsGenerator.MUNCHIES_TAG),
//                Ingredient.of(Items.NETHER_STAR),
//                RecipeCategory.MISC
//        ).save(writer, new ResourceLocation(CuriosMunchies.MODID, "snack_recipe"));
//    }
}
