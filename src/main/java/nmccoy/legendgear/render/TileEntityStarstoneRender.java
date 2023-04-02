package nmccoy.legendgear.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear2;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityStarstoneRender extends TileEntitySpecialRenderer {
	private static final ResourceLocation starTexture = new ResourceLocation(LegendGear2.MODID , "textures/chaosrainbow2.png");
	public TileEntityStarstoneRender() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double var2, double var4,
			double var6, float var8) {
		// TODO Auto-generated method stub
		float phase = (float)(Minecraft.getSystemTime() % 8000 / 8000f);
		float uphase = (float)(Minecraft.getSystemTime() % 11000 / 11000f);
		int sp = 64;
		
		float u = (((te.xCoord+te.zCoord+te.yCoord)%sp+sp)%sp)/(1.0f*sp) - uphase;//(float)(Minecraft.getSystemTime() % 80000 / 80000f);//(((te.xCoord+te.zCoord)%32+32)%32)/32.0f;
		float v = (((te.xCoord+te.zCoord+te.yCoord)%sp+sp)%sp)/(1.0f*sp) + phase;
		
		float ds = 1/64f;
		float dsu = 1/32f;
		
        Tessellator tess = Tessellator.instance;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2, (float)var4, (float)var6); 
        bindTexture(starTexture);
		
		GL11.glDepthMask(false);
		//GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE);//_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(-2f, -5f);
        

        tess.startDrawingQuads();
        tess.setColorRGBA_F(0.6f, 0.6f, 0.6f, 0.3f);
        
        if(!te.getWorldObj().getBlock(te.xCoord, te.yCoord+1, te.zCoord).isOpaqueCube())
        {
	        tess.addVertexWithUV(0, 1, 0, u+dsu, v+ds);
	        tess.addVertexWithUV(0, 1, 1, u+dsu*2, v+ds*2);
	        tess.addVertexWithUV(1, 1, 1, u+dsu*3, v+ds*3);
	        tess.addVertexWithUV(1, 1, 0, u+dsu*2, v+ds*2);
        }
        if(!te.getWorldObj().getBlock(te.xCoord, te.yCoord-1, te.zCoord).isOpaqueCube())
        {
        tess.addVertexWithUV(0, 0, 0, u, v);
        tess.addVertexWithUV(1, 0, 0, u+dsu, v+ds);
        tess.addVertexWithUV(1, 0, 1, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(0, 0, 1, u+dsu, v+ds);
        }
        if(!te.getWorldObj().getBlock(te.xCoord-1, te.yCoord, te.zCoord).isOpaqueCube())
        {
        tess.addVertexWithUV(0, 0, 1, u+dsu, v+ds);
        tess.addVertexWithUV(0, 1, 1, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(0, 1, 0, u+dsu, v+ds);
        tess.addVertexWithUV(0, 0, 0, u, v);
        }
        if(!te.getWorldObj().getBlock(te.xCoord+1, te.yCoord, te.zCoord).isOpaqueCube())
        {
        tess.addVertexWithUV(1, 1, 0, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(1, 1, 1, u+dsu*3, v+ds*3);
        tess.addVertexWithUV(1, 0, 1, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(1, 0, 0, u+dsu, v+ds);
        }
        if(!te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord+1).isOpaqueCube())
        {
        tess.addVertexWithUV(1, 0, 1, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(1, 1, 1, u+dsu*3, v+ds*3);
        tess.addVertexWithUV(0, 1, 1, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(0, 0, 1, u+dsu, v+ds);
        }
        if(!te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord-1).isOpaqueCube())
        {
        tess.addVertexWithUV(0, 1, 0, u+dsu, v+ds);
        tess.addVertexWithUV(1, 1, 0, u+dsu*2, v+ds*2);
        tess.addVertexWithUV(1, 0, 0, u+dsu, v+ds);
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
