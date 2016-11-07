package arekkuusu.stratoprism.client.render.tile;

import arekkuusu.stratoprism.api.state.enums.CrystalVariant;
import arekkuusu.stratoprism.client.model.ModelCrystal;
import arekkuusu.stratoprism.client.proxy.ResourceLocations;
import arekkuusu.stratoprism.common.block.tile.tile.TilePrism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TileRenderPrism extends TileEntitySpecialRenderer<TilePrism> {

	private ModelCrystal MODEL_CRYSTAL;

	@Override
	public void renderTileEntityAt(TilePrism te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate(x + 0.5F, y + 0.4, z + 0.5);
		renderCrystalMultiple(te);
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

	public void renderCrystalMultiple(TilePrism te) {
		if (MODEL_CRYSTAL == null) MODEL_CRYSTAL = new ModelCrystal();
		float maxUpAndDown = 0.10F;
		float speed = 2;
		float angle = 0;

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceLocations.TEXTURE_DUMMY_BLUE);

		float toDegrees = (float) Math.PI / 180F;
		angle += speed * te.tickCount;
		if (angle > 360) angle -= 360;

		GlStateManager.translate(0, maxUpAndDown * Math.sin(angle * toDegrees), 0);
		GlStateManager.rotate(te.tickCount * 6, 0.0F, 1.0F, 0.0F);

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.50, 0.50, 0.50);
		GlStateManager.translate(0, 1.65, 0);
		GlStateManager.rotate(15, 1, 0, 0);
		MODEL_CRYSTAL.renderCrystal(CrystalVariant.CRYSTAL_REFINED);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.25, 0.25, 0.25);
		GlStateManager.translate(0, 1.2, 0.7);
		GlStateManager.rotate(-15, 0, 0, 1);
		MODEL_CRYSTAL.renderCrystal(CrystalVariant.CRYSTAL_REFINED);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.1, 0.1, 0.1);
		GlStateManager.translate(0.40, 0.25, 0);
		GlStateManager.rotate(15, 1, 0, 0);
		MODEL_CRYSTAL.renderCrystal(CrystalVariant.CRYSTAL_REFINED);
		GlStateManager.popMatrix();

		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}
