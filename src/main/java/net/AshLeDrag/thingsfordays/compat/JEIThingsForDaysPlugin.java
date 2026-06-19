package net.AshLeDrag.thingsfordays.compat;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.AshLeDrag.thingsfordays.recipe.ModRecipes;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipe;
import net.AshLeDrag.thingsfordays.screen.custom.WeaponForgeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIThingsForDaysPlugin implements IModPlugin {
		@Override public ResourceLocation getPluginUid() {
				return ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "jei_plugin");
		}
		
		@Override
		public void registerCategories(IRecipeCategoryRegistration registration) {
				registration.addRecipeCategories(new WeaponForgeRecipeCategory(
						registration.getJeiHelpers().getGuiHelper()));
		}
		
		@Override
		public void registerRecipes(IRecipeRegistration registration) {
				RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
				
				List<WeaponForgeRecipe> growthChamberRecipes = recipeManager
																						 .getAllRecipesFor(ModRecipes.WEAPON_FORGE_TYPE.get()).stream().map(RecipeHolder::value).toList();
				registration.addRecipes(WeaponForgeRecipeCategory.WEAPON_FORGE_RECIPE_RECIPE_TYPE, growthChamberRecipes);
		}
		
		@Override
		public void registerGuiHandlers(IGuiHandlerRegistration registration) {
				registration.addRecipeClickArea(WeaponForgeScreen.class, 74, 30, 22, 20,
						WeaponForgeRecipeCategory.WEAPON_FORGE_RECIPE_RECIPE_TYPE);
		}
		
		@Override
		public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
				registration.addRecipeCatalyst(new ItemStack(ModBlocks.WEAPON_FORGE.get().asItem()),
						WeaponForgeRecipeCategory.WEAPON_FORGE_RECIPE_RECIPE_TYPE);
		}
}