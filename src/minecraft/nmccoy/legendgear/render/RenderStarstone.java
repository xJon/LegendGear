package nmccoy.legendgear.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import nmccoy.legendgear.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderStarstone implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
float time = (Minecraft.getSystemTime() % 1000000)/50f;
        
        float var27 = (float)(time)*0.4f+x+y+z;
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
		if(ClientProxy.renderPass == 0)
		{
		  renderer.renderStandardBlock(block, x, y, z);
		}
		else
		{
			//renderer.renderAllFaces = true;
			Tessellator.instance.setBrightness(240);
			Tessellator.instance.setColorRGBA_F(r, g, b, 1);
			renderer.renderTopFace(block, x, y, z, 65);
		}
		System.out.println("rendering");
		return false;
	}

	
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxy.starstoneRenderID;
	}

}
