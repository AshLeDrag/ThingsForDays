package net.AshLeDrag.thingsfordays.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.entity.custom.SteelThrowableProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SteelThrowableRendered extends EntityRenderer<SteelThrowableProjectileEntity> {
		private SteelThrowableModel model;
		
		public SteelThrowableRendered(EntityRendererProvider.Context context) {
				super(context);
				this.model = new SteelThrowableModel(context.bakeLayer(SteelThrowableModel.LAYER_LOCATION));
		}
		
		@Override
		public void render(SteelThrowableProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
				poseStack.pushPose();
				poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
				poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
				VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)),false, false);
				this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
				poseStack.popPose();
				super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		}
		
		@Override
		public ResourceLocation getTextureLocation(SteelThrowableProjectileEntity entity) {
				return ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "textures/entity/steel_throwable/steel_throwable.png");
		}
}
