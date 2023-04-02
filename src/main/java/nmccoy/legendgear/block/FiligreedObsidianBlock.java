package nmccoy.legendgear.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nmccoy.legendgear.LegendGear2;

public class FiligreedObsidianBlock extends Block {
	public FiligreedObsidianBlock() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
		setHarvestLevel("pickaxe", 3);
		
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(soundTypePiston);
		setBlockName("filigreedObsidian");
		setBlockTextureName(LegendGear2.MODID+":filigreedObsidian");
		setCreativeTab(LegendGear2.legendgearTab);
	}
	
}
