package nmccoy.legendgear.item;

import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

public class StaffIce extends SpellItem {

	public StaffIce() {
		// TODO Auto-generated constructor stub
		setUnlocalizedName("iceStaff");
		setTextureName(LegendGear2.MODID+":iceStaff");
		setMaxDamage(128);
		baseArcanePower = 4;
		baseMeleeDamage = 4;
		baseCastRadius = 3;
		baseCastRange = 6;
		baseCastTime = 1.5f;
		spellType = SpellID.Ice1;
		isStaff = true;
		hitsWater= true;
	}
	
	
}
