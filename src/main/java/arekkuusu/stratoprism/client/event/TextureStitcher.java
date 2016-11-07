package arekkuusu.stratoprism.client.event;

import arekkuusu.stratoprism.client.proxy.ResourceLocations;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcher {

	@SubscribeEvent
	public void stitcherEventPre(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(ResourceLocations.TEXTURE_CRYSTAL_PARTICLE);
		event.getMap().registerSprite(ResourceLocations.TEXTURE_VASTATIO_GAS);
	}
}
