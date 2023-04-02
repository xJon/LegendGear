package nmccoy.legendgear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerStarstatsExtension implements IExtendedEntityProperties {
	
	public int starChargePoints;
	public int starCooldownTimer;
	public int starsCollected;
	public int emeraldDropsRemaining;
	public int lastKillX;
	public int lastKillZ;
	public int lastSkyX;
	public int lastSkyZ;
	public int lastSkyWorld;
	
	public float castingProgress;
	
	public final static String EXT_PROP_NAME = "PlayerStarstats";
	private final EntityPlayer player;
	
	/**
	* Used to register these extended properties for the player during EntityConstructing event
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final void register(EntityPlayer regplayer)
	{
		regplayer.registerExtendedProperties(PlayerStarstatsExtension.EXT_PROP_NAME, new PlayerStarstatsExtension(regplayer));
	}
	

	/**
	* Returns ExtendedPlayer properties for player
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final PlayerStarstatsExtension get(EntityPlayer player)
	{
		return (PlayerStarstatsExtension) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	public PlayerStarstatsExtension(EntityPlayer pla) {
		// TODO Auto-generated constructor stub
		starChargePoints = 0;
		starCooldownTimer = 0;
		starsCollected = 0;
		emeraldDropsRemaining = LegendGear2.maxEmeraldDropsBanked;
		lastKillX = 0;
		lastKillZ = 0;
		lastSkyX = 0;
		lastSkyZ = 0;
		player = pla;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("starChargePoints", starChargePoints);
		data.setInteger("starCooldownTimer", starCooldownTimer);
		data.setInteger("starsCollected", starsCollected);
		data.setInteger("emeraldDropsRemaining", emeraldDropsRemaining);
		data.setInteger("lastKillX", lastKillX);
		data.setInteger("lastKillZ", lastKillZ);
		data.setInteger("lastSkyX", lastSkyX);
		data.setInteger("lastSkyZ", lastSkyZ);
		data.setInteger("lastSkyWorld", lastSkyWorld);
		
		compound.setTag(EXT_PROP_NAME, data);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		NBTTagCompound data = compound.getCompoundTag(EXT_PROP_NAME);
		starChargePoints = data.getInteger("starChargePoints");
		starCooldownTimer = data.getInteger("starCooldownTimer");
		starsCollected = data.getInteger("starsCollected");
		emeraldDropsRemaining = data.getInteger("emeraldDropsRemaining");
		lastKillX = data.getInteger("lastKillX");
		lastKillZ = data.getInteger("lastKillZ");
		lastSkyX = data.getInteger("lastSkyX");
		lastSkyZ = data.getInteger("lastSkyZ");
		lastSkyWorld = data.getInteger("lastSkyWorld");
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub

	}

}
