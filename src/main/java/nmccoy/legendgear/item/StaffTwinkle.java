package nmccoy.legendgear.item;

import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

public class StaffTwinkle extends SpellItem {

	
	public StaffTwinkle() {
		// TODO Auto-generated constructor stub
		setUnlocalizedName("twinkleStaff");
		setTextureName(LegendGear2.MODID+":twinkleStaff");
		setMaxDamage(64);
		baseArcanePower = 3;
		baseMeleeDamage = 3;
		baseCastRadius = 2.75;
		baseCastRange = 7;
		baseCastTime = 1.25f;
		spellType = SpellID.Twinkle;
		isStaff = true;
	}
	
}
