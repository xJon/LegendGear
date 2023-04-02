package nmccoy.legendgear.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.ShrubClusterMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MysticShrub extends Block implements IPlantable, IShearable {

	
	public MysticShrub(int par1, Material par2) {
		super(par1, par2);
		// TODO Auto-generated constructor stub
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setLightValue(5.0f/15);
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		if(meta == 2) setBlockBounds(0, 0, 0, 1, 3.0f/16, 1);
		else setBlockBounds(0, 0, 0, 1, 14.0f/16, 1);
	}
	
	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if(meta == 2) return 1.0f;
		else return 0f;
	}
	
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		// TODO Auto-generated method stub
		int meta =world.getBlockMetadata(x, y, z); 
		if(meta == 0)
			{
				if(LegendGear.subtleShrubs) return 0;
				else return 5;
			}
		if(meta == 1) return 8;
		return 2;
	}
	
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata (int side, int metadata) {
		if(metadata == 1) return 1;
		if(metadata == 2) return 19;
		if(LegendGear.subtleShrubs) return 16;
	    return 17;
	}
	
	@Override
	public String getTextureFile()
	{
		return CommonProxy.BLOCK_PNG;
	}
	
	 
	    public boolean isOpaqueCube()
	    {
	        return false;
	    }

	    /**
	     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	     */
	    public boolean renderAsNormalBlock()
	    {
	        return false;
	    }
	    
	    public int getRenderType()
	    {
	        return 1;
	    }

		@Override
		public EnumPlantType getPlantType(World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return EnumPlantType.Plains;
		}

		@Override
	    public int getPlantID(World world, int x, int y, int z)
	    {
	        return blockID;
	    }

	    @Override
	    public int getPlantMetadata(World world, int x, int y, int z)
	    {
	         return 0;
	    }
	    
	    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	    {
	        return null;
	    }
	    
	    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	    {
	        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
	    }
	    
	    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	    {
	        Block soil = blocksList[par1World.getBlockId(par2, par3 - 1, par4)];
	        return (par1World.canBlockSeeTheSky(par2, par3, par4) &&
	        		par1World.getTopSolidOrLiquidBlock(par2, par4)==par3) && 
	                (soil != null && soil.canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this));
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
	            par1World.setBlockWithNotify(par2, par3, par4, 0);
	        }
	        else if(par1World.getBlockMetadata(par2, par3, par4) == 1 && !par1World.isThundering())
	        {
	        	par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 0);
	        	par1World.updateAllLightTypes(par2, par3, par4);
	        }
	    }
	    
	    public int countShrubsNear(World world, int x, int y, int z, int r)
	    {
	    	int total = 0;
	    	for(int i = -r; i <= r; i++)
	    	{
	    		for(int j = -r; j <= r; j++)
	    		{
	    			if(world.getBlockId(x+i, y, z+j) == blockID) ++total;
	    		}
	    	}
	    	return total;
	    }
	    
	    
	    @Override
	    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
	    		int par4, Entity par5Entity) {
	    	// TODO Auto-generated method stub
	    	int meta = par1World.getBlockMetadata(par2, par3, par4); 
	    	if(meta == 1 && (par5Entity instanceof EntityLiving))
	    	{
	    		if(par1World.canLightningStrikeAt(par2, par3, par4) && !par1World.isRemote)
	    			par1World.addWeatherEffect(new EntityLightningBolt(par1World, par2, par3, par4));
	    		par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
	    	}
	    }
	    
	    @Override
	    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	    {
	    	//System.out.println("shrub ticking");
	        this.checkFlowerChange(par1World, par2, par3, par4);
	        if(par1World.isRaining() ? par5Random.nextInt(4) == 0 : par5Random.nextInt(20) == 0 )
	        {
	        	if(par1World.getBlockMetadata(par2, par3, par4) == 2)
	        	{
		        	int charged = 0;
		        	if(par1World.isThundering()) charged = 1;
		        	par1World.setBlockMetadataWithNotify(par2, par3, par4, charged);
		        	par1World.markBlockForUpdate(par2, par3, par4);
	        	}
//	        	if(countShrubsNear(par1World, par2, par3, par4, 9) >= 12) 
//	        	{
//	        		if(charged == 1)
//	        			
//	        		return;
//	        	}
//	        	int dir = par5Random.nextInt(4);
//	        	int xo = 0, zo = 0;
//	        	if(dir == 0) xo = 1;
//	        	if(dir == 1) xo = -1;
//	        	if(dir == 2) zo = 1;
//	        	if(dir == 3) zo = -1;
//	        	ShrubClusterMaker.putShrub(par1World, par2+xo, par3, par4+zo, charged);
	        }
	    }

		@Override
		public boolean isShearable(ItemStack item, World world, int x, int y,
				int z) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onBlockDestroyedByPlayer(World par1World, int par2, int par3,
				int par4, int par5) {
			// TODO Auto-generated method stub
			if(par5 < 2)
			{
				par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, 2);
			}
		}
		
		@Override
		public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune)
	    {
	        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
	       	        
	        if(meta == 1 && LegendGear.shrubSuperPrizes)
	        {
	        	switch(world.rand.nextInt(5))
	        	{
	        	case 0:
	        		ret.add(new ItemStack(Item.goldNugget));
	        		break;
	        	case 1:
	        		for(int i = 0; i < 3; i++) ret.add(new ItemStack(LegendGear.heartPickup));
	        		break;
	        	case 2:
	        		ret.add(new ItemStack(Item.arrow, 5));
	        		break;
	        	case 3:
	        		ret.add(new ItemStack(LegendGear.emeraldShard, 1, 1));
	        		break;
	        	case 4:
	        		ret.add(new ItemStack(LegendGear.bombItem, 3));
	        		break;
	        	default:
	        	
	        	}	
	        }
	        else if(meta == 0)
	        {
		        if (world.rand.nextFloat() < LegendGear.mysticShrubHeartChance) ret.add(new ItemStack(LegendGear.heartPickup));
		        if (world.rand.nextFloat() < LegendGear.mysticShrubShardChance) ret.add(new ItemStack(LegendGear.emeraldShard));
		        if (world.rand.nextFloat() < LegendGear.mysticShrubArrowChance) ret.add(new ItemStack(Item.arrow));
	        }
	        return ret;
	    }

		@Override
		public ArrayList<ItemStack> onSheared(ItemStack item, World world,
				int x, int y, int z, int fortune) {
			// TODO Auto-generated method stub
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
	        ret.add(new ItemStack(this, 1, 0));
	        return ret;
		}
		
		@SideOnly(Side.CLIENT)
		public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
			subItems.add(new ItemStack(this, 1, 0));
			subItems.add(new ItemStack(this, 1, 1));
		}
		
		@Override
		public int tickRate() {
			// TODO Auto-generated method stub
			return super.tickRate();
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	    {
	        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
	        if(var5 == 0 && LegendGear.subtleShrubs) return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
	        else return 0xFFFFFF;
	    }
}
