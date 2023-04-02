package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.Rainbow;
import nmccoy.legendgear.blocks.TileEntitySkybeam;

public class TileEntitySkybeamRender extends TileEntitySpecialRenderer {

	public TileEntitySkybeamRender() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
			double var6, float var8) {
		// TODO Auto-generated method stub
		GL11.glPushMatrix();
        GL11.glTranslatef((float)var2+0.5f, (float)var4+0.5f, (float)var6+0.5f);
        
        
        RenderEngine re = this.tileEntityRenderer.renderEngine;
		re.bindTexture(re.getTexture(CommonProxy.BEAM_PNG));
		Tessellator tess = Tessellator.instance;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	    GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_CULL_FACE);
	    
	    float phase = (float)(Minecraft.getSystemTime() % 2000 / 2000f);
	    TileEntitySkybeam beam = (TileEntitySkybeam) var1;
	    
	    float power = beam.powerlevel * 1.0f / TileEntitySkybeam.MAX_POWER;
	    if(beam.powerlevel > 0)
	    	
	    for(int i = 0; i < 8; i++)
	    {
	    	GL11.glPushMatrix();
	    	
	    	GL11.glTranslatef((float)Math.cos(Math.PI*3/4*i)*0.5f*power, 0, (float)Math.sin(Math.PI*3/4*i)*0.5f*power);
	    	GL11.glRotatef(180.0F - this.tileEntityRenderer.playerYaw, 0.0F, 1.0F, 0.0F);
	        
	    	
		    tess.startDrawingQuads();
	        tess.setColorRGBA_F(Rainbow.r(phase*2), Rainbow.g(phase*2), Rainbow.b(phase*2), 0.5f * (1-phase));
	        tess.setBrightness(240);
	        float w = 2.0f;
	        float h = 60*phase*power;
	        tess.addVertexWithUV(-w, h, 0, 0, 0);
	        tess.addVertexWithUV(w, h, 0, 1, 0);
	        tess.addVertexWithUV(w, 0, 0, 1, 1);
	        tess.addVertexWithUV(-w, 0, 0, 0, 1);
	        
//	        tess.addVertexWithUV(w, h, 0, 0, 0);
//	        tess.addVertexWithUV(-w, h, 0, 1, 0);
//	        tess.addVertexWithUV(-w, 0, 0, 1, 1);
//	        tess.addVertexWithUV(w, 0, 0, 0, 1);
//	        
	        tess.draw();
	        phase += 0.125;
	        if(phase > 1) phase -= 1;
	        GL11.glPopMatrix();
	    }
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
	    
        GL11.glPopMatrix();
	}

}
