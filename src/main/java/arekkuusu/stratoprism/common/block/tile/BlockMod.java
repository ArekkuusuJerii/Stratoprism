package arekkuusu.stratoprism.common.block.tile;

import arekkuusu.stratoprism.api.item.IModelRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockMod extends Block implements IModelRegister {

	public BlockMod(String id, Material materialIn) {
		super(materialIn);
		setUnlocalizedName(id);
		setDefaultState(defaultState());
		setRegistryName(id);
	}

	public Block setSound(SoundType type){
		return super.setSoundType(type);
	}

	protected IBlockState defaultState() {
		return blockState.getBaseState();
	}
}