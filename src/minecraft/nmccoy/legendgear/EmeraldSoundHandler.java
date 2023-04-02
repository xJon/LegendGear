package nmccoy.legendgear;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IPickupNotifier;

public class EmeraldSoundHandler implements IPickupNotifier {

	@Override
	public void notifyPickup(EntityItem eitem, EntityPlayer player) {
		// TODO Auto-generated method stub
		ItemStack item = eitem.func_92014_d();
		if(LegendGear.emeraldShardsAllowed && item.itemID == LegendGear.emeraldShard.shiftedIndex)
		{	
			if(item.getItemDamage() == 0)
				eitem.worldObj.playSoundAtEntity(eitem, "assets.moneysmall", 0.5f, 1.0f);
			if(item.getItemDamage() == 1)
				eitem.worldObj.playSoundAtEntity(eitem, "assets.moneymid", 0.5f, 1.0f);
		}
		if(LegendGear.emeraldShardsAllowed && item.itemID == Item.emerald.shiftedIndex)
		{
			eitem.worldObj.playSoundAtEntity(eitem, "assets.moneybig", 0.5f, 1.0f);
		}
	}

}
