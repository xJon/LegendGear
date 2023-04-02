package nmccoy.legendgear;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.Vec3;

public class MiniParticle
{
	public double x,y,z,vx,vy,vz,ax,ay,az;
	public int hibernateTime;
	public int maxLife=20;
	public int lifeTicks;
	public double age;
	public double size = 1;
	public double drag = 1;
	public double uniqueness;
	
	
	public MiniParticle(){}
	
	public MiniParticle(double ix, double iy, double iz)
	{
		x=ix; y=iy; z=iz;
	}
	public MiniParticle(double ix, double iy, double iz, double ivx, double ivy, double ivz)
	{
		this(ix, iy, iz);
		vx=ivx; vy=ivy; vz=ivz;
	}
	public MiniParticle(double ix, double iy, double iz, double ivx, double ivy, double ivz, double iax, double iay, double iaz)
	{
		this(ix, iy, iz, ivx, ivy, ivz);
		ax=iax; ay=iay; az=iaz;
	}
	
	public static MiniParticle NewRadialMiniParticle(Random rand, double spread, double velScale, double accScale)
	{
		double gx = rand.nextGaussian();
		double gy = rand.nextGaussian();
		double gz = rand.nextGaussian();

		Vec3 outward = Vec3.createVectorHelper(gx, gy, gz).normalize();
		gx = outward.xCoord * rand.nextDouble() * spread;
		gy = outward.yCoord * rand.nextDouble() * spread;
		gz = outward.zCoord * rand.nextDouble() * spread;
				
		MiniParticle p = new MiniParticle();
		p.x=gx; p.y=gy; p.z=gz;
		p.vx=gx*velScale;p.vy=gy*velScale;p.vz=gz*velScale;
		p.ax=gx*accScale;p.ay=gy*accScale;p.az=gz*accScale;
		p.uniqueness = rand.nextDouble();
		return p;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MiniParticle: hibernate "+hibernateTime+", lifeTicks "+lifeTicks+", maxLife "+maxLife;
	}
	
	public boolean tick()
	{	
		if(hibernateTime-- > 0) return true;
		vx+=ax; vy+=ay;	vz+=az;
		vx*=drag; vy*=drag; vz*=drag;
		x+=vx;	y+=vy;	z+=vz;
		lifeTicks++;
		age = 1.0*lifeTicks/maxLife;
		return age < 1;
	}
	
	public static List<MiniParticle> chewParticles(List<MiniParticle> input)
	{
		List output = new ArrayList<MiniParticle>(input.size());
		for(MiniParticle p : input)	
		{
			if(p.tick()) output.add(p);
			//System.out.println("chewing "+p);
		}
		return output;
	}
}