package nmccoy.legendgear;

import java.lang.reflect.Method;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import nmccoy.legendgear.entity.EntityFallingStar;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {

	static final String PNT = EntityPlayer.PERSISTED_NBT_TAG;
	static final String LG2PD = "LegendGear2PersistedData";
	
	public static void addPlayerStarCharge(EntityPlayer player, int amount)
	{
		/*
		if(player instanceof EntityPlayerMP)
		{
			if(!player.getEntityData().hasKey(PNT))
				player.getEntityData().setTag(PNT, new NBTTagCompound());
			NBTTagCompound persist = player.getEntityData().getCompoundTag(PNT);
			if(!persist.hasKey(LG2PD)) persist.setTag(LG2PD, createLegendgearPlayerData());
			NBTTagCompound lg2pd = persist.getCompoundTag(LG2PD);
			
			int karma = lg2pd.getInteger("karma");
			karma += amount;

			lg2pd.setInteger("karma", karma);
		}*/
		PlayerStarstatsExtension pse = PlayerStarstatsExtension.get(player);
		pse.starChargePoints += amount;
		
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			if(PlayerStarstatsExtension.get((EntityPlayer) event.entity) == null) PlayerStarstatsExtension.register((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void handleStatTransfer(PlayerEvent.Clone event)
	{
		EntityPlayer newCopy = event.entityPlayer;
		EntityPlayer original = event.original;
		NBTTagCompound storage = new NBTTagCompound();
		PlayerStarstatsExtension pseOld = PlayerStarstatsExtension.get(original);
		PlayerStarstatsExtension pseNew = PlayerStarstatsExtension.get(newCopy);
		pseOld.saveNBTData(storage); pseNew.loadNBTData(storage);	
	}
	
	public PlayerEventHandler() {
		// TODO Auto-generated constructor stub
	}
	/*
	private static NBTTagCompound createLegendgearPlayerData()
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("karma", 0);
		data.setInteger("starCooldown", LegendGear2.starCooldown);
		data.setInteger("emeraldBank", LegendGear2.maxEmeraldDropsBanked);
		data.setInteger("lastKillX", 0);
		data.setInteger("lastKillZ", 0);
		data.setInteger("lastSkyX", 0);
		data.setInteger("lastSkyZ", 0);
		return data;
	}*/
	
	public boolean isUnderSky(Entity e)
	{
		int px = (int) Math.floor(e.posX);
		int py = (int) Math.floor(e.posY);
		int pz = (int) Math.floor(e.posZ);
		return e.worldObj.canBlockSeeTheSky(px, py, pz);
	}
	
	public boolean isInTheDark(Entity e)
	{
		int px = (int) Math.floor(e.posX);
		int py = (int) Math.floor(e.posY);
		int pz = (int) Math.floor(e.posZ);
		
		return e.worldObj.getBlockLightValue(px, py, pz) < 5;
	}
	
		
	private static int computeEmeralds(int xp, World world, int looting)
	{
		if(xp < LegendGear2.minXpForEmeralds) return 0;
		float workingAmount = xp * LegendGear2.emeraldsPerXP;
		int guaranteed = (int) (workingAmount * LegendGear2.guaranteedEmeraldRatio);
		int leftover = (int)(workingAmount + world.rand.nextFloat() - guaranteed);
		
		leftover += (int)(workingAmount*looting/2); 
		
		if(leftover > 0) leftover = world.rand.nextInt(leftover);
		return guaranteed+leftover;
	}
	
	@SubscribeEvent
	public void handleLootDrop(LivingDropsEvent lde)
	{
	//	System.out.println("loot drop event triggered");
		if(lde.recentlyHit && LegendGear2.CONFIG_ALLOW_EMERALD_DROPS)
		{
			Entity source = lde.source.getSourceOfDamage();
			if(source != null && source instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source;
				try
				{
					Method method = EntityLivingBase.class.getDeclaredMethod("getExperiencePoints", EntityPlayer.class);
					if(!method.isAccessible()) method.setAccessible(true);
					Object result = method.invoke(lde.entityLiving, source);
					int loot = 0;
					if(result instanceof Integer) loot = (Integer) result;
					
					if(!(lde.entityLiving instanceof EntityPlayer))
					{
						int emeralds;
						emeralds = computeEmeralds(loot, lde.entityLiving.worldObj, lde.lootingLevel);
								
						if(emeralds > 0 && player instanceof EntityPlayerMP)
						{
						//	NBTTagCompound data = player.getEntityData().getCompoundTag(PNT).getCompoundTag(LG2PD);
							PlayerStarstatsExtension pse = PlayerStarstatsExtension.get(player);
							
							int cx = (int) Math.floor(player.posX);
							int cz = (int) Math.floor(player.posZ);
							int dx = pse.lastKillX - cx;
							int dz = pse.lastKillZ - cz;
							double distance = Math.sqrt(dx*dx + dz*dz);
							
							int bonus = (int) Math.floor(distance / LegendGear2.emeraldAccumulationDistance);
							if(bonus > 0) 
							{
								pse.lastKillX = cx;
								pse.lastKillZ = cz;
								pse.emeraldDropsRemaining += bonus;
								addPlayerStarCharge(player, LegendGear2.huntingKarmaBonus);
							}
							
							if(pse.emeraldDropsRemaining > 0)
							{
								ItemStack stack;
								if(emeralds > LegendGear2.emeraldExchangeRate*LegendGear2.emeraldExchangeRate)
								{
									emeralds /= LegendGear2.emeraldExchangeRate*LegendGear2.emeraldExchangeRate;
									stack = new ItemStack(Items.emerald, emeralds);
								} else	if(emeralds > LegendGear2.emeraldExchangeRate)
								{
									emeralds /= LegendGear2.emeraldExchangeRate;
									stack = new ItemStack(LegendGear2.emeraldShard, emeralds, 1);
								}
								else
								{
									if(emeralds > 64) emeralds = 64;
									stack = new ItemStack(LegendGear2.emeraldShard, emeralds, 0);
								}
								lde.drops.add(new EntityItem(lde.entity.worldObj, lde.entity.posX, lde.entity.posY, lde.entity.posZ, stack));
								
								if(pse.emeraldDropsRemaining > LegendGear2.maxEmeraldDropsBanked) pse.emeraldDropsRemaining = LegendGear2.maxEmeraldDropsBanked;
								
								pse.emeraldDropsRemaining--;
								
								//System.out.println("bank = "+bank);
							}
						}
					}
				}
				catch(Exception nsmx)
				{
					System.err.println(nsmx.getMessage());
					throw new RuntimeException("Reflection Failure: "+nsmx.getMessage());
				}
			//	int xpValue = 
			}
		}
	}
	
	@SubscribeEvent
    public void handleTimeTick(LivingUpdateEvent lue)
    {
		if(lue.entityLiving instanceof EntityPlayerMP)
		{
			
			
			EntityPlayerMP player = (EntityPlayerMP) lue.entityLiving;
			/*
			if(!player.getEntityData().hasKey(PNT))
				player.getEntityData().setTag(PNT, new NBTTagCompound());
			NBTTagCompound persist = player.getEntityData().getCompoundTag(PNT);
			if(!persist.hasKey(LG2PD)) persist.setTag(LG2PD, createLegendgearPlayerData());
			NBTTagCompound lg2pd = persist.getCompoundTag(LG2PD);*/
			
			PlayerStarstatsExtension pse = PlayerStarstatsExtension.get(player);
			
			if(isUnderSky(player))
			{
				
				pse.lastSkyX = (int) Math.floor(player.posX);
				pse.lastSkyZ = (int) Math.floor(player.posZ);
				pse.lastSkyWorld = player.dimension;
				
				if(isInTheDark(player))
				{
					
					//int karma = lg2pd.getInteger("karma");
					//int starCooldown = lg2pd.getInteger("starCooldown");
					pse.starChargePoints++;
					System.out.println(pse.starChargePoints);
					if(pse.starChargePoints >= LegendGear2.starKarmaCost)
					{
						if(pse.starCooldownTimer == 0) 
							System.out.println("Starfall imminent for "+player.getDisplayName());
						
						pse.starCooldownTimer++;
						if(pse.starCooldownTimer >= LegendGear2.starCooldown)
						{
							pse.starChargePoints -= LegendGear2.starKarmaCost;
							pse.starCooldownTimer = 0 - player.worldObj.rand.nextInt(LegendGear2.starCooldownFuzz);
							if(!player.worldObj.isRemote)
							{
								player.worldObj.spawnEntityInWorld(new EntityFallingStar(player));
							}
						}
					}
					
					//lg2pd.setInteger("karma", karma);
					//lg2pd.setInteger("starCooldown", starCooldown);
					
					//System.out.println("Karma: " + karma + " starCooldown: "+starCooldown);
				}
			}
		}
    }
}
