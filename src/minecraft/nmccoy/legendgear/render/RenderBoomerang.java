package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import nmccoy.legendgear.LegendGear;

public class RenderBoomerang extends Render {

	public RenderBoomerang() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doRender(Entity var1, double x, double y, double z,
			float var8x, float var9x) {
		// TODO Auto-generated method stub
		this.loadTexture(LegendGear.proxy.ITEMS_PNG);
		Tessellator var5 = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9x - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9x, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.5f, -0.5f, 0);
        
        int var6 = LegendGear.magicBoomerang.getIconFromDamage(0);
        float var7 = ((float)(var6 % 16 * 16) + 0.0F) / 256.0F;
        float var8 = ((float)(var6 % 16 * 16) + 15.99F) / 256.0F;
        float var9 = ((float)(var6 / 16 * 16) + 0.0F) / 256.0F;
        float var10 = ((float)(var6 / 16 * 16) + 15.99F) / 256.0F;
        
        ItemRenderer.renderItemIn2D(var5, var8, var9, var7, var10, 0.0625F);
        GL11.glPopMatrix();

	}

}
