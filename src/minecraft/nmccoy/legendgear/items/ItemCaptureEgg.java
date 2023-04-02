package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityCaptureEgg;

public class ItemCaptureEgg extends Item {

	public ItemCaptureEgg(int par1) {
		super(par1);
		this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setIconIndex(23);
        this.setItemName("itemCaptureEgg");
	}
	@Override
	public String getTextureFile() {
		// TODO Auto-generated method stub
		return CommonProxy.ITEMS_PNG;
	}
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityCaptureEgg(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }

}
