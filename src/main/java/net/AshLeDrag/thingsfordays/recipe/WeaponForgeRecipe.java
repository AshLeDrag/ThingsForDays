package net.AshLeDrag.thingsfordays.recipe;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record WeaponForgeRecipe(Ingredient input1, Ingredient input2, Ingredient input3,
                                Ingredient input4, Ingredient input5, Ingredient input6,
                                Ingredient input7, Ingredient input8, Ingredient input9,
                                Ingredient input10, Ingredient input11, Ingredient input12,
                                Ingredient input13, Ingredient input14, Ingredient input15,
                                Ingredient input16, Ingredient input17, ItemStack output) implements Recipe<WeaponForgeRecipeInput> {
		
		
		@Override public NonNullList<Ingredient> getIngredients() {
				NonNullList<Ingredient> list = NonNullList.create();
				list.add(input1); list.add(input2);
				list.add(input3); list.add(input4);
				list.add(input5); list.add(input6);
				list.add(input7); list.add(input8);
				list.add(input9); list.add(input10);
				list.add(input11); list.add(input12);
				list.add(input13); list.add(input14);
				list.add(input15); list.add(input16);
				list.add(input17);
				return list;
		}
		
		@Override public boolean matches(WeaponForgeRecipeInput weaponForgeRecipeInput, Level level) {
				if(level.isClientSide){return false;}
				
				return getBool(weaponForgeRecipeInput);
		}
		
		private boolean getBool(WeaponForgeRecipeInput weaponForgeRecipeInput) {
				boolean finalBool = true;
				NonNullList<Ingredient> list = getIngredients();
				
				for(int i = 0; i < weaponForgeRecipeInput.size(); ++i) {
						finalBool &= getIngredients().get(i).test(weaponForgeRecipeInput.getItem(i));
				}
				return finalBool;
		}
		
		
		
		
		
		
		@Override public ItemStack assemble(WeaponForgeRecipeInput weaponForgeRecipeInput, HolderLookup.Provider provider) {
				return output.copy();
		}
		
		@Override public boolean canCraftInDimensions(int i, int i1) {
				return true;
		}
		
		@Override public ItemStack getResultItem(HolderLookup.Provider provider) {
				return output;
		}
		
		@Override public RecipeSerializer<?> getSerializer() {
				return
		}
		
		@Override public RecipeType<?> getType() {
				return
		}
		
		<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Products.P16<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> group(final App<F, T1> t1, final App<F, T2> t2, final App<F, T3> t3, final App<F, T4> t4, final App<F, T5> t5, final App<F, T6> t6, final App<F, T7> t7, final App<F, T8> t8, final App<F, T9> t9, final App<F, T10> t10, final App<F, T11> t11, final App<F, T12> t12, final App<F, T13> t13, final App<F, T14> t14, final App<F, T15> t15, final App<F, T16> t16) {
				return new Products.P16<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
		}
		
		public static class Serializer implements RecipeSerializer<WeaponForgeRecipe>{
				public static final MapCodec<WeaponForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(WeaponForgeRecipe::input1),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(WeaponForgeRecipe::input2),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient3").forGetter(WeaponForgeRecipe::input3),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient4").forGetter(WeaponForgeRecipe::input4),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient5").forGetter(WeaponForgeRecipe::input5),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient6").forGetter(WeaponForgeRecipe::input6),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient7").forGetter(WeaponForgeRecipe::input7),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient8").forGetter(WeaponForgeRecipe::input8),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient9").forGetter(WeaponForgeRecipe::input9),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient10").forGetter(WeaponForgeRecipe::input10),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient11").forGetter(WeaponForgeRecipe::input11),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient12").forGetter(WeaponForgeRecipe::input12),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient13").forGetter(WeaponForgeRecipe::input13),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient14").forGetter(WeaponForgeRecipe::input14),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient15").forGetter(WeaponForgeRecipe::input15),
						Ingredient.CODEC_NONEMPTY.fieldOf("ingredient16").forGetter(WeaponForgeRecipe::input16),
						ItemStack.CODEC.fieldOf("result").forGetter(WeaponForgeRecipe::output)
				).apply(inst, WeaponForgeRecipe::new));
				
				public static final StreamCodec<RegistryFriendlyByteBuf, WeaponForgeRecipe> STREAM_CODEC =
						StreamCodec.composite(
								Ingredient.CONTENTS_STREAM_CODEC, WeaponForgeRecipe::inputItem,
								ItemStack.STREAM_CODEC, WeaponForgeRecipe::output,
								WeaponForgeRecipe::new);
				
				@Override public MapCodec<WeaponForgeRecipe> codec() {
						return null;
				}
				
				@Override public StreamCodec<RegistryFriendlyByteBuf, WeaponForgeRecipe> streamCodec() {
						return null;
				}
		}
		
		
}
