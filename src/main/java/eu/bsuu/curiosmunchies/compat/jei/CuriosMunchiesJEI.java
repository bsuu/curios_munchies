package eu.bsuu.curiosmunchies.compat.jei;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.recipe.SnackRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class CuriosMunchiesJEI implements IModPlugin {

    public static final RecipeType<SnackRecipe> SNACK_RECIPE_TYPE =
            new RecipeType<>(new ResourceLocation(CuriosMunchies.MODID, "snack_recipe"), SnackRecipe.class);

    private IRecipeCategory<SnackRecipe> snackCategory;

    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CuriosMunchies.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(snackCategory = new SnackRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<SnackRecipe> snackRecipeList = recipeManager.getAllRecipesFor(SnackRecipe.Type.INSTANCE);
        CuriosMunchies.LOGGER.info("Tyle jest recipes: " + snackRecipeList.size());
        registration.addRecipes(SNACK_RECIPE_TYPE, snackRecipeList);
    }
}
