package nmccoy.legendgear.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;

public class FragstoneBlock extends Block {

	public FragstoneBlock() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
		setBlockTextureName(LegendGear2.MODID+":fragstone");
		setBlockName("fragstoneBlock");
		setHardness(3f);
		setResistance(15f);
		setCreativeTab(LegendGear2.legendgearTab);
	}
	

}
