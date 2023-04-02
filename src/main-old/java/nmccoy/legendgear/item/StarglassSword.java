package nmccoy.legendgear.item;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import nmccoy.legendgear.CustomAttributes;
import nmccoy.legendgear.LegendGear2;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class StarglassSword extends ItemSword {

	public static final UUID starglassUUID = UUID.fromString("249c6e90-17a2-11e4-8c21-0800200c9a66");
	public static final AttributeModifier starglassCriticalDamage = new AttributeModifier(starglassUUID, "Starglass Critical Modifier", 13, 0);
	
	public StarglassSword() {
		super(LegendGear2.starglass);
		// TODO Auto-generated constructor stub
		setUnlocalizedName("starglassSword");
		setCreativeTab(LegendGear2.legendgearTab);
		setTextureName(LegendGear2.MODID+":starglassSwordAnim");
		
	}
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(CustomAttributes.criticalBonusDamage.getAttributeUnlocalizedName(), starglassCriticalDamage);
        return multimap;
    }
	
	@Override public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, java.util.List list, boolean p_77624_4_) {
		list.add("§cBreaks on critical hit");
	};
	
	@SubscribeEvent
	public void handleAttack(LivingHurtEvent lhe)
	{
		Entity source = lhe.source.getEntity();
		
		if( source instanceof EntityPlayer &&!lhe.entity.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer) source;
			ItemStack weapon = player.getHeldItem();
			double critBonus = player.getAttributeMap().getAttributeInstance(CustomAttributes.criticalBonusDamage).getAttributeValue(); 
			if(critBonus > 0)
			{
			//	System.out.println("attacked with Critical Damage attribute >0");
				 boolean flag = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null;
				if(flag)
				{
					//System.out.println("delivered a critical hit, added "+critBonus+" damage");
					lhe.ammount += critBonus;
				}
				System.out.println(weapon.getItem());
				boolean isStarglass = false;
				if(weapon.getItem() instanceof ItemTool  && ((ItemTool)weapon.getItem()).func_150913_i().equals(LegendGear2.starglass)) isStarglass = true;
				if(weapon.getItem() instanceof ItemSword  && ((ItemSword)weapon.getItem()).getToolMaterialName().equals(LegendGear2.starglass.name())) isStarglass = true;
				
				if(isStarglass)
				{
					//System.out.println("weapon should break");
					if(!player.capabilities.isCreativeMode) player.getHeldItem().damageItem(9999, player);
					player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":eviscerate", 0.8f, 1.3f);
					player.worldObj.playSoundAtEntity(player, "dig.glass", 0.8f, 0.75f);
				}
			}
		}
	}
}
