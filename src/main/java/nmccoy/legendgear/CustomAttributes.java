package nmccoy.legendgear;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class CustomAttributes  {
	public static final IAttribute criticalBonusDamage = (new RangedAttribute("lg2.criticalBonus", 0.0D, 0.0D, Double.MAX_VALUE)).setDescription("Critical Damage Bonus");
	public static final IAttribute arcanePower = (new RangedAttribute("lg2.arcanePower", 0.0D, 0.0D, Double.MAX_VALUE)).setDescription("Arcane Power");
	public static final IAttribute spellDamageRatio = (new RangedAttribute("lg2.spellDamageRatio", 1.0D, 0.0D, Double.MAX_VALUE)).setDescription("Spell Damage Ratio");
	public static final IAttribute spellDurationRatio = (new RangedAttribute("lg2.spellDurationRatio", 1.0D, 0.0D, Double.MAX_VALUE)).setDescription("Spell Duration Ratio");
	public static final IAttribute spellSurgePower = (new RangedAttribute("lg2.spellSurgePower", 1.0D, 0.0D, Double.MAX_VALUE)).setDescription("Spellsurge Power");
	public static final IAttribute armorPenaltyScaling = (new RangedAttribute("lg2.armorPenaltyScaling", 1.0D, 0.0D, Double.MAX_VALUE)).setDescription("Armor Penalty Multiplier");
	public static final IAttribute spellRange = (new RangedAttribute("lg2.spellRange", 0D, 0.0D, Double.MAX_VALUE)).setDescription("Spell Range").setShouldWatch(true);
	public static final IAttribute spellRadius = (new RangedAttribute("lg2.spellRadius", 0D, 0.0D, Double.MAX_VALUE)).setDescription("Spell Radius").setShouldWatch(true);
	
	
	public CustomAttributes() {
		// TODO Auto-generated constructor stub
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			player.getAttributeMap().registerAttribute(CustomAttributes.criticalBonusDamage);
			player.getAttributeMap().registerAttribute(CustomAttributes.arcanePower);
			player.getAttributeMap().registerAttribute(CustomAttributes.spellSurgePower);
			player.getAttributeMap().registerAttribute(CustomAttributes.spellDamageRatio);
			player.getAttributeMap().registerAttribute(CustomAttributes.spellDurationRatio);
			player.getAttributeMap().registerAttribute(CustomAttributes.armorPenaltyScaling);
			player.getAttributeMap().registerAttribute(CustomAttributes.spellRange);
			player.getAttributeMap().registerAttribute(CustomAttributes.spellRadius);
			
		}
	}
}