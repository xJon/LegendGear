package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.client.ClientProxy;
import nmccoy.legendgear.entities.EntityShotHook;

public class ItemHookshot extends Item {

	public ItemHookshot(int par1)
    {
        super(par1);
        this.setMaxDamage(256);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setTextureFile(ClientProxy.ITEMS_PNG);
        setItemName("hookshot");
        setIconIndex(44);
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return EnumAction.bow;
	}
	
	 public boolean shouldRotateAroundWhenRendering()
	 {
	        return true;
	 }
		@Override
		public CreativeTabs[] getCreativeTabs() {
			// TODO Auto-generated method stub
			return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
		}
		
	 public boolean isFull3D()
     {
	     return true;
	 }
	 
	 @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return 72000;
	}
	 
	 @Override
	public int getIconIndex(ItemStack stack, int renderPass,
			EntityPlayer player, ItemStack usingItem, int useRemaining) {
		// TODO Auto-generated method stub
		if(useRemaining > 0 && useRemaining < getMaxItemUseDuration(usingItem)-3) return 45;
		else return 44;
	}
	 
	 @Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		// TODO Auto-generated method stub
		super.onUsingItemTick(stack, player, count);
		if(!player.worldObj.isRemote && count == getMaxItemUseDuration(stack)-3)
		{
			player.worldObj.spawnEntityInWorld(new EntityShotHook(player.worldObj, player));
			player.worldObj.playSoundAtEntity(player, "random.bow", 1.0f, 1.0f);
			//stack.damageItem(1, player);
			//if(player.getCurrentEquippedItem().stackSize == 0) player.destroyCurrentEquippedItem();
		}
	}
	 @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		par2World.playSoundAtEntity(par3EntityPlayer, "random.break", 0.2f, 2.0f);
		return par1ItemStack;
	}
	 
	 
	 @Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		// TODO Auto-generated method stub
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
		if(par4 < getMaxItemUseDuration(par1ItemStack) - 3) par1ItemStack.damageItem(1, par3EntityPlayer);
		if(par1ItemStack.stackSize == 0 && par3EntityPlayer.getCurrentEquippedItem() != null) par3EntityPlayer.destroyCurrentEquippedItem();
		
	}
}
