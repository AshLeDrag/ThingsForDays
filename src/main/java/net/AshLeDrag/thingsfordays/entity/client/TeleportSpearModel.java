package net.AshLeDrag.thingsfordays.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.entity.custom.TeleportSpearProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class TeleportSpearModel extends EntityModel<TeleportSpearProjectileEntity> {
		// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "teleport_spear"), "main");
		
		private final ModelPart bone;
		
		public TeleportSpearModel(ModelPart root) {
				this.bone = root.getChild("bone");
		}
		
		public static LayerDefinition createBodyLayer() {
				MeshDefinition meshdefinition = new MeshDefinition();
				PartDefinition partdefinition = meshdefinition.getRoot();
				
				PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -19.0F, 7.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
																											.texOffs(30, 10).addBox(-9.5F, -19.25F, 6.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(22, 0).addBox(-9.5F, -15.25F, 6.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(22, 0).addBox(-9.5F, -13.25F, 6.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(22, 0).addBox(-9.5F, -1.25F, 6.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(22, 0).addBox(-9.5F, 0.75F, 6.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(22, 0).addBox(-9.5F, -3.25F, 6.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
																											.texOffs(39, 20).addBox(-12.5F, -31.5F, 8.0F, 9.0F, 12.25F, 0.0F, new CubeDeformation(0.0F))
																											.texOffs(42, 35).addBox(-8.5F, -28.5F, 7.25F, 1.0F, 9.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));
				
				PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 21).addBox(-8.5F, -0.5F, 0.0F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 3.0F, 8.0F, 0.0F, 0.0F, -0.3927F));
				
				PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(9, 32).addBox(-0.5F, -0.5F, 0.0F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 3.0F, 8.0F, 0.0F, 0.0F, 0.3927F));
				
				return LayerDefinition.create(meshdefinition, 64, 64);
		}
		
		@Override
		public void setupAnim(
				TeleportSpearProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
		
		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
				bone.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
		}
}
