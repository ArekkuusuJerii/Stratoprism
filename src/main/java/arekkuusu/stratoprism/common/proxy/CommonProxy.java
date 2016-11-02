package arekkuusu.stratoprism.common.proxy;

import arekkuusu.stratoprism.api.item.capability.DefaultVastatioCapability;
import arekkuusu.stratoprism.api.item.capability.IVastatioCapability;
import arekkuusu.stratoprism.api.item.capability.VastatioCapabilityStorage;
import arekkuusu.stratoprism.api.item.capability.VastatioProvider;
import arekkuusu.stratoprism.api.recipe.PrismRecipes;
import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.block.tile.BlockCrystalAltar;
import arekkuusu.stratoprism.common.block.tile.ModBlocks;
import arekkuusu.stratoprism.common.item.ItemBlockColors;
import arekkuusu.stratoprism.common.item.prism.ItemBrokenPrism;
import arekkuusu.stratoprism.common.item.prism.ItemPrism;
import arekkuusu.stratoprism.common.item.prism.ItemPrismShard;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.concurrent.Callable;

@Mod.EventBusSubscriber
public class CommonProxy {

	public static SimpleNetworkWrapper network;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				new ItemPrismShard(),
				new ItemPrism(),
				new ItemBrokenPrism()
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
		CapabilityManager.INSTANCE.register(IVastatioCapability.class, new VastatioCapabilityStorage(), DefaultVastatioCapability::new);

		network = NetworkRegistry.INSTANCE.newSimpleChannel("stratoprism");
		network.registerMessage(MessageVastatioUpdate.CapsMessageHandler.class, MessageVastatioUpdate.class, 2, Side.CLIENT);
	}

	public void init(FMLInitializationEvent event){
		PrismRecipes.init();
	}
}
