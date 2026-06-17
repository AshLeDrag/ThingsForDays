package net.AshLeDrag.thingsfordays.event;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.entity.client.SteelThrowableModel;
import net.AshLeDrag.thingsfordays.entity.client.TeleportSpearModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ThingsForDays.MOD_ID)
public class ModEventBusEvents {
		@SubscribeEvent
		public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
				event.registerLayerDefinition(SteelThrowableModel.LAYER_LOCATION, SteelThrowableModel::createBodyLayer);
				event.registerLayerDefinition(TeleportSpearModel.LAYER_LOCATION, TeleportSpearModel::createBodyLayer);
		}
}

