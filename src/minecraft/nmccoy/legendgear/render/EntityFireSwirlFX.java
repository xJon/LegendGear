package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityFireSwirlFX extends EntityFX {

	float baseScale;
	
	public EntityFireSwirlFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12, float scale)
	{
		this(par1World, par2, par4, par6, par8, par10, par12);
		baseScale = scale;
	}
	
	public EntityFireSwirlFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		// TODO Auto-generated constructor stub
		this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.posX = par2;
        this.posY = par4;
        this.posZ = par6;
       // this.setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
        this.noClip = true;
        this.particleMaxAge = 7;
        this.particleAge = 0;
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

        this.setParticleTextureIndex(7-particleAge);
        
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        
        float freshness = 1 - this.particleAge *1.0F/this.particleMaxAge;
        float r = Math.min(freshness*3, 1);
        float g = Math.max(Math.min(freshness*3-1, 1), 0);
        float b = Math.max(Math.min(freshness*3-2, 1), 0);
                		
        this.particleAlpha = 1;//freshness;
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
        this.particleScale = (1.5f-0*freshness)*baseScale;
    }
	public int getBrightnessForRender(float par1)
    {
		return 240;    
	}
}
