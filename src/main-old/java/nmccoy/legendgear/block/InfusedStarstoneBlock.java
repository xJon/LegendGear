package nmccoy.legendgear.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;

public class InfusedStarstoneBlock extends BlockContainer {

	public InfusedStarstoneBlock() {
		super(Material.iron);
		// TODO Auto-generated constructor stub
		setBlockTextureName(LegendGear2.MODID+":starstoneBlockAnim");
		setLightLevel(1.0F);
		setBlockName("infusedStarstoneBlock");
		setHardness(5f);
		setHarvestLevel("pickaxe", 2);
		//setCreativeTab(LegendGear2.legendgearTab);
	}
	
	
	@Override
	public int getRenderType() {
		// TODO Auto-generated method stub
		return 0;//CommonProxy.starstoneRenderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int par2) {
		// TODO Auto-generated method stub
		return new TileEntityStarstone();
	}
	

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z,
			int beaconX, int beaconY, int beaconZ) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return new ItemStack(LegendGear2.starDust, 1, 5);
	}
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return LegendGear2.starDust;
	}
	
    public int damageDropped(int p_149692_1_)
    {
    	return 5;
    }

}
