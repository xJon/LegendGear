package nmccoy.legendgear.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityEarthMedallion;

public class EarthMedallion extends ItemMedallion {
	
	public EarthMedallion(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setIconIndex(6);
		setItemName("earthMedallion");
	}
	
	
	@Override
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityEarthMedallion(par2World, par3EntityPlayer));
        }
        
        //par1ItemStack.damageItem(1, par3EntityPlayer);

        return par1ItemStack;
    }

}
