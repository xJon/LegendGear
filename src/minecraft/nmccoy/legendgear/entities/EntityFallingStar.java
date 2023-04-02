package nmccoy.legendgear.entities;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import nmccoy.legendgear.LegendGear;

public class EntityFallingStar extends Entity implements IEntityAdditionalSpawnData{

	public boolean impact;
	public int dwindle_timer;
	public static int DWINDLE_TIME = 20*22;
	public EntityFallingStar(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
		impact = false;
		noClip = false;
		setSize(0.5f, 1.0f);
		yOffset = 0.5f;
		DWINDLE_TIME = LegendGear.fallenStarLifetime;
		dwindle_timer = DWINDLE_TIME;
		renderDistanceWeight = 10;
	}
	
	
	
//	public EntityFallingStar(World world, int x, int y, int z)
//	{
//		this(world);
//		posX = x;
//		posY = y;
//		posZ = z;
//		motionY = -1.0;
//		motionX = rand.nextGaussian()*0.5;
//		motionZ = rand.nextGaussian()*0.5;
//	}
	
	
//	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		// TODO Auto-generated method stub
		if(!worldObj.isRemote && par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(LegendGear.itemStardust, 1, 1)))
		{
			this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			par1EntityPlayer.worldObj.playSoundAtEntity(par1EntityPlayer, "assets.starcaught", 2.0f, 1.0f);
			
			int xp = 15;
			while (xp > 0)
            {
                int var2 = 1;//EntityXPOrb.getXPSplit(xp);
                xp -= var2;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
            }

			par1EntityPlayer.onItemPickup(this, 1);
			this.setDead();
			
		}
	}
	
	public EntityFallingStar(EntityPlayer player)
	{
		this(player.worldObj);
		double theta = rand.nextDouble()*Math.PI*2;
		double r = 48;
		posX = player.posX + Math.cos(theta)*r;
		posY = player.posY + 150;
		posZ = player.posZ + Math.sin(theta)*r;
		setPosition(posX, posY, posZ);
		motionY = 0.0;
		motionX = rand.nextGaussian()*0.0;
		motionZ = rand.nextGaussian()*0.0;
		//System.out.println("created at "+posX+", "+posY+", "+posZ);
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		//System.out.println("beforepos: "+posX+", "+posY+", "+posZ);
		motionY -= 0.05;
		moveEntity(motionX, motionY, motionZ);
		
		//System.out.println("afterpos: "+posX+", "+posY+", "+posZ);
		if(this.ticksExisted == 2 && !worldObj.isRemote && !impact) 
			{
				worldObj.playSoundAtEntity(this, "assets.starfall", 100.0f, 1.0f);
				//System.out.println("making sound");
			}
		
		if(this.isCollided && !impact)
		{
			if(!worldObj.isRemote)
			{
				worldObj.createExplosion(this, posX, posY, posZ, 5.0f, false);
				setSize(0.5f, 0.5f);
				//dropItem(LegendGear.itemStardust.itemID, 1);
				//System.out.println("hit ground at "+posX+", "+posY+", "+posZ);
				
			}
			impact = true;
			//setDead();
		}
		
		if(worldObj.isRemote)
		{
			LegendGear.proxy.addSparkleParticle(worldObj, posX-motionX, posY-motionY, posZ-motionZ, rand.nextGaussian()*0.2, rand.nextGaussian()*0.2, rand.nextGaussian()*0.2, 4.0f*dwindle_timer/DWINDLE_TIME);
		}
		else
		{
			if(impact && dwindle_timer % 25 == 0) worldObj.playSoundAtEntity(this, "assets.twinkle", 2.0f, 1.0f);
		}
		
		if(impact && --dwindle_timer <= 0) 
		{
			if(!worldObj.isRemote) 
			{
				dropItem(LegendGear.itemStardust.itemID, 1);
				int xp = 5;
				while (xp > 0)
	            {
	                int var2 = 1;//EntityXPOrb.getXPSplit(xp);
	                xp -= var2;
	                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
	            }
			}
			setDead();
		}
		
		super.onUpdate();
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		impact = var1.getBoolean("impact");
		dwindle_timer = var1.getInteger("dwindle");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		var1.setBoolean("impact", impact);
		var1.setInteger("dwindle", dwindle_timer);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeBoolean(impact);
		data.writeInt(dwindle_timer);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		impact = data.readBoolean();
		dwindle_timer = data.readInt();
	}
	
	

}
