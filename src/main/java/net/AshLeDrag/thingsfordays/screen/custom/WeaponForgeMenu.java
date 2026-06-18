package net.AshLeDrag.thingsfordays.screen.custom;

import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.AshLeDrag.thingsfordays.block.entity.WeaponForgeBlockEntity;
import net.AshLeDrag.thingsfordays.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class WeaponForgeMenu extends AbstractContainerMenu {
		
		
		public final WeaponForgeBlockEntity blockEntity;
		private final Level level;
		private final ContainerData data;
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
		
		
		public WeaponForgeMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
				this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
		}
		
		public WeaponForgeMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
				super(ModMenuTypes.WEAPON_FORGE_MENU.get(), pContainerId);
				this.blockEntity = ((WeaponForgeBlockEntity) entity);
				this.level = inv.player.level();
				this.data = data;
				
				addPlayerInventory(inv);
				addPlayerHotbar(inv);
				
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, OUTPUT_SLOT, 159, 33));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_1_MIDDLE , 43, 34));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_2_N, 43, 15));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_3_FAR_N, 43, -8));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_4_S, 43, 54));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_5_FAR_S, 43, 76));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_6_E, 62, 34));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_7_FAR_E, 85, 34));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_8_W, 24, 34));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_9_FAR_W, 1, 34));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_10_NE, 73, 4));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_11_FAR_NE, 94, -17));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_12_SE, 73, 64));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_13_FAR_SE, 94, 85));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_14_SW, 13, 64));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_15_FAR_SW, -8, 85));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_16_NW, -8, -17));
				this.addSlot(new SlotItemHandler(blockEntity.itemHandler, INPUT_17_FAR_NW, 13, 4));
				
				addDataSlots(data);
		}
		public boolean isCrafting() {
				return data.get(0) > 0;
		}
		
		public int getScaledArrowProgress() {
				int progress = this.data.get(0);
				int maxProgress = this.data.get(1);
				int arrowPixelSize = 24;
				
				return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
		}
		
		
		
		
		// CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
		// must assign a slot number to each of the slots used by the GUI.
		// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
		// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
		//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
		//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
		//  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
		private static final int HOTBAR_SLOT_COUNT = 9;
		private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
		private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
		private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
		private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
		private static final int VANILLA_FIRST_SLOT_INDEX = 0;
		private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
		
		// THIS YOU HAVE TO DEFINE!
		private static final int TE_INVENTORY_SLOT_COUNT = 18;  // must be the number of slots you have!
		@Override
		public ItemStack quickMoveStack(Player playerIn, int pIndex) {
				Slot sourceSlot = slots.get(pIndex);
				if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
				ItemStack sourceStack = sourceSlot.getItem();
				ItemStack copyOfSourceStack = sourceStack.copy();
				
				// Check if the slot clicked is one of the vanilla container slots
				if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
						// This is a vanilla container slot so merge the stack into the tile inventory
						if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
																													  + TE_INVENTORY_SLOT_COUNT, false)) {
								return ItemStack.EMPTY;  // EMPTY_ITEM
						}
				} else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
						// This is a TE slot so merge the stack into the players inventory
						if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
								return ItemStack.EMPTY;
						}
				} else {
						System.out.println("Invalid slotIndex:" + pIndex);
						return ItemStack.EMPTY;
				}
				// If stack size == 0 (the entire stack was moved) set slot contents to null
				if (sourceStack.getCount() == 0) {
						sourceSlot.set(ItemStack.EMPTY);
				} else {
						sourceSlot.setChanged();
				}
				sourceSlot.onTake(playerIn, sourceStack);
				return copyOfSourceStack;
		}
		
		@Override
		public boolean stillValid(Player pPlayer) {
				return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
						pPlayer, ModBlocks.WEAPON_FORGE.get());
		}
		
		
		
		
		
		
		
		
		
		private void addPlayerInventory(Inventory playerInventory) {
				for (int i = 0; i < 3; ++i) {
						for (int l = 0; l < 9; ++l) {
								this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 113 + i * 18));
						}
				}
		}
		
		private void addPlayerHotbar(Inventory playerInventory) {
				for (int i = 0; i < 9; ++i) {
						this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 171));
				}
		}
}
