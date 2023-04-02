package nmccoy.legendgear.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;


public class EntitySpellEffect extends Entity implements IEntityAdditionalSpawnData {
	public enum SpellID
	{
		Twinkle, OrbExplosion(2, true), Fire1(15, true), Lightning1, Ice1(10, true), StarImpact(10, true), 
		WaterFlood(1, true), LavaFlood(1, true); 
		public int maxLife=10;
		public boolean affectsBlocks=false;
		private SpellID(){}
		private SpellID(int life){maxLife = life;}
		private SpellID(int life, boolean blocks){maxLife = life; affectsBlocks=blocks;}
		
	}
	public EntityPlayer caster;
	public double radius;
	public double power;
	public boolean isCrit;
	public SpellID spellType;
	public int lifeTicks;
	public int maxLife = 10;
	public HashSet hits;
	public ArrayList<ChunkPosition> blocksAffected;
	private static float blockRadiusFudge = 0.25f;
	
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
	
	private void affectBlock(int x, int y, int z)
	{
		Block block = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		if(spellType == SpellID.OrbExplosion)
		{
			boolean breakable = false;
			if(block == Blocks.cobblestone) breakable = true;
			if(!block.hasTileEntity(meta) && block.getBlockHardness(worldObj, x, y, z) == 0) breakable = true;
			if(breakable)
			{
				block.dropBlockAsItemWithChance(this.worldObj, x, y, z, this.worldObj.getBlockMetadata(x, y, z), 1, 0);
				worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(block) + (meta << 12));
				worldObj.setBlockToAir(x, y, z);
				blocksAffected.add(new ChunkPosition(x,y,z));
			}
		}
		if(spellType == SpellID.Ice1)
		{
			if((block == Blocks.water || block==Blocks.flowing_water)&&(meta&7)==0) worldObj.setBlock(x, y, z, Blocks.ice);
		}
		if(spellType == SpellID.Fire1)
		{
			if(block == Blocks.ice) worldObj.setBlock(x, y, z, Blocks.water);
		}
		if(spellType == SpellID.StarImpact)
		{
			if(block == Blocks.sand) worldObj.setBlock(x,y,z, LegendGear2.starSandBlock);
		}
		if(spellType == SpellID.WaterFlood)
		{
			if(block.isAir(worldObj, x, y, z)) worldObj.setBlock(x, y, z, Blocks.flowing_water, 1, 3);
		}
		if(spellType == SpellID.LavaFlood)
		{
			if(block.isAir(worldObj, x, y, z)) worldObj.setBlock(x, y, z, Blocks.flowing_lava, 1, 3);
		}
		
		
	}
	
	private void affectBlocks()
	{
		blocksAffected = new ArrayList<ChunkPosition>();
		double adjR = radius+blockRadiusFudge;
		AxisAlignedBB boxBounds = AxisAlignedBB.getBoundingBox(Math.floor(posX-adjR), Math.floor(posY-adjR), Math.floor(posZ-adjR), Math.ceil(posX+adjR), Math.ceil(posY+adjR), Math.ceil(posZ+adjR));
		Vec3 burstCenter = Vec3.createVectorHelper(posX, posY, posZ);
		for(int x=(int)boxBounds.minX; x<(int)boxBounds.maxX; x++)
			for(int y=(int)boxBounds.minY; y<(int)boxBounds.maxY; y++)
				for(int z=(int)boxBounds.minZ; z<(int)boxBounds.maxZ; z++)
				{
					Vec3 blockCenter = Vec3.createVectorHelper(x+0.5, y+0.5, z+0.5);
					if(blockCenter.squareDistanceTo(burstCenter) <= adjR*adjR) affectBlock(x, y, z);
				}
						
	}
	
	public void executeHit(Entity e)
	{
		Entity source = (caster == null? this : caster);
		switch(spellType)
		{
		
		case Twinkle:
		{
			if(e instanceof EntityLivingBase)
			{
				DamageSource twinkle = new EntityDamageSourceIndirect("glitterSpell", this, source).setMagicDamage().setDamageBypassesArmor();
				EntityLivingBase elb = (EntityLivingBase)e;
				elb.attackEntityFrom(twinkle, (float)power*2);
				if(isCrit) 
				{
					elb.addPotionEffect(new PotionEffect(Potion.blindness.id, 30, 0, false));
				}
				{
					Vec3 away = Vec3.createVectorHelper(elb.posX-source.posX, elb.posY-source.posY, elb.posZ-source.posZ);
					away=away.normalize();
					elb.addVelocity(away.xCoord*0.15, 0.15, away.zCoord*0.15);
				}
			}
			break;
		}
		case OrbExplosion:
		{
			if(e instanceof EntityLivingBase)
			{
				EntityLivingBase elb = (EntityLivingBase)e;
				float damage = (float) power;
				if(e.equals(caster)) damage = 4;
				elb.attackEntityFrom(new EntityDamageSourceIndirect("blastShell", this, source).setExplosion(), damage);
			}
			Vec3 away = Vec3.createVectorHelper(e.posX-posX, e.posY-posY, e.posZ-posZ);
			away=away.normalize();
			e.addVelocity(away.xCoord*0.5, away.yCoord*0.5, away.zCoord*0.5);
			break;
		}
		case Fire1:
		{
			if(e instanceof EntityLivingBase)
			{
				DamageSource flagra = new EntityDamageSourceIndirect("fireSpell", this, source).setMagicDamage().setFireDamage().setDamageBypassesArmor();
				EntityLivingBase elb = (EntityLivingBase)e;
				if(!elb.isImmuneToFire()) elb.attackEntityFrom(flagra, (float)power*2);
				if(!elb.isImmuneToFire()) elb.setFire((int)power*2);
				{
					Vec3 away = Vec3.createVectorHelper(elb.posX-source.posX, elb.posY-source.posY, elb.posZ-source.posZ);
					away=away.normalize();
					elb.addVelocity(away.xCoord*0.15, 0.15, away.zCoord*0.15);
				}
			}
			break;
		}
		case Lightning1:
		{
			if(e instanceof EntityLivingBase)
			{
				DamageSource ectrif = new EntityDamageSourceIndirect("zapSpell", this, source).setMagicDamage().setDamageBypassesArmor();
				EntityLivingBase elb = (EntityLivingBase)e;
				if(elb.isWet())
					elb.attackEntityFrom(ectrif, (float)power*3);
				else
					elb.attackEntityFrom(ectrif, (float)power*2);
				{
					Vec3 away = Vec3.createVectorHelper(elb.posX-source.posX, elb.posY-source.posY, elb.posZ-source.posZ);
					away=away.normalize();
					elb.addVelocity(away.xCoord*0.15, 0.15, away.zCoord*0.15);
				}
			}
			break;
		}
		case Ice1:
		{
			if(e instanceof EntityLivingBase)
			{
				DamageSource hibern = new EntityDamageSourceIndirect("iceSpell", this, source).setMagicDamage().setDamageBypassesArmor();
				EntityLivingBase elb = (EntityLivingBase)e;
				if(elb.isImmuneToFire())
					elb.attackEntityFrom(hibern, (float)power*3);
				else if(!(elb instanceof EntitySnowman))
				{
					elb.attackEntityFrom(hibern, (float)power*1.5f);
					elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, (int)power*20, 5, false));
					elb.addPotionEffect(new PotionEffect(Potion.jump.id, (int)power*20, -5, false));
				}
			}
			break;
		}
		
			
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
			
			/*if(spellType==SpellID.Ectrif)
			{
				if(worldObj.getBlock((int)Math.floor(posX),(int)Math.floor(posY), (int)Math.floor(posZ))==Blocks.water)
				{
					radius += 2;
					power += 2;
					if(!worldObj.isRemote) worldObj.playSoundAtEntity(this, "eviscerate", 1, 1);
				}
			}*/
			
			if(!worldObj.isRemote && spellType.affectsBlocks) affectBlocks();
			//LegendGear2.proxy.decorateSpell(this);
			if(!worldObj.isRemote) worldObj.spawnEntityInWorld(new SpellDecorator(this));
			if(!worldObj.isRemote) worldObj.playSoundAtEntity(this, LegendGear2.MODID+":spell."+spellType.toString(), 2.5f, 1.0f);
		}
		lifeTicks++;
		if(lifeTicks >= maxLife) setDead();
		
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
