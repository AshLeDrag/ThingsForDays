package net.AshLeDrag.thingsfordays.datagen;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
		public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
				super(output, registries);
		}
		
		@Override
		protected void buildRecipes(RecipeOutput recipeOutput) {
				
				modRecipes(recipeOutput);
				
				ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.Breadinium.Tools.HAMMER.get())
						.requires(ModItems.Breadinium.Tools.AXE_HAMMER)
						.unlockedBy("has_bismuth_block", has(ModItems.Breadinium.Resource.INGOT)).save(recipeOutput);
				ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.Breadinium.Tools.PICKAXE_HAMMER.get())
						.requires(ModItems.Breadinium.Tools.HAMMER)
						.unlockedBy("has_bismuth_block", has(ModItems.Breadinium.Resource.INGOT)).save(recipeOutput);
				ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.Breadinium.Tools.SHOVEL_HAMMER.get())
						.requires(ModItems.Breadinium.Tools.PICKAXE_HAMMER)
						.unlockedBy("has_bismuth_block", has(ModItems.Breadinium.Resource.INGOT)).save(recipeOutput);
				ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.Breadinium.Tools.AXE_HAMMER.get())
						.requires(ModItems.Breadinium.Tools.SHOVEL_HAMMER)
						.unlockedBy("has_bismuth_block", has(ModItems.Breadinium.Resource.INGOT)).save(recipeOutput);
				
				ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.Weapons.COLOSSAL_SWORD)
						.pattern("SMS")
						.pattern("SSS")
						.pattern("LTL")
						.define('S', ModItems.Steel.Stainless.Resource.INGOT)
						.define('M', ModItems.Mana.Resource.PURE_MANA)
						.define('L', Items.LEATHER)
						.define('T', Items.STICK)
						.unlockedBy("has_pure_mana", has(ModItems.Mana.Resource.PURE_MANA))
						.unlockedBy("has_stainless_steel_ingot", has(ModItems.Steel.Stainless.Resource.INGOT)).save(recipeOutput);
				
				
				List<ItemLike> BREADINIUM_SMELTABLES = List.of(ModItems.Breadinium.Resource.RAW,
						ModBlocks.BREADINIUM_ORE, ModBlocks.BREADINIUM_DEEPSLATE_ORE,
						ModBlocks.BREADINIUM_NETHER_ORE, ModBlocks.BREADINIUM_END_ORE);
				
				
				
				oreRecipies(recipeOutput, BREADINIUM_SMELTABLES, RecipeCategory.MISC, ModItems.Breadinium.Resource.INGOT, ModBlocks.BREADINIUM_BLOCK, 0.25f,  600, "breadinium");
				
				
				List<ItemLike> MYTHRIL_SMELTABLES = List.of(ModItems.Mythril.Resource.RAW,
						ModBlocks.MYTHRIL_ORE, ModBlocks.MYTHRIL_DEEPSLATE_ORE,
						ModBlocks.MYTHRIL_NETHER_ORE, ModBlocks.MYTHRIL_END_ORE);
				
				oreRecipies(recipeOutput, MYTHRIL_SMELTABLES, RecipeCategory.MISC,
						ModItems.Mythril.Resource.INGOT, ModBlocks.MYTHRIL_BLOCK, 0.25f, 600, "mythril");
		}
		
		
		
		
		
		protected static void modRecipes(RecipeOutput recipeOutput) {
				// In ModRecipeProvider.buildRecipes(), add these three:
				
				// 1. All 17 slots filled
				WeaponForgeRecipeBuilder.create(RecipeCategory.MISC)
						.forgingTime(600)
						.center      (Items.DIAMOND)
						.north       (Items.IRON_INGOT)
						.farNorth    (Items.GOLD_INGOT)
						.south       (Items.IRON_INGOT)
						.farSouth    (Items.GOLD_INGOT)
						.east        (Items.IRON_INGOT)
						.farEast     (Items.GOLD_INGOT)
						.west        (Items.IRON_INGOT)
						.farWest     (Items.GOLD_INGOT)
						.northEast   (Items.STICK)
						.farNorthEast(Items.FLINT)
						.southEast   (Items.STICK)
						.farSouthEast(Items.FLINT)
						.southWest   (Items.STICK)
						.farSouthWest(Items.FLINT)
						.northWest   (Items.STICK)
						.farNorthWest(Items.FLINT)
						.result      (Items.NETHERITE_INGOT)
						.unlockedBy  ("has_diamond", has(Items.DIAMOND))
						.save(recipeOutput, ThingsForDays.MOD_ID + ":weapon_forge_full_example");
				
				// 2. Cross pattern — center + 4 cardinal slots only
				WeaponForgeRecipeBuilder.create(RecipeCategory.COMBAT)
						.forgingTime(300)
						.center (Items.BLAZE_ROD)
						.north  (Items.IRON_INGOT)
						.south  (Items.IRON_INGOT)
						.east   (Items.IRON_INGOT)
						.west   (Items.IRON_INGOT)
						.result (Items.BLAZE_POWDER, 4)
						.unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
						.save(recipeOutput, ThingsForDays.MOD_ID + ":weapon_forge_cross_example");
				
				// 3. Diagonal pattern — center + 4 diagonal far slots only
				WeaponForgeRecipeBuilder.create(RecipeCategory.MISC)
						.forgingTime(400)
						.center      (Items.OBSIDIAN)
						.farNorthEast(Items.CRYING_OBSIDIAN)
						.farSouthEast(Items.CRYING_OBSIDIAN)
						.farSouthWest(Items.CRYING_OBSIDIAN)
						.farNorthWest(Items.CRYING_OBSIDIAN)
						.result      (Items.ANCIENT_DEBRIS)
						.unlockedBy  ("has_crying_obsidian", has(Items.CRYING_OBSIDIAN))
						.save(recipeOutput, ThingsForDays.MOD_ID + ":weapon_forge_diagonal_example");
				
				
				
				
				
				
		}
		
		
		
		
		
		
		
		
		protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
				float pExperience, int pCookingTIme, String pGroup) {
				oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
						pExperience, pCookingTIme, pGroup, "_from_smelting");
		}
		
		protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
				float pExperience, int pCookingTime, String pGroup) {
				oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
						pExperience, pCookingTime, pGroup, "_from_blasting");
		}
		
		protected static void oreRecipies(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, ItemLike pBlock,
				float pExperience, int pCookingTime, String pGroup) {
				
				oreSmelting(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime*2, pGroup);
				oreBlasting(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup);
				
				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pBlock)
						.pattern("BBB")
						.pattern("BBB")
						.pattern("BBB")
						.define('B', pResult)
						.unlockedBy("has_" + getItemName(pResult), has(pResult)).save(recipeOutput);
				
				ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pResult, 9)
						.requires(pBlock)
						.unlockedBy("has_" + getItemName(pBlock), has(pBlock)).save(recipeOutput);
		}
		
		protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
				List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
				for(ItemLike itemlike : pIngredients) {
						SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
								.save(recipeOutput, ThingsForDays.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
				}
		}
}