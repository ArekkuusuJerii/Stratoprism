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
import arekkuusu.stratoprism.api.state.enums.ParticleFX;
import arekkuusu.stratoprism.client.fx.CrystalParticle;
import arekkuusu.stratoprism.client.event.TextureStitcher;
import arekkuusu.stratoprism.client.fx.GasParticle;
import arekkuusu.stratoprism.client.render.tile.TileRenderCrystalAltar;
import arekkuusu.stratoprism.client.render.tile.TileRenderPrism;
import arekkuusu.stratoprism.common.block.tile.tile.TileCrystalAltar;
import arekkuusu.stratoprism.common.block.tile.tile.TilePrism;
import arekkuusu.stratoprism.common.lib.LibMod;
import arekkuusu.stratoprism.common.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
		MinecraftForge.EVENT_BUS.register(new TextureStitcher());
		OBJLoader.INSTANCE.addDomain(LibMod.MOD_ID);
		initRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		for (Block block : Block.REGISTRY) {
			if (block instanceof IModelRegister)
				((IModelRegister) block).registerModels();
		}

		for (Item item : Item.REGISTRY) {
			if (item instanceof IModelRegister)
				((IModelRegister) item).registerModels();
		}
	}

	private void initRenderers() {
		//Tiles
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalAltar.class, new TileRenderCrystalAltar());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePrism.class, new TileRenderPrism());
	}

	@Override
	public void sparkleFX(ParticleFX particleFX, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int timelive) {
		if (!doParticle()) return; //Maybe two separate methods instead??

		Particle particle;
		switch (particleFX) {
			case CRYSTAL_SQUARE:
				particle = new CrystalParticle(Minecraft.getMinecraft().theWorld, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, timelive);
				break;
			case GAS_ROUND:
				particle = new GasParticle(Minecraft.getMinecraft().theWorld, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, timelive);
				break;
			default:
				particle = new Particle(Minecraft.getMinecraft().theWorld, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		}
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	private boolean doParticle() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			return false;

		float chance = 1F;
		if (Minecraft.getMinecraft().gameSettings.particleSetting == 1)
			chance = 0.6F;
		else if (Minecraft.getMinecraft().gameSettings.particleSetting == 2)
			chance = 0.2F;

		return chance == 1F || Math.random() < chance;
	}
}
