package net.AshLeDrag.thingsfordays.block;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.custom.LampBlock;
import net.AshLeDrag.thingsfordays.block.custom.ManaBlock;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.AshLeDrag.thingsfordays.sound.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThingsForDays.MOD_ID);
    
    
    public static final DeferredBlock<Block> MANA_BLOCK = registerBlock("mana_block",
          () -> new ManaBlock(BlockBehaviour
                                    .Properties.of()
                                    .strength(2f)
                                    .requiresCorrectToolForDrops()
                                    .sound(ModSounds.MANA_BLOCK_SOUNDS)));
    
    
    public static final DeferredBlock<Block> BREADINIUM_ORE = registerBlock("breadinium_ore",
          () -> new DropExperienceBlock(UniformInt.of(2, 4),
                BlockBehaviour.Properties.of()
                      .strength(3f)
                      .requiresCorrectToolForDrops()
                      .sound(SoundType.STONE)));
    
    public static final DeferredBlock<Block> BREADINIUM_DEEPSLATE_ORE = registerBlock("breadinium_deepslate_ore",
          () -> new DropExperienceBlock(UniformInt.of(3, 6),
                BlockBehaviour.Properties.of()
                      .strength(4f)
                      .requiresCorrectToolForDrops()
                      .sound(SoundType.DEEPSLATE)));
    
    
    
    
    
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){BLOCKS.register(eventBus);}

}
