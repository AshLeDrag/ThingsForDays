package net.AshLeDrag.thingsfordays.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.AshLeDrag.thingsfordays.ThingsForDays;
import net.AshLeDrag.thingsfordays.entity.custom.SteelThrowableProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SteelThrowableModel extends EntityModel<SteelThrowableProjectileEntity> {
		// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ThingsForDays.MOD_ID, "steel_throwable"), "main");
		private final ModelPart bb_main;
		
		public SteelThrowableModel(ModelPart root) {
				this.bb_main = root.getChild("bb_main");
		}
		
		public static LayerDefinition createBodyLayer() {
				MeshDefinition meshdefinition = new MeshDefinition();
				PartDefinition partdefinition = meshdefinition.getRoot();
				
				PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
				
				PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 18).addBox(0.6854F, -8.5F, -2.299F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 0.0F, -1.1781F, 0.0F));
				
				PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 18).addBox(0.6484F, -8.5F, -1.6828F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, -3.1416F, -1.1781F, 3.1416F));
				
				PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 32).addBox(-0.125F, -11.8F, -1.1F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 0.0F, -1.5708F, 0.0F));
				
				PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 16).addBox(-1.0884F, 5.5F, -0.9116F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
																										  .texOffs(24, 16).addBox(-3.139F, -8.5F, -3.1036F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
																										  .texOffs(24, 0).addBox(1.0329F, -8.5F, 1.2097F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, -3.1416F, -0.7854F, 3.1416F));
				
				PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 0).addBox(-2.6854F, -8.5F, -2.299F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 3.1416F, -1.1781F, -3.1416F));
				
				PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 0).addBox(-1.025F, -12.165F, 4.3116F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 0.0F, -1.5708F, 0.3927F));
				
				PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-2.7441F, -8.5F, -1.4518F, 2.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 0.0F, -1.1781F, 0.0F));
				
				PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(32, 8).addBox(-1.025F, -12.1841F, -6.2654F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, -15.5F, 0.0F, 0.0F, -1.5708F, -0.3927F));
				
				return LayerDefinition.create(meshdefinition, 64, 64);
		}
		
		@Override
		public void setupAnim(SteelThrowableProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
		}
		
		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
				bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
		}
}
