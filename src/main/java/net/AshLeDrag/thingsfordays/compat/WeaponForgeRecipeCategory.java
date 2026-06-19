package net.AshLeDrag.thingsfordays.compat;


import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponForgeRecipeCategory implements IRecipeCategory<WeaponForgeRecipe> {
		public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "weapon_forge");
		public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,
				"textures/gui/weapon_forge/weapon_forge_gui.png");
		
		
		
		private final IDrawable background;
		private final IDrawable icon;
		
		public static final RecipeType<WeaponForgeRecipe> WEAPON_FORGE_RECIPE_RECIPE_TYPE =
				new RecipeType<>(UID, WeaponForgeRecipe.class);
		
		public WeaponForgeRecipeCategory(IGuiHelper guiHelper) {
				this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 243, 138);
				this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WEAPON_FORGE));
		}
		
		@Override public RecipeType<WeaponForgeRecipe> getRecipeType() {
				return WEAPON_FORGE_RECIPE_RECIPE_TYPE;
		}
		
		@Override public Component getTitle() {
				return Component.translatable( "block.thingsfordays.weapon_forge");
		}
		
		@Override public @Nullable IDrawable getIcon() {
				return icon;
		}
		
		List<String> SLOT_NAMES = List.of(
				"center",
				"north",      "far_north",
				"south",      "far_south",
				"east",       "far_east",
				"west",       "far_west",
				"north_east", "far_north_east",
				"south_east", "far_south_east",
				"south_west", "far_south_west",
				"north_west", "far_north_west"
		);
		
		@Override public void setRecipe(IRecipeLayoutBuilder builder, WeaponForgeRecipe recipe, IFocusGroup focuses) {
				builder.addSlot(RecipeIngredientRole.INPUT, 76, 60)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(0)));
				builder.addSlot(RecipeIngredientRole.INPUT, 76, 41)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(1)));
				builder.addSlot(RecipeIngredientRole.INPUT, 76, 18)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(2)));
				builder.addSlot(RecipeIngredientRole.INPUT, 76, 79)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(3)));
				builder.addSlot(RecipeIngredientRole.INPUT, 76, 102)  .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(4)));
				builder.addSlot(RecipeIngredientRole.INPUT, 95, 60)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(5)));
				builder.addSlot(RecipeIngredientRole.INPUT, 118, 60)  .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(6)));
				builder.addSlot(RecipeIngredientRole.INPUT, 57, 60)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(7)));
				builder.addSlot(RecipeIngredientRole.INPUT, 34, 60)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(8)));
				builder.addSlot(RecipeIngredientRole.INPUT, 106, 30)  .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(9)));
				builder.addSlot(RecipeIngredientRole.INPUT, 127, 9)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(10)));
				builder.addSlot(RecipeIngredientRole.INPUT, 106, 90)  .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(11)));
				builder.addSlot(RecipeIngredientRole.INPUT, 127, 111) .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(12)));
				builder.addSlot(RecipeIngredientRole.INPUT, 46, 90)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(13)));
				builder.addSlot(RecipeIngredientRole.INPUT, 25, 111)  .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(14)));
				builder.addSlot(RecipeIngredientRole.INPUT, 46, 30)   .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(15)));
				builder.addSlot(RecipeIngredientRole.INPUT, 25, 9)    .addIngredients(recipe.ingredientFor(SLOT_NAMES.get(16)));
				builder.addSlot(RecipeIngredientRole.OUTPUT, 192, 59)    .addItemStack(recipe.getResultItem(null));
				
		}
		
		@Override public @Nullable IDrawable getBackground() {
				return background;
		}
}
