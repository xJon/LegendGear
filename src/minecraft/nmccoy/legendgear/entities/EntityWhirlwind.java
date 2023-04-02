package nmccoy.legendgear.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityWhirlwind extends EntityThrowable implements IEntityAdditionalSpawnData{

	int throwerID;
	public EntityWhirlwind(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityWhirlwind(World par1World, EntityLiving par2EntityLiving) {
        super(par1World, par2EntityLiving);
        throwerID = par2EntityLiving.entityId;
        
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float var3 = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * var3);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 0.0F);
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		if(worldObj.isRemote) 
		{	
			Vec3 forward = worldObj.getWorldVec3Pool().getVecFromPool(motionX, motionY, motionZ).normalize();
			Vec3 right = forward.crossProduct(worldObj.getWorldVec3Pool().getVecFromPool(0, 1, 0)).normalize();
			Vec3 up = right.crossProduct(forward).normalize();
			
			double theta = ticksExisted * -0.4;
			double r = 1;
			double r2 = 0.5;
			double v = 0.3;
			double f = 0.0;
			for(int i = 0; i < 3; i++)
			{
				double lx = Math.cos(theta);
				double ly = Math.sin(theta);
				Vec3 out = worldObj.getWorldVec3Pool().getVecFromPool(right.xCoord*lx+up.xCoord*ly, right.yCoord*lx+up.yCoord*ly, right.zCoord*lx+up.zCoord*ly);
				if(!isInWater())
				{
				worldObj.spawnParticle("spell", posX+out.xCoord*r, posY+out.yCoord*r, posZ+out.zCoord*r, out.xCoord*v+motionX*f, out.yCoord*v+motionY*f, out.zCoord*v+motionZ*f);
				worldObj.spawnParticle("explode", posX+out.xCoord*r2, posY+out.yCoord*r2, posZ+out.zCoord*r2, out.xCoord*v+motionX*f, out.yCoord*v+motionY*f, out.zCoord*v+motionZ*f);
				}
				else
				{
					worldObj.spawnParticle("bubble", posX+out.xCoord*r2, posY+out.yCoord*r2, posZ+out.zCoord*r2, out.xCoord*v+motionX*f, out.yCoord*v+motionY*f, out.zCoord*v+motionZ*f);
				}
				theta += Math.PI*2/3;
			}
	
		}
		
		List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1, 1, 1));
		for(Object o:entities)
		{
			Entity ent = (Entity)o;
			if(ent.entityId!=throwerID && ent instanceof EntityLiving)
			{
				ent.motionX = motionX;
				ent.motionY = motionY;
				ent.motionZ = motionZ;
				ent.velocityChanged = true;
			}
		}
		
		if(ticksExisted > 30) setDead();
	}
	
	@Override
	protected float getGravityVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
		if(var1.typeOfHit == EnumMovingObjectType.TILE)
			{
			setDead();
			//System.out.println("collided");
			}
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeInt(throwerID);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("throwerID", throwerID);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		throwerID = par1nbtTagCompound.getInteger("throwerID");
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		throwerID = data.readInt();
	}

}
