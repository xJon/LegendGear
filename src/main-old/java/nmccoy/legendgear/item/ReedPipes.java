package nmccoy.legendgear.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;

public class ReedPipes extends Item {

	public static int[] notes = {0, 3, 7, 9, 12};
	public static int[] altnotes = {-1, 2, 5, 8, 11};
	
	public ReedPipes() {
		super();
		setMaxStackSize(1);
		//setTextureFile(CommonProxy.ITEMS_PNG);
		//setIconIndex(63);
		setUnlocalizedName("reedPipes");
		setCreativeTab(LegendGear2.legendgearTab);
		
		setTextureName(LegendGear2.MODID+":itemReedPipes");
			// TODO Auto-generated constructor stub
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return EnumAction.none;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		if(!par3EntityPlayer.isUsingItem())
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
			
			int note = getNoteFromSpan((180-(par3EntityPlayer.rotationPitch + 90))/180, notes);
			if(par3EntityPlayer.isSneaking()) 
				note = getNoteFromSpan((180-(par3EntityPlayer.rotationPitch + 90))/180, altnotes);
				
			note++;
			
			System.out.println(par3EntityPlayer.rotationPitch);
			if(!par2World.isRemote) par2World.playSoundAtEntity(par3EntityPlayer, LegendGear2.MODID+":"+"fluteattack", 1.0f, getNotePitch(note));
			par3EntityPlayer.getEntityData().setInteger("flutenote", note);
		}
		return par1ItemStack;
	}
	
	
	public static float getNotePitch(int note)
	{
		note +=2;
		return (float)Math.pow(2.0D, (double)(note - 12) / 12.0D);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		// TODO Auto-generated method stub
		par3EntityPlayer.getEntityData().setFloat("noteTime", 0);
	}
	
	public static int getNoteFromSpan(double span, int[] notearray)
	{
		int which = (int)(span*(notearray.length -1)+0.5);
		if(which < 0) which = 0;
		if(which >= notearray.length) which = notearray.length - 1;
		return notearray[which];
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		// TODO Auto-generated method stub
		float noteTime = player.getEntityData().getFloat("noteTime");
		
		int savednote = player.getEntityData().getInteger("flutenote");
		int anglenote = getNoteFromSpan((180-(player.rotationPitch + 90))/180, notes);
		if(player.isSneaking()) 
			anglenote = getNoteFromSpan((180-(player.rotationPitch + 90))/180, altnotes);
		
		anglenote++;
		
		float pitch = getNotePitch(anglenote);
		if(anglenote != savednote)
		{
			player.getEntityData().setInteger("flutenote", anglenote);
			noteTime = 0;
			if(!player.worldObj.isRemote) player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":"+"fluteattack", 1.0f, pitch);
		}
		else noteTime += pitch;
		
		if(noteTime >= 5)
		{
			noteTime -= 5;
			if(!player.worldObj.isRemote) player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":"+"flutesustain", 1.0f, pitch);
		}
		player.getEntityData().setFloat("noteTime", noteTime);
	}
	
	public void addRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(this), "XXX", "XX ","X  ", 'X', Items.reeds);
		GameRegistry.addShapedRecipe(new ItemStack(this), "XXX", " XX","  X", 'X', Items.reeds);
		
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return 72000;
	}

}
