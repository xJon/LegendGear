package nmccoy.legendgear.blocks;

import net.minecraft.block.BlockCloth;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBoostBlock extends ItemBlock {

	public ItemBoostBlock(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		// TODO Auto-generated constructor stub
	}
	public String getItemNameIS(ItemStack par1ItemStack)
    {
        return super.getItemName() + "." + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage())];
    }
	public int getMetadata(int par1)
    {
        return par1;
    }
}
