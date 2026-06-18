package net.AshLeDrag.thingsfordays.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WeaponForgeScreen extends AbstractContainerScreen<WeaponForgeMenu> {
		private static final ResourceLocation GUI_TEXTURE =
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,"textures/gui/weapon_forge/weapon_forge_gui.png");
		private static final ResourceLocation ARROW_TEXTURE =
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID,"textures/gui/arrow_progress.png");
		
		public WeaponForgeScreen(WeaponForgeMenu menu, Inventory playerInventory, Component title) {
				super(menu, playerInventory, title);
		}
		
		@Override
		protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, GUI_TEXTURE);
				
				int x = (width - 243) / 2;
				int y = (height - 221) / 2;
				System.out.println(x + " " + y);
				guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, 243, 221);
				
				renderProgressArrow(guiGraphics, x, y);
		}
		
		private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
				if(menu.isCrafting()) {
						guiGraphics.blit(ARROW_TEXTURE,x + 73, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
				}
		}
		
		@Override
		public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
				super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
				this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
		}
}