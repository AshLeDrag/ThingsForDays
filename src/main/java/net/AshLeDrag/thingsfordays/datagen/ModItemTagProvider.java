package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.AshLeDrag.thingsfordays.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, ThingsForDays.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ModTags.Items.ESSENCE_TO_MANA)
			.add(ModItems.Mana.Resource.MANA_ESSENCE.get());
		
		tag(ItemTags.SWORDS)
				.add(ModItems.Breadinium.Weapons.SWORD.get());
		
		tag(ItemTags.BOW_ENCHANTABLE)
				.add(ModItems.Breadinium.Weapons.BOW.get());
		
		tag(ItemTags.AXES)
				.add(ModItems.Breadinium.Tools.AXE.get());
		
		tag(ItemTags.PICKAXES)
				.add(ModItems.Breadinium.Tools.PICKAXE.get());
		
		tag(ItemTags.SHOVELS)
				.add(ModItems.Breadinium.Tools.SHOVEL.get());
		
		tag(ItemTags.HOES)
				.add(ModItems.Breadinium.Tools.HOE.get());
		
		
		this.tag(ItemTags.TRIMMABLE_ARMOR)
				.add(ModItems.Breadinium.Armor.HELMET.get())
				.add(ModItems.Breadinium.Armor.CHESTPLATE.get())
				.add(ModItems.Breadinium.Armor.LEGGINGS.get())
				.add(ModItems.Breadinium.Armor.BOOTS.get());
	}
}
