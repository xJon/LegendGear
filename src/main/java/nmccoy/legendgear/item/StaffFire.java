package nmccoy.legendgear.item;

import net.minecraft.item.ItemStack;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

public class StaffFire extends SpellItem {

	public StaffFire() {
		// TODO Auto-generated constructor stub
		setUnlocalizedName("fireStaff");
		setTextureName(LegendGear2.MODID+":fireStaff");
		setMaxDamage(128);
		baseArcanePower = 4;
		baseMeleeDamage = 4;
		baseCastRadius = 3.5;
		baseCastRange = 6;
		baseCastTime = 1.5f;
		spellType = SpellID.Fire1;
		isStaff = true;
	}
	
	
}
