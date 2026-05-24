package net.AshLeDrag.thingsfordays.item;

import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, ThingsForDays.MOD_ID);

    public static final Supplier<CreativeModeTab> TOOLSFORDAYS = CREATIVE_MODE_TAB
            .register("toolsfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                              .title(Component.translatable("creativetab.thingsfordays.tfd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModItems.CHISEL);
                              })).build());

    public static final Supplier<CreativeModeTab> WEAPONSFORDAYS = CREATIVE_MODE_TAB
            .register("weaponsfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "toolsfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.wfd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModBlocks.BISMUTH_ORE);
                              })).build());

    public static final Supplier<CreativeModeTab> ARMORFORDAYS = CREATIVE_MODE_TAB
            .register("armorfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "weaponsfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.afd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModBlocks.BISMUTH_ORE);
                              })).build());

    public static final Supplier<CreativeModeTab> ORESFORDAYS = CREATIVE_MODE_TAB
            .register("oresfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModBlocks.BISMUTH_ORE.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "armorfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.ofd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModBlocks.BISMUTH_ORE);
                                  output.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
                                  output.accept(ModItems.RAW_BISMUTH);
                              })).build());

    public static final Supplier<CreativeModeTab> BLOCKSFORDAYS = CREATIVE_MODE_TAB
            .register("blocksfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "oresfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.bfd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModBlocks.MANA_BLOCK);
                              })).build());

    public static final Supplier<CreativeModeTab> RESOURCESFORDAYS = CREATIVE_MODE_TAB
            .register("resourcesfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModItems.PURE_MANA.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "blocksfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.rfd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModItems.PURE_MANA);
                              })).build());

    public static final Supplier<CreativeModeTab> FOODFORDAYS = CREATIVE_MODE_TAB
            .register("foodfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModItems.RADISH.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "resourcesfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.fofd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModItems.RADISH);
                              })).build());

    public static final Supplier<CreativeModeTab> FUELFORDAYS = CREATIVE_MODE_TAB
            .register("fuelfordaystab",
                      () -> CreativeModeTab.builder()
                              .icon(() -> new ItemStack(ModBlocks.BISMUTH_ORE.get()))
                              .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "foodfordaystab"))
                              .title(Component.translatable("creativetab.thingsfordays.fufd_tab"))
                              .displayItems(((itemDisplayParameters, output) -> {
                                  output.accept(ModItems.STARLIGHT_ASHES);
                              })).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
