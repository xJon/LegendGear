package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public abstract class ItemMedallion extends Item {

	public static final int MAX_DAMAGE = 51;
	public static final int THROW_TIME = 15;
	
	public ItemMedallion(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(6);
		setMaxDamage(MAX_DAMAGE);
		setFull3D();
		hasSubtypes = false;
		setTextureFile(CommonProxy.ITEMS_PNG);
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return THROW_TIME;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return EnumAction.bow;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage() == 0)
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
    }
	
	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isRepairable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return par1ItemStack.getItemDamage() == 0;
	}
	
	public void charge(ItemStack item, int amt)
	{
		item.setItemDamage(Math.min(item.getItemDamage() - amt, 0));
	}

}
