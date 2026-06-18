package net.AshLeDrag.thingsfordays.compat;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class WeaponForgeRecipeCategory implements IRecipeCategory<WeaponForgeRecipe> {
		public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "weapon_forge");
		public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
				"textures/gui/weapon_forge/weapon_forge_gui.png");
		
		
		@Override public RecipeType<WeaponForgeRecipe> getRecipeType() {
				return null;
		}
		
		@Override public Component getTitle() {
				return null;
		}
		
		@Override public @Nullable IDrawable getIcon() {
				return null;
		}
		
		@Override public void setRecipe(IRecipeLayoutBuilder builder, WeaponForgeRecipe recipe, IFocusGroup focuses) {
		
		}
}
