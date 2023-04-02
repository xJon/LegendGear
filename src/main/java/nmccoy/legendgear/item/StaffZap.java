package nmccoy.legendgear.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

public class StaffZap extends SpellItem {

	public StaffZap() {
		// TODO Auto-generated constructor stub
		setUnlocalizedName("zapStaff");
		setTextureName(LegendGear2.MODID+":zapStaff");
		setMaxDamage(128);
		baseArcanePower = 4;
		baseMeleeDamage = 4;
		baseCastRadius = 2.25;
		baseCastRange = 9;
		baseCastTime = 1.5f;
		spellType = SpellID.Lightning1;
		isStaff = true;
		hitsWater = true;
	}
	
	
	@Override
	public void GenerateSpell(EntityPlayer caster, SpellID id, Vec3 location, double radius, double power, boolean critical)
	{
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(location.xCoord-0.5, location.yCoord-0.5, location.zCoord-0.5, location.xCoord+0.5, location.yCoord-0.5, location.zCoord-0.5);
		if(caster.worldObj.isMaterialInBB(bb, Material.water))
		//Block block = caster.worldObj.getBlock((int)Math.floor(location.xCoord), (int)Math.floor(location.yCoord),(int)Math.floor(location.zCoord));
		//if(block == Blocks.water)
			super.GenerateSpell(caster, id, location, radius+2.5, power+2, critical);
		else super.GenerateSpell(caster, id, location, radius, power, critical);
	}
	
	
}
