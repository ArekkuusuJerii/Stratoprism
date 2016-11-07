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
import arekkuusu.stratoprism.client.proxy.ResourceLocations;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ModelCrystal {

	private IBakedModel crystalRaw;
	private IBakedModel crystalRefined;

	public ModelCrystal() {//FIXME: This SHIT doesnt use the whole UV given in the mtl!!!!!!! >:(
		try {
			VertexFormat format = Attributes.DEFAULT_BAKED_FORMAT; //Some Format
			/*
			 * Model crystalRaw
			 */
			IModel crystal = OBJLoader.INSTANCE.loadModel(ResourceLocations.MODEL_CRYSTAL_RAW);

			this.crystalRaw = crystal.bake(TRSRTransformation.identity(), format, ModelLoader.defaultTextureGetter()); //Actual model to use
			/*
			 * Model crystalRefined
			 */
			crystal = OBJLoader.INSTANCE.loadModel(ResourceLocations.MODEL_CRYSTAL_REFINED);

			this.crystalRefined = crystal.bake(TRSRTransformation.identity(), format, ModelLoader.defaultTextureGetter()); //Actual model to use
		} catch (Exception e) {
			throw new ReportedException(new CrashReport("Error making crystal submodels!", e));
		}
	}

	public void renderCrystal(CrystalVariant variant) {
		switch (variant) {
			case CRYSTAL_RAW:
				renderModel(crystalRaw);
				break;
			case CRYSTAL_REFINED:
				renderModel(crystalRefined);
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

	private IModelState hideGroups(List<String> groups) {
		return iModelState -> {
			if (iModelState.isPresent()) {
				UnmodifiableIterator<String> parts = Models.getParts(iModelState.get());
				if (parts.hasNext()) {
					String name = parts.next();
					if (!parts.hasNext() && groups.contains(name)) {
						return Optional.of(TRSRTransformation.identity());
					}
				}
			}
			return Optional.absent();
		};
	}
}
