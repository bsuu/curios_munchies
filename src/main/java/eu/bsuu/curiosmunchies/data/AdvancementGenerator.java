package eu.bsuu.curiosmunchies.data;

import eu.bsuu.curiosmunchies.init.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementGenerator extends ForgeAdvancementProvider {

    public AdvancementGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new MunchiesAdvancement()));
    }

    public static class MunchiesAdvancement implements AdvancementGenerator  {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            DisplayInfo advancementDisplay = new DisplayInfo(new ItemStack(ModItems.SNACK_ITEM.get()),
                    Component.translatable("advancement.curiosmunchies.snack.title"),
                    Component.translatable("advancement.curiosmunchies.snack.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK, true, true, false);

            Advancement.Builder.advancement()
                    .display(advancementDisplay)
                    .addCriterion("snack", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SNACK_ITEM.get()))
                    .save(saver, "curios_munchies:snack_advancement");
        }
    }


}
