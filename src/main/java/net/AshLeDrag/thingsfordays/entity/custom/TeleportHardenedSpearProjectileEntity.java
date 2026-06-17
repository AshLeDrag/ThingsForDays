package net.AshLeDrag.thingsfordays.entity.custom;

import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportHardenedSpearProjectileEntity extends HardenedSwordProjectileEntity {
		
		// Saved target for post-explosion teleport: top of the block that was hit
		private double teleportTargetX;
		private double teleportTargetY;
		private double teleportTargetZ;
		private boolean pendingTeleport = false;
		
		// We wait for explosion to finish then teleport
		// Explosion fires after EXPLODE_DELAY ticks, teleport fires one tick after that
		private int teleportDelayTicks = -1;
		private static final int EXTRA_TELEPORT_DELAY = 2;
		
		public TeleportHardenedSpearProjectileEntity(EntityType<? extends SteelThrowableProjectileEntity> entityType, Level level) {
				super(entityType, level);
		}
		
		public TeleportHardenedSpearProjectileEntity(LivingEntity thrower, Level level, ItemStack pickupItemStack) {
				super(thrower, level, pickupItemStack);
		}
		
		public TeleportHardenedSpearProjectileEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
				super(level, x, y, z, pickupItemStack);
		}
		
		@Override
		protected void onHitBlock(BlockHitResult hitResult) {
				// Save teleport target before calling super (which triggers explosion delay)
				BlockPos hitPos = hitResult.getBlockPos();
				teleportTargetX = hitPos.getX() + 0.5;
				teleportTargetY = hitPos.getY() + 1.0; // top surface of hit block
				teleportTargetZ = hitPos.getZ() + 0.5;
				pendingTeleport = true;
				
				super.onHitBlock(hitResult);
				
				// Teleport fires after explosion delay + a couple extra ticks
				teleportDelayTicks = 10 + EXTRA_TELEPORT_DELAY;
				this.setDeltaMovement(Vec3.ZERO);
		}
		
		@Override
		public void tick() {
				super.tick();
				
				if (teleportDelayTicks > 0) {
						teleportDelayTicks--;
						this.setDeltaMovement(Vec3.ZERO);
				} else if (teleportDelayTicks == 0 && pendingTeleport) {
						teleportDelayTicks = -1;
						pendingTeleport = false;
						Entity owner = this.getOwner();
						if (owner != null && !this.level().isClientSide) {
								owner.teleportTo(teleportTargetX, teleportTargetY, teleportTargetZ);
								this.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
						}
						// Sword stays in ground after teleport — no return
						this.dealtDamage = true;
				}
		}
		
		@Override
		public void readAdditionalSaveData(CompoundTag compound) {
				super.readAdditionalSaveData(compound);
				this.teleportDelayTicks = compound.getInt("TeleportDelay");
				this.teleportTargetX = compound.getDouble("TeleportTargetX");
				this.teleportTargetY = compound.getDouble("TeleportTargetY");
				this.teleportTargetZ = compound.getDouble("TeleportTargetZ");
				this.pendingTeleport = compound.getBoolean("PendingTeleport");
		}
		
		@Override
		public void addAdditionalSaveData(CompoundTag compound) {
				super.addAdditionalSaveData(compound);
				compound.putInt("TeleportDelay", this.teleportDelayTicks);
				compound.putDouble("TeleportTargetX", this.teleportTargetX);
				compound.putDouble("TeleportTargetY", this.teleportTargetY);
				compound.putDouble("TeleportTargetZ", this.teleportTargetZ);
				compound.putBoolean("PendingTeleport", this.pendingTeleport);
		}
		
		@Override
		protected byte getLoyaltyFromItem(ItemStack stack) {
				return 0; // No loyalty on teleport variants
		}
		
		@Override
		protected ItemStack getDefaultPickupItem() {
				return new ItemStack(ModItems.Weapons.HARDENED_TELEPORT_SWORD.get());
		}
}