package net.AshLeDrag.thingsfordays.util;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        
        public static final TagKey<Block> NEEDS_BREADINIUM_TOOL = createTag("needs_breadinium_tool");
        public static final TagKey<Block> INCORRECT_FOR_BREADINIUM_TOOL = createTag("incorrect_for_breadinium_tool");
        
        public static final TagKey<Block> NEEDS_CHROMIUM_TOOL = createTag("needs_chromium_tool");
        public static final TagKey<Block> INCORRECT_FOR_CHROMIUM_TOOL = createTag("incorrect_for_chromium_tool");
        
        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");
        
        public static final TagKey<Block> NEEDS_STAINLESS_STEEL_TOOL = createTag("needs_stainless_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_STAINLESS_STEEL_TOOL = createTag("incorrect_for_stainless_steel_tool");
        
        
        public static final TagKey<Block> BASIC_HAMMER_MINEABLE = createTag("hammer_mineable");
        public static final TagKey<Block> PICKAXE_HAMMER_MINEABLE = createTag("pickaxe_hammer_mineable");
        public static final TagKey<Block> SHOVEL_HAMMER_MINEABLE = createTag("shovel_hammer_mineable");
        public static final TagKey<Block> AXE_HAMMER_MINEABLE = createTag("axe_hammer_mineable");
        
        

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ESSENCE_TO_MANA = createTag("essence_to_mana");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, name));
        }
    }
}