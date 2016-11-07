package arekkuusu.stratoprism.common.block.tile;

import arekkuusu.stratoprism.common.lib.LibMod;
import arekkuusu.stratoprism.common.lib.LibNameBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(LibMod.MOD_ID)
public class ModBlocks {

	@ObjectHolder(LibNameBlock.CRYSTAL_ALTAR)
	public static final Block CRYSTAL_ALTAR = new Block(Material.ROCK);
	@ObjectHolder(LibNameBlock.PRISM_TIER1)
	public static final Block PRISM = new Block(Material.ROCK);

}
