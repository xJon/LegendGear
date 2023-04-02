package nmccoy.legendgear.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockSwordPedestalTechnical extends Block {

	public BlockSwordPedestalTechnical(int par1) {
		super(par1, Material.rock);
		// TODO Auto-generated constructor stub
		setTextureFile(CommonProxy.BLOCK_PNG);
		setLightOpacity(0);
		setHardness(4);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean var5 = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 2) != 0;
        

        if (var5)
        {
            this.setBlockBounds(0.25f, 0F, 0.5f-0.03125f, 0.75F, 0.5f, 0.5f+0.03125f);
        }
        else
        {
        	this.setBlockBounds(0.5f-0.03125f, 0F, 0.25f, 0.5f+0.03125f, 0.5f, 0.75f);
        }
    }
	
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 255;
	}
	
	@Override
	public int getLightOpacity(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public void onNeighborBlockChange(World par1World, int x, int y,
			int z, int par5) {
		// TODO Auto-generated method stub
		if(par1World.getBlockId(x, y-1, z) != LegendGear.blockPedestal.blockID || (par1World.getBlockMetadata(x, y-1, z) & 8) != 8)
		{
			par1World.setBlockWithNotify(x, y, z, 0);
		}
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		// TODO Auto-generated method stub
		return LegendGear.blockPedestal.onBlockActivated(par1World, par2, par3-1, par4, par5EntityPlayer,
				par6, par7, par8, par9);
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		//System.out.println("called");
		if(par1World.getBlockId(par2, par3-1, par4) == LegendGear.blockPedestal.blockID &&
				(par1World.getBlockMetadata(par2, par3-1, par4) & 8) == 8)
		{
			LegendGear.blockPedestal.ejectSword(par1World, par2, par3-1, par4, true);
		//	System.out.println("tried to eject");
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		// TODO Auto-generated method stub
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
