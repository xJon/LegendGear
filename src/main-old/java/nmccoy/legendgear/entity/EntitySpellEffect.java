package nmccoy.legendgear.entity;

import io.netty.buffer.ByteBuf;

import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;


public class EntitySpellEffect extends Entity implements IEntityAdditionalSpawnData {
	public enum SpellID
	{
		Twinkle;
		public int maxLife=10;
		private SpellID(){}
		private SpellID(int life){maxLife = life;}
	}
	public EntityPlayer caster;
	public double radius;
	public double power;
	public boolean isCrit;
	public SpellID spellType;
	public int lifeTicks;
	public int maxLife = 10;
	public HashSet hits;
	
	public EntitySpellEffect(World world)
	{
		super(world);
		noClip = true;
		this.isImmuneToFire=true;
		 hits = new HashSet();
		 this.height = 0;
		 this.width = 0;
	}
	
	public EntitySpellEffect(World world, SpellID id, EntityPlayer caster, Vec3 location, double radius, double power, boolean critical) {
		this(world);
		// TODO Auto-generated constructor stub
		this.posX = location.xCoord;
		this.posY = location.yCoord;
		this.posZ = location.zCoord;
		
		//System.out.println("spell at "+posX+", "+posY+", "+posZ);
		
		this.caster = caster;
		this.radius = radius;
		this.power = power;
		this.isCrit = critical;
		this.spellType = id;
		this.maxLife = id.maxLife;
	}
	
	public void executeHit(Entity e)
	{
		switch(spellType)
		{
		case Twinkle:
			Entity source = caster == null? this : caster;
			if(e instanceof EntityLivingBase)
			{
				EntityLivingBase elb = (EntityLivingBase)e;
				elb.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, caster), (float)power*2);
				if(isCrit) elb.addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0, false));
				else elb.addPotionEffect(new PotionEffect(Potion.blindness.id, 30, 0, false));
			}
			break;
		default:
		}
	}
	
	public boolean confirmHit(Entity e)
	{
		if(!hits.contains(e))
		{
			executeHit(e);
			hits.add(e);	
			return true;
		}
		return false;
	}
	
	public void tryHittingEntities()
	{
		AxisAlignedBB firstHitPass = AxisAlignedBB.getBoundingBox(posX-radius, posY-radius, posZ-radius, posX+radius, posY+radius, posZ+radius);
		List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this,firstHitPass);
		for(Object obj:entities)
		{
			System.out.println("considering "+obj+" for hit");
			Entity ent = (Entity) obj;
			AxisAlignedBB entbox = ent.boundingBox;
			if(entbox==null)
			{

				System.out.println("null bounding box");
				continue;
			}
			double cx=Math.min(entbox.maxX, Math.max(posX, entbox.minX));
			double cy=Math.min(entbox.maxY, Math.max(posY, entbox.minY));
			double cz=Math.min(entbox.maxZ, Math.max(posZ, entbox.minZ));
			double dsq = (cx-posX)*(cx-posX) + (cy-posY)*(cy-posY) + (cz-posZ)*(cz-posZ);
			System.out.println("dsq = "+dsq+", radiusSquared = "+radius*radius);
			if(dsq <= radius*radius) confirmHit(ent);
		}
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub	
		super.onUpdate();	
		if(!worldObj.isRemote) tryHittingEntities();
		if(lifeTicks == 0)
		{
			//LegendGear2.proxy.decorateSpell(this);
			if(!worldObj.isRemote) worldObj.spawnEntityInWorld(new SpellDecorator(this));
			if(!worldObj.isRemote) worldObj.playSoundAtEntity(this, LegendGear2.MODID+":spell."+spellType.toString(), 1.0f, 1.0f);
		}
		lifeTicks++;
		if(lifeTicks > maxLife) setDead();
		
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
		spellType = SpellID.values()[tag.getInteger("spellType")];
		radius = tag.getDouble("radius");
		power = tag.getDouble("power");
		isCrit = tag.getBoolean("isCrit");
		maxLife = tag.getInteger("maxLife");
		lifeTicks = tag.getInteger("lifeTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
		tag.setInteger("spellType", spellType.ordinal());
		tag.setDouble("radius", radius);
		tag.setDouble("power", power);
		tag.setBoolean("isCrit", isCrit);
		tag.setInteger("maxLife", maxLife);
		tag.setInteger("lifeTicks", lifeTicks);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub
		buffer.writeInt(spellType.ordinal());
		buffer.writeDouble(radius);
		buffer.writeDouble(power);
		buffer.writeBoolean(isCrit);
		buffer.writeInt(maxLife);
		buffer.writeInt(lifeTicks);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		// TODO Auto-generated method stub
		spellType = SpellID.values()[additionalData.readInt()];
		radius = additionalData.readDouble();
		power = additionalData.readDouble();
		isCrit = additionalData.readBoolean();
		maxLife=additionalData.readInt();
		lifeTicks=additionalData.readInt();
	}

}
