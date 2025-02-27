package net.kallen.konstructionlib.util;


import net.kallen.konstructionlib.KonstructionLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class KonstructionTags {


    public static class Blocks {
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");




        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(KonstructionLib.MOD_ID, name));
        }

    }




}