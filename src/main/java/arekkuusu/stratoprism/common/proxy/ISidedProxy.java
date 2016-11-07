package arekkuusu.stratoprism.common.proxy;

import arekkuusu.stratoprism.api.state.enums.ParticleFX;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface ISidedProxy {
	void preInit(FMLPreInitializationEvent event);
	void init(FMLInitializationEvent event);
	void sparkleFX(ParticleFX particleFX, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int timelive);
}
