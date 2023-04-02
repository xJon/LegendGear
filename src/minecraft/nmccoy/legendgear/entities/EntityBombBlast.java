package nmccoy.legendgear.entities;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class EntityBombBlast extends Entity {

	EntityLiving owner;
	public EntityBombBlast(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}
	
	public EntityBombBlast(World world, EntityLiving thrower, double x, double y, double z)
	{
		super(world);
		owner = thrower;
		setPosition(x, y, z);
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		if(this.ticksExisted == 1)
		{
			if(worldObj.isRemote) for(int i=0; i < 8; i++)
			{
				 worldObj.spawnParticle("largeexplode", posX+rand.nextGaussian(), posY+rand.nextGaussian(), posZ+rand.nextGaussian(), 0, 0, 0);
				 worldObj.spawnParticle("lava", posX, posY+0.25, posZ, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
				 worldObj.spawnParticle("explode", posX+rand.nextGaussian(), posY+rand.nextGaussian(), posZ+rand.nextGaussian(), 0, 0, 0);
				 //LegendGear.proxy.addFlareParticle(worldObj, posX+rand.nextGaussian(), posY+rand.nextGaussian(), posZ+rand.nextGaussian(), 0, 0, 0, 10.0f);
			}
			if(!worldObj.isRemote)
			{
				worldObj.playSoundAtEntity(this, "random.explode", 3.0f, 0.7f + (float)Math.random()*0.2f);
			}
			List ents = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4, 4, 4));
			DamageSource damage = DamageSource.causeThrownDamage(this, owner);
			damage.damageType = "explosion";
			
			double blastradius = 3.5;
			for(int i = 0; i< ents.size(); i++)
			{
				Entity e = (Entity)ents.get(i);
				Vec3 away = worldObj.getWorldVec3Pool().getVecFromPool(e.posX-posX, e.posY-posY, e.posZ-posZ);
				double dist = away.lengthVector(); 
				if(dist < blastradius)
				{
					away.normalize();
					if(away.yCoord < 0.6) away.yCoord = 0.6;
					double blastforce = 4.0 /(2 + dist);
					if(e instanceof EntityLiving) e.attackEntityFrom(damage, LegendGear.bombDamage);
					
					if(!(e instanceof EntityBomb && ((EntityBomb)e).unblastable))
					{
						e.motionX = away.xCoord * blastforce;
						e.motionY = away.yCoord * blastforce;
						e.motionZ = away.zCoord * blastforce;
					}
					e.fallDistance = 0;
				}
			}
			int r = 1;
			for(int x = (int)Math.floor(posX) - r; x <= (int)Math.floor(posX)+r; x++)
				for(int y = (int)Math.floor(posY) - r; y <= (int)Math.floor(posY)+r; y++)
					for(int z = (int)Math.floor(posZ) - r; z <= (int)Math.floor(posZ)+r; z++)
					{
						int targetid =worldObj.getBlockId(x, y, z); 
						if(targetid == LegendGear.bombFlower.blockID)
						{
							if(worldObj.getBlockMetadata(x, y, z) == 0)
								LegendGear.bombFlower.onBlockDestroyedByExplosion(worldObj, x, y, z);
						}
						if(targetid != 0 && Arrays.binarySearch(LegendGear.bombableBlocks, targetid) >= 0)
						{
							worldObj.playAuxSFX(2001, x, y, z, targetid + (worldObj.getBlockMetadata(x, y, z) << 12));
							worldObj.setBlockWithNotify(x, y, z, 0);
							Block.blocksList[targetid].onBlockDestroyedByExplosion(worldObj, x, y, z);
						}
					}
		}
		
		//String side = "Local";
		//if(!worldObj.isRemote) side = "Server";
		//System.out.println(side+": detonated at "+posX+", "+posY+", "+posZ);
		if(this.ticksExisted > 3) setDead();
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub

	}

}
