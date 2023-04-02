package nmccoy.legendgear.blocks;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityGrindStar;

public class BlockGrindNode extends BlockTorch {

	public BlockGrindNode(int par1) {
		super(par1, 11);
		// TODO Auto-generated constructor stub
		setBlockName("grindNode");
		setTextureFile(CommonProxy.BLOCK_PNG);
		setCreativeTab(LegendGear.creativeTab);
		setLightValue(1);
	}

	@Override 
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		// TODO Auto-generated method stub
	     int var6 = par1World.getBlockMetadata(par2, par3, par4)&7;
	        double var7 = (double)((float)par2 + 0.5F);
	        double var9 = (double)((float)par3 + 0.7F);
	        double var11 = (double)((float)par4 + 0.5F);
	        double var13 = 0.2199999988079071D;
	        double var15 = 0.27000001072883606D;

	        double ox = par5Random.nextGaussian()*0.08;
	        double oy = par5Random.nextGaussian()*0.08-0.04;
	        double oz = par5Random.nextGaussian()*0.08;
	        
	        
	        if (var6 == 1)
	        {
	            LegendGear.proxy.addSparkleParticle(par1World, var7 - var15+ox, var9 + var13+oy, var11+oz, 0.0D, 0.0D, 0.0D, 0.5f);
	            
	        }
	        else if (var6 == 2)
	        {
	        	LegendGear.proxy.addSparkleParticle(par1World, var7 + var15+ox, var9 + var13+oy, var11+oz, 0.0D, 0.0D, 0.0D, 0.5f);
	        }
	        else if (var6 == 3)
	        {
	        	LegendGear.proxy.addSparkleParticle(par1World, var7+ox, var9 + var13+oy, var11 - var15+oz, 0.0D, 0.0D, 0.0D, 0.5f);
	        }
	        else if (var6 == 4)
	        {
	        	LegendGear.proxy.addSparkleParticle(par1World, var7+ox, var9 + var13+oy, var11 + var15+oz, 0.0D, 0.0D, 0.0D, 0.5f);
	        }
	        else
	        {
	        	LegendGear.proxy.addSparkleParticle(par1World, var7+ox, var9+oy, var11+oz, 0.0D, 0.0D, 0.0D, 0.5f);
	        }
	}
	
//	@Override
//	public boolean onBlockActivated(World par1World, int x, int y,
//			int z, EntityPlayer ep, int par6, float par7,
//			float par8, float par9) {
//		// TODO Auto-generated method stub
//		System.out.println("activated at "+x+", "+y+", "+z);
//		if(ep.ridingEntity == null && !par1World.isRemote)
//		{
//			EntityGrindStar star = EntityGrindStar.tryMakingStar(par1World, x, y, z, ep);
//			if(star != null)
//			{
//				System.out.println("tried spawning star");
//				par1World.spawnEntityInWorld(star);
//				ep.mountEntity(star);
//			}
//			else
//				System.out.println("spawn star failed");
//		}
//		return true;
//	}
//	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int x, int y,
			int z, Entity par5Entity) {
		// TODO Auto-generated method stub
		if(par5Entity instanceof EntityPlayer)
		{
			EntityPlayer ep = (EntityPlayer)par5Entity;
			if(ep.fallDistance > 0.1 && ep.ridingEntity == null && ep.isSneaking() && !par1World.isRemote)
			{
				EntityGrindStar star = EntityGrindStar.tryMakingStar(par1World, x, y, z, ep);
				if(star != null)
				{
					//System.out.println("tried spawning star");
					Vec3 dir = star.getLineDirection();
					
					
					par1World.spawnEntityInWorld(star);
					
					ep.mountEntity(star);
				}
				//else
					//System.out.println("spawn star failed");
			}
		}
	}
	
	 private boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4)
	    {
	        int var5 = par1World.getBlockMetadata(par2, par3, par4)&7;
	        return var5 == 5 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3 - 1, par4, 0) ? true : (var5 == 3 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 - 1, 2) ? true : (var5 == 4 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 + 1, 3) ? true : (var5 == 1 && par1World.isBlockIndirectlyProvidingPowerTo(par2 - 1, par3, par4, 4) ? true : var5 == 2 && par1World.isBlockIndirectlyProvidingPowerTo(par2 + 1, par3, par4, 5))));
	    }
	 
	 
}
