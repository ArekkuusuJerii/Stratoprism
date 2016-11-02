/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of Stratoprism. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Stratoprism
 *
 * Stratoprism is Open Source and distributed under the
 * MIT Licence: https://github.com/ArekkuusuJerii/Stratoprism/blob/master/LICENSE
 */
package arekkuusu.stratoprism.client.proxy;

import arekkuusu.stratoprism.api.item.IModelRegister;
import arekkuusu.stratoprism.client.render.tile.TileRenderCrystalAltar;
import arekkuusu.stratoprism.common.block.tile.tile.TileCrystalAltar;
import arekkuusu.stratoprism.common.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		initRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		for(Block block : Block.REGISTRY) {
			if(block instanceof IModelRegister)
				((IModelRegister) block).registerModels();
		}

		for(Item item : Item.REGISTRY) {
			if(item instanceof IModelRegister)
				((IModelRegister) item).registerModels();
		}
	}

	private void initRenderers() {
		//Tiles
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalAltar.class, new TileRenderCrystalAltar());
	}
}
