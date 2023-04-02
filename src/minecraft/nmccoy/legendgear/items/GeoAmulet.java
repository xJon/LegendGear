package nmccoy.legendgear.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class GeoAmulet extends Item {

	public static final double MIN_QUAKE_RADIUS = 3;
	public static final double MAX_QUAKE_RADIUS = 12;
	
	
	public GeoAmulet(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setIconIndex(27);
		setItemName("geoAmulet");
		setTextureFile(CommonProxy.ITEMS_PNG);
		setMaxDamage(50);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
	
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return 72000;
	}
	
	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	

}
