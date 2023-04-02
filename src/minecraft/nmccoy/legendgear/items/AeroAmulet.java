package nmccoy.legendgear.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class AeroAmulet extends Item {
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	public AeroAmulet(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setIconIndex(26);
		setItemName("aeroAmulet");
		setTextureFile(CommonProxy.ITEMS_PNG);
		setMaxDamage(50);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
	
	}
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return 72000;
	}
	
	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	
	@Override
	public void onUsingItemTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {

		// TODO Auto-generated method stub

		if(player.worldObj.isRemote)
		{
			for(int i=0; i < 3; i++)
			{
			double azimuth = (count)*0.3 + i*Math.PI*2/3;
			double r = 2;
				double elevation = Math.sin((count)*0.13 + i*Math.PI*2/3);
				
				double y = Math.sin(elevation) * r + player.posY;
				double out = Math.cos(elevation);
				double x = Math.sin(azimuth)* out * r + player.posX;
				double z = Math.cos(azimuth)* out * r + player.posZ;
				
				LegendGear.proxy.addRuneParticle(player.worldObj, x, y, z, itemRand.nextGaussian()*0.002, itemRand.nextGaussian()*0.002, itemRand.nextGaussian()*0.002, 1.0f);
			}
		}
		
		AxisAlignedBB aabb = player.boundingBox;
		if(aabb == null)
			{
			System.err.println("null aabb");
			return;
			}
		aabb = aabb.expand(2, 2, 2);
		List ents = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb);
		if(ents == null) {
			System.err.println("null ents");
			return;
			}
		for(int i = 0; i < ents.size(); i++)
		{
			if(ents.get(i) instanceof EntityArrow)
			{
				EntityArrow arr = (EntityArrow)ents.get(i);
				if(arr.shootingEntity != player && player.worldObj.getWorldVec3Pool().getVecFromPool(arr.posX - arr.lastTickPosX, arr.posY - arr.lastTickPosY, arr.posZ - arr.lastTickPosZ).lengthVector() > 0.1)
				{
					arr.motionX *= -1;
					arr.motionY *= -1;
					arr.motionZ *= -1;
					arr.shootingEntity = player;
					double power = arr.getDamage();
					stack.damageItem((int)power, player);
					arr.setDamage(power*1.5);
					arr.setIsCritical(true);
					player.worldObj.playSoundAtEntity(arr, "assets.repel", 1.0f, 0.8f+itemRand.nextFloat()*0.4f);
					if(player.worldObj.isRemote)
					{
						LegendGear.proxy.addScrambleParticle(player.worldObj, arr.posX, arr.posY, arr.posZ, arr.motionX*0.2, arr.motionY*0.2, arr.motionZ*0.2, 6.0f);
					}
					
				}
			}
		}
	}

}
