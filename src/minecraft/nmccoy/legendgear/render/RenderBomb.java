package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.entities.EntityBomb;

public class RenderBomb extends Render {

	public RenderBomb() {
		// TODO Auto-generated constructor stub
		shadowSize = 0.125f;
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	 
	 	//icon = ((EntityThrownMedallion)par1Entity).iconIndex;
	 
		EntityBomb bomb = (EntityBomb)par1Entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4+0.1f, (float)par6);
        if(bomb.ridingEntity != null) GL11.glTranslatef(0, -0.6f, 0); 
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        if(bomb.fuse > EntityBomb.SHORT_FUSE_TIME)
        	GL11.glScalef(0.5F, 0.5F, 0.5F);
        else
        {
        	float i = 0.25f - 0.25f * bomb.fuse/EntityBomb.SHORT_FUSE_TIME;
        	GL11.glScalef(0.5F+i, 0.5F+i, 0.5F+i);
        }
        this.loadTexture(CommonProxy.ITEMS_PNG);
        Tessellator var10 = Tessellator.instance;
        
        int pulsephase = ((int)bomb.pulse)%2;
        this.func_77026_a(var10, 41+pulsephase);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private void func_77026_a(Tessellator par1Tessellator, int par2)
    {
        float var3 = (float)(par2 % 16 * 16 + 0) / 256.0F;
        float var4 = (float)(par2 % 16 * 16 + 16) / 256.0F;
        float var5 = (float)(par2 / 16 * 16 + 0) / 256.0F;
        float var6 = (float)(par2 / 16 * 16 + 16) / 256.0F;
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(0.0F - var9), 0.0D, (double)var3, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(0.0F - var9), 0.0D, (double)var4, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(var7 - var9), 0.0D, (double)var4, (double)var5);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(var7 - var9), 0.0D, (double)var3, (double)var5);
        par1Tessellator.draw();
    }

}
