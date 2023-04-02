package nmccoy.legendgear.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBomb extends Entity implements IEntityAdditionalSpawnData{

	public EntityLiving thrower;
	public int fuse;
	public static final int LONG_FUSE_TIME = 80;
	public static final int SHORT_FUSE_TIME = 10; 
	public boolean primed = false;
	public double pulse = 0;
	public static final double SLOW_PULSE = 0.1;
	public static final double FAST_PULSE = 1.0;
	public boolean was_on_arrow;
	public boolean unblastable;
	public EntityBomb(World par1World) {
		super(par1World);
		noClip = false;
		this.setSize(0.5F, 0.5F);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canBeCollidedWith() {
		// TODO Auto-generated method stub
		return !this.isDead && this.ridingEntity == null && this.onGround;
	}
	
	@Override
	public boolean canBePushed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getBrightnessForRender(float par1) {
		// TODO Auto-generated method stub
		int pulsephase = ((int) pulse)%2;
		if(pulsephase == 1) return 240;
		return super.getBrightnessForRender(par1);
	}

	
	
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
    }
	
	public EntityBomb(World par1World, EntityLiving par2EntityLiving, int fusetime)
    {
        super(par1World);
        this.thrower = par2EntityLiving;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float var3 = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * var3);
        setThrowableHeading(motionX, motionY, motionZ, 0.5f, 0.0f);
		fuse = fusetime;
		noClip = false;
		this.setSize(0.5f, 0.5f);
		unblastable = fuse <= SHORT_FUSE_TIME;
		// TODO Auto-generated constructor stub
	}

	public EntityBomb(World par1World, double par2, double par4, double par6, int fusetime) {
		super(par1World);
		setPosition(par2, par4, par6);
		fuse = fusetime;
		noClip = false;
		this.setSize(0.5f, 0.5f);
		unblastable = fuse <= SHORT_FUSE_TIME;
		// TODO Auto-generated constructor stub
	}

	
	public void fizzle()
	{
		this.setDead();
		if(!worldObj.isRemote)
		{
			EntityItem bombdrop = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(LegendGear.bombItem));
			//bombdrop.motionX = this.motionX;
			//bombdrop.motionY = this.motionY;
			//bombdrop.motionZ = this.motionZ;
			this.worldObj.spawnEntityInWorld(bombdrop);
			
			worldObj.playSoundAtEntity(this, "random.fizz", 1.0f, 1.7f);
		}
		else
		{
			for(int i=0; i<3; i++)
			worldObj.spawnParticle("explode", posX, posY+0.5, posZ, 0, 0, 0);
		}
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		
		if(isInWater())
		{
			fizzle();
			return;
		}
		if(worldObj.isRemote)
		{
			worldObj.spawnParticle("smoke", posX, posY+0.6, posZ, 0, 0, 0);
		}
		
		String side = "Local";
		if(!worldObj.isRemote) side = "Server";
		if(ridingEntity == null) 
			{
				side += " NOT RIDING! ********** ";
			}
		else 
			{
				side += "(Riding "+ridingEntity.getEntityName() + " @ "+(int)ridingEntity.posX+", "+(int)ridingEntity.posY+", "+(int)ridingEntity.posZ+")";
				if(ridingEntity.getDistanceToEntity(this) > 5) side += " ****** WHAT ****** ";
				//ridingEntity.updateRiderPosition();
			}
			
		//System.out.println(side+": "+(int)posX+", "+(int)posY+", "+(int)posZ);
		
		
		motionY -= 0.04f;
		float var2 = 0.98f;
		if (this.onGround)
        {
            var2 = 0.58800006F;
            int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

            if (var3 > 0)
            {
                var2 = Block.blocksList[var3].slipperiness * 0.98F;
            }
        }

        this.motionX *= (double)var2;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)var2;

       
        
        
		if(ridingEntity == null) moveEntity(motionX, motionY, motionZ);
		
		if(!primed) 
		{
			primed = true;
			worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 1.0f);
		}
		
		fuse--;
		double burntime = ((LONG_FUSE_TIME - fuse * 1.0)/LONG_FUSE_TIME);
		burntime *= burntime;
		//pulse += SLOW_PULSE + burntime*(FAST_PULSE - SLOW_PULSE);
		if(fuse < SHORT_FUSE_TIME) pulse += FAST_PULSE; else pulse += SLOW_PULSE;
		if(fuse <= 0 || isBurning()) detonate();
		else if(ridingEntity != null && ridingEntity instanceof EntityArrow)
		{
			EntityArrow ea = (EntityArrow) ridingEntity;
			
		//	mountEntity(ea);
			
				motionX = ea.motionX;
				motionY = ea.motionY;
				motionZ = ea.motionZ;
				setPosition(ea.posX, ea.posY, ea.posZ);
				prevPosX = ea.prevPosX;
				prevPosY = ea.prevPosY;
				prevPosZ = ea.prevPosZ;
				lastTickPosX = ea.lastTickPosX;
				lastTickPosY = ea.lastTickPosY;
				lastTickPosZ = ea.lastTickPosZ;
				
				
			if(ea.arrowShake != 0) detonate();
			was_on_arrow = true;
			//if(worldObj.isRemote)
			
		}
		else if(was_on_arrow) detonate();
		//if(fuse < LONG_FUSE_TIME - 20 && ridingEntity != null) mountEntity(ridingEntity);

	}
	
	
	public void detonate()
	{	
		if(!worldObj.isRemote) 
		{
			if(ridingEntity != null)
			{
				posX = ridingEntity.posX;
				posY = ridingEntity.posY;
				posZ = ridingEntity.posZ;
				ridingEntity.setDead();
			}
			worldObj.spawnEntityInWorld(new EntityBombBlast(worldObj, thrower, posX, posY, posZ));
		}
		setDead();
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeInt(fuse);
		if(ridingEntity != null)
			data.writeInt(ridingEntity.entityId);
		else data.writeInt(0);
		data.writeBoolean(unblastable);
	}
	
	

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		fuse = data.readInt();
		int arrowid = data.readInt();
		if(arrowid != 0 && ridingEntity == null)
		{
			mountEntity(worldObj.getEntityByID(arrowid));
			System.out.println("mounted arrow");
			was_on_arrow = true;
		}
		unblastable = data.readBoolean();
				
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		fuse = var1.getInteger("fuse");
		unblastable = var1.getBoolean("unblastable");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		var1.setInteger("fuse", fuse);
		var1.setBoolean("unblastable", unblastable);
	}

}
