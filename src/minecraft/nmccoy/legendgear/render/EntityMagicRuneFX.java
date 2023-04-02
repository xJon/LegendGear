package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityMagicRuneFX extends EntityFX {

	double hue;
	
	public EntityMagicRuneFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12, float scale)
	{
		this(par1World, par2, par4, par6, par8, par10, par12);
		particleScale = scale;
	}
	
	public EntityMagicRuneFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		// TODO Auto-generated constructor stub
		this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.posX = par2;
        this.posY = par4;
        this.posZ = par6;
        this.setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
        this.noClip = true;
        this.particleMaxAge = 30;
        this.particleAge = 0;
        this.hue = Math.random()*Math.PI*2;
        this.particleScale = 1.5F;
	}
	public float getBrightness(float par1)
    {
		return 1.0F;
    }
	
	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2,
			float par3, float par4, float par5, float par6, float par7) {
		// TODO Auto-generated method stub
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
		
	}
	
	public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        
        this.hue += 0.3;
        float r = (float)Math.sin(hue)/2 + 0.5f;
        float g = (float)Math.sin(hue+Math.PI*2/3)/2 + 0.5f;
        float b = (float)Math.sin(hue-Math.PI*2/3)/2 + 0.5f;
        float freshness = 1 - this.particleAge *1.0F/this.particleMaxAge;
        this.particleAlpha = freshness;
        this.particleRed = freshness + (1-freshness)*r;
        this.particleGreen = freshness + (1-freshness)*g;
        this.particleBlue = freshness + (1-freshness)*b;
    }
	public int getBrightnessForRender(float par1)
    {
		return 240;    
	}
}
