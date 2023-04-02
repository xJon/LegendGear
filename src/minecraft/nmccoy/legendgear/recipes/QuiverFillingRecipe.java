package nmccoy.legendgear.recipes;

import cpw.mods.fml.common.ICraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class QuiverFillingRecipe implements IRecipe, ICraftingHandler {

	private Item quiver;
	private Item ammunition;
	private int maxCapacity;
	public QuiverFillingRecipe(Item container, Item ammo, int capacity) {
		// TODO Auto-generated constructor stub
		quiver = container;
		ammunition = ammo;
		maxCapacity = capacity;
	}

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		// TODO Auto-generated method stub
		int numQuivers = 0;
		boolean hasArrows = false;
		boolean hasNothingElse = true;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(is.itemID == quiver.itemID) numQuivers++;
				else if(is.itemID == ammunition.itemID) hasArrows = true;
				else hasNothingElse = false;
			}
		}
		return (numQuivers == 1 && hasArrows && hasNothingElse);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		// TODO Auto-generated method stub
		int numArrows = 0;
		int quiverFullness = 0;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(is.itemID == quiver.itemID) quiverFullness = is.getItemDamage();
				if(is.itemID == ammunition.itemID) numArrows += is.stackSize;
			}
		}
		return(new ItemStack(quiver, 1, Math.min(maxCapacity, quiverFullness+numArrows)));
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
					if(is.itemID == quiver.itemID) quiverFullness = is.getItemDamage();
					if(is.itemID == ammunition.itemID) numArrows += is.stackSize;
				}
			}
			int arrowsToTake = Math.min(maxCapacity-quiverFullness, numArrows);
			int totalArrows = arrowsToTake + quiverFullness;
			for(int i=0; i<craftMatrix.getSizeInventory(); i++)
			{
				ItemStack is = craftMatrix.getStackInSlot(i);
				if(is != null)
				{
					if(is.itemID == ammunition.itemID)
					{
						int deduction = Math.min(arrowsToTake, is.stackSize);
						arrowsToTake -= deduction;
						is.stackSize -= deduction-1; //crafting takes away the last one
					}
				}
			}
			item.setItemDamage(totalArrows);
			
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}
