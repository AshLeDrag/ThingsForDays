package net.AshLeDrag.thingsfordays.item;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.item.custom.ChiselItem;
import net.AshLeDrag.thingsfordays.item.custom.FuelItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThingsForDays.MOD_ID);

    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PURE_MANA = ITEMS.register("pure_mana", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MANA_ESSENCE = ITEMS.register("mana_essence", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(new Item.Properties().durability(32)) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.thingsfordays.radish.tooltip"));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    });

    public static final DeferredItem<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(ModFoodProperties.RADISH)));

    public static final DeferredItem<Item> STARLIGHT_ASHES = ITEMS.register("starlight_ashes", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
