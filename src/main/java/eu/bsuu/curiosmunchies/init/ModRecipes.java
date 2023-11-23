package eu.bsuu.curiosmunchies.init;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.recipe.SnackRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CuriosMunchies.MODID);

    public static final RegistryObject<RecipeSerializer<?>> SNACK_RECIPE_SERIALIZER =
            SERIALIZERS.register("snack_recipe", ModRecipes::registerSerializer);

    private static RecipeSerializer<?> registerSerializer() {
        SnackRecipe.SERIALIZER = new SnackRecipe.SnackRecipeSerializer(SnackRecipe::new);
        return SnackRecipe.SERIALIZER;
    }

}
