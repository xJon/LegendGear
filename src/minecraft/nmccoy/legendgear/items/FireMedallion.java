package nmccoy.legendgear.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.entities.EntityFireMedallion;

public class FireMedallion extends ItemMedallion {

	public FireMedallion(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setIconIndex(8);
		setItemName("fireMedallion");
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
            par2World.spawnEntityInWorld(new EntityFireMedallion(par2World, par3EntityPlayer));
        }
        
        //par1ItemStack.damageItem(1, par3EntityPlayer);

        return par1ItemStack;
    }

}
