package nmccoy.legendgear.events;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityBomb;
import nmccoy.legendgear.entities.EntityQuake;
import nmccoy.legendgear.items.GeoAmulet;

public class ForgeEventHooksHandler {
	
//	@ForgeSubscribe
//	public void heartEffect(PlaySoundAtEntityEvent pse)
//	{
//		if("assets.heart".equals(pse.name))
//		{
//			Random rand = pse.entity.worldObj.rand;
//			if(pse.entity.worldObj.isRemote)
//			{
//				Entity ep = pse.entity;
//				for (int var3 = 0; var3 < 7; ++var3)
//		       	{
//			        double var4 = rand.nextGaussian() * 0.02D;
//			        double var6 = rand.nextGaussian() * 0.02D;
//			        double var8 = rand.nextGaussian() * 0.02D;
//		         	pse.entity.worldObj.spawnParticle("heart", ep.posX + rand.nextGaussian(), ep.posY+ rand.nextGaussian(), ep.posZ+ rand.nextGaussian(), var4, var6, var8);
//		        }
//			}
//		}
//	}
	
	@ForgeSubscribe
	public void handleCollect(EntityItemPickupEvent eipe)
	{
		ItemStack item = eipe.item.func_92014_d();
		if(LegendGear.quiverAllowed && item.itemID == Item.arrow.itemID)
		{
			int totalArrows = item.stackSize;
			ItemStack quiv;
			while(totalArrows > 0 && (quiv = findNonfullQuiver(eipe.entityPlayer)) != null)
			{
				int arrowsToTake = Math.min(totalArrows, LegendGear.maxQuiverCapacity - quiv.getItemDamage());
				totalArrows -= arrowsToTake;
				quiv.animationsToGo = 5;
				quiv.setItemDamage(quiv.getItemDamage()+arrowsToTake);
			}
			item.stackSize = totalArrows;
			//eipe.setResult(Result.ALLOW);
			return;
		}
		if(LegendGear.allowBombs && item.itemID == LegendGear.bombItem.itemID)
		{
			int totalBombs = item.stackSize;
			ItemStack bag;
			while(totalBombs > 0 && (bag = findNonfullBombBag(eipe.entityPlayer)) != null)
			{
				int bombsToTake = Math.min(totalBombs, LegendGear.maxBombBagCapacity - bag.getItemDamage());
				totalBombs -= bombsToTake;
				bag.animationsToGo = 5;
				bag.setItemDamage(bag.getItemDamage()+bombsToTake);
			}
			item.stackSize = totalBombs;
			//eipe.setResult(Result.ALLOW);
			return;
		}
		
		if(LegendGear.heartsAllowed && item.itemID == LegendGear.heartPickup.itemID)
		{
			EntityPlayer ep = eipe.entityPlayer;
			if( true)//!ep.capabilities.isCreativeMode)
			{
				if (!ep.worldObj.isRemote)
		        {
					ep.heal(eipe.item.func_92014_d().stackSize * 2);
					ep.worldObj.playSoundAtEntity(eipe.item, "assets.heart", 0.5f, 1.0f);
					ep.onItemPickup(eipe.item, 1);
					eipe.item.setDead();
					eipe.setCanceled(true);
		        }
			}
			//eipe.setResult(Result.ALLOW);
			return;
		}

	}
	
	@ForgeSubscribe
	public void handleDrawBow(ArrowNockEvent ane)
	{
		if(!LegendGear.quiverAllowed || ane.entityPlayer.capabilities.isCreativeMode) return;
		EntityPlayer ep = ane.entityPlayer;
		if(findNonemptyQuiver(ep, LegendGear.quiver.itemID)!= null)
		{
			ep.setItemInUse(ane.result, Item.bow.getMaxItemUseDuration(ane.result));
			ane.setCanceled(true);
		}
	}
	
	public ItemStack findDamaged(EntityPlayer p, Item type)
	{
		ItemStack result = null;
		ItemStack[] inv = p.inventory.mainInventory;
		for(int i = 0; i < inv.length; i++)
		{
			if(inv[i] != null && inv[i].itemID == type.itemID && inv[i].getItemDamage() > 0)
				return inv[i];
		}
		
		return result;
	}
	
	@ForgeSubscribe
	public void handleHurt(LivingHurtEvent lhe)
	{
		if(lhe.source.getEntity() != null && lhe.source.getEntity() instanceof EntityPlayer && lhe.entityLiving.getHealth() == lhe.entityLiving.getMaxHealth())
		{
			lhe.entityLiving.getEntityData().setBoolean("firstHitByPlayer", true);
		}
		
		if(LegendGear.allowMedallions && 
				lhe.entityLiving instanceof EntityPlayer && 
				!lhe.entityLiving.worldObj.isRemote)
		{
			EntityPlayer ep = (EntityPlayer) lhe.entityLiving;
			if(lhe.source.damageType.equals("fall"))
			{
				ItemStack is = ep.inventory.getCurrentItem();
				if(ep.isUsingItem() && is != null && is.itemID == LegendGear.geoAmulet.itemID)
				{
					is.damageItem(lhe.ammount, lhe.entityLiving);
					World world = ep.worldObj;
					if(!world.isRemote)
					{
						float scale = Math.min(lhe.ammount / 20.0f, 1);
						double radius = GeoAmulet.MIN_QUAKE_RADIUS+GeoAmulet.MAX_QUAKE_RADIUS * scale;
						
						EntityQuake eq = new EntityQuake(world, ep.posX, ep.posY, ep.posZ, ep, true, radius);
						eq.damage_per_hit = lhe.ammount;
						world.spawnEntityInWorld(eq);
						world.playSoundAtEntity(ep, "random.explode", 0.4f+3*scale, 1.2f - scale*0.8f);
						lhe.setCanceled(true);
					}
				}
				else
				{
					ItemStack stack = findDamaged(ep, LegendGear.earthMedallion);
					if(stack != null) 
					{
						stack.setItemDamage(Math.max(0, stack.getItemDamage()-lhe.ammount));
						if(stack.getItemDamage() == 0)
							ep.worldObj.playSoundAtEntity(ep, "assets.fullcharge", 0.2f, 1.0f);
						else
							ep.worldObj.playSoundAtEntity(ep, "assets.partialcharge", 0.2f, 1.0f);
					}
				}
			}
			if(lhe.source.damageType.equals("arrow"))
			{
				ItemStack stack = findDamaged(ep, LegendGear.windMedallion);
				if(stack != null) 
				{
					stack.setItemDamage(Math.max(0, stack.getItemDamage()-lhe.ammount));
					if(stack.getItemDamage() == 0)
						ep.worldObj.playSoundAtEntity(ep, "assets.fullcharge", 0.2f, 1.0f);
					else
						ep.worldObj.playSoundAtEntity(ep, "assets.partialcharge", 0.2f, 1.0f);
				}	
			}
			if(lhe.source.damageType.equals("inFire") || lhe.source.damageType.equals("lava") || lhe.source.damageType.equals("onFire") )
			{
				ItemStack is = ep.inventory.getCurrentItem();
				if(ep.isUsingItem() && is != null && is.itemID == LegendGear.pyroAmulet.itemID)
				{
					is.damageItem(lhe.ammount, lhe.entityLiving);
					ep.heal(lhe.ammount);
					ep.worldObj.playSoundAtEntity(ep, "assets.heart", 0.2f, 1.0f);
					ep.hurtResistantTime = ep.maxHurtResistantTime;
					lhe.setCanceled(true);
					
				}
				else
				{
					ItemStack stack = findDamaged(ep, LegendGear.fireMedallion);
					if(stack != null) 
					{
						stack.setItemDamage(Math.max(0, stack.getItemDamage()-lhe.ammount));
						if(stack.getItemDamage() == 0)
							ep.worldObj.playSoundAtEntity(ep, "assets.fullcharge", 0.2f, 1.0f);
						else
							ep.worldObj.playSoundAtEntity(ep, "assets.partialcharge", 0.2f, 1.0f);
					}
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void handleFireBow(ArrowLooseEvent ale)
	{
		if(!LegendGear.quiverAllowed || ale.entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, ale.bow) > 0) return;
		ItemStack quiv = findNonemptyQuiver(ale.entityPlayer, LegendGear.quiver.itemID);
		if(quiv != null)
		{
			ale.setCanceled(true);
            float var7 = (float)ale.charge / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double)var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            EntityArrow var8 = new EntityArrow(ale.entityPlayer.worldObj, ale.entityPlayer, var7 * 2.0F);

            if (var7 == 1.0F)
            {
                var8.setIsCritical(true);
            }

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, ale.bow);

            if (var9 > 0)
            {
                var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, ale.bow);

            if (var10 > 0)
            {
                var8.setKnockbackStrength(var10);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, ale.bow) > 0)
            {
                var8.setFire(100);
            }

            ale.bow.damageItem(1, ale.entityPlayer);
            ale.entityPlayer.worldObj.playSoundAtEntity(ale.entityPlayer, "random.bow", 1.0F, 1.0F / (float)(Math.random() * 0.4F + 1.2F) + var7 * 0.5F);

            quiv.setItemDamage(quiv.getItemDamage()-1);

            if (!ale.entityPlayer.worldObj.isRemote)
            {
            	ale.entityPlayer.worldObj.spawnEntityInWorld(var8);
            	int bowIndex = ale.entityPlayer.inventory.currentItem;
            	
            	if(LegendGear.allowBombs && LegendGear.enableBombArrows && ale.entityPlayer.isSneaking())
            	{
            		ItemStack bombs = findNonemptyQuiver(ale.entityPlayer, LegendGear.bombBag.itemID);
            		if(bombs != null)
	            	{
	            		bombs.setItemDamage(bombs.getItemDamage()-1);
	            		EntityBomb eb = new EntityBomb(ale.entityPlayer.worldObj, ale.entityPlayer, EntityBomb.LONG_FUSE_TIME);
	            		
	            		ale.entityPlayer.worldObj.spawnEntityInWorld(eb);
	            		//ale.entityPlayer.mountEntity(var8);
	            		eb.mountEntity(var8);
	            		var8.addVelocity(-var8.motionX*0.4, -var8.motionY*0.4, -var8.motionZ*0.4);
	            		var8.velocityChanged = true;
	            	}
            	}
            }
		}
	}
	
	
	private ItemStack findNonfullBombBag(EntityPlayer player)
	{
		ItemStack[] inv = player.inventory.mainInventory;
		ItemStack suitableBag = null;
		int i;
		for(i = 0; i < inv.length; i++)
		{
			if(inv[i] != null && inv[i].itemID == LegendGear.bombBag.itemID && inv[i].getItemDamage() < LegendGear.maxBombBagCapacity)
			{
				suitableBag = inv[i];
				break;
			}
		}
		return suitableBag;
	}
	
	private ItemStack findNonfullQuiver(EntityPlayer player)
	{
		ItemStack[] inv = player.inventory.mainInventory;
		ItemStack moreArrows = null;
		int i;
		for(i = 0; i < inv.length; i++)
		{
			if(inv[i] != null && inv[i].itemID == LegendGear.quiver.itemID && inv[i].getItemDamage() < LegendGear.maxQuiverCapacity)
			{
				moreArrows = inv[i];
				break;
			}
		}
		return moreArrows;
	}
	
	private int findNonfullQuiverIndex(EntityPlayer player)
	{
		ItemStack[] inv = player.inventory.mainInventory;
		ItemStack moreArrows = null;
		int i;
		for(i = 0; i < inv.length; i++)
		{
			if(inv[i] != null && inv[i].itemID == LegendGear.quiver.itemID && inv[i].getItemDamage() < LegendGear.maxQuiverCapacity)
			{
				moreArrows = inv[i];
				break;
			}
		}
		return i;
	}
	
	
	
	private ItemStack findNonemptyQuiver(EntityPlayer player, int id)
	{
		ItemStack[] inv = player.inventory.mainInventory;
		ItemStack moreArrows = null;
		int i;
		for(i = 0; i < inv.length; i++)
		{
			if(inv[i] != null && inv[i].itemID == id && inv[i].getItemDamage() > 0)
			{
				moreArrows = inv[i];
				break;
			}
		}
		return moreArrows;
	}
	
	
	@ForgeSubscribe
	public void phoenixEffect(LivingDeathEvent lde)
	{
		if(LegendGear.allowPFeather && lde.entityLiving instanceof EntityPlayer /*&& lde.entityLiving.getHealth() <= lde.entityLiving.lde.ammount*/)
		{
			EntityPlayer ep = (EntityPlayer)lde.entityLiving;
			if(ep.inventory.hasItem(LegendGear.phoenixFeather.itemID))
			{
				if(!ep.worldObj.isRemote)
				{
					ep.inventory.consumeInventoryItem(LegendGear.phoenixFeather.itemID);
					ep.setEntityHealth(1);
					ep.hurtResistantTime = 65;
					ep.addPotionEffect(new PotionEffect(Potion.regeneration.id, 28, 3));
					ep.addPotionEffect(new PotionEffect(Potion.resistance.id, 65, 4));
					ep.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 65, 1));
					ep.removePotionEffect(Potion.poison.id);
					ep.removePotionEffect(Potion.wither.id);
					ep.setFire(3);
					ep.worldObj.playSoundAtEntity(ep, "fireworks.launch", 5.0F,1.0F);
					ep.worldObj.playSoundAtEntity(ep, "assets.speedboost", 0.4F, 0.3F);
					
				}
				lde.setCanceled(true);
			}
		}
	}
	
	@ForgeSubscribe 
	public void shrubHandler(PlayerInteractEvent pie)
	{
		World world = pie.entityPlayer.worldObj;
		if(LegendGear.shrubSuperPrizes &&
			!world.isRemote &&
			world.rand.nextDouble() < LegendGear.shrubJackpotChance &&
			pie.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK &&
			world.getBlockId(pie.x, pie.y, pie.z) == LegendGear.mysticShrub.blockID &&
			world.getBlockMetadata(pie.x, pie.y, pie.z) == 1 &&
			world.canLightningStrikeAt(pie.x, pie.y, pie.z))
		{
			world.addWeatherEffect(new EntityLightningBolt(world, pie.x, pie.y, pie.z));
			ItemStack fabulousPrizes = null;
			switch(world.rand.nextInt(13))
			{
			case 0:
				fabulousPrizes = new ItemStack(Item.emerald, 4+world.rand.nextInt(4));
				break;
			case 1:
				fabulousPrizes = new ItemStack(Item.enderPearl, 3+world.rand.nextInt(3));
				break;
			case 2:
				if(LegendGear.allowPFeather)
					fabulousPrizes = new ItemStack(LegendGear.phoenixFeather);
				else
					fabulousPrizes = new ItemStack(Item.ghastTear, 2+world.rand.nextInt(2));
				break;
			case 3:
				fabulousPrizes = new ItemStack(Item.appleGold);
				break;
			case 4:
				fabulousPrizes = new ItemStack(Item.fireballCharge, 8+world.rand.nextInt(8));
				break;
			case 5:
				fabulousPrizes = new ItemStack(Item.expBottle, 15+world.rand.nextInt(11));
				break;
			case 6:
				fabulousPrizes = new ItemStack(Item.pocketSundial);
				break;
			case 7:
				fabulousPrizes = new ItemStack(Item.cake);
				break;
			case 8:
				fabulousPrizes = new ItemStack(Item.record13.itemID + world.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1), 1, 0);
				break;
			case 9:
				fabulousPrizes = new ItemStack(Block.tnt, 8);
				break;
			case 10:
				fabulousPrizes = new ItemStack(Item.diamond);
				break;
			case 11:
				fabulousPrizes = new ItemStack(LegendGear.mysticSeed);
				break;
					
			default:
			}
			if(fabulousPrizes != null)
				world.spawnEntityInWorld(new EntityItem(world, pie.x, pie.y+30, pie.z, fabulousPrizes));
			else
				world.setBlock(pie.x, world.getActualHeight()-3, pie.z, Block.anvil.blockID);
		}
	}
	
	
	public ArrayList<ItemStack> getLootFrom(LivingDropsEvent lde)
	{
		EntityLiving el = lde.entityLiving;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		int bonusloot = (int)(Math.random()*(lde.lootingLevel+1))+1;
		if(el instanceof EntityEnderman || el instanceof EntityBlaze || el instanceof EntityGhast || el instanceof EntityPigZombie ||
				(el instanceof EntitySkeleton && ((EntitySkeleton)el).getSkeletonType() == 1) || 
				(el instanceof EntityCreeper && ((EntityCreeper)el).getPowered()))
		{
			//roll on nice loot table
			int roll = el.worldObj.rand.nextInt(10);
			switch(roll)
			{
			case 0:	case 1:
				if(LegendGear.heartsAllowed) 
				{
					for(int i = 0; i < 3; i++) items.add(new ItemStack(LegendGear.heartPickup));
				} break;
			case 2: case 3: case 4:
				if(LegendGear.heartsAllowed) 
				{
					items.add(new ItemStack(LegendGear.heartPickup));
				} break;
			case 5: case 6: case 7:
				if(LegendGear.emeraldShardsAllowed) 
				{
					items.add(new ItemStack(LegendGear.emeraldShard, 1*bonusloot, 0));
				} break;
			case 8: 
				if(LegendGear.emeraldShardsAllowed) 
				{
					items.add(new ItemStack(LegendGear.emeraldShard, 3*bonusloot, 0));
				} break;	
			case 9:
				if(LegendGear.emeraldShardsAllowed) 
				{
					items.add(new ItemStack(LegendGear.emeraldShard, 1*bonusloot, 1));
				} break;
			default:
			}	
		}
		else 
		{
			int chance;
			if(el.experienceValue >= 5) chance = 5; else chance = 15; 
			
			int roll = el.worldObj.rand.nextInt(chance);
			switch(roll)
			{
				case 0:	case 1:
				if(LegendGear.heartsAllowed) 
				{
					items.add(new ItemStack(LegendGear.heartPickup));
				} break;
				case 2: case 3:
				if(LegendGear.emeraldShardsAllowed) 
				{
					items.add(new ItemStack(LegendGear.emeraldShard, 1*bonusloot, 0));
				} break;
				default:
			}
		}
		
//		if(lde.entityLiving instanceof EntityCreeper && LegendGear.allowBombs)
//		{
//			if(lde.source.getEntity() instanceof EntityPlayer)
//			{
//				EntityPlayer ep = (EntityPlayer)lde.source.getEntity();
//				if(Math.random() < 0.5 && ep.inventory.hasItem(LegendGear.bombBag.itemID))
//					items.add(new ItemStack(LegendGear.bombItem));
//			}
//		}
		
		
		return items;
	}
	
	@ForgeSubscribe
	public void bonusLoot(LivingDropsEvent lde)
	{
		World world = lde.entityLiving.worldObj;
		if(!world.isRemote  && !(lde.entityLiving instanceof EntityPlayer))
		{
			int xp = lde.entityLiving.experienceValue;
			EntityLiving ent = lde.entityLiving;
			
			if(lde.recentlyHit && (!LegendGear.antiGrinder || (lde.entityLiving.getEntityData().hasKey("firstHitByPlayer") && lde.entityLiving.getEntityData().getBoolean("firstHitByPlayer"))))
			for(ItemStack i:getLootFrom(lde))
			{
				lde.drops.add(new EntityItem(world, ent.posX, ent.posY, ent.posZ, i));
			}
			
			if(LegendGear.allowBombs && ent instanceof EntityCreeper && lde.source.damageType.equals("explosion"))
				lde.drops.add(new EntityItem(world, ent.posX, ent.posY, ent.posZ, new ItemStack(LegendGear.bombItem, 3)));
			/*
			if(LegendGear.heartsAllowed)
			{
				int totalhearts = (int)Math.floor(xp * (Math.random() * (1-LegendGear.heartMinMult) + LegendGear.heartMinMult) * LegendGear.heartDropScale);
				if(totalhearts > LegendGear.maxHeartDrop) totalhearts = LegendGear.maxHeartDrop;
				for(int i = 0; i < totalhearts; i++)
					lde.drops.add(new EntityHeartItem(world, ent.posX, ent.posY, ent.posZ, new ItemStack(LegendGear.heartPickup)));
			}
			if(LegendGear.emeraldShardsAllowed)
			{
				int totalshards = (int)Math.floor(xp * (Math.random() * (1-LegendGear.emeraldDropMinMult) + LegendGear.emeraldDropMinMult) * LegendGear.emeraldDropScale);
				
				int totalemeralds = totalshards/(LegendGear.emeraldShardExchangeRate * LegendGear.emeraldShardExchangeRate);
				totalshards -= totalemeralds*LegendGear.emeraldShardExchangeRate*LegendGear.emeraldShardExchangeRate;
				
				int totalpieces = totalshards/(LegendGear.emeraldShardExchangeRate);
				totalshards -= totalpieces*LegendGear.emeraldShardExchangeRate;
				
				lde.drops.add(new EntityItem(world, ent.posX, ent.posY, ent.posZ, new ItemStack(Item.emerald, totalemeralds)));
				lde.drops.add(new EntityItem(world, ent.posX, ent.posY, ent.posZ, new ItemStack(LegendGear.emeraldShard, totalpieces, 1)));
				lde.drops.add(new EntityItem(world, ent.posX, ent.posY, ent.posZ, new ItemStack(LegendGear.emeraldShard, totalshards, 0)));				
			}*/
		}
	}

}
