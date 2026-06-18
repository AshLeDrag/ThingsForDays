package net.AshLeDrag.thingsfordays.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record WeaponForgeRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3,
                                     ItemStack input4, ItemStack input5, ItemStack input6,
                                     ItemStack input7, ItemStack input8, ItemStack input9,
                                     ItemStack input10, ItemStack input11, ItemStack input12,
                                     ItemStack input13, ItemStack input14, ItemStack input15,
                                     ItemStack input16, ItemStack input17) implements RecipeInput {
		
		@Override public ItemStack getItem(int i) {
				return switch(i){
						case 0 -> input1;
						case 1 -> input2;
						case 2 -> input3;
						case 3 -> input4;
						case 4 -> input5;
						case 5 -> input6;
						case 6 -> input7;
						case 7 -> input8;
						case 8 -> input9;
						case 9 -> input10;
						case 10 -> input11;
						case 11 -> input12;
						case 12 -> input13;
						case 13 -> input14;
						case 14 -> input15;
						case 15 -> input16;
						case 16 -> input17;
						default -> ItemStack.EMPTY;
				};
		}
		
		@Override public int size() {
				return 17;
		}
}
