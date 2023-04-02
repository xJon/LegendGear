package nmccoy.legendgear.recipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;
import cpw.mods.fml.common.ICraftingHandler;

public class QuiverEmptyingRecipe implements ICraftingHandler, IRecipe {

	private Item quiver;
	private Item ammo;
	public QuiverEmptyingRecipe(Item container, Item ammunition)
	{
		quiver = container;
		ammo = ammunition;
	}
	
	
	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		// TODO Auto-generated method stub
		int numQuivers = 0;
		boolean hasNothingElse = true;
		boolean quiverNotEmpty = false;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(is.itemID == quiver.itemID) 
				{
					numQuivers++;
					quiverNotEmpty = is.getItemDamage() > 0;
				}
				else hasNothingElse = false;
			}
		}
		return (numQuivers == 1 && quiverNotEmpty && hasNothingElse);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		// TODO Auto-generated method stub
		int quiverFullness = 0;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(is.itemID == quiver.itemID) 
				{
					quiverFullness = is.getItemDamage();
				}
			}
		}
		return new ItemStack(ammo, Math.min(quiverFullness, ammo.getItemStackLimit()));
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		// TODO Auto-generated method stub
		if(craftMatrix instanceof InventoryCrafting && matches((InventoryCrafting)craftMatrix, player.worldObj))
		{
			int quiverFullness = 0;
			int numArrows = 0;
			for(int i=0; i<craftMatrix.getSizeInventory(); i++)
			{
				ItemStack is = craftMatrix.getStackInSlot(i);
				if(is != null)
				{
					if(is.itemID == quiver.itemID) 
					{
						is.setItemDamage(Math.max(is.getItemDamage() - ammo.getItemStackLimit(), 0));
						is.stackSize++;
					}
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub

	}

}
