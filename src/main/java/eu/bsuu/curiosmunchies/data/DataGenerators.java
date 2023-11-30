package eu.bsuu.curiosmunchies.data;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = CuriosMunchies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        CuriosMunchies.LOGGER.info("Gathering data for Curios Munchies");

        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(event.includeServer(), new RecipeGenerator(output));
        gen.addProvider(event.includeServer(), new AdvancementGenerator(output, provider, existingFileHelper));

        BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(output, provider, existingFileHelper);
        gen.addProvider(event.includeServer(), blockTagsGenerator);
        gen.addProvider(event.includeServer(), new ItemTagsGenerator(output, provider, blockTagsGenerator.contentsGetter(), existingFileHelper));
    }
}
