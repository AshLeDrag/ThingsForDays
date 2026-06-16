package net.AshLeDrag.thingsfordays.entity.custom;

import net.AshLeDrag.thingsfordays.entity.ModEntities;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HardenedSwordProjectileEntity extends SteelThrowableProjectileEntity {
		
		private static final EntityDataAccessor<Boolean> HAS_EXPLODED =
				SynchedEntityData.defineId(HardenedSwordProjectileEntity.class, EntityDataSerializers.BOOLEAN);
		
		private static final float RADIUS = 8.5F;
		private static final int EXPLODE_DELAY = 10;
		private int explodeDelayTicks = -1;
		private BlockPos pendingExplosionPos = null;
		
		public HardenedSwordProjectileEntity(EntityType<? extends SteelThrowableProjectileEntity> entityType, Level level) {
				super(entityType, level);
		}
		
		public HardenedSwordProjectileEntity(LivingEntity thrower, Level level, ItemStack pickupItemStack) {
				super(ModEntities.STEEL_THROWABLE.get(), thrower, level, pickupItemStack);
		}
		
		public HardenedSwordProjectileEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
				super(ModEntities.STEEL_THROWABLE.get(), level, x, y, z, pickupItemStack);
		}
		
		@Override
		protected void defineSynchedData(SynchedEntityData.Builder builder) {
				super.defineSynchedData(builder);
				builder.define(HAS_EXPLODED, false);
		}
		
		@Override
		public void tick() {
				if (explodeDelayTicks > 0) {
						explodeDelayTicks--;
						doPreExplosionEffects();
				} else if (explodeDelayTicks == 0) {
						explodeDelayTicks = -1;
						if (pendingExplosionPos != null) {
								doHardenedExplosion(RADIUS, pendingExplosionPos);
								pendingExplosionPos = null;
						}
				}
				super.tick();
		}
		
		@Override
		protected void onHitBlock(BlockHitResult hitResult) {
				if (this.level().isClientSide) {
						super.onHitBlock(hitResult);
						return;
				}
				
				ServerLevel serverLevel = (ServerLevel) this.level();
				BlockPos landed = hitResult.getBlockPos();
				BlockState state = serverLevel.getBlockState(landed);
				
				// Pass through leaves and a limited number of logs
				if (state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS)) {
						serverLevel.destroyBlock(landed, true, this);
						this.dealtDamage = false;
						return;
				}
				
				super.onHitBlock(hitResult);
				this.dealtDamage = true;
				pendingExplosionPos = landed;
				explodeDelayTicks = EXPLODE_DELAY;
				this.setDeltaMovement(Vec3.ZERO);
		}
		
		private void doPreExplosionEffects() {
				if (!(this.level() instanceof ServerLevel serverLevel)) return;
				serverLevel.sendParticles(ParticleTypes.FLAME,
						this.getX(), this.getY(), this.getZ(), 5, 0.3, 0.3, 0.3, 0.05);
		}
		
		private void doHardenedExplosion(float radius, BlockPos landedPos) {
				if (!(this.level() instanceof ServerLevel serverLevel)) return;
				if (this.entityData.get(HAS_EXPLODED)) return;
				this.entityData.set(HAS_EXPLODED, true);
				
				Entity owner = this.getOwner();
				DamageSource damageSource = this.damageSources()
															 .explosion(this, owner instanceof LivingEntity le ? le : null);
				
				// Entity damage with falloff
				AABB explosionBox = new AABB(this.position(), this.position()).inflate(radius + 2.0);
				List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, explosionBox);
				for (LivingEntity target : entities) {
						if (target == owner) continue;
						double distance = target.position().distanceTo(this.position());
						if (distance > radius + 2.0) continue;
						float damage = (float) (25.0 * (1.0 - (distance / (radius + 2.0))));
						if (damage > 0) {
								target.hurt(damageSource, damage);
								Vec3 knockback = target.position().subtract(this.position())
															  .normalize().scale(3.0).add(0, 1.2, 0);
								target.setDeltaMovement(target.getDeltaMovement().add(knockback));
						}
				}
				
				// Bowl-shaped crater
				int craterRadius = (int) radius;
				for (int x = -craterRadius; x <= craterRadius; x++) {
						for (int z = -craterRadius; z <= craterRadius; z++) {
								for (int y = Mth.ceil(-craterRadius * 0.75); y <= Mth.ceil(craterRadius * 1.25); y++) {
										double dist = Math.sqrt(x * x + z * z + y * y);
										if (dist > craterRadius) continue;
										
										BlockPos pos = landedPos.offset(x, y, z);
										if (pos.equals(landedPos)) continue;
										if (pos.getY() < 5) continue;
										
										BlockState state = serverLevel.getBlockState(pos);
										if (state.isAir()) continue;
										
										float threshold;
										if (dist > craterRadius * 0.66) threshold = 6.0F;
										else if (dist > craterRadius * 0.33) threshold = 12.0F;
										else threshold = 30.0F;
										
										float resistance = state.getBlock().getExplosionResistance();
										boolean drop = ThreadLocalRandom.current().nextInt(1, craterRadius + 1) > craterRadius - 2;
										if (resistance < threshold) {
												serverLevel.destroyBlock(pos, drop, this);
										}
								}
						}
				}
				
				// Particles
				serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
						this.getX(), this.getY(), this.getZ(), 3, 0.5, 0.2, 0.5, 0.1);
				for (int i = 0; i < 24; i++) {
						double angle = (Math.PI * 2 / 24) * i;
						double rx = Math.cos(angle) * radius;
						double rz = Math.sin(angle) * radius;
						serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
								this.getX() + rx, this.getY() + 0.3, this.getZ() + rz,
								3, 0.2, 0.1, 0.2, 0.03);
						serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
								this.getX() + rx * 0.5, this.getY() + 0.5, this.getZ() + rz * 0.5,
								2, 0.1, 0.2, 0.1, 0.04);
				}
				serverLevel.sendParticles(ParticleTypes.FLAME,
						this.getX(), this.getY(), this.getZ(), 40, 1.8, 0.3, 1.8, 0.18);
				serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
						this.getX(), this.getY() + 0.5, this.getZ(), 20, 1.2, 0.5, 1.2, 0.12);
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DIRT.defaultBlockState()),
						this.getX(), this.getY() + 0.5, this.getZ(), 60, 2.0, 0.4, 2.0, 0.25);
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
						this.getX(), this.getY() + 0.5, this.getZ(), 40, 1.5, 0.3, 1.5, 0.2);
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.DIRT.defaultBlockState()),
						this.getX(), this.getY() + 3.0, this.getZ(), 30, 2.0, 0.5, 2.0, 0.0);
				serverLevel.sendParticles(ParticleTypes.LAVA,
						this.getX(), this.getY() + 0.3, this.getZ(), 15, 1.5, 0.2, 1.5, 0.0);
				for (int i = 0; i < 6; i++) {
						serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
								this.getX() + (serverLevel.random.nextFloat() - 0.5F) * 0.5,
								this.getY() + i * 1.2,
								this.getZ() + (serverLevel.random.nextFloat() - 0.5F) * 0.5,
								1, 0.1, 0.0, 0.1, 0.01);
				}
				
				// Sounds
				serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(),
						SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS,
						5.0F, 0.7F + serverLevel.random.nextFloat() * 0.3F);
				serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(),
						SoundEvents.IRON_GOLEM_HURT, SoundSource.PLAYERS, 3.0F, 0.4F);
				serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(),
						SoundEvents.STONE_BREAK, SoundSource.BLOCKS,
						3.0F, 0.6F + serverLevel.random.nextFloat() * 0.2F);
				serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(),
						SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 2.0F, 1.8F);
				serverLevel.playSound(null, this.getX() + 1, this.getY(), this.getZ() + 1,
						SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 3.0F, 0.5F);
		}
		
		@Override
		public void readAdditionalSaveData(CompoundTag compound) {
				super.readAdditionalSaveData(compound);
				this.explodeDelayTicks = compound.getInt("ExplodeDelay");
		}
		
		@Override
		public void addAdditionalSaveData(CompoundTag compound) {
				super.addAdditionalSaveData(compound);
				compound.putInt("ExplodeDelay", this.explodeDelayTicks);
		}
		
		@Override
		protected ItemStack getDefaultPickupItem() {
				return new ItemStack(ModItems.Weapons.HARDENED_SWORD.get());
		}
}