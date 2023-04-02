package nmccoy.legendgear;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.Constants.NBT;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class StarSpirit {
	public Map<ItemStack, Integer> opinions;
	public static final int NONE = -1;
	public static final int PHOENIX = 0;
	public static final int ENDCHILD = 1;
	public static final int NETHERLORD = 2;
	public static final int[] starMaterialValues = {10, 50, 500, 20, 100, 1000};
	public static final int APPRECIATE = 10;
	public static final int LIKE = 30;
	public static final int LOVE = 100;
	public static final int FAVORITE = 300;
	
	public static final int DONATION_COOLDOWN = 60 * 5;
	
	public static final float STALENESS_SCALING = 0.125f;
	
	public int type;
	
	public static void randomTell(int spirit, EntityPlayer player, Object... args)
	{
		if(args.length == 0) return;
		if(player.worldObj.isRemote) return;
		int which = player.worldObj.rand.nextInt(args.length);
		String message = "";
		if(spirit == PHOENIX) message = message+ChatFormatting.GOLD+ChatFormatting.ITALIC;
		if(spirit == ENDCHILD) message = message+ChatFormatting.LIGHT_PURPLE+ChatFormatting.ITALIC;
		if(spirit == NETHERLORD) message = message+ChatFormatting.RED+ChatFormatting.ITALIC;
		message += args[which].toString();
		player.addChatMessage(new ChatComponentText(message));
	}
	
	public static int getRecentDonationCharge(int spirit, EntityPlayer player)
	{
		NBTTagCompound favorTag = getPlayerFavorTag(player, spirit);
		int donationTime = favorTag.getInteger("lastDonationWorldSeconds");
		int nowTime = (int)(player.worldObj.getTotalWorldTime()/20);
		int donationCharge = favorTag.getInteger("recentDonationCharge");
		int donationAgo = nowTime-donationTime;
		
		System.out.println("donation charge "+donationCharge);
		System.out.println("last donation "+donationAgo+" seconds ago");
		
		if(donationAgo >= DONATION_COOLDOWN) donationCharge = 0;
		else donationCharge = (int)(donationCharge*(1 - (1.0*donationAgo)/DONATION_COOLDOWN));
		
		System.out.println("modified donation charge "+donationCharge);
		
		return donationCharge;
	}
	
	
	public static void commentOnOffering(int spirit, EntityPlayer player, ItemStack stack, int value)
	{
		if(value < 10) return;
		
		if(spirit == ENDCHILD)
		{
			if(stack.getItem() == Item.getItemFromBlock(Blocks.grass)) 
			{
				randomTell(spirit, player, "oh! verdant linears!", "hairy ground collectioning?", "of place piece grateful", "why does not lift when? nice");
				return;
			}
			if(value >= 50)
			{
				randomTell(spirit, player, "nice nice nice nice", "it brings a gratitude", "find nice nice", "things!", "aaaaa preciate", "collecting collection", "present present");
				return;
			}
		}
		if(spirit == PHOENIX)
		{
			if(value >= 300)
			{
				randomTell(spirit, player, "I am astounded by such a gift! You are gracious indeed, worldchild.", "You should not feel obligated to offer me such things, but such a sacrifice is deeply appreciated.", "A wondrous offering - I can scarcely match it with gratitude. Blessings of the sun and wind upon you.");
				return;
			}
			
			if(value >= 50)
			{
				randomTell(spirit, player, "You are kind, worldchild, and you have my gratitude.", "I gratefully accept your offering.", "Your generosity does not go unnoticed. Wind guide you, worldchild.");
				return;
			}
		}
	}
	
	public static NBTTagCompound getPlayerFavorTag(EntityPlayer player, int spirit)
	{
		String PNT = EntityPlayer.PERSISTED_NBT_TAG;
		NBTTagCompound data = player.getEntityData();
		if(!data.hasKey(PNT)) data.setTag(PNT, new NBTTagCompound());
		NBTTagCompound persist = player.getEntityData().getCompoundTag(PNT);
		if(!persist.hasKey("SpiritFavor"+spirit)) persist.setTag("SpiritFavor"+spirit, new NBTTagCompound());
		NBTTagCompound favorTag = persist.getCompoundTag("SpiritFavor"+spirit);
		return favorTag;
	}
	
	public static void adjustFavor(int spirit, EntityPlayer player, int amount)
	{
		NBTTagCompound favorTag = getPlayerFavorTag(player, spirit);
		int currentFavor = favorTag.getInteger("favor");
		
		currentFavor += amount;
		favorTag.setInteger("favor", currentFavor);
	}
	
	public static int getSpiritFavor(int spirit, EntityPlayer player)
	{
		NBTTagCompound favorTag = getPlayerFavorTag(player, spirit);
		return favorTag.getInteger("favor");
	}
	
	public static boolean rollFavor(int spirit, EntityPlayer player, int targetNumber, float cap)
	{
		int totalFavor = getSpiritFavor(spirit, player);
		int roll = player.worldObj.rand.nextInt((int)(totalFavor*cap));
		if(roll >= targetNumber) return true; else return false;
	}
	
	public static void handleOffering(int spirit, EntityPlayer player, ItemStack stack)
	{
		NBTTagCompound favorTag = getPlayerFavorTag(player, spirit);
		int donationCharge = getRecentDonationCharge(spirit, player);
		int offeringValue = evaluateOffering(spirit, player, stack);
		int currentFavor = favorTag.getInteger("favor");
		
		currentFavor += offeringValue;
		donationCharge += offeringValue;
		favorTag.setInteger("recentDonationCharge", donationCharge);
		favorTag.setInteger("lastDonationWorldSeconds", (int)(player.worldObj.getTotalWorldTime()/20));
		favorTag.setInteger("favor", currentFavor);
		System.out.println("Donation of "+offeringValue+", total favor "+currentFavor);
		commentOnOffering(spirit, player, stack, offeringValue);
	}
	
	public static int evaluateOffering(int spirit, EntityPlayer player, ItemStack stack)
	{
		if(stack == null) return 0;
		
		int materialValue = 0;
		int baseOfferingFavor = 0;
		Item item = stack.getItem();
		if(item == LegendGear2.starDust) materialValue = starMaterialValues[stack.getItemDamage()];
		
		if(spirit == ENDCHILD)
		{
			if(item == Item.getItemFromBlock(Blocks.grass)) materialValue = LIKE;
		}
		
		
		double quantityBonus = Math.sqrt(stack.stackSize);
		int favor = (int)(baseOfferingFavor + materialValue*quantityBonus);
		int donationCharge = getRecentDonationCharge(spirit, player);
		
		if(donationCharge >= favor) favor *= STALENESS_SCALING;
		else favor = (int) ((favor - donationCharge) + donationCharge * STALENESS_SCALING);
		
		return favor;
	}
	
}
