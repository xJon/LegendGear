package nmccoy.legendgear.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockSkybeam extends BlockContainer {

	public BlockSkybeam(int par1) {
		super(par1, Material.rock);
		// TODO Auto-generated constructor stub
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(soundStoneFootstep);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setCreativeTab(LegendGear.creativeTab);
		setBlockName("blockSkybeam");
	}
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 31;
	}
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntitySkybeam();
	}

}
