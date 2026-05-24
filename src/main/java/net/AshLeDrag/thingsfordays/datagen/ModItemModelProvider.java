package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ThingsForDays.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		basicItem(ModItems.PURE_MANA.get());
		basicItem(ModItems.RADISH.get());
		basicItem(ModItems.STARLIGHT_ASHES.get());
		basicItem(ModItems.BISMUTH.get());
		basicItem(ModItems.RAW_BISMUTH.get());
		basicItem(ModItems.CHISEL.get());
		basicItem(ModItems.MANA_ESSENCE.get());
	}
	
	public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
			.texture("texture",  ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																																 "block/" + baseBlock.getId().getPath()));
	}
	
	public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
			.texture("texture",  ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																																 "block/" + baseBlock.getId().getPath()));
	}
	
	public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
			.texture("wall",  ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
																															"block/" + baseBlock.getId().getPath()));
	}
}
