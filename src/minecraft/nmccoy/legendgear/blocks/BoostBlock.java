package nmccoy.legendgear.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;

public class BoostBlock extends Block {

	public BoostBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub

		this.setCreativeTab(CreativeTabs.tabBlock);
		setLightValue(0.5f);
		 float var5 = 0.0625F;
	        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.03125F, 1.0F);
	}
	@Override
	
	public int getRenderType()
	{
		return 0;
	}
	
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

	public BoostBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		// TODO Auto-generated constructor stub

		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	public String getTextureFile()
	{
		return CommonProxy.BLOCK_PNG;
	}
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return 32+par2;
    }
	
	public int damageDropped(int par1)
    {
        return par1;
    }
	
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
		
	}

	
	/**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }
    
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }
	
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
    	return canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */	
	
	@Override
    public void onEntityCollidedWithBlock(World world, int par2, int par3, int par4, Entity par5Entity) {
    	
    	int meta = world.getBlockMetadata(par2, par3, par4);
    	if(!(par5Entity instanceof EntityLiving)) return;
    	EntityLiving el = (EntityLiving) par5Entity;
    	
    	if(meta == 0) 
    	{
    		double boost = 5.0;
    		double flatX = el.motionX;
    		double flatZ = el.motionZ;
    		double vf = Math.sqrt(flatX*flatX + flatZ*flatZ);
    		if(vf != 0)
    		{
	    		flatX *= boost/vf;
	    		flatZ *= boost/vf;
	    		el.motionX = flatX;
	    		el.motionZ = flatZ;
    		}
    		
    		
    		PotionEffect pe = new PotionEffect(Potion.moveSpeed.id, 20, 3);
    		if(el.getActivePotionEffect(Potion.moveSpeed) == null)
    		{
    			world.playSoundAtEntity(par5Entity, "assets.speedboost", 1.0f, 1.0f);
    			if(world.isRemote)
    			{
    				for(int i = 0; i < 15; i++)
    				{
    					double vs = world.rand.nextDouble()*2;
    					world.spawnParticle("flame", el.posX + world.rand.nextGaussian()*0.4, el.posY - el.yOffset,  el.posZ + world.rand.nextGaussian()*0.4, el.motionX*vs, 0, el.motionZ*vs);
    				}
    			}
    		}
	    	if(!world.isRemote)
	        {
	        	el.addPotionEffect(pe);
	        }
    	}
    	if(meta == 1) 
    	{
    		PotionEffect pe = new PotionEffect(Potion.jump.id, 20, 5);
	    	if(!world.isRemote)
	        {
	        	el.addPotionEffect(pe);
	        }
    	}
    	
    	
    }


}
