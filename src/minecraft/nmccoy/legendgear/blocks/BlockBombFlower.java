package nmccoy.legendgear.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityBomb;

public class BlockBombFlower extends Block implements IShearable{

	public BlockBombFlower(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setTickRandomly(true);
		this.setCreativeTab(LegendGear.creativeTab);
		this.setBlockName("bombFlower");
		setStepSound(soundGrassFootstep);
	}
	
	
	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if(meta == 1) return 1.0f;
		else return 0f;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		if(meta == 1) setBlockBounds(0, 0, 0, 1, 3.0f/16, 1);
		else setBlockBounds(0, 0, 0, 1, 14.0f/16, 1);
	}
	@Override
	public int getBlockTextureFromSideAndMetadata (int side, int metadata) {
		if(metadata == 1) return 5;
		return 4;
	}
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 4;
	}
	
	@Override
	public String getTextureFile()
	{
		return CommonProxy.BLOCK_PNG;
	}
	
	@Override
	 public boolean isOpaqueCube()
	    {
	        return false;
	    }

	    /**
	     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	     */
	@Override
	    public boolean renderAsNormalBlock()
	    {
	        return false;
	    }
	    
	@Override
	    public int getRenderType()
	    {
	        return 1;
	    }
	    
	@Override
	    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	    {
	        return null;
	    }
	    
	@Override
	    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	    {
	        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
	    }
	    
	@Override
	    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	    {
		int scanradius = 1;
		boolean hasSurface = false;
		boolean hasLava = false;
		boolean hasAir = true;
		int y = par3;
		

        if(par1World.getBlockId(par2, par3-1, par4)== Block.stone.blockID || 
        		par1World.getBlockId(par2, par3-1, par4)== Block.netherrack.blockID) hasSurface = true;
        if(!hasSurface) return false;
		
		
        if(par1World.getBlockMaterial(par2, par3+1, par4) != Material.air) hasAir = false;
        if(!hasAir) return false;
        
	        for(int x=par2-scanradius; x <= par2+scanradius; x++)
	        	for(int z=par4-scanradius; z <= par4+scanradius; z++)
	        	{
	        		
	        		if((par1World.getBlockMaterial(x, y, z) == Material.air) &&
	        			(par1World.getBlockMaterial(x, y-1, z) == Material.lava))
	        			hasLava = true;	        		
	        		//if(Block.blocksList[par1World.getBlockId(x, y, z)] != null)
	        		//System.out.println(Block.blocksList[par1World.getBlockId(x, y, z)].getBlockName());
	        	}
	        
	        return hasSurface && hasLava && hasAir;
	    }
	    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	    {
	        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	        this.checkFlowerChange(par1World, par2, par3, par4);
	    }
	    
	    protected final void checkFlowerChange(World par1World, int par2, int par3, int par4)
	    {
	        if (!this.canBlockStay(par1World, par2, par3, par4))
	        {
	            
	            if(!par1World.isRemote && par1World.getBlockMetadata(par2, par3, par4) == 0)
	    		{
	    			EntityBomb eb = new EntityBomb(par1World, par2+0.5, par3+0.5, par4+0.5, EntityBomb.LONG_FUSE_TIME);
	    			eb.fuse -= par1World.rand.nextInt(5);
	    			eb.motionY = 0.1;
	    			par1World.spawnEntityInWorld(eb);
	    		}
	            par1World.setBlockWithNotify(par2, par3, par4, 0);
	        }
	    }
	    
	    @Override
	    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3,
	    		int par4, int par5) {
	    	// TODO Auto-generated method stub
	    	if(par5 == 0)
	    	{
	    		par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 1);
	    		if(!par1World.isRemote)
	    		{
	    			EntityBomb eb = new EntityBomb(par1World, par2+0.5, par3+0.5, par4+0.5, EntityBomb.LONG_FUSE_TIME);
	    			eb.fuse -= par1World.rand.nextInt(5);
	    			eb.motionY = 0.1;
	    			par1World.spawnEntityInWorld(eb);
	    		}
	    	}
	    }
	    
	    @Override
	    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3,
	    		int par4) {
	    	// TODO Auto-generated method stub
	    	if(par1World.getBlockMetadata(par2, par3, par4) == 0)
	    	{
	    		par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 1);
	    		if(!par1World.isRemote)
	    		{
	    			EntityBomb eb = new EntityBomb(par1World, par2+0.5, par3+0.5, par4+0.5, EntityBomb.SHORT_FUSE_TIME);
	    			//eb.fuse -= par1World.rand.nextInt(5);
	    			eb.motionY = 0.1;
	    			par1World.spawnEntityInWorld(eb);
	    		}
	    	}
	    }
	    
//	    @Override
//	    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
//	    		int par4, Entity par5Entity) {
//	    	// TODO Auto-generated method stub
//	    	if(par1World.getBlockMetadata(par2, par3, par4) == 0)
//	    	{
//	    		if(par5Entity instanceof EntityArrow && par5Entity.riddenByEntity == null)
//	    		{
//	    			
//	    			if(!par1World.isRemote)
//	    			{
//	    				par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 1);
//	    				EntityBomb eb = new EntityBomb(par1World, par2, par3, par4, EntityBomb.LONG_FUSE_TIME);
//	    				eb.mountEntity(par5Entity);
//	    			}
//	    		}
//	    	}
//	    }
	    
	    @Override
	    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
	    		int z, int metadata, int fortune) {
	    	// TODO Auto-generated method stub
	    	return new ArrayList<ItemStack>();
	    }

		@Override
		public boolean isShearable(ItemStack item, World world, int x, int y,
				int z) {
			// TODO Auto-generated method stub
			return item.itemID == Item.shears.itemID && world.getBlockMetadata(x, y, z) == 0;
		}

		@Override
		public ArrayList<ItemStack> onSheared(ItemStack item, World world,
				int x, int y, int z, int fortune) {
			// TODO Auto-generated method stub
			ArrayList<ItemStack> stack = new ArrayList<ItemStack>();
			stack.add(new ItemStack(this));
			world.setBlockWithNotify(x, y, z, 0);
			return stack;
		}
		
		@Override
	    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	    {
	        this.checkFlowerChange(par1World, par2, par3, par4);
	        int meta = par1World.getBlockMetadata(par2, par3, par4);
	        int id = par1World.getBlockId(par2, par3, par4);
	        if(par5Random.nextInt(5) == 0 && id == blockID && meta == 1)
	        {
	        	par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 0);
	        }
	    }
}
