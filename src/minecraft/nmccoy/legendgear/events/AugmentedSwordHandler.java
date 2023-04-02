package nmccoy.legendgear.events;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityQuake;
import nmccoy.legendgear.entities.EntityWhirlwind;

public class AugmentedSwordHandler {

	
	int swordChargeTime = 30;
	int swordDischargeTime = 15;
	float sparkDistance = 0.75f;
	Random rand;
	
	public AugmentedSwordHandler() {
		// TODO Auto-generated constructor stub
		rand = new Random();
	}
	
	@ForgeSubscribe
	public void swordTick(LivingUpdateEvent lue)
	{
		if(lue.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) lue.entityLiving;
			ItemStack stack = player.inventory.getCurrentItem();
			if(player.isBlocking() && stack != null && stack.getItem() instanceof ItemSword && stack.hasTagCompound() && stack.getTagCompound().hasKey("medallion"))
			{
				int swordCharge = player.getEntityData().getInteger("swordCharge");
				player.getEntityData().setInteger("swordCharge", ++swordCharge);
				
				if(swordCharge < swordChargeTime && player.worldObj.isRemote && swordCharge > 4)
				{
					double x = player.posX - (double)(MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance)*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
					double y = player.posY + player.getEyeHeight() - (double)(MathHelper.sin(player.rotationPitch / 180.0F * (float)Math.PI) * sparkDistance) - 0.125;
					double z = player.posZ + (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance)*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
					
					double offx = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance);
					double offz = (double)(MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance);
					
					double r = (swordCharge - swordChargeTime)*1.0f/swordChargeTime;
					double theta = r*Math.PI*3;
					double h = Math.cos(theta)*r;
					double v = Math.sin(theta)*r;
					double offy = v;
					offx *= h;
					offz *= h;
					
					double sparkspan = 0.6;
					
					LegendGear.proxy.addSparkleParticle(player.worldObj, x + offx*sparkspan, y+offy*sparkspan, z+offz*sparkspan, 0, 0, 0, 0.3f);
					LegendGear.proxy.addSparkleParticle(player.worldObj, x - offx*sparkspan, y-offy*sparkspan, z-offz*sparkspan, 0, 0, 0, 0.3f);
				}
				
				if(swordCharge == swordChargeTime && !player.worldObj.isRemote)
				{
					player.worldObj.playSoundAtEntity(player, "assets.partialcharge", 0.15f, 2.0f);
				}
				if(swordCharge >= swordChargeTime)
					player.getEntityData().setInteger("medallionEnergy", swordDischargeTime);
			}
			else
			{
				player.getEntityData().setInteger("swordCharge", 0);
			}
			
			int energy = player.getEntityData().getInteger("medallionEnergy");
			if(stack == null || ! (stack.getItem() instanceof ItemSword) || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("medallion")) energy = 0;
			if(energy > 0) 
			{
				energy--;
				if(player.worldObj.isRemote)
				{
					
					double x = player.posX - (double)(MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance)*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
					double y = player.posY + player.getEyeHeight() - (double)(MathHelper.sin(player.rotationPitch / 180.0F * (float)Math.PI) * sparkDistance) - 0.125;
					double z = player.posZ + (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * sparkDistance)*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
					
					x += rand.nextGaussian()*0.05;
					y += rand.nextGaussian()*0.05;
					z += rand.nextGaussian()*0.05;
					
					LegendGear.proxy.addSparkleParticle(player.worldObj, x, y, z, 0, 0, 0, 0.6f);
				}
				if(player.swingProgressInt == 1 && player.getEntityData().getInteger("swordCharge")==0)
				{
					doSpecialAbility(player, stack);
					energy = 0;
				}
			}
			player.getEntityData().setInteger("medallionEnergy", energy);
		}
	}
	
	public void doSpecialAbility(EntityPlayer player, ItemStack sword)
	{
		if(!(sword.getItem() instanceof ItemSword && sword.hasTagCompound() && sword.getTagCompound().hasKey("medallion"))) return;
		
		int uses = sword.getItem().getItemEnchantability() * 4;
		int health = sword.getItem().getMaxDamage();
		int damage = health/uses;
		double overflow = sword.getTagCompound().getDouble("damageOverflow")+health*1.0/uses - damage;
		if(overflow > 1) 
		{
			damage++;
			overflow -= 1;
		}
		sword.getTagCompound().setDouble("damageOverflow", overflow);
				
		sword.damageItem(damage, player);
		if(sword.stackSize == 0) player.destroyCurrentEquippedItem();
		
		double atX = -(double)(MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI))*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
		double atY = -(double)(MathHelper.sin(player.rotationPitch / 180.0F * (float)Math.PI));
		double atZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI))*MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI);
		
		int medalID = sword.getTagCompound().getInteger("medallion");
		if(medalID == LegendGear.fireMedallion.itemID)
		{
			if(!player.worldObj.isRemote)
			{
				EntitySmallFireball fireball = new EntitySmallFireball(player.worldObj, player.posX+atX, player.posY+atY+player.getEyeHeight(), player.posZ+atZ, atX*0.01, atY*0.01, atZ*0.01);
				//fireball.motionX = atX * 0.5;
				//fireball.motionY = atY * 0.5;
				//fireball.motionZ = atZ * 0.5;
				fireball.shootingEntity = player;
				player.worldObj.spawnEntityInWorld(fireball);
				player.worldObj.playSoundAtEntity(player, "mob.ghast.fireball", 1.0f, 1.0f);
			}
			else
			{
				//Random rand = player.worldObj.rand;
				for(int i = 0; i < 10; i++)
					player.worldObj.spawnParticle("flame", player.posX+atX, player.posY+atY+player.getEyeHeight(), player.posZ+atZ, rand.nextGaussian()*0.04, rand.nextGaussian()*0.04, rand.nextGaussian()*0.04);
			}
		}
		if(medalID == LegendGear.earthMedallion.itemID)
		{
			if(!player.worldObj.isRemote)
			{
				EntityQuake eq = new EntityQuake(player.worldObj, player.posX, player.posY, player.posZ, player, true, 5.0);
				eq.damage_per_hit = 5;
				player.worldObj.spawnEntityInWorld(eq);
				player.worldObj.playSoundAtEntity(eq, "random.explode", 1.0f, 1.2f);
			}
		}
		if(medalID == LegendGear.windMedallion.itemID)
		{
			if(!player.worldObj.isRemote)
			{
				player.worldObj.playSoundAtEntity(player, "assets.whirlwind", 1.0f, 0.8f+(float)Math.random()*0.4f);
				player.worldObj.spawnEntityInWorld(new EntityWhirlwind(player.worldObj, player));
			}
		}
	}
}
