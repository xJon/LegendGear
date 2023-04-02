package nmccoy.legendgear.items;

import java.util.Arrays;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class Quiver extends Item {
	public Quiver(int id){
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
		setIconIndex(19);
		setItemName("quiver");
		setMaxDamage(0);
		setFull3D();
	}
	
	@Override
	public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	@Override
	public boolean hasContainerItem() {
		// TODO Auto-generated method stub
		return !LegendGear.enableCraftyLoading;
	}
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ItemStack getContainerItemStack(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return new ItemStack(LegendGear.quiver, 1, Math.max(itemStack.getItemDamage()-Item.arrow.getItemStackLimit(), 0));
	}
	
	
	@Override
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		if(par2 == 0) return 19;
		if(par2 == 1) return par1 % 10 + 48;
		if(par2 == 2) 
			{
				if(par1 < 10) return 255;
				else return (par1 / 10) % 10 + 64;
			}
		if(par1 < 100) return 255;
		else return (par1 / 100) % 10 + 80;
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        if(par2 == 1 && par1ItemStack.getItemDamage() == 0) return 0xAAAAAA;
        if(par2 > 0 &&  par1ItemStack.getItemDamage() >= LegendGear.maxQuiverCapacity) return 0xFFFF55;
        return 0xFFFFFF;
    }
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	
	public int getRenderPasses(int metadata)
	{
		return 4;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par3EntityPlayer.isSneaking())
		{
			ItemStack[] inv = par3EntityPlayer.inventory.mainInventory;
			ItemStack moreArrows = null;
			int i;
			for(i = 0; i < inv.length; i++)
			{
				if(inv[i] != null && inv[i].itemID == Item.arrow.itemID)
				{
					moreArrows = inv[i];
					break;
				}
			}
			if(moreArrows != null)
			{
				int absorb = Math.min(moreArrows.stackSize, LegendGear.maxQuiverCapacity - par1ItemStack.getItemDamage());
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + absorb);
				moreArrows.stackSize -= absorb;
				if(moreArrows.stackSize <= 0) inv[i] = null;
				if(absorb>0) par1ItemStack.animationsToGo = 5;
			}
		}
		else
		{
			int total = Math.min(par1ItemStack.getItemDamage(), 64);
			if(total > 0)
			{
				ItemStack dumpedArrows = new ItemStack(Item.arrow, total);
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - dumpedArrows.stackSize);
				par3EntityPlayer.inventory.addItemStackToInventory(dumpedArrows);
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + dumpedArrows.stackSize);
				par1ItemStack.animationsToGo = 5;
			}
		}
		return par1ItemStack;
	}
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par1ItemStack.getItemDamage() == 0 || !(par3Entity instanceof EntityPlayer)) return;
		
		EntityPlayer ep = (EntityPlayer)par3Entity;
		if(ep.getCurrentEquippedItem() != null && Arrays.binarySearch(LegendGear.quiverLeakBows, ep.getCurrentEquippedItem().itemID) >= 0)			
		{
			if(!ep.inventory.hasItem(Item.arrow.itemID))
			{
				if(ep.inventory.addItemStackToInventory(new ItemStack(Item.arrow))) par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()-1);
			}
		}
		
		
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
}
