package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemSlimeSword extends ItemSword {

	public ItemSlimeSword(int par1) {
		super(par1, LegendGear.toolMaterialSlimeball);
		// TODO Auto-generated constructor stub
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(47);
		setItemName("slimeSword");
		setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
	public int getDamageVsEntity(Entity par1Entity) {
		// TODO Auto-generated method stub
		return 1;
	}
	
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
//			Entity entity) {
//		// TODO Auto-generated method stub
//		if(!player.worldObj.isRemote && entity instanceof EntityLiving)
//		{
//			EntityLiving el = (EntityLiving)entity;
//			player.worldObj.playSoundAtEntity(el, "mob.slime.small", 1.0f, 0.7f+itemRand.nextFloat()*0.5f);
//			el.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
//			el.attackEntityFrom(DamageSource.causePlayerDamage(player), 0);
//		}
//		return true;	
//
//	}
	@Override
	public boolean hitEntity(ItemStack par1ItemStack,
			EntityLiving entity, EntityLiving elf) {
		// TODO Auto-generated method stub
		boolean res  = super.hitEntity(par1ItemStack, entity, elf);
		if(elf instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) elf;
			if(!player.worldObj.isRemote && entity instanceof EntityLiving)
			{
				EntityLiving el = (EntityLiving)entity;
				player.worldObj.playSoundAtEntity(el, "mob.slime.small", 1.0f, 0.7f+itemRand.nextFloat()*0.5f);
				el.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
				el.heal(1);
				el.hurtResistantTime = el.maxHurtResistantTime;
				//el.attackEntityFrom(DamageSource.causePlayerDamage(player), 0);
			}
		}
		return res;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	


}
