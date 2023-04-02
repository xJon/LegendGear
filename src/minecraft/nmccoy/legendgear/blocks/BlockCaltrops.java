package nmccoy.legendgear.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockCaltrops extends Block {

	public BlockCaltrops(int par1) {
		super(par1, Material.circuits);
		// TODO Auto-generated constructor stub
		setCreativeTab(LegendGear.creativeTab);
		setBlockBounds(3f/16f, 0, 3f/16f, 13f/16f, 3f/16f, 13f/16f);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setHardness(0.25f);
		setBlockName("blockCaltrops");
	}
	
		
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		return 10;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public int getRenderType()
    {
        return 6;
    }
	
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
	      return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		// TODO Auto-generated method stub
        if(!canPlaceBlockAt(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
	}
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		// TODO Auto-generated method stub
		if(par5Entity instanceof EntityLiving && par5Entity.onGround)
		{
			if(par5Entity.attackEntityFrom(DamageSource.cactus, 2))
			{
				((EntityLiving) par5Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5*20, 2));
				((EntityLiving) par5Entity).addPotionEffect(new PotionEffect(Potion.jump.id, 5*20, -2));
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
	}
}
