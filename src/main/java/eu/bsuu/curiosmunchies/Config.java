package eu.bsuu.curiosmunchies;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CuriosMunchies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAX_NUMBER_OF_CURIOS = BUILDER
            .comment("Maximum number of curios slots")
            .defineInRange("number_of_slots", 4, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKED_CURIOS = BUILDER
            .comment("A list of blocked convertible curios.")
            .defineListAllowEmpty("blocked_curios", List.of(), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int numberOfCurios;
    public static Set<Item> blockedCurios;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        numberOfCurios = MAX_NUMBER_OF_CURIOS.get();
        blockedCurios = BLOCKED_CURIOS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }
}
