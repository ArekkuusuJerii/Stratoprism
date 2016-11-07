package arekkuusu.stratoprism.api.state.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum ParticleFX implements IStringSerializable {
	CRYSTAL_SQUARE,
	GAS_ROUND;

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
