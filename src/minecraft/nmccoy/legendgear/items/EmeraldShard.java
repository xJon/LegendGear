package nmccoy.legendgear.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class EmeraldShard extends Item {
	public EmeraldShard(int id)
	{
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMaterials);
		setIconIndex(16);
		setItemName("emeraldShard");
		setMaxDamage(0);
		setFull3D();
		hasSubtypes = true;
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	@Override
	public int getIconFromDamage(int par1)
    {
		if(par1 == 1)
        return 17;
		else return 16;
    }
	@Override
	public String getItemNameIS(ItemStack par1ItemStack)
    {
        if(par1ItemStack.getItemDamage() == 0) return "emeraldShard";
        else return "emeraldPiece";
    }
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.stackSize < LegendGear.emeraldShardExchangeRate) return par1ItemStack;
		par1ItemStack.stackSize -= LegendGear.emeraldShardExchangeRate;
		ItemStack merged;
		if(par1ItemStack.getItemDamage() == 0) 
			{
				merged = new ItemStack(LegendGear.emeraldShard, 1, 1);
				if(!par2World.isRemote) par2World.playSoundAtEntity(par3EntityPlayer, "assets.moneymid", 0.5f, 1.0f);
			}
		else 
			{
				merged = new ItemStack(Item.emerald);
				if(!par2World.isRemote) par2World.playSoundAtEntity(par3EntityPlayer, "assets.moneybig", 0.5f, 1.0f);
			}
		
		if (!par3EntityPlayer.inventory.addItemStackToInventory(merged))
        {
            par3EntityPlayer.dropPlayerItem(merged);
        }
		return par1ItemStack;
    }
}
