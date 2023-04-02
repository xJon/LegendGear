package nmccoy.legendgear.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.render.Rainbow;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPrismaticXP extends Render
{
	private static final ResourceLocation experienceOrbTextures = new ResourceLocation(LegendGear2.MODID, "textures/xporb.png");
	
    public RenderPrismaticXP()
    {
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return experienceOrbTextures;
    }
    
    /**
     * Renders the XP Orb.
     */
    public void renderTheXPOrb(EntityXPOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        int var10 = par1EntityXPOrb.getTextureByXP();
        this.bindEntityTexture(par1EntityXPOrb);
        Tessellator var11 = Tessellator.instance;
        float var12 = (float)(var10 % 4 * 16 + 0) / 64.0F;
        float var13 = (float)(var10 % 4 * 16 + 16) / 64.0F;
        float var14 = (float)(var10 / 4 * 16 + 0) / 64.0F;
        float var15 = (float)(var10 / 4 * 16 + 16) / 64.0F;
        float var16 = 1.0F;
        float var17 = 0.5F;
        float var18 = 0.25F;
        int var19 = 240;//par1EntityXPOrb.getBrightnessForRender(par9);
        int var20 = var19 % 65536;
        int var21 = var19 / 65536;
       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var20 / 1.0F, (float)var21 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var26 = 255.0F;
        
        float time = (Minecraft.getSystemTime() % 1000000)/50f;
        float phase = (float)((Minecraft.getSystemTime() % 1000 / 1000f)+(par1EntityXPOrb.posX+par1EntityXPOrb.posZ)*0.05); 
//        float var27 = (float)(time + (float)par1EntityXPOrb.posX + (float)par1EntityXPOrb.posZ)*0.4f;
//        float r = (MathHelper.sin(var27 + 0.0F) + 1.0F) * 0.5F;
//        float g = (MathHelper.sin(var27 + 2.1f) + 1.0F) * 0.5F;
//        float b = (MathHelper.sin(var27 + 4.2f) + 1.0F) * 0.5F;
//        float resat = Math.min(r, Math.min(g, b));
//        r -= resat;
//        g -= resat;
//        b -= resat;
//        float scaler = 1/Math.max(r, Math.max(g, b));
//        
//        r = Math.min(scaler*r*0.5f + 0.5f, 1.0f);
//        g = Math.min(scaler*g*0.5f + 0.5f, 1.0f);
//        b = Math.min(scaler*b*0.5f + 0.5f, 1.0f);
        float r = Rainbow.r(phase);
        float g = Rainbow.g(phase);
        float b = Rainbow.b(phase);
        
        
        
        
        var21 = (int)(r * var26);
        int var22 = (int)(g * var26);
        int var23 = (int)(b * var26);
        int var24 = var21 << 16 | var22 << 8 | var23;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float var25 = 0.3F;
        GL11.glScalef(var25, var25, var25);
        GL11.glDisable(GL11.GL_LIGHTING);
        var11.startDrawingQuads();
        var11.setBrightness(240);
        var11.setColorRGBA_I(var24, 255);
        var11.setNormal(0.0F, 1.0F, 0.0F);
        var11.addVertexWithUV((double)(0.0F - var17), (double)(0.0F - var18), 0.0D, (double)var12, (double)var15);
        var11.addVertexWithUV((double)(var16 - var17), (double)(0.0F - var18), 0.0D, (double)var13, (double)var15);
        var11.addVertexWithUV((double)(var16 - var17), (double)(1.0F - var18), 0.0D, (double)var13, (double)var14);
        var11.addVertexWithUV((double)(0.0F - var17), (double)(1.0F - var18), 0.0D, (double)var12, (double)var14);
        var11.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderTheXPOrb((EntityXPOrb)par1Entity, par2, par4, par6, par8, par9);
    }
}
