package nmccoy.legendgear.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockSugarCube extends Block {

	public BlockSugarCube(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub
		setStepSound(soundGravelFootstep);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setCreativeTab(LegendGear.creativeTab);
		setBlockName("sugarCube");
		setLightOpacity(5);
		setHardness(0.25f);
	}
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 6;
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		// TODO Auto-generated method stub
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Item.sugar, 9));
		return items;
	}
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        this.checkForDissolve(par1World, par2, par3, par4);
    }
	
	@Override
	 public void onBlockAdded(World par1World, int par2, int par3, int par4)
	    {
	        this.checkForDissolve(par1World, par2, par3, par4);
	    }
	
	 private void checkForDissolve(World par1World, int par2, int par3, int par4)
	    {
	        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
	        {
	           
	                boolean var5 = false;

	                if (var5 || par1World.getBlockMaterial(par2, par3, par4 - 1) == Material.water)
	                {
	                    var5 = true;
	                }

	                if (var5 || par1World.getBlockMaterial(par2, par3, par4 + 1) == Material.water)
	                {
	                    var5 = true;
	                }

	                if (var5 || par1World.getBlockMaterial(par2 - 1, par3, par4) == Material.water)
	                {
	                    var5 = true;
	                }

	                if (var5 || par1World.getBlockMaterial(par2 + 1, par3, par4) == Material.water)
	                {
	                    var5 = true;
	                }

	                if (var5 || par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.water)
	                {
	                    var5 = true;
	                }

	                if (var5)
	                {
                        par1World.setBlockWithNotify(par2, par3, par4, 0);
                        if(!par1World.isRemote) dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
	                }
	            
	        }
	    }
	@Override
	public void fillWithRain(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		
			if(!par1World.isRemote) 
			{
				par1World.setBlockWithNotify(par2, par3, par4, 0);
				dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
			}
        
	}
	

}
