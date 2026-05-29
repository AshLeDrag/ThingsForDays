package net.AshLeDrag.thingsfordays.event;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;



@EventBusSubscriber(modid = ThingsForDays.MOD_ID, value =  Dist.CLIENT)
public class ModClientEvents {
		@SubscribeEvent
		public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
				if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.Breadinium.Weapons.BOW.get()) {
						float fovModifier = 1f;
						int ticksUsingItem = event.getPlayer().getTicksUsingItem();
						float deltaTicks = (float)ticksUsingItem / 20f;
						if(deltaTicks > 1f) {
								deltaTicks = 1f;
						} else {
								deltaTicks *= deltaTicks;
						}
						fovModifier *= 1f - deltaTicks * 0.15f;
						event.setNewFovModifier(fovModifier);
				}
				if(event.getPlayer().hasItemInSlot(EquipmentSlot.MAINHAND) && event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem() == ModItems.Breadinium.Weapons.SWORD.get()
				&& event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getDisplayName().getString().contains("Achille pro vision")) {
						event.setNewFovModifier(1000f);
				}
		}
}