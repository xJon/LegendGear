package nmccoy.legendgear.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderStarglass implements ISimpleBlockRenderingHandler {

	public RenderStarglass() {
		// TODO Auto-generated constructor stub
	}
	private static final ResourceLocation starTexture = new ResourceLocation(LegendGear2.MODID , "textures/chaosrainbow2.png");

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		//renderer.renderBlockAsItem(Blocks.sand, 0, 1.0f);
		//renderer.overrideBlockTexture = block.getIcon(0, 0);
       // GL11.glDepthMask(false);
        //GL11.glDepthFunc(GL11.GL_LEQUAL);
        //renderer.renderBlockAsItem(Blocks.sand, 0, 1.0F);
        //GL11.glDepthFunc(GL11.GL_LESS);
        //GL11.glDepthMask(true);
       // renderer.overrideBlockTexture=null;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
	//	renderer.renderStandardBlock(Blocks.sand, x, y, z);
	//	GL11.glDepthFunc(GL11.GL_LEQUAL);
		//GL11.glDepthMask(false);
		//GL11.glDisable(GL11.GL_LIGHTING);
		//renderer.renderStandardBlockWithColorMultiplier(block, x, y, z,1,1,1);
		//GL11.glEnable(GL11.GL_LIGHTING);
		//GL11.glDepthMask(true);
		//GL11.glDepthFunc(GL11.GL_LESS);
		Tessellator tess = Tessellator.instance;
		tess.draw();
		
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glDepthMask(false);
		renderer.enableAO=false;
		tess.startDrawingQuads();
		
		renderer.renderFaceXNeg(block, x, y, z, block.getIcon(0, 0));
		renderer.renderFaceXPos(block, x, y, z, block.getIcon(0, 0));
		renderer.renderFaceYNeg(block, x, y, z, block.getIcon(0, 0));
		renderer.renderFaceYPos(block, x, y, z, block.getIcon(0, 0));
		renderer.renderFaceZNeg(block, x, y, z, block.getIcon(0, 0));
		renderer.renderFaceZPos(block, x, y, z, block.getIcon(0, 0));
		tess.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthMask(true);
		tess.startDrawingQuads();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxy.starglassRenderID;
	}

}
