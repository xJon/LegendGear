package nmccoy.legendgear.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.entities.EntityShotHook;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderHookshot extends Render {

	int icon;
	public RenderHookshot(int iconindex)
	{
		icon = iconindex;
	}
	
	 public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	    {
		 
		 	//icon = ((EntityThrownMedallion)par1Entity).iconIndex;
		 

	        EntityShotHook esh = (EntityShotHook)par1Entity;
		 
	        GL11.glPushMatrix();
	        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glScalef(0.5F, 0.5F, 0.5F);
	        this.loadTexture(CommonProxy.ITEMS_PNG);
	        Tessellator var10 = Tessellator.instance;

	        if(!esh.anchored) this.func_77026_a(var10, icon);
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glPopMatrix();
	        
	        if (esh.shooter != null)
	        {
	            float var20 = esh.shooter.getSwingProgress(par9);
	            float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
	            Vec3 var22 = esh.worldObj.getWorldVec3Pool().getVecFromPool(-0.5D, 0.03D, 0.8D);
	            var22.rotateAroundX(-(esh.shooter.prevRotationPitch + (esh.shooter.rotationPitch - esh.shooter.prevRotationPitch) * par9) * (float)Math.PI / 180.0F);
	            var22.rotateAroundY(-(esh.shooter.prevRotationYaw + (esh.shooter.rotationYaw - esh.shooter.prevRotationYaw) * par9) * (float)Math.PI / 180.0F);
	            var22.rotateAroundY(var21 * 0.5F);
	            var22.rotateAroundX(-var21 * 0.7F);
	            double var23 = esh.shooter.prevPosX + (esh.shooter.posX - esh.shooter.prevPosX) * (double)par9 + var22.xCoord;
	            double var25 = esh.shooter.prevPosY + (esh.shooter.posY - esh.shooter.prevPosY) * (double)par9 + var22.yCoord;
	            double var27 = esh.shooter.prevPosZ + (esh.shooter.posZ - esh.shooter.prevPosZ) * (double)par9 + var22.zCoord;
	            double var29 = esh.shooter != Minecraft.getMinecraft().thePlayer ? (double)esh.shooter.getEyeHeight() : 0.0D;

	            if (this.renderManager.options.thirdPersonView > 0 || esh.shooter != Minecraft.getMinecraft().thePlayer)
	            {
	                float var31 = (esh.shooter.prevRenderYawOffset + (esh.shooter.renderYawOffset - esh.shooter.prevRenderYawOffset) * par9) * (float)Math.PI / 180.0F;
	                double var32 = (double)MathHelper.sin(var31);
	                double var34 = (double)MathHelper.cos(var31);
	                var23 = esh.shooter.prevPosX + (esh.shooter.posX - esh.shooter.prevPosX) * (double)par9 - var34 * 0.35D - var32 * 0.85D;
	                var25 = esh.shooter.prevPosY + var29 + (esh.shooter.posY - esh.shooter.prevPosY) * (double)par9 - 0.45D;
	                var27 = esh.shooter.prevPosZ + (esh.shooter.posZ - esh.shooter.prevPosZ) * (double)par9 - var32 * 0.35D + var34 * 0.85D;
	            }

	            double var46 = esh.prevPosX + (esh.posX - esh.prevPosX) * (double)par9;
	            double var33 = esh.prevPosY + (esh.posY - esh.prevPosY) * (double)par9 + 0.25D;
	            double var35 = esh.prevPosZ + (esh.posZ - esh.prevPosZ) * (double)par9;
	            double var37 = (double)((float)(var23 - var46));
	            double var39 = (double)((float)(var25 - var33));
	            double var41 = (double)((float)(var27 - var35));
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            GL11.glDisable(GL11.GL_LIGHTING);
	            var10.startDrawing(3);
	            var10.setColorOpaque_I(0);
	            byte var43 = 16;

	            for (int var44 = 0; var44 <= var43; ++var44)
	            {
	                float var45 = (float)var44 / (float)var43;
	                var10.addVertex(par2 + var37 * (double)var45, par4 + var39 * (double)(var45 * var45 + var45) * 0.5D + 0.25D, par6 + var41 * (double)var45);
	            }

	            var10.draw();
	            GL11.glEnable(GL11.GL_LIGHTING);
	            GL11.glEnable(GL11.GL_TEXTURE_2D);
	        }
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
