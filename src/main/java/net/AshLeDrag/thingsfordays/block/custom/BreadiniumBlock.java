package net.AshLeDrag.thingsfordays.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BreadiniumBlock extends HorizontalDirectionalBlock {
		public static final MapCodec<BreadiniumBlock> CODEC = simpleCodec(BreadiniumBlock::new);
		
		@Override protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
				VoxelShape shape = Shapes.empty();
				shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
				shape = Shapes.join(shape, Shapes.box(0.03125, 0.5, 0.03125, 0.96875, 0.625, 0.96875), BooleanOp.OR);
				shape = Shapes.join(shape, Shapes.box(0.0625, 0.609375, 0.0625, 0.9375, 0.671875, 0.9375), BooleanOp.OR);
				shape = Shapes.join(shape, Shapes.box(0.1875, 0.640625, 0.1875, 0.8125, 0.703125, 0.8125), BooleanOp.OR);
				
				return shape;
		}
		
		public BreadiniumBlock(Properties properties) {
				super(properties);
		}
		
		@Override protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
				return CODEC;
		}
		
		@Override public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
				return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		}
		
		@Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
				builder.add(FACING);
		}
}
