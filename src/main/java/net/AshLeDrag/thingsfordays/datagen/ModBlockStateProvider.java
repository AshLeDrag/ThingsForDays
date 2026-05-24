package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.AshLeDrag.thingsfordays.block.custom.LampBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
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
		
		
		// customLamp Blocks
		customLamp(ModBlocks.BISMUTH_LAMP.get(), "bismuth_lamp");
	}
	
	
	private void customLamp(Block lamp, String name) {
		getVariantBuilder(lamp).forAllStates(state -> {
			if (state.getValue(LampBlock.CLICKED)) {
				return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_on",
																																					ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																																																								"block/" + name + "_on")))};
			} else {
				return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name + "_off",
																																					ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																																																								"block/" + name + "_off")))};
			}
		});
		
		simpleBlockItem(lamp,
										models().cubeAll(name + "_on",
																		 ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																																					 "block/" + name + "_on")));
	}
	
	
	public void blockWithItem(DeferredBlock<?> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));
	}
	
	private void blockItem(DeferredBlock<?> deferredBlock) {
		simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("thingsfordays:block/" + deferredBlock.getId().getPath()));
	}
	
	private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
		simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("thingsfordays:block/" + deferredBlock.getId().getPath() + appendix));
	}
}
