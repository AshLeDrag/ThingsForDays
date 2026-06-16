package net.AshLeDrag.thingsfordays.entity.custom;

import net.AshLeDrag.thingsfordays.entity.ModEntities;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GatherSwordProjectileEntity extends SteelThrowableProjectileEntity {
		
		private static final EntityDataAccessor<Boolean> GATHER_RETURN =
				SynchedEntityData.defineId(GatherSwordProjectileEntity.class, EntityDataSerializers.BOOLEAN);
		
		public GatherSwordProjectileEntity(EntityType<? extends SteelThrowableProjectileEntity> entityType, Level level) {
				super(entityType, level);
		}
		
		public GatherSwordProjectileEntity(LivingEntity thrower, Level level, ItemStack pickupItemStack) {
				super(ModEntities.STEEL_THROWABLE.get(), thrower, level, pickupItemStack);
		}
		
		public GatherSwordProjectileEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
				super(ModEntities.STEEL_THROWABLE.get(), level, x, y, z, pickupItemStack);
		}
		
		@Override
		protected void defineSynchedData(SynchedEntityData.Builder builder) {
				super.defineSynchedData(builder);
				builder.define(GATHER_RETURN, false);
		}
		
		@Override
		protected void onHitBlock(BlockHitResult hitResult) {
				super.onHitBlock(hitResult);
				if (this.level().isClientSide) return;
				
				LivingEntity owner = this.getOwner() instanceof LivingEntity le ? le : null;
				if (owner == null) return;
				
				this.dealtDamage = true;
				this.setNoPhysics(true);
				this.entityData.set(GATHER_RETURN, true);
				this.playSound(SoundEvents.TRIDENT_RETURN, 1.0F, 0.8F);
		}
		
		@Override
		public void tick() {
				if (this.inGroundTime > 4) {
						this.dealtDamage = true;
				}
				
				Entity entity = this.getOwner();
				boolean gatherReturn = this.entityData.get(GATHER_RETURN);
				
				if (gatherReturn && this.dealtDamage && entity != null) {
						if (!this.isAcceptableReturnOwner()) {
								if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
										this.spawnAtLocation(this.getPickupItem(), 0.1F);
								}
								this.discard();
						} else {
								this.setNoPhysics(true);
								int returnSpeed = 3;
								Vec3 vec3 = entity.getEyePosition().subtract(this.position());
								this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * returnSpeed, this.getZ());
								if (this.level().isClientSide) {
										this.yOld = this.getY();
								}
								this.setDeltaMovement(this.getDeltaMovement().scale(0.95)
																	 .add(vec3.normalize().scale(0.05 * returnSpeed)));
								if (this.clientSideReturnTridentTickCount == 0) {
										this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
								}
								
								// Pull nearby items toward owner
								if (!this.level().isClientSide) {
										AABB searchBox = new AABB(this.position(), this.position()).inflate(8.0);
										List<ItemEntity> nearbyItems = this.level().getEntitiesOfClass(ItemEntity.class, searchBox);
										Vec3 ownerPos = entity.position();
										for (ItemEntity item : nearbyItems) {
												Vec3 pull = ownerPos.subtract(item.position()).normalize().scale(0.3);
												item.setDeltaMovement(item.getDeltaMovement().add(pull));
										}
								}
								
								++this.clientSideReturnTridentTickCount;
						}
				} else {
						// Fall back to base loyalty logic for normal return
						super.tick();
						return;
				}
				
				// Still need to call Entity.tick() for physics
				// We skip AbstractArrow's super.tick() here since we handle movement above
				// but we need the entity base tick — call it via the chain
				// Note: calling super.tick() would double-process loyalty; we handle it manually above
		}
		
		@Override
		protected ItemStack getDefaultPickupItem() {
				return new ItemStack(ModItems.Weapons.GATHER_SWORD.get());
		}
}