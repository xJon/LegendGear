package nmccoy.legendgear.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFallingStar extends Entity implements IEntityAdditionalSpawnData{

	public boolean impact;
	public int dwindle_timer;
	public static int DWINDLE_TIME = 20*22;
	public EntityFallingStar(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
		impact = false;
		noClip = false;
		setSize(0.5f, 1.0f);
		yOffset = 0.5f;
		DWINDLE_TIME = LegendGear2.starFadeTime;
		dwindle_timer = DWINDLE_TIME;
		renderDistanceWeight = 10;
		
		
	}
	
//	@Override
//	public boolean shouldRenderInPass(int pass) {
//		// TODO Auto-generated method stub
//		return pass == 1;
//	}
	
//	public EntityFallingStar(World world, int x, int y, int z)
//	{
//		this(world);
//		posX = x;
//		posY = y;
//		posZ = z;
//		motionY = -1.0;
//		motionX = rand.nextGaussian()*0.5;
//		motionZ = rand.nextGaussian()*0.5;
//	}
	
	
//	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		// TODO Auto-generated method stub
		if(!worldObj.isRemote && par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(LegendGear2.starDust, 1, 1)))
		{
			this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			par1EntityPlayer.worldObj.playSoundAtEntity(par1EntityPlayer, LegendGear2.MODID+":starcaught", 2.0f, 1.0f);
			
			int xp = 15;
			while (xp > 0)
            {
                int var2 = 1;//EntityXPOrb.getXPSplit(xp);
                xp -= var2;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
            }

			//par1EntityPlayer.onItemPickup(this, 1);
			
		      if (!this.isDead && !this.worldObj.isRemote)
		        {
		            EntityTracker entitytracker = ((WorldServer)this.worldObj).getEntityTracker();
		            entitytracker.func_151247_a(this, new S0DPacketCollectItem(this.getEntityId(), par1EntityPlayer.getEntityId()));
		            
		        }
			
			this.setDead();
			
		}
	}
	
	public EntityFallingStar(EntityPlayer player)
	{
		this(player.worldObj);
		double theta = rand.nextDouble()*Math.PI*2;
		double r = 48;
		posX = player.posX + Math.cos(theta)*r;
		posY = player.posY + 150;
		posZ = player.posZ + Math.sin(theta)*r;
		setPosition(posX, posY, posZ);
		motionY = 0.0;
		motionX = rand.nextGaussian()*0.0;
		motionZ = rand.nextGaussian()*0.0;
		//System.out.println("created at "+posX+", "+posY+", "+posZ);
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		//System.out.println("beforepos: "+posX+", "+posY+", "+posZ);
		motionY -= 0.05;
		moveEntity(motionX, motionY, motionZ);
		
		//System.out.println("afterpos: "+posX+", "+posY+", "+posZ);
		if(this.ticksExisted == 2 && !worldObj.isRemote && !impact) 
			{
				worldObj.playSoundAtEntity(this, LegendGear2.MODID+":starfall", 100.0f, 1.0f);
				//System.out.println("making sound");
			}
		
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, this.boundingBox.minY-0.5, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ);

        if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
        {
			if(!impact && !worldObj.isRemote)
			{
				worldObj.createExplosion(this, posX, posY, posZ, 5.0f, false);
				setSize(0.5f, 0.5f);
				//dropItem(LegendGear.itemStardust.itemID, 1);
				//System.out.println("hit ground at "+posX+", "+posY+", "+posZ);
				worldObj.playSoundAtEntity(this, "game.neutral.swim.splash", 10.0f, 1.0f);
				
			//	worldObj.spawnEntityInWorld(new EntitySpellEffect(worldObj, SpellID.StarImpact, null, pos, 2.5, 1, false));
			}
			impact = true;
			
			this.motionY += 0.2;
			this.motionY *= 0.9;
        }
		
		if(this.isCollided && !impact)
		{
			if(!worldObj.isRemote)
			{
				worldObj.createExplosion(this, posX, posY, posZ, 5.0f, false);
				setSize(0.5f, 0.5f);
				Vec3 pos = Vec3.createVectorHelper(posX, posY, posZ);
				worldObj.spawnEntityInWorld(new EntitySpellEffect(worldObj, SpellID.StarImpact, null, pos, 2, 1, false));
				//dropItem(LegendGear.itemStardust.itemID, 1);
				//System.out.println("hit ground at "+posX+", "+posY+", "+posZ);
				
			}
			impact = true;
			//setDead();
		}
		
		if(worldObj.isRemote)
		{
			//LegendGear.proxy.addSparkleParticle(worldObj, posX-motionX, posY-motionY, posZ-motionZ, rand.nextGaussian()*0.2, rand.nextGaussian()*0.2, rand.nextGaussian()*0.2, 4.0f*dwindle_timer/DWINDLE_TIME);
		}
		else
		{
			if(impact && dwindle_timer % 25 == 0) worldObj.playSoundAtEntity(this, LegendGear2.MODID+":twinkle", 2.0f, 1.0f);
		}
		
		if(impact && --dwindle_timer <= 0) 
		{
			if(!worldObj.isRemote) 
			{
				dropItem(LegendGear2.starDust, 1);
				int xp = 5;
				while (xp > 0)
	            {
	                int var2 = 1;//EntityXPOrb.getXPSplit(xp);
	                xp -= var2;
	                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
	            }
			}
			setDead();
		}
		
		super.onUpdate();
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		impact = var1.getBoolean("impact");
		dwindle_timer = var1.getInteger("dwindle");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		var1.setBoolean("impact", impact);
		var1.setInteger("dwindle", dwindle_timer);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub
		buffer.writeBoolean(impact);
		buffer.writeInt(dwindle_timer);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub
		impact = buffer.readBoolean();
		dwindle_timer = buffer.readInt();
	}
	
	

}
