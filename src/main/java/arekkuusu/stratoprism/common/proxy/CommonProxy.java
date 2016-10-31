package arekkuusu.stratoprism.common.proxy;

import arekkuusu.stratoprism.api.recipe.PrismRecipes;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.block.tile.BlockCrystalAltar;
import arekkuusu.stratoprism.common.block.tile.ModBlocks;
import arekkuusu.stratoprism.common.handler.PacketDispatcher;
import arekkuusu.stratoprism.common.item.ItemBlockColors;
import arekkuusu.stratoprism.common.item.prism.ItemPrismFragment;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

	PacketDispatcher netDispatcher = new PacketDispatcher();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				new ItemPrismFragment()
		);

		event.getRegistry().registerAll(
			itemBlock(ModBlocks.CRYSTAL_ALTAR)
		);
	}

	private static Item itemBlockColors(Block block) {return new ItemBlockColors(block).setRegistryName(block.getRegistryName());}

	private static Item itemBlock(Block block) {
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		ModelHandler.registerTileEntitieRenders();

		event.getRegistry().registerAll(
			new BlockCrystalAltar()
		);
	}

	public void preInit(FMLPreInitializationEvent event){

	}

	public void init(FMLInitializationEvent event){
		PrismRecipes.init();
	}
}
