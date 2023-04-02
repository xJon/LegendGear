package nmccoy.legendgear.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class EntityArrowStorm extends Entity {

	public static int MAX_LIFESPAN = 50;
	public static double SPREAD_RADIUS = 4;
	public static EntityLiving thrower;
	public int lifetime;
	public static EntityLiving target;
	
	public EntityArrowStorm(World par1World, double par2, double par4, double par6, EntityLiving responsible)
    {
		super(par1World);
        this.setSize(0.0F, 0.0F);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0F;
        this.noClip = true;
        lifetime = 0;
        thrower = responsible;
        target = null;
    }
	
	public EntityArrowStorm(World par1World) {
		super(par1World);
        this.setSize(0.0F, 0.0F);
        this.noClip = true;
        lifetime = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		if(worldObj.isRemote && lifetime == 0)
		{
		for(int i = 0; i < 15; i++)
	    	LegendGear.proxy.addRuneParticle(worldObj, posX + rand.nextGaussian()*0.3, posY, posZ+ rand.nextGaussian()*0.3, 0, + rand.nextDouble(), 0, 1.5f);
		}
		
		
			if(!worldObj.isRemote && thrower != null) 
			{
				if(target != null) setPosition(target.posX, target.posY, target.posZ);
				doArrow();
			}
			lifetime++;
			if(lifetime > MAX_LIFESPAN) setDead();
		
	}
	
	public void doArrow()
	{
		EntityArrow ea = new EntityArrow(this.worldObj, thrower, 1.0f);
		ea.canBePickedUp = 0;
		ea.setDamage(5);
		
		ea.setPosition(thrower.posX, thrower.posY+3, thrower.posZ);
		double dist = this.getDistanceToEntity(ea);
		
		double vx = posX - ea.posX;
		double vy = posY + dist * 0.05 * 2.5 - ea.posY;
		double vz = posZ - ea.posZ;
		
		
		ea.setThrowableHeading(vx, vy, vz, 2.5F, 12.0F);
		
		
		
		worldObj.spawnEntityInWorld(ea);
		
		worldObj.playSoundAtEntity(thrower, "random.fizz", 0.1F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F) +  0.5F);
		
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
