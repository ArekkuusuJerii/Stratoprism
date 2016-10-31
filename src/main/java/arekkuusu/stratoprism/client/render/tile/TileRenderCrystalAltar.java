package arekkuusu.stratoprism.client.render.tile;

import arekkuusu.stratoprism.common.block.tile.tile.TileCrystalAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.DamageSource.lava;

public class TileRenderCrystalAltar extends TileEntitySpecialRenderer<TileCrystalAltar> {

	@Override
	public void renderTileEntityAt(TileCrystalAltar te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate(x + 0.5F, y + 0.78, z + 0.5);

		if (te.hasWater()) {
			float v = 0.150F;

			int items = 0;
			List<ItemStack> stackList = new ArrayList<>();
			for (int i = 0; i < te.getSizeInventory(); i++)
				if (te.itemHandler.getItemSimulate(i) != null) {
					stackList.add(te.itemHandler.getItemSimulate(i));
					items++;
				} else break;

			if (items > 0) {
				final float modifier = 6F;
				final float rotationModifier = 0.25F;
				final float radiusBase = 1.2F;
				final float radiusMod = 0.1F;

				double ticks = te.tickCount * 0.5;

				float offsetPerPetal = 360 / items;

				GlStateManager.pushMatrix();
				GlStateManager.translate(-0.05F, -0.3F, 0F);
				GlStateManager.scale(v, v, v);
				for (int i = 0; i < items; i++) {
					float offset = offsetPerPetal * i;
					float deg = (int) (ticks / rotationModifier % 360F + offset);
					float rad = deg * (float) Math.PI / 180F;
					float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
					float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
					float d0 = (float) (radiusX * Math.cos(rad));
					float d1 = (float) (radiusZ * Math.sin(rad));

					GlStateManager.pushMatrix();
					GlStateManager.translate(d0, 0, d1);
					float xRotate = (float) Math.sin(ticks * rotationModifier) / 2F;
					float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
					float zRotate = (float) Math.cos(ticks * rotationModifier) / 2F;

					v /= 2F;
					GlStateManager.translate(v, v, v);
					GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
					GlStateManager.translate(-v, -v, -v);
					v *= 2F;

					GlStateManager.color(1F, 1F, 1F, 1F);

					ItemStack item = stackList.get(i);

					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.GROUND);
					GlStateManager.popMatrix();
				}
				GlStateManager.popMatrix();
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Fluid fluid = FluidRegistry.WATER;

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableAlpha();

			float w = -0.425F;
			//GlStateManager.color(1F, 1F, 1F, 0.7F);
			GlStateManager.translate(w, -0.3F, w);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			float s = 0.0390625F;
			GlStateManager.scale(s, s, s);

			renderIcon(fluid.getStill());

			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
		}
		GlStateManager.popMatrix();
	}

	public void renderIcon(ResourceLocation loc) {
		TextureAtlasSprite par3Icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(loc.toString());
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		tessellator.getBuffer().pos(0, 22, 0).tex(par3Icon.getMinU(), par3Icon.getMaxV()).endVertex();
		tessellator.getBuffer().pos(22, 22, 0).tex(par3Icon.getMaxU(), par3Icon.getMaxV()).endVertex();
		tessellator.getBuffer().pos(22, 0, 0).tex(par3Icon.getMaxU(), par3Icon.getMinV()).endVertex();
		tessellator.getBuffer().pos(0, 0, 0).tex(par3Icon.getMinU(), par3Icon.getMinV()).endVertex();
		tessellator.draw();
	}
}
