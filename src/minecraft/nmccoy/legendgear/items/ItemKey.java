package nmccoy.legendgear.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemKey extends Item {

	public ItemKey(int par1) {
		super(par1);
		hasSubtypes = true;
		setTextureFile(CommonProxy.ITEMS_PNG);
		setCreativeTab(CreativeTabs.tabMisc);
		// TODO Auto-generated constructor stub
	}
	
	public boolean shouldRotateAroundWhenRendering()
	 {
	        return true;
	 }
	
	@Override
	public int getIconFromDamage(int par1) {
		// TODO Auto-generated method stub
		return 59+par1;
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
	@Override
	public String getItemNameIS(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		int meta = par1ItemStack.getItemDamage();
		String name = "ironKey";
		if(meta == 1) name = "goldKey";
		if(meta == 2) name = "diamondKey";
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
	}

}
