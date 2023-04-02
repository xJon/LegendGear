package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.liquids.IBlockLiquid.BlockType;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.ShrubClusterMaker;

public class MysticSeed extends ItemSeeds {

	public MysticSeed(int par1, int par2, int par3) {
		super(par1, par2, par3);
		// TODO Auto-generated constructor stub
		setCreativeTab(CreativeTabs.tabDecorations);
		setIconIndex(24);
		setItemName("mysticSeed");
		setMaxDamage(0);
		hasSubtypes = true;
	}
	
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return EnumPlantType.Plains;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		// TODO Auto-generated method stub
		return 24+par1;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if(!par2World.isRemote)
		{
			boolean thunder = par2World.isThundering();
			boolean glow = par1ItemStack.getItemDamage() == 1;
			if(thunder && !glow) par1ItemStack.setItemDamage(1);
			if(!thunder && glow) par1ItemStack.setItemDamage(0);
		}
	}

	@Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return LegendGear.mysticShrub.blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
    	return 0;
    }
	
	public boolean isDamageable()
	{
		return false;
	}
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		boolean result = super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5,
				par6, par7, par8, par9, par10);
		if(result) 
			{
				int thunder = 0;
				if(par3World.isThundering()) thunder = 1;
				if(!par3World.isRemote) 
				{
					ShrubClusterMaker.buildShrubStar(par3World, par4, par5+1, par6, thunder);
				}
				else
				{
					for(int i=0; i<12; i++)
					{
						par3World.spawnParticle("crit", par4+0.5, par5+1.5, par6+0.5, itemRand.nextGaussian(), 1, itemRand.nextGaussian());
					}
				}
				
				par3World.playSound(par4, par5, par6, "dig.grass", 1.0f, 1.0f, true);
			}
		return result;
	}
}
