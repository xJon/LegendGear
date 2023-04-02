package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class PhoenixFeather extends Item {

	public PhoenixFeather(int par1) {
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
		setIconIndex(21);
		setItemName("phoenixFeather");
	}
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	

}
