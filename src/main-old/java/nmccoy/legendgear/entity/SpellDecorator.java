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

	public SpellID spellType;
	public List<MiniParticle> particles;
	public double radius;
	public double power;
	public boolean isCrit;
	
	public int longLife = 0;
	public int maxAge = 200;
	
	public SpellDecorator(World world)
	{
		super(world);
		//particles = new ArrayList<MiniParticle>();
	}
	
	public SpellDecorator(EntitySpellEffect spell) {
		super(spell.worldObj);
		posX = spell.posX;
		posY = spell.posY;
		posZ = spell.posZ;
		radius = spell.radius;
		power = spell.power;
		isCrit = spell.isCrit;
		spellType = spell.spellType;
		generateParticles();
		this.height = (float)radius;
		this.width = (float)radius;
		// TODO Auto-generated constructor stub
	}

	protected void generateParticles()
	{
		particles = new ArrayList<MiniParticle>();
		if(spellType == null) return;
		if(spellType.equals(SpellID.Twinkle))
		{
			Random rand = worldObj.rand;
			for(int i=0; i < radius * 20; i++)
			{
				MiniParticle p = MiniParticle.NewRadialMiniParticle(rand, radius, 0, 0);
				p.maxLife = 5;
				p.hibernateTime = rand.nextInt(15);
				particles.add(p);
			}
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
		if(spellType != null)
			buffer.writeInt(spellType.ordinal());
		else
			buffer.writeInt(0);
		buffer.writeDouble(radius);
		buffer.writeDouble(power);
		buffer.writeBoolean(isCrit);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		// TODO Auto-generated method stub
		spellType = SpellID.values()[additionalData.readInt()];
		radius = additionalData.readDouble();
		power = additionalData.readDouble();
		isCrit = additionalData.readBoolean();
	}

}
