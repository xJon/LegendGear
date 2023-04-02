package nmccoy.legendgear.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import nmccoy.legendgear.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderJar implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
       
        
        		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
        boolean var5 = false;
        int var6 = 0;

        for (int var7 = 0; var7 < 8; ++var7)
        {
            byte var8 = 0;
            byte var9 = 1;

            if (var7 == 0)
            {
                var8 = 4;
            }

            if (var7 == 1)
            {
                var8 = 3;
            }

            if (var7 == 2)
            {
                var8 = 4;
            }

            if (var7 == 3)
            {
                var8 = 5;
                var9 = 2;
            }

            if (var7 == 4)
            {
                var8 = 6;
                var9 = 3;
            }

            if (var7 == 5)
            {
                var8 = 7;
                var9 = 5;
            }

            if (var7 == 6)
            {
                var8 = 6;
                var9 = 2;
            }

            if (var7 == 7)
            {
                var8 = 4;
            }

            float var10 = (float)var8 / 16.0F;
            float var11 = 1.0F - (float)var6 / 16.0F;
            float var12 = 1.0F - (float)(var6 + var9) / 16.0F;
            var6 += var9;
            renderer.setRenderBounds((double)(0.5F - var10), (double)var12, (double)(0.5F - var10), (double)(0.5F + var10), (double)var11, (double)(0.5F + var10));
            renderer.renderStandardBlock(block, x, y, z);
        }
        
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxy.jarRenderID;
	}

}
