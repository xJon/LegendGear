package nmccoy.legendgear.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockStarstone extends BlockContainer {

	public BlockStarstone(int par1) {
		super(par1, Material.iron);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setHardness(1.5f);
		setLightValue(1);
		setBlockName("blockStarstone");
		//setCreativeTab(LegendGear.creativeTab);
		//float epsilon = 0.0005f;
		//setBlockBounds(epsilon, epsilon, epsilon, 1-epsilon, 1-epsilon, 1-epsilon);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2,par3,par4,par2+1,par3+1,par4+1);
	}
	
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 41;
	}
	
//	@Override
//	public int getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
//			int par3, int par4, int par5) {
//		// TODO Auto-generated method stub
//		return 255;
//	}
	
	@Override
	public int getRenderType() {
		// TODO Auto-generated method stub
		return 0;//CommonProxy.starstoneRenderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityStarstone();
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		// TODO Auto-generated method stub
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		a.add(new ItemStack(LegendGear.itemStardust, 1, 2));
		return a;
	}
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z,
			int beaconX, int beaconY, int beaconZ) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return new ItemStack(LegendGear.itemStardust, 1, 2);
	}
	
//	@Override
//	public int getRenderBlockPass() {
//		// TODO Auto-generated method stub
//		return 1;
//	}
//	
//	@Override
//	public boolean canRenderInPass(int pass) {
//		// TODO Auto-generated method stub
//		CommonProxy.renderPass = pass;
//		
//		return true;
//	}
}
