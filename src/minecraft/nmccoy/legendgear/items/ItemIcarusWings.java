package nmccoy.legendgear.items;

import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemIcarusWings extends ItemArmor {

	public ItemIcarusWings(int par1) {
		super(par1, LegendGear.armorFeathers, 0, 1);
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(36);
		setItemName("icarusWings");
		// TODO Auto-generated constructor stub
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@ForgeSubscribe
	public void HandleAirUpdate(LivingUpdateEvent lue) {
		// TODO Auto-generated method stub
		if(lue.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer ep = (EntityPlayer) lue.entityLiving;
			if(ep.inventory.armorInventory[2]!= null && ep.inventory.armorInventory[2].itemID == itemID)
			{
				if(!ep.onGround) ep.motionY += 0.015;
				
				ep.jumpMovementFactor = 0.06f;
				if(!ep.worldObj.isRemote) System.out.println("Server!");
				if(ep.isJumping)
				{
					boolean wasJumping = ep.getEntityData().getBoolean("wasJumping");
					if(!wasJumping && ep.isJumping)
					{
						System.out.println("jumped!"+ep.worldObj.isRemote);
						ep.motionY *= 0.75;
						ep.motionY += 0.6;
						ep.fallDistance = 0;
					}
				}
				ep.getEntityData().setBoolean("wasJumping", ep.isJumping);
				//System.out.println("worn!");
			}
		}
	}
	
	
}
