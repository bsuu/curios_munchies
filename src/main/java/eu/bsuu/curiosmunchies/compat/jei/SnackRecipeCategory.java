package eu.bsuu.curiosmunchies.compat.jei;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.recipe.SnackRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Services;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

public class SnackRecipeCategory implements IRecipeCategory<SnackRecipe> {

    private final IDrawable background;
    private final IDrawable icon;

    public SnackRecipeCategory(IGuiHelper iGuiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("jei", "textures/jei/gui/gui_vanilla.png");
        this.background = iGuiHelper.createDrawable(backgroundLocation, 0, 168, 108, 18);
        this.icon = iGuiHelper.createDrawableItemStack(new ItemStack(Blocks.SMITHING_TABLE));
    }

    @Override
    public RecipeType<SnackRecipe> getRecipeType() {
        return CuriosMunchiesJEI.SNACK_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Blocks.SMITHING_TABLE.getName();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SnackRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(Ingredient.of(ItemStack.EMPTY));

        builder.addSlot(RecipeIngredientRole.INPUT, 19, 1)
                .addIngredients(recipe.base);

        builder.addSlot(RecipeIngredientRole.INPUT, 37, 1)
                .addIngredients(recipe.addition);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 1)
                .addItemStack(getResultItem(recipe));
    }

    private ItemStack getResultItem(Recipe<?> recipe) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();
        return recipe.getResultItem(registryAccess);
    }
}
