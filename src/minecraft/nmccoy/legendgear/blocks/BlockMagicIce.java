package nmccoy.legendgear.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class BlockMagicIce extends Block {

	public BlockMagicIce(int par1, Material par2Material) {
		super(par1, 67, par2Material);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabMisc);
		setLightValue(0.5f);
	    setLightOpacity(3);
	    //setTextureFile(LegendGear.proxy.BLOCK_PNG);
		// TODO Auto-generated constructor stub
	}
	 
	public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	public int getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		return 67;
	}
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 67;
	}
	
	
	public int tickRate()
	 {
	      return 60;
	 }

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        par1World.setBlockWithNotify(par2, par3, par4, 0);
        par1World.playSoundEffect(par2, par3, par4, "random.glass", 1.0f, 1.0f);
        
    }
	
	@Override
	public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
        LegendGear.proxy.addRuneParticle(par1World, x-0.5+par5Random.nextDouble()*2, y-0.5+par5Random.nextDouble()*2, z-0.5+par5Random.nextDouble()*2, 0, 0, 0, 1.0f);
    }
	
	 /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.setInWeb();
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
    }
	
	@Override
	 public boolean isOpaqueCube()
	{
	    return false;
	}

	  
	@Override
	 public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	 {
	     return null;
	 }
	
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

