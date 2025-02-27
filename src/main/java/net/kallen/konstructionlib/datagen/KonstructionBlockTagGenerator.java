package net.kallen.konstructionlib.datagen;

import net.kallen.konstructionlib.KonstructionLib;
import net.kallen.konstructionlib.util.KonstructionTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class KonstructionBlockTagGenerator extends BlockTagsProvider{
    public KonstructionBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KonstructionLib.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(KonstructionTags.Blocks.METAL_DETECTOR_VALUABLES)
                .addTag(Tags.Blocks.ORES);
    }
}
