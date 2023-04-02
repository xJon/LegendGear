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

public class EntityFireblast extends Entity implements IEntityAdditionalSpawnData{

	public static int MAX_LIFESPAN = 70;
	public static int DETONATION_TIME = 60;
	public static int PULSE_INTERVAL = 7;
	public static double VERTICAL_LAUNCH = 0.5;
	public static double HORIZONTAL_LAUNCH = 0.3;
	public static double DETONATION_RADIUS = 8.0;
	
	public int damage_per_hit = 15; 	
	public static Entity thrower;
	public boolean noFF;
	public int lifetime;
	
	public EntityFireblast(World par1World, double par2, double par4, double par6, Entity responsible, boolean safeToUser)
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
	public EntityFireblast(World par1World, double par2, double par4, double par6, Entity responsible)
    {
		this(par1World, par2, par4, par6, responsible, false);	
	}
	public EntityFireblast(World par1World) {
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
		
		if(lifetime == 0 && worldObj.isRemote)
		{
		for(int i = 0; i < 15; i++)
	    	LegendGear.proxy.addRuneParticle(worldObj, posX + rand.nextGaussian()*0.3, posY, posZ+ rand.nextGaussian()*0.3, 0, + rand.nextDouble(), 0, 1.5f);
		}
		
		if(lifetime == 0 && !worldObj.isRemote)
		{
			worldObj.playSoundAtEntity(this, "assets.firebuild", 3.0f, 1.0f);
		}
		
		if(lifetime < DETONATION_TIME)
		{
			double progress = (1.0*lifetime/DETONATION_TIME);
			double decel = 1-progress;
			decel = decel * decel * decel;
			
			
			decel = 1-decel;
			double r = DETONATION_RADIUS*decel;
			double theta = decel * Math.PI * 4;
			for(int i = 0; i < 3; i++)
			{
				double ox = Math.cos(theta)*r;
				double oz = Math.sin(theta)*r;
				LegendGear.proxy.addFlareParticle(worldObj, posX+ox, posY, posZ+oz, 0, 0.05, 0, 3.0f);
				//worldObj.spawnParticle("flame", posX+ox, posY, posZ+oz, 0, 0.05, 0);
				theta += Math.PI * 2 / 3;
			}	
		}
		if(lifetime == DETONATION_TIME)
		{
			worldObj.playSoundAtEntity(this, "mob.ghast.fireball", 3.0f, 0.8f);
			worldObj.playSoundAtEntity(this, "mob.ghast.fireball", 3.0f, 1.0f);
			worldObj.playSoundAtEntity(this, "mob.ghast.fireball", 3.0f, 1.2f);
			burnThings(DETONATION_RADIUS);
			
		}	
		if(lifetime > DETONATION_TIME)
		{
			for(int i = 0; i < 5; i++)
			{
				LegendGear.proxy.addFlareParticle(worldObj, posX+rand.nextGaussian()*DETONATION_RADIUS*0.5, posY, posZ+rand.nextGaussian()*DETONATION_RADIUS*0.5, 0, 0.5, 0, 10.0f);
			    worldObj.spawnParticle("lava", posX+rand.nextGaussian()*DETONATION_RADIUS*0.5, posY, posZ+rand.nextGaussian()*DETONATION_RADIUS*0.5, 0, 0.5, 0);
			}
		}
		
		
		lifetime++;
		if(lifetime >= MAX_LIFESPAN) setDead();
	}
	
	
	private void burnThings(double radius)
	{
		List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(radius, radius, radius));
		for (int var9 = 0; var9 < var5.size(); ++var9)
        {
            Entity ent = (Entity)var5.get(var9);
            if(ent instanceof EntityLiving)
            {
            	EntityLiving el = (EntityLiving) ent;
            	if(el.getDistanceToEntity(this) <= radius)
            	{
            		if(!(el.equals(thrower) && noFF)) 
            		{
            			DamageSource damage = DamageSource.causeIndirectMagicDamage(this, thrower);
            			damage.damageType = DamageSource.inFire.damageType;
            			el.attackEntityFrom(damage, damage_per_hit);
            			el.setFire(10);
            			//el.addVelocity(rand.nextGaussian()*HORIZONTAL_LAUNCH, VERTICAL_LAUNCH, rand.nextGaussian()*HORIZONTAL_LAUNCH);
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
		//data.writeBoolean(oneshot);
		//data.writeDouble(oneshot_radius);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		//oneshot = data.readBoolean();
		//oneshot_radius = data.readDouble();
	}

}
