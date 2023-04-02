package nmccoy.legendgear.items;

import net.minecraft.block.BlockCloth;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear;

public class ItemBlockChained extends ItemBlock {

	public ItemBlockChained(int par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return super.getItemName() + par1ItemStack.getItemDamage();
    }

}
