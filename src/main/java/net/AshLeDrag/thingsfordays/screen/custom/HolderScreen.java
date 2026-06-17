package net.AshLeDrag.thingsfordays.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HolderScreen extends AbstractContainerScreen<HolderMenu> {
		public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/gui/holder/holder_gui.png");
		
		
		public HolderScreen(HolderMenu menu, Inventory playerInventory, Component title) {
				super(menu, playerInventory, title);
		}
		
		
		@Override
		protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, GUI_TEXTURE);
				
				int x = (width - imageWidth) / 2;
				int y = (height - imageHeight) / 2;
				
				guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
		}
}
