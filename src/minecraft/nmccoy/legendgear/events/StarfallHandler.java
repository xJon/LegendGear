package nmccoy.legendgear.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityFallingStar;

public class StarfallHandler {
	@ForgeSubscribe
	public void makeStarsFall(LivingUpdateEvent lue)
	{
		if(lue.entityLiving instanceof EntityPlayer && !lue.entityLiving.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer) lue.entityLiving;
			
			boolean underSky = player.worldObj.canBlockSeeTheSky((int)Math.floor(player.posX), (int)Math.floor(player.posY+2.1), (int)Math.floor(player.posZ));
			boolean isDark = player.worldObj.getBlockLightValue((int)Math.floor(player.posX), (int)Math.floor(player.posY+2.1), (int)Math.floor(player.posZ)) < 6;
			
			if(underSky && isDark && player.dimension >= 0 && LegendGear.enableStarfall)
			{
				int startimer = player.getEntityData().getInteger("starTimer");
				startimer+= (int)(Math.random()*3);
				if(startimer >= LegendGear.starFallRarity)
				{
					player.worldObj.spawnEntityInWorld(new EntityFallingStar(player));
					startimer = 0;
				}
				player.getEntityData().setInteger("starTimer", startimer);
			}
		}
	}
}
