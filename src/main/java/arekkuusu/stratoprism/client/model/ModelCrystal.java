/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.client.model;

import arekkuusu.stratoprism.api.state.enums.CrystalVariant;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.opengl.GL11;

public class ModelCrystal {

	private IBakedModel crystalAltar;
	private IBakedModel vastatioAltar;

	public  ModelCrystal(){
		try{

			//TODO: Finish this thing
			OBJModel model = (OBJModel)OBJLoader.INSTANCE.loadModel(new ResourceLocation("stratoprism:models/block/crystal.obj"));

			IModel craftingModel = ((OBJModel)model.retexture(ImmutableMap.of("#crystal", "stratoprism:model/crystal"))).process(ImmutableMap.of("flip-v", "true"));

			VertexFormat format = Attributes.DEFAULT_BAKED_FORMAT;

		} catch(Exception e) {
			throw new ReportedException(new CrashReport("Error making crystal submodels!", e));
		}
	}

	public void renderCrystal(CrystalVariant variant) {
		switch (variant) {
			case ALTAR:
				renderModel(crystalAltar);
				break;
			case VASTATIO:
				renderModel(vastatioAltar);
				break;
		}
	}

	private void renderModel(IBakedModel model) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);

		for(BakedQuad bakedquad : model.getQuads(null, null, 0))
			LightUtil.renderQuadColor(worldrenderer, bakedquad, -1);
		tessellator.draw();
	}
}
