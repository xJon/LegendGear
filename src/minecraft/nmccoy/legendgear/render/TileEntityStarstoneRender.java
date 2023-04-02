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

public class TileEntityStarstoneRender extends TileEntitySpecialRenderer {

	public TileEntityStarstoneRender() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double var2, double var4,
			double var6, float var8) {
		// TODO Auto-generated method stub
		float phase = (float)(Minecraft.getSystemTime() % 8000 / 8000f);
		float u = 0;//(((te.xCoord+te.zCoord)%32+32)%32)/32.0f;
		
		int sp = 64;
		float v = (((te.xCoord+te.zCoord+te.yCoord)%sp+sp)%sp)/(1.0f*sp) - phase;
		float ds = 1/32f;
        Tessellator tess = Tessellator.instance;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2, (float)var4, (float)var6); 
        RenderEngine re = this.tileEntityRenderer.renderEngine;
		re.bindTexture(re.getTexture(CommonProxy.CHAOS_PNG));
		
		GL11.glDepthMask(false);
		//GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(-2f, -5f);
        

        tess.startDrawingQuads();
        tess.setColorRGBA_F(1, 1, 1, 0.6f);
        
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord, te.yCoord+1, te.zCoord))
        {
	        tess.addVertexWithUV(0, 1, 0, u, v+ds);
	        tess.addVertexWithUV(0, 1, 1, u, v+ds*2);
	        tess.addVertexWithUV(1, 1, 1, u, v+ds*3);
	        tess.addVertexWithUV(1, 1, 0, u, v+ds*2);
        }
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord, te.yCoord-1, te.zCoord))
        {
        tess.addVertexWithUV(0, 0, 0, u, v);
        tess.addVertexWithUV(1, 0, 0, u, v+ds);
        tess.addVertexWithUV(1, 0, 1, u, v+ds*2);
        tess.addVertexWithUV(0, 0, 1, u, v+ds);
        }
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord-1, te.yCoord, te.zCoord))
        {
        tess.addVertexWithUV(0, 0, 1, u, v+ds);
        tess.addVertexWithUV(0, 1, 1, u, v+ds*2);
        tess.addVertexWithUV(0, 1, 0, u, v+ds);
        tess.addVertexWithUV(0, 0, 0, u, v);
        }
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord+1, te.yCoord, te.zCoord))
        {
        tess.addVertexWithUV(1, 1, 0, u, v+ds*2);
        tess.addVertexWithUV(1, 1, 1, u, v+ds*3);
        tess.addVertexWithUV(1, 0, 1, u, v+ds*2);
        tess.addVertexWithUV(1, 0, 0, u, v+ds);
        }
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord, te.yCoord, te.zCoord+1))
        {
        tess.addVertexWithUV(1, 0, 1, u, v+ds*2);
        tess.addVertexWithUV(1, 1, 1, u, v+ds*3);
        tess.addVertexWithUV(0, 1, 1, u, v+ds*2);
        tess.addVertexWithUV(0, 0, 1, u, v+ds);
        }
        if(!te.worldObj.isBlockOpaqueCube(te.xCoord, te.yCoord, te.zCoord-1))
        {
        tess.addVertexWithUV(0, 1, 0, u, v+ds);
        tess.addVertexWithUV(1, 1, 0, u, v+ds*2);
        tess.addVertexWithUV(1, 0, 0, u, v+ds);
        tess.addVertexWithUV(0, 0, 0, u, v);
        }
        
        
        
        
        
        
        tess.draw();
        

        GL11.glPolygonOffset(0, 0);
        GL11.glDisable(GL11.GL_BLEND);
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        //GL11.glDepthFunc(GL11.GL_LEQUAL);
	    
        GL11.glPopMatrix();
		
	}

}
