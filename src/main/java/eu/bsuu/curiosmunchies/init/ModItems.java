package eu.bsuu.curiosmunchies.init;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import eu.bsuu.curiosmunchies.items.DebugItem;
import eu.bsuu.curiosmunchies.items.SnackItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CuriosMunchies.MODID);


    public static final RegistryObject<Item> SNACK_ITEM = ITEMS.register("snack_item", SnackItem::new);
//    public static final RegistryObject<Item> BEBUG_ITEM = ITEMS.register("debug_item", DebugItem::new);


}
