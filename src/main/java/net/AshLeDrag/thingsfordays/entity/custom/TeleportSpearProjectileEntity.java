package net.AshLeDrag.thingsfordays.entity.custom;

import net.AshLeDrag.thingsfordays.entity.ModEntities;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class TeleportSpearProjectileEntity extends SteelThrowableProjectileEntity {
		
		public TeleportSpearProjectileEntity(EntityType<? extends SteelThrowableProjectileEntity> entityType, Level level) {
				super(entityType, level);
		}
		
		public TeleportSpearProjectileEntity(LivingEntity thrower, Level level, ItemStack pickupItemStack) {
				super(ModEntities.TELEPORT_SWORD.get(), thrower, level, pickupItemStack);
		}
		
		public TeleportSpearProjectileEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
				super(ModEntities.TELEPORT_SWORD.get(), level, x, y, z, pickupItemStack);
		}
		
		@Override
		protected void onHitBlock(BlockHitResult hitResult) {
				super.onHitBlock(hitResult);
				if (this.level().isClientSide) return;
				
				LivingEntity owner = this.getOwner() instanceof LivingEntity le ? le : null;
				if (owner == null) return;
				
				// Teleport to exactly where the sword landed
				owner.teleportTo(this.getX(), this.getY(), this.getZ());
				this.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
				// Stays in ground for normal pickup — no return
		}
		
		@Override
		protected ItemStack getDefaultPickupItem() {
				return new ItemStack(ModItems.Weapons.TELEPORT_SPEAR.get());
		}
		
		// No loyalty — override tick to skip the return logic entirely
		@Override
		protected byte getLoyaltyFromItem(ItemStack stack) {
				return 0;
		}
}