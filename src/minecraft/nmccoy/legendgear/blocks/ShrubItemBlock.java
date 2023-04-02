package nmccoy.legendgear.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear;

public class ShrubItemBlock extends ItemBlock {

	public ShrubItemBlock(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setHasSubtypes(true);
	}
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		if(itemstack.getItemDamage() == 0) return "itemMysticShrub";
		return "itemMysticShrubCharged";
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		// TODO Auto-generated method stub
		if(par1 > 0) return 1;
		return 17;
	}

}
