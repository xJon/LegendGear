package nmccoy.legendgear.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class EntityWindMedallion extends EntityThrowable {

	public int iconIndex = 7;

	public EntityWindMedallion(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}	
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		if(worldObj.isRemote)
		{
			LegendGear.proxy.addRuneParticle(worldObj, posX+rand.nextGaussian()*0.1, posY+rand.nextGaussian()*0.1, posZ+rand.nextGaussian()*0.1, rand.nextGaussian()*0.03, rand.nextGaussian()*0.03, rand.nextGaussian()*0.03, 1.0f);
			
		}
	}
	
	
	public EntityWindMedallion(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		
		
			if(!worldObj.isRemote)
			{
				EntityArrowStorm eas = new EntityArrowStorm(worldObj, posX, posY, posZ, getThrower());
				if(var1.entityHit != null && var1.entityHit instanceof EntityLiving)
				{
					eas.target = (EntityLiving)var1.entityHit;
				}
				worldObj.spawnEntityInWorld(eas);
				
				worldObj.playSoundAtEntity(this, "random.glass", 2.0F, 0.7F);
			}
			
			// TODO Auto-generated method stub
			setDead();
		
	}

}
