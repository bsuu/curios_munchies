package eu.bsuu.curiosmunchies.data;

import eu.bsuu.curiosmunchies.CuriosMunchies;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CuriosMunchies.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider p_256380_) {}
}
