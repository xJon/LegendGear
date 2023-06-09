package nmccoy.legendgear.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.Rainbow;
import nmccoy.legendgear.entities.EntityFallingStar;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFallingStar extends Render {

	public RenderFallingStar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	 public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	 
	 	//icon = ((EntityThrownMedallion)par1Entity).iconIndex;
	 
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        GL11.glDepthMask(false);
        
        
        this.loadTexture(CommonProxy.BEAM_PNG);
        Tessellator var10 = Tessellator.instance;
        
        
        float phase = (float)(Minecraft.getSystemTime() % 1000 / 1000f);
        float r = Rainbow.r(phase);
        float g = Rainbow.g(phase);
        float b = Rainbow.b(phase);
        
        
        float scale = ((EntityFallingStar)par1Entity).dwindle_timer *3.0f/EntityFallingStar.DWINDLE_TIME;
        
        EntityFallingStar star = (EntityFallingStar) par1Entity;
        if(star.dwindle_timer > star.DWINDLE_TIME - 10)
        {
	        GL11.glPushMatrix();
	       
	        var10.startDrawingQuads();
	        var10.setColorRGBA_F(r, g, b, 1);
	        var10.setBrightness(240);
	        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        double w = scale*0.3;
	        float sc = star.dwindle_timer - (star.DWINDLE_TIME-10);
	        sc *= 0.1f;
	        GL11.glScalef(sc, sc, sc);
	        
	        var10.addVertexWithUV(-w, 30, 0, 0, 0);
	        var10.addVertexWithUV(w, 30, 0, 1, 0);
	        var10.addVertexWithUV(w, 0, 0, 1, 1);
	        var10.addVertexWithUV(-w, 0, 0, 0, 1);
	        
	        var10.addVertexWithUV(w, 30, 0, 0, 0);
	        var10.addVertexWithUV(-w, 30, 0, 1, 0);
	        var10.addVertexWithUV(-w, 0, 0, 1, 1);
	        var10.addVertexWithUV(w, 0, 0, 0, 1);
	        
	        
	       
	        var10.draw();
	       
	        GL11.glPopMatrix();
        }
        
        this.loadTexture(CommonProxy.STAR_PNG);
        

        var10.startDrawingQuads();
        var10.setColorRGBA_F(r, g, b, 1);
        var10.setBrightness(240);
        //float scale = ((EntityFallingStar)par1Entity).dwindle_timer *3.0f/EntityFallingStar.DWINDLE_TIME;
        GL11.glScalef(scale, scale, scale);
        this.billboard(var10, (float)180*phase);
        
        var10.startDrawingQuads();
        var10.setColorRGBA_F(g, b, r, 1);
        var10.setBrightness(240);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        this.billboard(var10, (float)-270*phase);
        
        var10.startDrawingQuads();
        var10.setColorRGBA_F(b, r, g, 1);
        var10.setBrightness(240);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        this.billboard(var10, (float)90*phase);
        
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    private void billboard(Tessellator par1Tessellator, float angle)
    {
        float var3 = 0;
        float var4 = 1;
        float var5 = 0;
        float var6 = 1;
        float var7 = 1.0F;
        float var8 = 0.0F;
        float var9 = 0.0F;
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.5f, -0.5f, 0);
        
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(0.0F - var9), 0.0D, (double)var3, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(0.0F - var9), 0.0D, (double)var4, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(var7 - var9), 0.0D, (double)var4, (double)var5);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(var7 - var9), 0.0D, (double)var3, (double)var5);
        
        par1Tessellator.draw();
        
        
        GL11.glPopMatrix();
    }

}
