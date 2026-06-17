package net.AshLeDrag.thingsfordays.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.entity.custom.TeleportSpearProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TeleportSpearRendered extends EntityRenderer<TeleportSpearProjectileEntity> {
		private TeleportSpearModel model;
		
		public TeleportSpearRendered(EntityRendererProvider.Context context) {
				super(context);
				this.model = new TeleportSpearModel(context.bakeLayer(TeleportSpearModel.LAYER_LOCATION));
		}
		
		@Override
		public void render(
				TeleportSpearProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
				poseStack.pushPose();
				poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
				poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
				VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)),false, false);
				this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
				poseStack.popPose();
				super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		}
		
		private static final ResourceLocation[] FRAMES = new ResourceLocation[] {
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile000.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile001.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile002.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile003.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile004.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile005.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile006.png"),
				ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/teleport_sword/tile007.png"),
		};
		
		private static final int TICKS_PER_FRAME = 5;
		
		@Override
		public ResourceLocation getTextureLocation(
				TeleportSpearProjectileEntity entity) {
				int frame = (int)(entity.tickCount / TICKS_PER_FRAME) % FRAMES.length;
				return FRAMES[frame];
		}
}
