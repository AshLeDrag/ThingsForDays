package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
	}
}
