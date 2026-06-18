package net.AshLeDrag.thingsfordays.block.entity;

import net.AshLeDrag.thingsfordays.item.ModItems;
import net.AshLeDrag.thingsfordays.recipe.ModRecipes;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipe;
import net.AshLeDrag.thingsfordays.recipe.WeaponForgeRecipeInput;
import net.AshLeDrag.thingsfordays.screen.custom.WeaponForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeaponForgeBlockEntity extends BlockEntity implements MenuProvider {
		
		
		public final ItemStackHandler itemHandler = new ItemStackHandler(18) {
				@Override
				protected void onContentsChanged(int slot) {
						setChanged();
						if(!level.isClientSide()) {
								level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
						}
				}
		};
		
		private static final int OUTPUT_SLOT = 0;
		
		
		protected final ContainerData data;
		private int progress =0;
		private int maxProgress = 600;
		
		
		public WeaponForgeBlockEntity(BlockPos pos, BlockState blockState) {
				super(ModBlockEntities.WEAPON_FORGE_BE.get(), pos, blockState);
				data = new ContainerData() {
						
						@Override
						public int get(int i) {
								return switch (i) {
										case 0 -> WeaponForgeBlockEntity.this.progress;
										case 1 -> WeaponForgeBlockEntity.this.maxProgress;
										default -> 0;
								};
						}
						
						@Override
						public void set(int i, int value) {
								switch (i) {
										case 0: WeaponForgeBlockEntity.this.progress = value;
										case 1: WeaponForgeBlockEntity.this.maxProgress = value;
								}
						}
						
						@Override
						public int getCount() {
								return 2;
						}
				};
		}
		
		@Override public Component getDisplayName() {
				return Component.translatable("block.thingsfordays.weapon_forge");
		}
		
		@Override public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
				return new WeaponForgeMenu(i, inventory, this, this.data);
		}
		
		
		public void drops() {
				SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
				for (int i = 0; i < itemHandler.getSlots(); i++) {
						inventory.setItem(i, itemHandler.getStackInSlot(i));
				}
				
				Containers.dropContents(this.level, this.worldPosition, inventory);
		}
		
		
		@Override
		protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
				pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
				pTag.putInt("growth_chamber.progress", progress);
				pTag.putInt("growth_chamber.max_progress", maxProgress);
				
				super.saveAdditional(pTag, pRegistries);
		}
		
		@Override
		protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
				super.loadAdditional(pTag, pRegistries);
				
				itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
				progress = pTag.getInt("growth_chamber.progress");
				maxProgress = pTag.getInt("growth_chamber.max_progress");
		}
		
		public void tick(Level level, BlockPos blockPos, BlockState blockState) {
				if (hasRecipe()) {
						// Sync maxProgress from the recipe on the first tick (progress == 0)
						if (progress == 0) {
								getCurrentRecipe().ifPresent(r -> maxProgress = r.value().forgingTime());
						}
						increaseCraftingProgress();
						setChanged(level, blockPos, blockState);
						if (hasCraftingFinished()) {
								craftItem();
								resetProgress();
						}
				} else {
						resetProgress();
				}
		}
		
		private void resetProgress() {progress=0;maxProgress=600;}
		
		private void craftItem() {
				Optional<RecipeHolder<WeaponForgeRecipe>> recipe = getCurrentRecipe();
				ItemStack output = recipe.get().value().output();
				
				for (int i = 1; i <= WeaponForgeRecipe.SLOT_NAMES.size(); i++) {  // start at 1
						itemHandler.extractItem(i, 1, false);
				}
				
				itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(
						output.getItem(),
						itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()
				));
		}
		
		private boolean hasCraftingFinished() {return this.progress >= this.maxProgress;}
		
		private void increaseCraftingProgress() {progress++;}
		
		private boolean hasRecipe() {
				Optional<RecipeHolder<WeaponForgeRecipe>> recipe = getCurrentRecipe();
				if(recipe.isEmpty()) {return false;}
				
				ItemStack output = recipe.get().value().output();
				return canInsertAmoutIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
		}
		
		private Optional<RecipeHolder<WeaponForgeRecipe>> getCurrentRecipe() {
				List<ItemStack> inputs = new ArrayList<>();
				for (int i = 1; i <= WeaponForgeRecipe.SLOT_NAMES.size(); i++) {  // start at 1, skip output slot
						inputs.add(itemHandler.getStackInSlot(i));
				}
				return level.getRecipeManager()
								 .getRecipeFor(ModRecipes.WEAPON_FORGE_TYPE.get(),
										 new WeaponForgeRecipeInput(inputs), level);
		}
		
		private boolean canInsertItemIntoOutputSlot(ItemStack output) {
				return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || output.getItem() == itemHandler.getStackInSlot(OUTPUT_SLOT).getItem();
		}
		
		private boolean canInsertAmoutIntoOutputSlot(int count) {
				int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
				int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();
				
				return maxCount >= currentCount + count;
		}
		
		@Override
		public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
				return saveWithoutMetadata(pRegistries);
		}
		
		@Nullable
		@Override
		public Packet<ClientGamePacketListener> getUpdatePacket() {
				return ClientboundBlockEntityDataPacket.create(this);
		}
		
		
}