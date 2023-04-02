package nmccoy.legendgear.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.common.registry.GameRegistry;

public class CaltropsBlock extends Block {

	DamageSource caltropsDamage;
	public static float steppedOnDropChance = 0.5f;
	public CaltropsBlock() {
		super(Material.circuits);
		// TODO Auto-generated constructor stub
		setCreativeTab(LegendGear2.legendgearTab);
		setBlockBounds(3f/16f, 0, 3f/16f, 13f/16f, 3f/16f, 13f/16f);
		//setTextureFile(CommonProxy.BLOCK_PNG);
		setHardness(0.25f);
		setBlockName("caltrops");
		setBlockTextureName(LegendGear2.MODID+":blockCaltrops");
		caltropsDamage = new DamageSource("caltrops");
		caltropsDamage.setDamageBypassesArmor();
		SoundType caltropsSound = new SoundType("caltrops", 0.5f, 1.2f)
		{
			@Override 
			public String getBreakSound() {
				return LegendGear2.MODID+":caltropsland";
			}
			
			@Override
			public String getStepResourcePath() {
				// TODO Auto-generated method stub
				return LegendGear2.MODID+":caltropstap";
			}
			@Override
			public String func_150496_b() {
				// TODO Auto-generated method stub
				return LegendGear2.MODID+":caltropstap";
			}
		};
		
		setStepSound(caltropsSound);
		
	}
	
//	@Override
//	public int getBlockTextureFromSide(int par1) {
//		// TODO Auto-generated method stub
//		return 10;
//	}
	
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
	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4)) return false;
	      
	      return true;
	};
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		  if(!canBlockStay(par1World,par2, par3, par4)) return false;	
		 Block target = par1World.getBlock(par2, par3, par4);
	      if(target.getMaterial().isLiquid()) return false;
	      if(!target.isReplaceable(par1World, par2, par3, par4)) return false;
	      return true;
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, Block block) {
		// TODO Auto-generated method stub
        if(!canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
	}
	
	public void addRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(this, 4), " X ", " X ", "X X", 'X', Items.iron_ingot);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		// TODO Auto-generated method stub

	//	System.out.println("collision happened");	
		if(par5Entity instanceof EntityLivingBase && !par5Entity.isSneaking())// && par5Entity.onGround)
		{
			if(par5Entity.attackEntityFrom(caltropsDamage, 1))
			{
				((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5*20, 2, true));
				((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(Potion.jump.id, 5*20, -3, true));
				par1World.setBlockToAir(par2, par3, par4);//, Blocks.air, 0, 0x2);
				dropBlockAsItemWithChance(par1World, par2, par3, par4, 0, steppedOnDropChance, 0);
			}
		}
	}
}
