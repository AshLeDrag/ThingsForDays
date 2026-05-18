package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, ThingsForDays.MOD_ID, exFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		// cubeAll Blocks
		blockWithItem(ModBlocks.BISMUTH_BLOCK);
		blockWithItem(ModBlocks.BISMUTH_ORE);
		blockWithItem(ModBlocks.BISMUTH_DEEPSLATE_ORE);
		blockWithItem(ModBlocks.MANA_BLOCK);
	}
	
	
	public void blockWithItem(DeferredBlock<?> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));
	}
}
