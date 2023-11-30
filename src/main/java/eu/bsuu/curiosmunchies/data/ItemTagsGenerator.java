package eu.bsuu.curiosmunchies.data;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, CuriosMunchies.MODID, existingFileHelper);
    }

    public static final TagKey<Item> MUNCHIES_TAG = ItemTags.create(new ResourceLocation(CuriosMunchies.MODID, "curios_munchies_snack"));

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(MUNCHIES_TAG)
                .addOptionalTag(new ResourceLocation("curios", "necklace"))
                .addOptionalTag(new ResourceLocation("curios", "curio"))
                .addOptionalTag(new ResourceLocation("curios", "back"))
                .addOptionalTag(new ResourceLocation("curios", "belt"))
                .addOptionalTag(new ResourceLocation("curios", "body"))
                .addOptionalTag(new ResourceLocation("curios", "bracelet"))
                .addOptionalTag(new ResourceLocation("curios", "charm"))
                .addOptionalTag(new ResourceLocation("curios", "head"))
                .addOptionalTag(new ResourceLocation("curios", "hands"))
                .addOptionalTag(new ResourceLocation("curios", "feet"))
                .addOptionalTag(new ResourceLocation("curios", "ring"))
                .addOptionalTag(new ResourceLocation("curios", "trinkets"));
    }


}
