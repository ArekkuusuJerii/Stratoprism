package arekkuusu.stratoprism.common.block.tile;

import arekkuusu.stratoprism.client.proxy.ModelHandler;
import arekkuusu.stratoprism.common.block.tile.tile.TileCrystalAltar;
import arekkuusu.stratoprism.common.lib.LibNameBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrystalAltar extends BlockMod implements ITileEntityProvider {

	public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.50D, 1.0D);

	public BlockCrystalAltar() {
		super(LibNameBlock.CRYSTAL_ALTAR, Material.ROCK);
		setSound(SoundType.STONE);
		setResistance(5F);
		setHardness(3F);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileCrystalAltar tile = (TileCrystalAltar) worldIn.getTileEntity(pos);
		return tile != null && (playerIn.isSneaking() ? tile.removeItem(playerIn) : heldItem != null && tile.addItem(playerIn, heldItem));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileCrystalAltar tile = (TileCrystalAltar) worldIn.getTileEntity(pos);
		if(tile !=null) tile.destroy();
		worldIn.removeTileEntity(pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {

	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		//TODO: Add FX
	}

	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return super.getItemDropped(state, rand, fortune);
	}

	@SuppressWarnings("deprecation") //Internal
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getHorizontal(meta);
		return getDefaultState().withProperty(PROPERTYFACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = state.getValue(PROPERTYFACING);
		return facing.getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTYFACING);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
									 EntityLivingBase placer) {
		EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
		return this.getDefaultState().withProperty(PROPERTYFACING, enumfacing);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCrystalAltar();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerBlock(this, 0);
	}
}
