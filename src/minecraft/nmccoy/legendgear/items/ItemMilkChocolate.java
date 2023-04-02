package nmccoy.legendgear.items;

import com.google.common.collect.Sets;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemMilkChocolate extends ItemFood {

	public ItemMilkChocolate(int par1) {
		super(par1, 1, false);
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(76);
		setItemName("milkChocolate");
		setCreativeTab(CreativeTabs.tabFood);
		this.setAlwaysEdible();
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@Override
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		par3EntityPlayer.curePotionEffects(new ItemStack(Item.bucketMilk));
		return super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
	}
	
}
