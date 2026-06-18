package net.AshLeDrag.thingsfordays.block.entity;

import net.AshLeDrag.thingsfordays.item.ModItems;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

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
		private static final int INPUT_1_MIDDLE = 1;
		private static final int INPUT_2_N = 2;
		private static final int INPUT_3_FAR_N = 3;
		private static final int INPUT_4_S = 4;
		private static final int INPUT_5_FAR_S = 5;
		private static final int INPUT_6_E = 6;
		private static final int INPUT_7_FAR_E = 7;
		private static final int INPUT_8_W = 8;
		private static final int INPUT_9_FAR_W = 9;
		private static final int INPUT_10_NE = 10;
		private static final int INPUT_11_FAR_NE = 11;
		private static final int INPUT_12_SE = 12;
		private static final int INPUT_13_FAR_SE = 13;
		private static final int INPUT_14_SW = 14;
		private static final int INPUT_15_FAR_SW = 15;
		private static final int INPUT_16_NW = 16;
		private static final int INPUT_17_FAR_NW = 17;
		
		
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
				if(hasRecipe()) {
						increaseCraftingProgress();
						setChanged(level, blockPos, blockState);
						
						if(hasCraftingFinished()) {
								craftItem();
								resetProgress();
						}
				} else {
						resetProgress();
				}
		}
		
		private void resetProgress() {progress=0;maxProgress=600;}
		
		private void craftItem() {
				ItemStack output = new ItemStack(ModItems.Steel.Resource.SPEAR_HEAD.get(), 2);
				itemHandler.extractItem(INPUT_1_MIDDLE, 1, false);
				itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(), itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
		}
		
		private boolean hasCraftingFinished() {return this.progress >= this.maxProgress;}
		
		private void increaseCraftingProgress() {progress++;}
		
		private boolean hasRecipe() {
				ItemStack output = new ItemStack(ModItems.Steel.Resource.SPEAR_HEAD.get());
				return (itemHandler.getStackInSlot(INPUT_1_MIDDLE).is(ModItems.Steel.Resource.INGOT) ||itemHandler.getStackInSlot(INPUT_1_MIDDLE).isEmpty()) &&
						canInsertAmoutIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
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