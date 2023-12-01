package eu.bsuu.curiosmunchies;

import com.mojang.logging.LogUtils;
import eu.bsuu.curiosmunchies.init.ModItems;
import eu.bsuu.curiosmunchies.init.ModRecipes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static eu.bsuu.curiosmunchies.init.ModItems.SNACK_ITEM;

@Mod(CuriosMunchies.MODID)
public class CuriosMunchies {

    public static final String MODID = "curios_munchies";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CuriosMunchies() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(SNACK_ITEM);
        }
    }
}
