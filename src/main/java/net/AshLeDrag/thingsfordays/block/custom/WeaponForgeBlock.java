package net.AshLeDrag.thingsfordays.block.custom;

import com.mojang.serialization.MapCodec;
import net.AshLeDrag.thingsfordays.block.entity.ModBlockEntities;
import net.AshLeDrag.thingsfordays.block.entity.WeaponForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WeaponForgeBlock extends BaseEntityBlock {
		public static final MapCodec<WeaponForgeBlock> CODEC = simpleCodec(WeaponForgeBlock::new);
		
		
		public WeaponForgeBlock(Properties properties) {
				super(properties);
		}
		
		@Override protected MapCodec<? extends BaseEntityBlock> codec() {
				return CODEC;
		}
		
		@Override public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
				return new WeaponForgeBlockEntity(blockPos, blockState);
		}
		
		@Override protected RenderShape getRenderShape(BlockState state) {
				return RenderShape.MODEL;
		}
		
		@Override
		public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
				if (pState.getBlock() != pNewState.getBlock()) {
						BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
						if (blockEntity instanceof WeaponForgeBlockEntity growthChamberBlockEntity) {
								growthChamberBlockEntity.drops();
						}
				}
				
				super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
		
		@Override
		protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
				if (!pLevel.isClientSide()) {
						BlockEntity entity = pLevel.getBlockEntity(pPos);
						if(entity instanceof WeaponForgeBlockEntity weaponForgeBlockEntity) {
								((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(weaponForgeBlockEntity, Component.literal("Weapon's Forge")), pPos);
						} else {
								throw new IllegalStateException("Our Container provider is missing!");
						}
				}
				
				return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
		}
		
		@Nullable
		@Override
		public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
				if(level.isClientSide()) {
						return null;
				}
				
				return createTickerHelper(blockEntityType, ModBlockEntities.WEAPON_FORGE_BE.get(),
						(level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState));
		}
}
