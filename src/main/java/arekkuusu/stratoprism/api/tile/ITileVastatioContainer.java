package arekkuusu.stratoprism.api.tile;

public interface ITileVastatioContainer {
	boolean add(float amount);
	float take(float amount);
	float getVastatio();
	boolean canRecieve();
}
