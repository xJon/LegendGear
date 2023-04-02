package nmccoy.legendgear.entities;



import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityMagicBoomerang extends EntityThrowable implements IEntityAdditionalSpawnData{
	
	public int return_time;
	public static int MAX_THROW_TIME = 10;
	public ItemStack boomerang_item;
	public static float BOOMERANG_SPEED = 1.5f;
	public static float CORNERING_SPEED = 0.2f;
	public EntityLiving owner;
	public int thrown_from_slot;

	public EntityMagicBoomerang(World par1World) {
		super(par1World);
		return_time = MAX_THROW_TIME;
		// TODO Auto-generated constructor stub
	}

	public EntityMagicBoomerang(World par1World, EntityLiving par2EntityLiving, ItemStack boomerangThrown) {
		super(par1World, par2EntityLiving);
		return_time = MAX_THROW_TIME;
		boomerang_item = boomerangThrown;
		noClip = false;
		owner = par2EntityLiving;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		if(owner == null) owner=getThrower();
		boomerang_item = ItemStack.loadItemStackFromNBT(par1nbtTagCompound.getCompoundTag("Item"));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		if(boomerang_item != null) 
			par1nbtTagCompound.setCompoundTag("Item", boomerang_item.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		String ownername ="";
		if(getThrower() != null)
			ownername = getThrower().getEntityName();
		data.writeUTF(ownername);
	}
	
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		String ownername = data.readUTF(); 
		owner = this.worldObj.getPlayerEntityByName(ownername);
        
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
		if(var1.typeOfHit == EnumMovingObjectType.ENTITY)
		{
			
			if(var1.entityHit != null && var1.entityHit instanceof EntityLiving)
			{
				if(var1.entityHit == getThrower() && return_time <= 0 && var1.entityHit instanceof EntityPlayer) //caught by thrower
				{
					EntityPlayer ep = (EntityPlayer) var1.entityHit;
					ItemStack crowded_item = ep.inventory.getStackInSlot(thrown_from_slot);
					ep.inventory.mainInventory[thrown_from_slot]=boomerang_item;
					if(crowded_item != null)
					{
						boolean success = ep.inventory.addItemStackToInventory(crowded_item);
						if(!success) 
						{
							ep.dropPlayerItem(crowded_item);
						}
					}
					if(this.riddenByEntity != null)
					{
						//riddenByEntity.onCollideWithPlayer(ep);
						//System.out.println("dropped "+rb.getEntityName());
					}
					
					ep.onItemPickup(this, 1);
					
					setDead();
					
				}
				if(var1.entityHit != getThrower())
				{
					EntityLiving el = (EntityLiving) var1.entityHit;
					boomerang_item.damageItem(1, owner);
					if(boomerang_item.stackSize == 0) 
					{
						worldObj.playSoundAtEntity(this, "random.break", 1.0f, 0.8f);
						setDead();
					}
					
					el.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), LegendGear.magicBoomerangDamage);
					//if(!noClip)
					//{
					//	return_time = 0;
				//		noClip = true;
				//		this.motionX *= -1;
				//		this.motionY *= -1;
				//		this.motionZ *= -1;
				//	}
				}	
			}
		}
		else
		{
			int id = worldObj.getBlockId(var1.blockX, var1.blockY, var1.blockZ);
			int meta = worldObj.getBlockMetadata(var1.blockX, var1.blockY, var1.blockZ);
			Material mat = worldObj.getBlockMaterial(var1.blockX, var1.blockY, var1.blockZ);
			
			
			Block block =Block.blocksList[id]; 
			boolean solid = (block.getCollisionBoundingBoxFromPool(worldObj, var1.blockX, var1.blockY, var1.blockZ) != null);
			
			if(block.getBlockHardness(worldObj, var1.blockX, var1.blockY, var1.blockZ) == 0.0f &&
					(mat == Material.plants || mat == Material.glass || mat == Material.vine))
			{
				if(block.removeBlockByPlayer(worldObj, null, var1.blockX, var1.blockY, var1.blockZ))
				{
					block.dropBlockAsItem(worldObj, var1.blockX, var1.blockY, var1.blockZ, meta, 0);
					block.onBlockDestroyedByPlayer(worldObj, var1.blockX, var1.blockY, var1.blockZ, meta);
					//worldObj.playSoundEffect(var1.blockX, var1.blockY, var1.blockZ, block.stepSound.getBreakSound(), 1.0f, 1.0f);
					//if(worldObj.isRemote)
						worldObj.playAuxSFX(2001, var1.blockX, var1.blockY, var1.blockZ, id + (meta << 12));
				}
				if(boomerang_item != null)
				{
					boomerang_item.damageItem(1, owner);
					if(boomerang_item.stackSize == 0) 
					{
						worldObj.playSoundAtEntity(this, "random.break", 1.0f, 0.8f);
						setDead();
					}
				}
				
			}
			else if(!noClip && solid)
			{
				return_time = Math.min(return_time, 0);
				noClip = true;
				this.motionX *= -1;
				this.motionY *= -1;
				this.motionZ *= -1;
			}
		}
	}
	
	/**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotationRadians(float par1, float par2, float par3)
    {
        float var4 = (par2-par1);
        var4 %= (float)Math.PI*2;
        if (var4 >= Math.PI)
        {
            var4 -= (float)Math.PI*2;
        }
        if (var4 < -Math.PI)
        {
            var4 += (float)Math.PI*2;
        }


        if (var4 > par3)
        {
            var4 = par3;
        }

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        return par1 + var4;
    }
	
	@Override
	public void onUpdate() {
		EntityLiving thrower = owner;
		if(thrower != null && return_time % 3 == 0)
			worldObj.playSoundAtEntity(this, "assets.boomerang", 2.0f, 1.0f + 1.0f/(1.0f + this.getDistanceToEntity(thrower)/16));
		
		if(this.worldObj.isRemote)
		for (int var9 = 0; var9 < 4; ++var9)
        {
            this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)var9 / 4.0D, this.posY + this.motionY * (double)var9 / 4.0D, this.posZ + this.motionZ * (double)var9 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
        }
		// TODO Auto-generated method stub
		if(!worldObj.isRemote)
		{
			if(this.riddenByEntity == null)
			{
				List ents = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0, 1.0, 1.0));
				for(Object o:ents) 
				{
					Entity e = (Entity)o;
					if(e instanceof EntityItem || e instanceof EntityFallingStar)
					{
						e.mountEntity(this);
						break;
					}
				}
			}
			
		}
		
		
		if(--return_time <= 0)
		{
			//noClip = true;
			if(thrower != null)
			{
				float current_heading = (float)Math.atan2(motionZ, motionX);
				float heading_to_thrower = (float)Math.atan2(thrower.posZ - posZ, thrower.posX-posX);
				float curve_scale = -return_time * 0.007f;
				
				float new_heading = updateRotationRadians(current_heading, heading_to_thrower, curve_scale);
				
				
				
				double current_pitch = Math.atan2(motionY,Math.sqrt(motionX*motionX+motionZ*motionZ));
				//double target_pitch = 0;
				//if(Math.abs(current_heading - heading_to_thrower) < Math.PI/2) 
						double target_pitch = Math.atan2(thrower.posY+(double)thrower.getEyeHeight()-posY,Math.sqrt((thrower.posX - posX)*(thrower.posX - posX)+(thrower.posZ - posZ)*(thrower.posZ - posZ)));
				float new_pitch = updateRotationRadians((float)current_pitch, (float)target_pitch, curve_scale * 0.3f);
				
				motionX = Math.cos(new_heading)*Math.cos(new_pitch);
				motionZ = Math.sin(new_heading)*Math.cos(new_pitch);
				motionY = Math.sin(new_pitch);
				setThrowableHeading(motionX, motionY, motionZ, BOOMERANG_SPEED, 0.0f);	
			}
		}
		if(return_time < -100 && !worldObj.isRemote) 
		{
			if(boomerang_item != null)
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, boomerang_item));
			setDead();
			
		}
		rotationPitch = return_time * 50;
		prevRotationPitch = (return_time + 1) * 50;
		super.onUpdate();
		
	}
	
	@Override
	protected float getGravityVelocity() {
		// TODO Auto-generated method stub
		return 0.0f;
	}

}
