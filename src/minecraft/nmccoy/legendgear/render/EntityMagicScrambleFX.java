package nmccoy.legendgear.render;

import net.minecraft.world.World;

public class EntityMagicScrambleFX extends EntityMagicRuneFX {

	public EntityMagicScrambleFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12, float scale) {
		super(par1World, par2, par4, par6, par8, par10, par12, scale);
		// TODO Auto-generated constructor stub
		this.particleMaxAge = 15;
	}

	public EntityMagicScrambleFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		// TODO Auto-generated constructor stub
		this.particleMaxAge = 15;
	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
		this.setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
		float freshness = 1 - this.particleAge *1.0F/this.particleMaxAge;
		this.particleGreen = Math.min(freshness*2, 1);
		this.particleRed = Math.max(freshness*2-1, 0);
		this.particleBlue = 1;
	}

}
