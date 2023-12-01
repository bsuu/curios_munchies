package eu.bsuu.curiosmunchies.init;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.recipe.SnackRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CuriosMunchies.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CuriosMunchies.MODID);

    public static final RegistryObject<RecipeSerializer<?>> SNACK_RECIPE_SERIALIZER =
            SERIALIZERS.register("snack_recipe", ModRecipes::registerSerializer);

    private static RecipeSerializer<?> registerSerializer() {
        SnackRecipe.SERIALIZER = new SnackRecipe.Serializer(SnackRecipe::new);
        return SnackRecipe.SERIALIZER;
    }

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

}
