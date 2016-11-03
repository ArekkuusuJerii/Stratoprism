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
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ModelCrystal {

	private IBakedModel crystal;
	private IBakedModel vastatio;

	public ModelCrystal() {
		try {
			/*
			 * Model crystal
			 */
			IModel crystal = OBJLoader.INSTANCE.loadModel(ResourceLocations.MODEL_CRYSTAL); //.obj Location

			VertexFormat format = Attributes.DEFAULT_BAKED_FORMAT; //Some Format

			this.crystal = crystal.bake(TRSRTransformation.identity(), format, ModelLoader.defaultTextureGetter()); //Actual model to use
			/*
			 * Model vastatioAltar
			 */
		} catch (Exception e) {
			throw new ReportedException(new CrashReport("Error making crystal submodels!", e));
		}
	}

	public void renderCrystal(CrystalVariant variant) {
		switch (variant) {
			case CRYSTAL:
				renderModel(crystal);
				break;
			case VASTATIO:
				renderModel(vastatio);
				break;
		}
	}

	private void renderModel(IBakedModel model) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.ITEM);

		for (BakedQuad bakedquad : model.getQuads(null, null, 0))
			LightUtil.renderQuadColor(vertexBuffer, bakedquad, -1);

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
