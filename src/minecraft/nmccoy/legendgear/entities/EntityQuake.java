package nmccoy.legendgear.entities;

import java.util.List;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class EntityQuake extends Entity implements IEntityAdditionalSpawnData{

	public static int MAX_LIFESPAN = 60;
	public static int PULSE_INTERVAL = 7;
	public static double FIRST_PULSE_RADIUS = 1;
	public static double RADIUS_PER_TICK = 0.2;
	public static double VERTICAL_LAUNCH = 0.5;
	public static double HORIZONTAL_LAUNCH = 0.3;
	
	public int damage_per_hit = 7; 	
	public static Entity thrower;
	public boolean noFF;
	public int lifetime;
	public boolean oneshot = false;
	public double oneshot_radius = 5;
	
	public EntityQuake(World par1World, double par2, double par4, double par6, Entity responsible, boolean safeToUser, double radius)
    {
		this(par1World, par2, par4, par6, responsible, safeToUser);
		oneshot = true;
		oneshot_radius = radius;
    }
	
	public EntityQuake(World par1World, double par2, double par4, double par6, Entity responsible, boolean safeToUser)
    {
        super(par1World);
        noFF = safeToUser;
        this.setSize(0.0F, 0.0F);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0F;
        this.noClip = true;
        lifetime = 0;
        thrower = responsible;
        
    }
	public EntityQuake(World par1World, double par2, double par4, double par6, Entity responsible)
    {
		this(par1World, par2, par4, par6, responsible, false);	
	}
	public EntityQuake(World par1World) {
		super(par1World);
        this.setSize(0.25F, 0.25F);
        this.noClip = true;
        lifetime = 0;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		
		if(lifetime == 0 && worldObj.isRemote && !oneshot)
		{
		for(int i = 0; i < 15; i++)
	    	LegendGear.proxy.addRuneParticle(worldObj, posX + rand.nextGaussian()*0.3, posY, posZ+ rand.nextGaussian()*0.3, 0, + rand.nextDouble(), 0, 1.5f);
		}
		
		if(oneshot)
		{
			doPulse(oneshot_radius);
			
			setDead();
		}
		else
		{
			if(lifetime % PULSE_INTERVAL == 0) doPulse(FIRST_PULSE_RADIUS + RADIUS_PER_TICK*lifetime);
			lifetime++;
			if(lifetime > MAX_LIFESPAN) setDead();
		}
	}
	
	
	public void doPulse(double radius)
	{
		if(this.worldObj.isRemote)
		{
			for (int i = 0; i < 12; i++)
			{
				double th = i* Math.PI * 2 / 12;
				double vx = Math.cos(th);
				double vz = Math.sin(th);
				
				worldObj.spawnParticle("largeexplode", posX+vx*radius, posY, posZ+vz*radius, vx*0.05, 0.2, vz*0.05);
			}
		}
		else
		{
			List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(radius, radius, radius));
			for (int var9 = 0; var9 < var5.size(); ++var9)
            {
                Entity ent = (Entity)var5.get(var9);
                if(ent instanceof EntityLiving)
                {
                	EntityLiving el = (EntityLiving) ent;
                	if(el.onGround && el.getDistanceToEntity(this) <= radius)
                	{
                		if(!(el.equals(thrower) && noFF)) 
                		{
                			DamageSource damage = DamageSource.causeIndirectMagicDamage(this, thrower);
                			//damage.damageType = DamageSource.fall.damageType;
                			el.attackEntityFrom(damage, damage_per_hit);
                			el.addVelocity(rand.nextGaussian()*HORIZONTAL_LAUNCH, VERTICAL_LAUNCH, rand.nextGaussian()*HORIZONTAL_LAUNCH);
                		}
                	}
                }
            }
                
		}
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

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeBoolean(oneshot);
		data.writeDouble(oneshot_radius);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		oneshot = data.readBoolean();
		oneshot_radius = data.readDouble();
	}

}
