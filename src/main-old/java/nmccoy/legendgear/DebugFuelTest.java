package nmccoy.legendgear;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class DebugFuelTest implements IFuelHandler {

	public DebugFuelTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		// TODO Auto-generated method stub
		if(fuel.getItem().equals(Item.getItemFromBlock(LegendGear2.caltropsBlock))) return 500;
		return 0;
	}

}
