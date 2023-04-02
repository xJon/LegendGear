package nmccoy.legendgear.entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityGrindStar extends Entity implements IEntityAdditionalSpawnData{

	//public Vec3 fromNode;
	//public Vec3 toNode;
	public double fromX;
	public double fromY;
	public double fromZ;
	public double toX;
	public double toY;
	public double toZ;
	
	
	public Vec3 nextNode;
	public static final double min_grind_speed = 0.02;
	public static final double max_grind_speed = 1.0;
	public static final double start_grind_speed = 0.3;
	public static final double drag = 0.002;
	public static final double YOFF = 1.0/16;
	
	
	public double speed;
	public static final int NODE_LINK_DISTANCE = 6;
	
	
	
	public boolean isPast(Vec3 node)
	{
		
		Vec3 vel = worldObj.getWorldVec3Pool().getVecFromPool(motionX, motionY, motionZ);
		Vec3 tonode = node.addVector(-posX, -posY, -posZ);
		return vel.dotProduct(tonode) <= 0;
	}
	
	public void snapOn()
	{
		Vec3 fromToPos = worldObj.getWorldVec3Pool().getVecFromPool(posX - fromX, posY - fromY, posZ - fromZ);
		Vec3 snapDir =  worldObj.getWorldVec3Pool().getVecFromPool(toX - fromX, toY- fromY, toZ - fromZ).normalize();
		double snapdist = snapDir.dotProduct(fromToPos);
		posX = fromX + snapDir.xCoord*snapdist;
		posY = fromY + snapDir.yCoord*snapdist;
		posZ = fromZ + snapDir.zCoord*snapdist;
		
	}
	
	public static List<Vec3>getNodesNearButNotAt(World world, int x, int y, int z, int radius)
	{
		ArrayList<Vec3> nodes = new ArrayList<Vec3>();
		for(int ix = x - radius; ix <= x+radius; ix++)
			for(int iy = y - radius; iy <= y+radius; iy++)
				for(int iz = z - radius; iz <= z+radius; iz++)
				{
					if(!(ix==x && iy==y & iz==z) && world.getBlockId(ix, iy, iz) == LegendGear.blockGrindNode.blockID)
						nodes.add(world.getWorldVec3Pool().getVecFromPool(ix+0.5, iy+0.5+YOFF, iz+0.5));
				}
		return nodes;
	}
	
	public boolean nodeHop()
	{
		int newOldX = (int)Math.floor(toX);
		int newOldY = (int)Math.floor(toY);
		int newOldZ = (int)Math.floor(toZ);
		List<Vec3> candidates = getNodesNearButNotAt(worldObj, newOldX, newOldY, newOldZ, NODE_LINK_DISTANCE);
		Vec3 dir = getLineDirection();
		fromX = toX;
		fromY = toY;
		fromZ = toZ;
		Vec3 toNode = findBestAlignedNode(worldObj, candidates, worldObj.getWorldVec3Pool().getVecFromPool(fromX, fromY, fromZ), dir, 0.80);
		if(toNode == null)
		{
			boot(); 
			//System.out.println("no next node");
			
			return false;
		}
		else
		{
			toX = toNode.xCoord;
			toY = toNode.yCoord;
			toZ = toNode.zCoord;
			
			Vec3 newdir = getLineDirection();
			if(newdir.yCoord < 0 && dir.yCoord > 0 && !riddenByEntity.isSneaking()) 
			{
				//boot(); return false;
			}
			
			float speedlevel =(float)( (speed-min_grind_speed)/(max_grind_speed-min_grind_speed));
			//worldObj.playSoundAtEntity(this, "assets.wah", 0.3f, 0.5f+speedlevel*1.5f);
			
			return true;
		}
	}
	
	public void boot()
	{
		setDead();
		if(riddenByEntity != null)
		{
			Entity e = riddenByEntity;
			double oldX = e.posX;
			double oldY = e.posY;
			double oldZ = e.posZ;
			
			
			e.motionX = this.motionX;
			e.motionY = this.motionY;
			e.motionZ = this.motionZ;
			e.posX = this.posX;
			e.posY = this.posY + this.getMountedYOffset() + 0.1;
			e.posZ = this.posZ;
			e.fallDistance = 0;
		}
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		
		loadCoordsFromData();
		
		Vec3 dir = getLineDirection();
		
		if(riddenByEntity == null)
		{
			setDead();
			return;
		}
		
		if(worldObj.getBlockId((int)Math.floor(fromX), (int)Math.floor(fromY), (int)Math.floor(fromZ)) != LegendGear.blockGrindNode.blockID)
		{
			boot();
			//System.out.println("from block is bad");
			return;
		}
		if(worldObj.getBlockId((int)Math.floor(toX), (int)Math.floor(toY), (int)Math.floor(toZ)) != LegendGear.blockGrindNode.blockID)
		{
			boot();
			//System.out.println("to block is bad");
			return;
		}
		
		if(riddenByEntity.getEntityData().getBoolean("justJumped"))
		{
			Entity ent = riddenByEntity;
			//System.out.println("jumped!");
			boot();
			ent.motionY += 0.5;
		}
		
		speed = worldObj.getWorldVec3Pool().getVecFromPool(motionX, motionY, motionZ).lengthVector();
		
		speed += dir.yCoord * -0.02;
		if(riddenByEntity.isSneaking()) speed += dir.yCoord * -0.04;
		speed -= drag;
		
		//dataWatcher.updateObject(24,new Integer((int)(speed*1000000)));
		if(speed < min_grind_speed && !worldObj.isRemote) 
		{
			boot();
			return;
		}
		if(speed > max_grind_speed) speed = max_grind_speed;
		
		motionX = speed*dir.xCoord;
		motionY = speed*dir.yCoord;
		motionZ = speed*dir.zCoord;
		
		String side = "server";
		if(worldObj.isRemote) side = "client";
		//System.out.println(side + ": Updating, pos ("+posX+", "+posY+", "+posZ+"),  direction "+dir
		//		+", speed"+speed+", from: ("+fromX+", "+fromY+", "+ fromZ+") to: ("+toX+", "+toY+", "+toZ+")");
		
		//moveEntity(motionX, motionY, motionZ);
		
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		
		if(isPast(worldObj.getWorldVec3Pool().getVecFromPool(toX, toY, toZ)))
		{
			if(nodeHop()) snapOn();
		}
		if(worldObj.isRemote)
		{
			LegendGear.proxy.addSparkleParticle(worldObj, posX, posY, posZ, rand.nextGaussian()*0.1, rand.nextGaussian()*0.1, rand.nextGaussian()*0.1, 3.0f);
			LegendGear.proxy.addRuneParticle(worldObj, posX, posY, posZ, 0, 0.0, 0, 2.0f);
		}
		if(!worldObj.isRemote && ticksExisted % 5== 0)
		{
			float speedlevel =(float)( (speed-min_grind_speed)/(max_grind_speed-min_grind_speed));
			worldObj.playSoundAtEntity(this, "assets.gah", 0.3f, 0.5f+speedlevel*1.5f);
		}
		
		saveCoordsToData();
	}
	
	public void loadCoordsFromData()
	{
		String[] parts = dataWatcher.getWatchableObjectString(25).split(",");
		if(parts.length != 6)
		{
			System.out.println("parts length not right size!");
		}
		else
		{
			fromX = Integer.parseInt(parts[0]) + 0.5;
			fromY = Integer.parseInt(parts[1]) + 0.5 + YOFF;
			fromZ = Integer.parseInt(parts[2]) + 0.5;
			toX = Integer.parseInt(parts[3]) + 0.5;
			toY = Integer.parseInt(parts[4]) + 0.5 + YOFF;
			toZ = Integer.parseInt(parts[5]) + 0.5;
		}
		//System.out.println("loaded "+dataWatcher.getWatchableObjectString(25) +" as ("+fromX+", "+fromY+", "+fromZ+")-("+toX+", "+toY+", "+toZ+")");
	}
	
	public void saveCoordsToData()
	{
		String out = "";
		out += (int)Math.floor(fromX)+",";
		out += (int)Math.floor(fromY)+",";
		out += (int)Math.floor(fromZ)+",";
		out += (int)Math.floor(toX)+",";
		out += (int)Math.floor(toY)+",";
		out += (int)Math.floor(toZ);
		dataWatcher.updateObject(25, out);
		//System.out.println("saved "+out);
	}
	
	public static Vec3 findBestAlignedNode(World world, List<Vec3> nodes, Vec3 pos, Vec3 dir)
	{
		return findBestAlignedNode(world, nodes, pos, dir, 0);
	}
	
	
	public static Vec3 findBestAlignedNode(World world, List<Vec3> nodes, Vec3 pos, Vec3 dir, double minimumDot)
	{
		Vec3 best = null;
		double matchvalue = minimumDot;
		dir = dir.normalize();
		for(Vec3 test:nodes)
		{
			Vec3 posToTest = test.addVector(-pos.xCoord, -pos.yCoord, -pos.zCoord).normalize();
			Vec3 posCopy = world.getWorldVec3Pool().getVecFromPool(pos.xCoord, pos.yCoord, pos.zCoord);
			double dot = dir.dotProduct(posToTest);
			if(dot > matchvalue && world.rayTraceBlocks_do_do(posCopy, test, false, true) == null)
			{
				matchvalue = dot;
				best = test;
			}
		}
		return best;
	}
	
	public Vec3 getLineDirection()
	{
		return worldObj.getWorldVec3Pool().getVecFromPool(toX-fromX, toY-fromY, toZ-fromZ).normalize();
	}
	

	public static EntityGrindStar tryMakingStar(World world, int x, int y, int z, EntityLiving rider)
	{
		Vec3 from = world.getWorldVec3Pool().getVecFromPool(x+0.5, y+0.5+YOFF, z+0.5);
		Vec3 dir = rider.getLookVec();
		List<Vec3> nodes = getNodesNearButNotAt(world, x, y, z, NODE_LINK_DISTANCE);
		//System.out.println("trying to make star, num nodes for next = "+nodes.size());
		Vec3 to = findBestAlignedNode(world, nodes, from, dir, 0);
		if(to == null) return null;
		else
		{
		//	System.out.println("made star from "+from+" to "+to);
			Vec3 linedir = to.addVector(-from.xCoord, -from.yCoord, -from.zCoord).normalize();
			
			//System.out.println(linedir);
			//System.out.println("Rider: "+rider.motionX+", "+rider.motionY+","+rider.motionZ);
			double speeds = rider.motionX*linedir.xCoord +rider.motionY*linedir.yCoord + rider.motionZ*linedir.zCoord;  
			//System.out.println(speeds);
			return new EntityGrindStar(world, from, to, speeds);
		}
	}
	
	public EntityGrindStar(World world, Vec3 startNode, Vec3 towardsNode, double speeds)
	{
		super(world);
		Vec3 fromNode = startNode;
		Vec3 toNode = towardsNode;
		
		fromX = startNode.xCoord;
		fromY = startNode.yCoord;
		fromZ = startNode.zCoord;
		toX = towardsNode.xCoord;
		toY = towardsNode.yCoord;
		toZ = towardsNode.zCoord;
		
		posX = fromNode.xCoord;
		posY = fromNode.yCoord;
		posZ = fromNode.zCoord;
		speed = start_grind_speed;
		Vec3 dir = getLineDirection();
		speed = Math.max(speed, speeds);
		
		motionX = dir.xCoord*speed;
		motionY = dir.yCoord*speed;
		motionZ = dir.zCoord*speed;
		noClip = true;
		setSize(0.25f, 0.25f);
		dataWatcher.addObject(24, new Integer((int)(speed*1000000)));
		dataWatcher.addObject(25, "");
		saveCoordsToData();
	}
	
	public EntityGrindStar(World par1World) {
		super(par1World);
		noClip = true;
		setSize(0.25f, 0.25f);
		speed = start_grind_speed;
		dataWatcher.addObject(24, new Integer((int)(speed*1000000)));
		dataWatcher.addObject(25, "");
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean shouldRiderSit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}
	@Override
	public double getMountedYOffset() {
		// TODO Auto-generated method stub
		return 0.5;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		
		fromX = var1.getDouble("fromX");
		fromY = var1.getDouble("fromY");
		 fromZ = var1.getDouble("fromZ");
		toX = var1.getDouble("toX");
		toY = var1.getDouble("toY");
		toZ = var1.getDouble("toZ");
		//fromNode = worldObj.getWorldVec3Pool().getVecFromPool(fromX, fromY, fromZ);
		//toNode = worldObj.getWorldVec3Pool().getVecFromPool(toX, toY, toZ);
		speed = var1.getDouble("speed");
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub
		var1.setDouble("fromX", fromX);
		var1.setDouble("fromY", fromY);
		var1.setDouble("fromZ", fromZ);
		var1.setDouble("toX", toX);
		var1.setDouble("toY", toY);
		var1.setDouble("toZ", toZ);
		var1.setDouble("speed", speed);
		
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeDouble(fromX);
		data.writeDouble(fromY);
		data.writeDouble(fromZ);
		data.writeDouble(toX);
		data.writeDouble(toY);
		data.writeDouble(toZ);
		data.writeDouble(speed);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		 fromX = data.readDouble();
		fromY = data.readDouble();
		fromZ = data.readDouble();
		toX = data.readDouble();
		toY = data.readDouble();
		toZ = data.readDouble();
		//fromNode = worldObj.getWorldVec3Pool().getVecFromPool(fromX, fromY, fromZ);
		//toNode = worldObj.getWorldVec3Pool().getVecFromPool(toX, toY, toZ);
		
		speed=data.readDouble();
		
		saveCoordsToData();
		//System.out.println("read spawn data, "+fromNode+toNode);
		
	}

}
