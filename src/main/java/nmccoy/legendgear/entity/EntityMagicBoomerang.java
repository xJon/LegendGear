package nmccoy.legendgear.entity;



import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.PlayerEventHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityMagicBoomerang extends EntityThrowable implements IEntityAdditionalSpawnData{
	
	public int return_time;
	public static int MAX_THROW_TIME = 10;
	public ItemStack boomerang_item;
	public static float BOOMERANG_SPEED = 1.5f;
	public static float CORNERING_SPEED = 0.2f;
	public static int BOOMERANG_DAMAGE = 6;
	public Entity owner;
	public int thrown_from_slot;

	
	public EntityMagicBoomerang(World par1World) {
		super(par1World);
		return_time = MAX_THROW_TIME;
		// TODO Auto-generated constructor stub
	}
	
	public EntityMagicBoomerang(World par1World, EntityLivingBase par2EntityLiving, ItemStack boomerangThrown) {
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
			par1nbtTagCompound.setTag("Item", boomerang_item.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void writeSpawnData(ByteBuf data) {
		// TODO Auto-generated method stub
	//	String ownername ="";
	//	if(getThrower() != null)
	//		ownername = getThrower().getUniqueID().toString();
	//	data.writeUTF(ownername);
		int id = getThrower().getEntityId();
		data.writeInt(id);
	}
	
	@Override
	public void readSpawnData(ByteBuf data) {
		// TODO Auto-generated method stub
		//String ownername = data.readUTF(); 
	//	owner = this.worldObj.getPlayerEntityByName(ownername);
		int id = data.readInt();
        owner = worldObj.getEntityByID(id);
	}
	

	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
		
		
		if(var1.typeOfHit == MovingObjectType.ENTITY)
		{
			
			if(var1.entityHit != null && var1.entityHit instanceof EntityLivingBase)
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
							ep.dropPlayerItemWithRandomChoice(crowded_item, true);
						}
					}
					if(this.riddenByEntity != null)
					{
						Entity passenger = this.riddenByEntity;
						passenger.mountEntity(null);
						passenger.posX = ep.posX;
						passenger.posY = ep.posY;
						passenger.posZ = ep.posZ;
						
						//riddenByEntity.onCollideWithPlayer(ep);
						//System.out.println("dropped "+rb.getEntityName());
					}
					
				      if (!this.isDead && !this.worldObj.isRemote)
				        {
				    	  this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				            EntityTracker entitytracker = ((WorldServer)this.worldObj).getEntityTracker();
				            entitytracker.func_151247_a(this, new S0DPacketCollectItem(this.getEntityId(), getThrower().getEntityId()));
				            
				        }
					//ep.onItemPickup(this, 1);
					
					setDead();
					
				}
				if(var1.entityHit != getThrower() && getThrower() != null)
				{
					EntityLivingBase el = (EntityLivingBase) var1.entityHit;
					if(boomerang_item != null)
					{boomerang_item.damageItem(1, getThrower());
						if(boomerang_item.stackSize == 0) 
						{
							worldObj.playSoundAtEntity(this, "random.break", 1.0f, 0.8f);
							setDead();
						}
					}
					
					el.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), BOOMERANG_DAMAGE);
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
		//	int id = worldObj.getBlockId(var1.blockX, var1.blockY, var1.blockZ);
			int meta = worldObj.getBlockMetadata(var1.blockX, var1.blockY, var1.blockZ);
			Block block =worldObj.getBlock(var1.blockX, var1.blockY, var1.blockZ);
			int id = Block.getIdFromBlock(block);
			Material mat = block.getMaterial();
			
			
			 
			boolean solid = (block.getCollisionBoundingBoxFromPool(worldObj, var1.blockX, var1.blockY, var1.blockZ) != null);
			
			if(block.getBlockHardness(worldObj, var1.blockX, var1.blockY, var1.blockZ) == 0.0f &&
					(mat == Material.plants || mat == Material.glass || mat == Material.vine))
			{
				if(!worldObj.isRemote && worldObj.setBlockToAir(var1.blockX, var1.blockY, var1.blockZ))
				{
					block.dropBlockAsItem(worldObj, var1.blockX, var1.blockY, var1.blockZ, meta, 0);
					block.onBlockDestroyedByPlayer(worldObj, var1.blockX, var1.blockY, var1.blockZ, meta);
					if(block == Blocks.tallgrass && owner != null && owner instanceof EntityPlayer) 
						PlayerEventHandler.considerGrassDrops((EntityPlayer) owner, worldObj, var1.blockX, var1.blockY, var1.blockZ, 0);
					//worldObj.playSoundEffect(var1.blockX, var1.blockY, var1.blockZ, block.stepSound.getBreakSound(), 1.0f, 1.0f);
					//if(worldObj.isRemote)
						worldObj.playAuxSFX(2001, var1.blockX, var1.blockY, var1.blockZ, id + (meta << 12));
				}
				if(boomerang_item != null && getThrower() != null)
				{
				//	boomerang_item.damageItem(1, getThrower());
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
		EntityLivingBase thrower = null;
		if(owner instanceof EntityLivingBase) thrower = (EntityLivingBase) owner;
		if(thrower != null)
		{
			EntityPlayer ep = (EntityPlayer) thrower;
			if(ep.capabilities.isCreativeMode){
				ItemStack hand = ep.inventory.mainInventory[ep.inventory.currentItem];
				if(boomerang_item != null && hand != null && boomerang_item.isItemEqual(hand))
				{
					ep.inventory.mainInventory[ep.inventory.currentItem] = null;
				}
			}
		}
		
		if(thrower != null && return_time % 3 == 0)
			worldObj.playSoundAtEntity(this, LegendGear2.MODID+":boomerang", 2.0f, 1.0f + 1.0f/(1.0f + this.getDistanceToEntity(thrower)/16));
		
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
