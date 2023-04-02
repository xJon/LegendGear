package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityBomb;

public class ItemBombBag extends Item {
	public ItemBombBag(int id)
	{
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
		setIconIndex(43);
		setItemName("bombBag");
		setMaxDamage(0);
		setFull3D();
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
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
		return new ItemStack(LegendGear.bombBag, 1, Math.max(itemStack.getItemDamage()-LegendGear.bombItem.getItemStackLimit(), 0));
	}
	
	@Override
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		if(par2 == 0) return 43;
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
        if(par2 > 0 &&  par1ItemStack.getItemDamage() >= LegendGear.maxBombBagCapacity) return 0xFFFF55;
        return 0xFFFFFF;
    }
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	
	public int getRenderPasses(int metadata)
	{
		return 4;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par1ItemStack.getItemDamage() > 0)
		{
		 if (!par3EntityPlayer.capabilities.isCreativeMode)
	      {
	          par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()-1);
	      }

	      par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	      if (!par2World.isRemote)
	      {
	          par2World.spawnEntityInWorld(new EntityBomb(par2World, par3EntityPlayer, EntityBomb.LONG_FUSE_TIME));
	      }
	      par3EntityPlayer.swingItem();
		}
	      return par1ItemStack;
		
	}
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity) {
		// TODO Auto-generated method stub
		if(entity instanceof EntityBomb)
		{
			((EntityBomb) entity).fizzle();
			return true;
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
