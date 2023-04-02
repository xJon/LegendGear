package nmccoy.legendgear;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabLegendGear extends CreativeTabs {

	public CreativeTabLegendGear(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getIconItemStack() {
		// TODO Auto-generated method stub
		return new ItemStack(LegendGear.magicBoomerang);
	}

}
