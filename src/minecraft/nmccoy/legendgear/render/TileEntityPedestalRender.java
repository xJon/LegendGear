package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.blocks.TileEntityPedestal;

public class TileEntityPedestalRender extends TileEntitySpecialRenderer {

	public TileEntityPedestalRender() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
			double var6, float var8) {
		TileEntityPedestal tep = (TileEntityPedestal)var1;
		if(tep.contents != null)
		{
			GL11.glPushMatrix();
	        GL11.glTranslatef((float)var2+0.5f, (float)var4+0.9f, (float)var6+0.5f);
	       
	        float yaw = 0;
	        if((tep.getBlockMetadata() & 2) == 0) yaw = 90;
	        
	        GL11.glRotatef(yaw, 0, 1, 0);
	        
	        
	        GL11.glRotatef(135f, 0, 0, 1);
	        
	        
	        GL11.glTranslatef(-0.5f, -0.5f, 0.03125F);
	        
			//System.out.println("trying to render item");
			RenderEngine re = this.tileEntityRenderer.renderEngine;
			re.bindTexture(re.getTexture(tep.contents.getItem().getTextureFile()));
			
			
			int icon = tep.contents.getItem().getIconFromDamage(tep.contents.getItemDamage());
	        float v7 = ((float)(icon % 16 * 16) + 0.0F) / 256.0F;
	        float v8 = ((float)(icon % 16 * 16) + 15.99F) / 256.0F;
	        float v9 = ((float)(icon / 16 * 16) + 0.0F) / 256.0F;
	        float v10 = ((float)(icon / 16 * 16) + 15.99F) / 256.0F;
	        
	        
	        ItemRenderer.renderItemIn2D(Tessellator.instance, v8, v9, v7, v10, 0.0625F);
	        
	        if (tep.contents.hasEffect())
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                re.bindTexture(re.getTexture("%blur%/misc/glint.png"));
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                float var14 = 0.76F;
                GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float var15 = 0.125F;
                GL11.glScalef(var15, var15, var15);
                float var16 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(var16, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(var15, var15, var15);
                var16 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-var16, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }
	        
	        GL11.glPopMatrix();
		}
		
	}

}
