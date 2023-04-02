package nmccoy.legendgear.render;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntityMagicBoomerang;

import org.lwjgl.opengl.GL11;

public class RenderBoomerang extends Render {

	public RenderBoomerang() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doRender(Entity var1, double x, double y, double z,
			float var8x, float var9x) {
		// TODO Auto-generated method stub
		
		Tessellator var5 = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9x - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9x, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.5f, -0.5f, 0);
        
        IIcon icon = LegendGear2.magicBoomerang.getIconFromDamage(0);

        this.bindEntityTexture(var1);
//        float var7 = ((float)(var6 % 16 * 16) + 0.0F) / 256.0F;
//        float var8 = ((float)(var6 % 16 * 16) + 15.99F) / 256.0F;
//        float var9 = ((float)(var6 / 16 * 16) + 0.0F) / 256.0F;
//        float var10 = ((float)(var6 / 16 * 16) + 15.99F) / 256.0F;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();

        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        GL11.glPopMatrix();

	}

	@Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.renderManager.renderEngine.getResourceLocation(LegendGear2.magicBoomerang.getSpriteNumber());
    }


}
