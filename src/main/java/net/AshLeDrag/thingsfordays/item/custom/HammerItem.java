package net.AshLeDrag.thingsfordays.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {
		private static int width_range = 1;
		private static int depth_range = 1;
		
		public HammerItem(Tier tier, Properties properties, int rngw, int rngd, TagKey<Block> block) {
				super(tier, block, properties);
				
				assert rngw%2 == 1;
				
				width_range = (rngw-1)/2;
				depth_range = rngd-1;
		}
		
		public static List<BlockPos> getBlocksToBeDestroyed( BlockPos initalBlockPos, ServerPlayer player) {
				List<BlockPos> positions = new ArrayList<>();
				
				BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
						(player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
						ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
				if(traceResult.getType() == HitResult.Type.MISS) {
						return positions;
				}
				
				if(traceResult.getDirection() == Direction.UP) {
						for(int y = -depth_range; y <=0; y++) {
								for (int x = -width_range; x <= width_range; x++) {
										for (int z = -width_range; z <= width_range; z++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				if(traceResult.getDirection() == Direction.DOWN) {
						for(int y = 0; y <=depth_range; y++) {
								for (int x = -width_range; x <= width_range; x++) {
										for (int z = -width_range; z <= width_range; z++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				
				
				
				if(traceResult.getDirection() == Direction.NORTH) {
						for(int z = 0; z <=depth_range; z++) {
								for (int x = -width_range; x <= width_range; x++) {
										for (int y = -width_range; y <= width_range; y++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				if(traceResult.getDirection() == Direction.SOUTH) {
						for(int z = -depth_range; z <=0; z++) {
								for (int x = -width_range; x <= width_range; x++) {
										for (int y = -width_range; y <= width_range; y++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				
				
				
				if(traceResult.getDirection() == Direction.EAST) {
						for(int x = -depth_range; x <=0; x++) {
								for (int z = -width_range; z <= width_range; z++) {
										for (int y = -width_range; y <= width_range; y++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				if(traceResult.getDirection() == Direction.WEST) {
						for(int x = 0; x <=depth_range; x++) {
								for (int z = -width_range; z <= width_range; z++) {
										for (int y = -width_range; y <= width_range; y++) {
												positions.add(new BlockPos(
														initalBlockPos.getX() + x,
														initalBlockPos.getY() + y,
														initalBlockPos.getZ() + z
												));
										}
								}
						}
				}
				
				return positions;
		}
		
		
}
