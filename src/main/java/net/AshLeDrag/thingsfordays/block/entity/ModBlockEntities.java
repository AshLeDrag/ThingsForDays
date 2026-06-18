package net.AshLeDrag.thingsfordays.block.entity;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
		public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ThingsForDays.MOD_ID);
		
		public static final Supplier<BlockEntityType<HolderBlockEntity>> HOLDER_BE = BLOCK_ENTITIES.register("holder", () -> BlockEntityType.Builder.of(
				HolderBlockEntity::new, ModBlocks.HOLDER.get()).build(null));
		
		public static final Supplier<BlockEntityType<WeaponForgeBlockEntity>> WEAPON_FORGE_BE = BLOCK_ENTITIES.register("weapon_forge", () -> BlockEntityType.Builder.of(
				WeaponForgeBlockEntity::new, ModBlocks.WEAPON_FORGE.get()).build(null));
		
		
		public static void register(IEventBus bus) {
				BLOCK_ENTITIES.register(bus);
		}
}
