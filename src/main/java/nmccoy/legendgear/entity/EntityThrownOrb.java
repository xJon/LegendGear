package nmccoy.legendgear.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;
import nmccoy.legendgear.item.StarglassOrb;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityThrownOrb extends EntityThrowable implements IEntityAdditionalSpawnData {
	public int orbIndex;

	public EntityThrownOrb(World p_i1776_1_) {
		super(p_i1776_1_);
		// TODO Auto-generated constructor stub
	}

	public EntityThrownOrb(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
		super(p_i1777_1_, p_i1777_2_);
		// TODO Auto-generated constructor stub
	}

	public EntityThrownOrb(World p_i1778_1_, double p_i1778_2_,
			double p_i1778_4_, double p_i1778_6_) {
		super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
		// TODO Auto-generated constructor stub
	}
	
	private void impactSpellSpawn(MovingObjectPosition mop, SpellID id, double radius, double power, boolean critical)
	{
		if(!worldObj.isRemote)
		{
			EntityPlayer thrower = null;
			if(getThrower() instanceof EntityPlayer) thrower = (EntityPlayer) getThrower();
			double tx, ty, tz;
			tx = posX; ty=posY; tz=posZ;
			if(mop.typeOfHit == MovingObjectType.BLOCK) {
				tx=mop.hitVec.xCoord;
				ty=mop.hitVec.yCoord;
				tz=mop.hitVec.zCoord;
				
			}
			else if(mop.entityHit != null)
			{
				if(mop.entityHit.boundingBox != null)
				{
					AxisAlignedBB bb = mop.entityHit.boundingBox;
					tx = Math.max(tx, bb.minX);
					tx = Math.min(tx, bb.maxX);
					ty = Math.max(ty, bb.minY);
					ty = Math.min(ty, bb.maxY);
					tz = Math.max(tz, bb.minZ);
					tz = Math.min(tz, bb.maxZ);
				}
			}
			
			worldObj.spawnEntityInWorld(new EntitySpellEffect(worldObj, id, thrower, Vec3.createVectorHelper(tx, ty, tz), radius, power, critical));
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		// TODO Auto-generated method stub
		if(orbIndex == StarglassOrb.OrbTypes.blast.ordinal())
		{
			impactSpellSpawn(mop, SpellID.OrbExplosion, 1.5f, 6, true);
		}
		
		if(orbIndex == StarglassOrb.OrbTypes.twinkle.ordinal())
		{
			impactSpellSpawn(mop, SpellID.Twinkle, 6f, 8, true);
		}
		if(orbIndex == StarglassOrb.OrbTypes.zap.ordinal())
		{
			impactSpellSpawn(mop, SpellID.Lightning1, 6f, 10, true);
		}
		if(orbIndex == StarglassOrb.OrbTypes.fire.ordinal())
		{
			impactSpellSpawn(mop, SpellID.Fire1, 6f, 10, true);
		}
		if(orbIndex == StarglassOrb.OrbTypes.ice.ordinal())
		{
			impactSpellSpawn(mop, SpellID.Ice1, 6f, 10, true);
		}
		if(orbIndex == StarglassOrb.OrbTypes.water.ordinal())
		{
			impactSpellSpawn(mop, SpellID.WaterFlood, 1.5f, 0, false);
		}
		if(orbIndex == StarglassOrb.OrbTypes.lava.ordinal())
		{
			impactSpellSpawn(mop, SpellID.LavaFlood, 1f, 0, false);
		}
		
		
		if(!worldObj.isRemote)
		{
			worldObj.playSoundAtEntity(this, "game.potion.smash", 1.0f, 1.0f);
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("orbType", orbIndex);
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(p_70037_1_);
		orbIndex = p_70037_1_.getInteger("orbType");
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub
		buffer.writeInt(orbIndex);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		// TODO Auto-generated method stub
		orbIndex = additionalData.readInt();
	}

}
