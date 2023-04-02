package nmccoy.legendgear.recipes;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear;

public class MedallionSwordRecipe implements IRecipe {

	ArrayList<Item> medallionIDs;
	
	public MedallionSwordRecipe() {
		medallionIDs = new ArrayList<Item>();
		medallionIDs.add(LegendGear.fireMedallion);
		medallionIDs.add(LegendGear.earthMedallion);
		medallionIDs.add(LegendGear.windMedallion);
	}
	

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		int numMedallions = 0;
		int numUnaugmentedSwords = 0;
		boolean hasNothingElse = true;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(medallionIDs.contains(is.getItem()) && is.getItemDamage() == 0) numMedallions++;
				else if(is.getItem() instanceof ItemSword &&
						(!is.hasTagCompound() ||
						!is.getTagCompound().hasKey("medallion"))
						&& is.getItem().getItemEnchantability() > 0
						&& is.getMaxDamage() > 0) numUnaugmentedSwords++;
				else hasNothingElse = false;
			}
		}
		return (numMedallions == 1 && numUnaugmentedSwords == 1 && hasNothingElse);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		// TODO Auto-generated method stub
		ItemStack medallion = null;
		ItemStack sword = null;
		for(int i=0; i<var1.getSizeInventory(); i++)
		{
			ItemStack is = var1.getStackInSlot(i);
			if(is != null)
			{
				if(medallionIDs.contains(is.getItem()) && is.getItemDamage() == 0) medallion = is;
				else if(is.getItem() instanceof ItemSword &&
						(!is.hasTagCompound() ||
						!is.getTagCompound().hasKey("medallion"))) sword = is;
			}
		}
		if(sword != null && medallion != null)
		{
			ItemStack modifiedSword = sword.copy();
			if(!modifiedSword.hasTagCompound()) 
				modifiedSword.setTagCompound(new NBTTagCompound());
			
			NBTTagCompound nbt = modifiedSword.getTagCompound();
			nbt.setInteger("medallion", medallion.itemID);
			
			NBTTagCompound display = nbt.getCompoundTag("display");
			NBTTagList lore = display.getTagList("Lore");
			
			String augmentName = "" + StringTranslate.getInstance().translateNamedKey(medallion.getItem().getLocalItemName(medallion)).trim();
			lore.appendTag(new NBTTagString("augment", "\u00a76Augment: "+augmentName));
			
			display.setTag("Lore", lore);
			nbt.setTag("display", display);
			
			//nbt.setTag("Lore", lore);
//			if (!nbt.hasKey("ench"))
//	        {
//	            nbt.setTag("ench", new NBTTagList("ench"));
//	        }
			return modifiedSword;
		}
		
		return null;
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

}
