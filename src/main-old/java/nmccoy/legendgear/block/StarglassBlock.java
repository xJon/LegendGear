package nmccoy.legendgear.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.client.ClientProxy;

public class StarglassBlock extends Block {

	public StarglassBlock() {
		super(Material.glass);
		// TODO Auto-generated constructor stub
		setBlockTextureName(LegendGear2.MODID+":starglassAnim");
		setLightLevel(0.2F);
		setBlockName("starGlass");
		setStepSound(soundTypeGlass);
		setCreativeTab(LegendGear2.legendgearTab);
		setHardness(1.5f);
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
    @Override
	public int getRenderBlockPass() {
		// TODO Auto-generated method stub
		return 1;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return ClientProxy.starglassRenderID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        if (block == this)
        {
             return false;
        }


        return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
	

}
