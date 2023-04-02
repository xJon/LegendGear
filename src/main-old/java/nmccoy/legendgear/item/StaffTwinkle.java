package nmccoy.legendgear.item;

import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

public class StaffTwinkle extends SpellItem {

	public StaffTwinkle() {
		// TODO Auto-generated constructor stub
		setUnlocalizedName("twinkleStaff");
		setTextureName(LegendGear2.MODID+":twinkleStaff");
		setMaxDamage(60);
		baseArcanePower = 3;
		baseMeleeDamage = 3;
		baseCastRadius = 2;
		baseCastRange = 7;
		baseCastTime = 1.5f;
		spellType = SpellID.Twinkle;
		isStaff = true;
	}
	
}
