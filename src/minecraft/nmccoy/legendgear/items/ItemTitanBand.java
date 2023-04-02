package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemTitanBand extends Item {

	public ItemTitanBand(int par1) {
		super(par1);
		setMaxDamage(50);
		setMaxStackSize(1);
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(29);
		setItemName("titanBand");
		setCreativeTab(CreativeTabs.tabMisc);
		// TODO Auto-generated constructor stub
	}
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return EnumAction.block;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@ForgeSubscribe
	public void handleHurt(LivingHurtEvent lhe)
	{
		if(lhe.entity instanceof EntityPlayer &&
				!lhe.entity.worldObj.isRemote &&
				lhe.entity.riddenByEntity != null)
			{
				EntityPlayer ep = (EntityPlayer)lhe.entity;
				Entity rider = ep.riddenByEntity;
				rider.mountEntity(ep);
				rider.moveEntity(0, 0, 0);
				ep.worldObj.playSoundAtEntity(ep, "assets.throw", 0.3f, 1.0f);
			
			}
		if(lhe.entity.ridingEntity != null && lhe.entity.ridingEntity instanceof EntityPlayer && lhe.entity.ridingEntity.getEntityData().getBoolean("TitanLift"))
		{
			EntityPlayer ep = (EntityPlayer)lhe.entity.ridingEntity;
			Entity rider = ep.riddenByEntity;
			rider.mountEntity(ep);
			rider.moveEntity(0, 0, 0);
			ep.worldObj.playSoundAtEntity(ep, "assets.throw", 0.3f, 1.0f);
			
		}
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
	@ForgeSubscribe
	public void HandleDrop(LivingUpdateEvent lue)
	{
		if(lue.entity instanceof EntityPlayer &&
			!lue.entity.worldObj.isRemote &&
			lue.entity.riddenByEntity != null)
		{
			EntityPlayer ep = (EntityPlayer)lue.entity;
			if(ep.getEntityData().getBoolean("TitanLift"))
			if(ep.inventory.getCurrentItem() == null || ep.inventory.getCurrentItem().itemID != itemID)
			{
				Entity rider = ep.riddenByEntity;
				rider.mountEntity(ep);
				rider.moveEntity(0, 0, 0);
				ep.worldObj.playSoundAtEntity(ep, "assets.throw", 0.3f, 1.0f);
			}
		}
		if(lue.entity instanceof EntityPlayer && lue.entity.riddenByEntity == null)
		{
			lue.entity.getEntityData().setBoolean("TitanLift", false);
		}
		
	}
	
	@ForgeSubscribe
	public void HandleLift(EntityInteractEvent eie)
	{
		if( !eie.entityPlayer.worldObj.isRemote &&
			eie.target instanceof EntityLiving && 
			eie.entityPlayer.riddenByEntity == null &&
			eie.entityPlayer.inventory.getCurrentItem() != null &&
			eie.entityPlayer.inventory.getCurrentItem().itemID == itemID)
		{
			if(!eie.entityPlayer.worldObj.isRemote) eie.target.mountEntity(eie.entityPlayer);
			eie.entityPlayer.inventory.getCurrentItem().damageItem(1, eie.entityPlayer);
			((EntityLiving) eie.target).playLivingSound();
			eie.entityPlayer.getEntityData().setInteger("carryingTime", 0);
			eie.entityPlayer.worldObj.playSoundAtEntity(eie.entityPlayer, "assets.lift", 0.3f, 1.0f);
			eie.entityPlayer.getEntityData().setBoolean("TitanLift", true);
			eie.setCanceled(true);
			//eie.entityPlayer.setItemInUse(eie.entityPlayer.inventory.getCurrentItem(), 72000);
		}
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		int carry = par3Entity.getEntityData().getInteger("carryingTime");
		if(par3Entity instanceof EntityPlayer)
		{
			ItemStack item = ((EntityPlayer)par3Entity).getCurrentEquippedItem();
			if(item != null && item.itemID == LegendGear.titanBand.itemID && par3Entity.riddenByEntity != null) 
			{
				carry++;
				if(par2World.isRemote) LegendGear.proxy.addRuneParticle(par2World, par3Entity.riddenByEntity.posX, par3Entity.riddenByEntity.posY, par3Entity.riddenByEntity.posZ,  itemRand.nextGaussian()*0.1,  itemRand.nextGaussian()*0.1, itemRand.nextGaussian()*0.1, 1.0f);
				if (par3Entity.riddenByEntity instanceof EntityChicken && !par3Entity.onGround && par3Entity.motionY < 0.0D)
		        {
					par3Entity.motionY *= 0.6D;
					par3Entity.fallDistance = 0;
		        }
			}
		   else carry = 0;
		}
		par3Entity.getEntityData().setInteger("carryingTime", carry);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer player) {
		// TODO Auto-generated method stub
		if(player.riddenByEntity != null && !player.worldObj.isRemote && player.getEntityData().getInteger("carryingTime") > 5)
		{
			Entity toThrow = player.riddenByEntity; 
			toThrow.mountEntity(player);
			 float var3 = 0.8F;
		     toThrow.motionX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * var3);
		     toThrow.motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * var3);
		     toThrow.motionY = (double)(-MathHelper.sin((player.rotationPitch) / 180.0F * (float)Math.PI) * var3)+0.2;
		     if(toThrow instanceof EntityLiving)
		    	 ((EntityLiving) toThrow).playLivingSound();
			toThrow.rotationYaw = player.rotationYaw;
			//par1ItemStack.damageItem(1, player);
			player.worldObj.playSoundAtEntity(player, "assets.throw", 0.3f, 1.0f);
			player.getEntityData().setBoolean("TitanLift", false);
		}
		return par1ItemStack;
	}

}
