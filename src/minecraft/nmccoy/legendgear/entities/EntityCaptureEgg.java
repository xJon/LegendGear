package nmccoy.legendgear.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCaptureEgg extends EntityThrowable {

	public EntityCaptureEgg(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityCaptureEgg(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}

	public EntityCaptureEgg(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		// TODO Auto-generated method stub
		if(mop.entityHit != null && (mop.entityHit instanceof EntityLiving))
		{
			EntityLiving target = (EntityLiving)mop.entityHit;
			target.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 3);
			int id = EntityList.getEntityID(target);
			ItemStack caught = null;
            if (id > 0 && EntityList.entityEggs.containsKey(id) && target.getHealth() <= 0)
            {
               caught = new ItemStack(Item.monsterPlacer, 1, id);
            }
			if(caught != null)
			{
				if(!this.worldObj.isRemote) this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, caught));
				target.setDead();
			}
		}
		
		for (int var5 = 0; var5 < 8; ++var5)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
		
		if (!this.worldObj.isRemote)
        {
            this.setDead();
        }

	}

}
