package nmccoy.legendgear.entities;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityShotHook extends EntityThrowable implements IEntityAdditionalSpawnData{

	public int travelTime;
	public EntityPlayer shooter = null;
	public boolean anchored = false;
	
	public EntityShotHook(World world, EntityLiving player)
	{
		super(world, player);
		travelTime = 15;
		if(player instanceof EntityPlayer)
		shooter = (EntityPlayer)player;
		setSize(0.25f, 0.25f);
		yOffset = 0;
		noClip = true;
	}
	public EntityShotHook(World world)
	{
		super(world);
		travelTime = 15;
		setSize(0.25f, 0.25f);
		yOffset = 0;
		noClip = true;
	}
	
	@Override
	protected float getGravityVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private boolean isValidMaterial(double x, double y, double z)
	{
		if(worldObj.getBlockMaterial((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z))== Material.wood)
			return true;
		if(Arrays.binarySearch(LegendGear.extraHookshotBlocks, worldObj.getBlockId((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z))) >= 0)
				return true;
		return false;
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
		super.onUpdate();
		anchored = dataWatcher.getWatchableObjectByte(24)==1;
		String local = "server";
		if(worldObj.isRemote)
		{
			local = "client";
		}
		
		if(travelTime == -25)
		{
			if(!worldObj.isRemote) setDead();
		}
		
		if(anchored)
		{
			motionX = 0;
			motionY = 0;
			motionZ = 0;
			if(!isValidMaterial(posX, posY, posZ))
			{
				if(!worldObj.isRemote) setDead();
				//return;
			}
		}
		if(shooter == null)
		{
			if(!worldObj.isRemote) setDead();
			//System.out.println("died: bad thrower");
			//return;
		}
		EntityPlayer player = shooter;
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().itemID != LegendGear.itemHookshot.itemID ||
				!player.isUsingItem())
		{
			if(!worldObj.isRemote) setDead();
			//System.out.println("died: bad item");
			//return;
		}
		
		travelTime--;
		
		if(anchored)
		{
			Vec3 toMe = worldObj.getWorldVec3Pool().getVecFromPool(posX-player.posX, posY-(player.posY-player.getEyeHeight()), posZ-player.posZ);
			toMe = toMe.normalize();
			player.motionX = toMe.xCoord;
			player.motionY = toMe.yCoord;
			player.motionZ = toMe.zCoord;
			player.fallDistance = 0;
			
			if(travelTime % 2 == 0 && !worldObj.isRemote)
			{
				worldObj.playSoundAtEntity(player, "random.break", 0.2f, 2.0f);
			}
			
			if(worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(0.5, 0.5, 0.5)).contains(shooter))
			{
				if(!worldObj.isRemote) setDead();
				player.motionX = 0;
				player.motionY = 0;
				player.motionZ = 0;
				player.fallDistance = 0;
				return;
			}
		}
		
		if(travelTime <= 0 && worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(0.25, 0.25, 0.25)).contains(shooter) && !anchored)
		{
			
			if(!worldObj.isRemote) setDead();
			return;
		}
		
		if(travelTime <= 0 && !anchored)
			setThrowableHeading(player.posX-posX, player.posY-posY+player.getEyeHeight(), player.posZ-posZ, 1.5f, 0.0f);
	}
	
	@Override
	public void setDead() {
		// TODO Auto-generated method stub
		super.setDead();
		if(shooter != null && dataWatcher.getWatchableObjectByte(24)==1)
		{
			shooter.motionX = 0;
			shooter.motionY = 0;
			shooter.motionZ = 0;
			shooter.fallDistance = 0;
		}
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
		if(var1.typeOfHit == EnumMovingObjectType.TILE && !anchored && travelTime > 0 && !worldObj.isRemote)
		{
			//System.out.println(""+var1.blockX+", "+var1.blockY+", "+var1.blockZ);
			
			if(isValidMaterial(var1.blockX, var1.blockY, var1.blockZ))
			{
				//anchored = true;
				dataWatcher.updateObject(24, new Byte((byte)1));
				motionX = 0;
				motionY = 0;
				motionZ = 0;
				posX = var1.blockX+0.5;
				posY = var1.blockY; 
				posZ = var1.blockZ+0.5;
				velocityChanged = true;
				
				//System.out.println("pos: "+posX+", "+posY+", "+posZ);
			}
			travelTime = Math.min(travelTime, 0);
			Block block = Block.blocksList[worldObj.getBlockId(var1.blockX, var1.blockY, var1.blockZ)];
			if(!worldObj.isRemote)
			{
				worldObj.playSoundAtEntity(this, block.stepSound.getPlaceSound(), 1.0f, 1.0f);
			}
			else
			{
				for(int i = 0; i < 10; i++)
				worldObj.spawnParticle("tilecrack_"+worldObj.getBlockId(var1.blockX, var1.blockY, var1.blockZ)+"_"+worldObj.getBlockMetadata(var1.blockX, var1.blockY, var1.blockZ), posX, posY, posZ, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
			}
		}
		//if(var1.typeOfHit == EnumMovingObjectType.ENTITY)
	//	{
	//		if(var1.entityHit instanceof EntityLiving && this.riddenByEntity == null)
	//		{
	//			var1.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
	//			var1.entityHit.mountEntity(this);
	//			travelTime = 0;
	//		}
	//	}
	}
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		if(shooter != null) data.writeInt(shooter.entityId);
		else 
			{
				data.writeInt(0);
				setDead();
			}
	}
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		
		Entity e = worldObj.getEntityByID(data.readInt());
		if(e instanceof EntityPlayer) shooter = (EntityPlayer)e;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setString("shooterName", shooter.getEntityName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		shooter = worldObj.getPlayerEntityByName(par1nbtTagCompound.getString("shooterName"));
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		super.entityInit();
		dataWatcher.addObject(24, new Byte((byte)0));
	}
}
