package nmccoy.legendgear.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nmccoy.legendgear.MiniParticle;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class SpellDecorator extends Entity implements IEntityAdditionalSpawnData {

	public int spellType;
	public List<MiniParticle> particles;
	public double radius;
	public double power;
	public boolean isCrit;
	
	public int longLife = 0;
	public int maxAge = 200;
	
	public SpellDecorator(World world)
	{
		super(world);
		spellType = Integer.MIN_VALUE;
		//particles = new ArrayList<MiniParticle>();
		ignoreFrustumCheck = true;
	}
	
	public SpellDecorator(EntitySpellEffect spell) {
		super(spell.worldObj);
		posX = spell.posX;
		posY = spell.posY;
		posZ = spell.posZ;
		radius = spell.radius;
		power = spell.power;
		isCrit = spell.isCrit;
		spellType = spell.spellType.ordinal();
		generateParticles();
		this.height = (float)radius*2;
		this.width = (float)radius*2;
		ignoreFrustumCheck = true;
		// TODO Auto-generated constructor stub
	}

	protected void generateParticles()
	{
		particles = new ArrayList<MiniParticle>();
		//if(spellType == null) return;

		Random rand = worldObj.rand;
		if(spellType == SpellID.Twinkle.ordinal())
		{
			for(int i=0; i < 50; i++)
			{
				MiniParticle p = MiniParticle.NewRadialMiniParticle(rand, radius, 0, 0);
				p.maxLife = 5;
				p.hibernateTime = rand.nextInt(15);
				particles.add(p);
			}
		}
		if(spellType == SpellID.Fire1.ordinal())
		{
			for(int i=0; i < 30; i++)
			{
				MiniParticle p = MiniParticle.NewRadialMiniParticle(rand, radius*0.75, 0.1, -0.002);
				p.maxLife = 15+rand.nextInt(10);
				p.ay=0.03;
				p.hibernateTime = rand.nextInt(5);
				p.drag=0.8;
				p.uniqueness = rand.nextDouble();
				particles.add(p);
			}
		}
		if(spellType == SpellID.Lightning1.ordinal())
		{
			for(int i=0; i < 30; i++)
			{
				MiniParticle p = MiniParticle.NewRadialMiniParticle(rand, radius, 0, 0);
				p.uniqueness = rand.nextDouble();
				p.maxLife = 15;
				p.hibernateTime = rand.nextInt(5);
				particles.add(p);
			}
		}
		if(spellType == SpellID.Ice1.ordinal())
		{
			for(int i=0; i < 20; i++)
			{
				MiniParticle p = MiniParticle.NewRadialMiniParticle(rand, radius, 0, 0);
				p.maxLife = 15;
				p.hibernateTime = rand.nextInt(5);
				particles.add(p);
			}
		}
		
		if(spellType == SpellID.OrbExplosion.ordinal())
		{
			if(worldObj.isRemote) worldObj.spawnParticle("largeexplode", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
			if(!worldObj.isRemote) this.worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
		if(particles == null)
		{
			generateParticles();
		}
		//System.out.println("x"+posX+"y"+posY+"z"+posZ);
		if(worldObj.isRemote) particles = MiniParticle.chewParticles(particles);
		longLife++;
		if(worldObj.isRemote && particles.size() == 0) 
		{
			setDead();
			System.out.println("out of particles, dying");
		}
		if(longLife > maxAge)
		{
			setDead();
			System.out.println("timer expired, dying");
		}
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		System.out.println("decorator spawned");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub

	}
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		// TODO Auto-generated method stub
	
		buffer.writeInt(spellType);
		buffer.writeDouble(radius);
		buffer.writeDouble(power);
		buffer.writeBoolean(isCrit);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		// TODO Auto-generated method stub
		spellType = additionalData.readInt();
		radius = additionalData.readDouble();
		power = additionalData.readDouble();
		isCrit = additionalData.readBoolean();
		this.height = (float)radius*2;
		this.width = (float)radius*2;
	}

}
