package nmccoy.legendgear.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemRockCandy extends Item {

	public ItemRockCandy(int par1) {
		super(par1);
		hasSubtypes = true;
		setTextureFile(CommonProxy.ITEMS_PNG);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabFood);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getItemNameIS(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return "rockCandy"+par1ItemStack.getItemDamage();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return 32;
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		// TODO Auto-generated method stub
		return 96+par1;
	}
	@Override
	public int getIconIndex(ItemStack stack, int pass) {
		// TODO Auto-generated method stub
		return getIconFromDamage(stack.getItemDamage());
	}
	
	
	@Override
	public net.minecraft.item.EnumAction getItemUseAction(net.minecraft.item.ItemStack par1ItemStack) {
		return EnumAction.eat;
	};
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	@Override
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		par3EntityPlayer.getFoodStats().addStats(1, 0);
		if(!par2World.isRemote)
		{
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 15*20, 2));
			int damage = par1ItemStack.getItemDamage();
			if(damage==1) par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 15*20, 0));
			if(damage==3) par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 15*20, 3));
			if(damage==2) par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 15*20, 1));
			if(damage==4) par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 15*20, 3));
		}
		return new ItemStack(Item.stick);
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		// TODO Auto-generated method stub
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 3));
		par3List.add(new ItemStack(this, 1, 2));
		par3List.add(new ItemStack(this, 1, 4));
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
}
