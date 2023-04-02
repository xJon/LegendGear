package nmccoy.legendgear.items;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class HeartPickup extends Item {

	public HeartPickup(int par1) {
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(20);
		setItemName("heartPickup");
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	
	public boolean hasCustomEntity(ItemStack stack)
    {
        return false;
    }
	
//	public Entity createEntity(World world, Entity location, ItemStack itemstack)
//    {
//        EntityHeartItem ehi = new EntityHeartItem(world, location.posX, location.posY, location.posZ, itemstack);
//        ehi.motionX = location.motionX;
//        ehi.motionY = location.motionY;
//        ehi.motionZ = location.motionZ;
//        return ehi;
//    }
	
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer ep)
    {
		Random rand = itemRand;
		if(par2World.isRemote)
		for (int var3 = 0; var3 < 7; ++var3)
       	{
	        double var4 = rand.nextGaussian() * 0.02D;
	        double var6 = rand.nextGaussian() * 0.02D;
	        double var8 = rand.nextGaussian() * 0.02D;
         	par2World.spawnParticle("heart", ep.posX + rand.nextGaussian(), ep.posY+ rand.nextGaussian(), ep.posZ+ rand.nextGaussian(), var4, var6, var8);
        }
        return par1ItemStack;
    }
	
	
}
