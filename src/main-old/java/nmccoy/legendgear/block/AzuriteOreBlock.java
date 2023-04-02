package nmccoy.legendgear.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nmccoy.legendgear.LegendGear2;

public class AzuriteOreBlock extends Block {
	public AzuriteOreBlock() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
		setBlockTextureName(LegendGear2.MODID+":blockAzuriteStone");
		//setLightLevel(1.0F);
		setBlockName("azuriteOre");
		setHardness(3f);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(LegendGear2.legendgearTab);
	}
	@Override 
	protected boolean canSilkHarvest() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
