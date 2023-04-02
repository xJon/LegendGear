package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import nmccoy.legendgear.entities.EntityGrindStar;

public class RenderGrindStar extends Render {

	public RenderGrindStar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doRender(Entity var1, double var2, double var4, double var6,
			float var8, float var9) {
		// TODO Auto-generated method stub
		EntityGrindStar star = (EntityGrindStar)var1;
		
float var26 = 255.0F;
        
        float time = (Minecraft.getSystemTime() % 1000000)/50f;
        
        float var27 = (float)(time)*0.4f;
        float r = (MathHelper.sin(var27 + 0.0F) + 1.0F) * 0.5F;
        float g = (MathHelper.sin(var27 + 2.1f) + 1.0F) * 0.5F;
        float b = (MathHelper.sin(var27 + 4.2f) + 1.0F) * 0.5F;
        float resat = Math.min(r, Math.min(g, b));
        r -= resat;
        g -= resat;
        b -= resat;
        float scaler = 1/Math.max(r, Math.max(g, b));
        
        r = Math.min(scaler*r*0.5f + 0.5f, 1.0f);
        g = Math.min(scaler*g*0.5f + 0.5f, 1.0f);
        b = Math.min(scaler*b*0.5f + 0.5f, 1.0f);
        
        
        
        int var21 = (int)(r * var26);
        int var22 = (int)(g * var26);
        int var23 = (int)(b * var26);
        int var24 = var21 << 16 | var22 << 8 | var23;
		
		
        Tessellator var12 = Tessellator.instance;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        var12.startDrawing(3);
        var12.setBrightness(240);
        var12.setColorOpaque_I(var24);
        
        var12.addVertex(var2+star.fromX-star.posX, var4+star.fromY-star.posY, var6+star.fromZ-star.posZ);
        
        var12.addVertex(var2+star.toX-star.posX, var4+star.toY-star.posY, var6+star.toZ-star.posZ);
        

        var12.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
