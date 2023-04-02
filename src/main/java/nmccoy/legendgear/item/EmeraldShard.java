package nmccoy.legendgear.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EmeraldShard extends Item {

	private IIcon shardIcon;
	private IIcon pieceIcon;
	
	public EmeraldShard() {
		// TODO Auto-generated constructor stub
		super();
		setUnlocalizedName("shardEmerald");
		setCreativeTab(LegendGear2.legendgearTab);
		setHasSubtypes(true);
		//setTextureName();

	}
	
    public void addRecipes()
    {
    	ItemStack oneShard = new ItemStack(this, 1, 0);
    	ItemStack eightShards = new ItemStack(this, 8, 0);
    	ItemStack eightPieces = new ItemStack(this, 8, 1);
    	ItemStack onePiece = new ItemStack(this, 1, 1);
    	ItemStack oneEmerald = new ItemStack(Items.emerald);
    	
    	GameRegistry.addShapelessRecipe(eightShards, onePiece);
    	//GameRegistry.addShapelessRecipe(onePiece, eightShards);
    	//GameRegistry.addShapelessRecipe(oneEmerald, eightPieces);
    	GameRegistry.addShapelessRecipe(eightPieces, oneEmerald);
    	
    	if(LegendGear2.emeraldExchangeRate <= 9)
    	{
    		Object[] shards = new Object[LegendGear2.emeraldExchangeRate];
    		Object[] pieces = new Object[LegendGear2.emeraldExchangeRate];
    		for(int i = 0; i<LegendGear2.emeraldExchangeRate; i++)
    		{
    			shards[i]=oneShard;
    			pieces[i]=onePiece;
    		}
    		GameRegistry.addShapelessRecipe(onePiece, shards);
    		
    		GameRegistry.addShapelessRecipe(oneEmerald, pieces);
    		
    	}
    }

    
    @SubscribeEvent
    public void makeEmeraldCollectionSounds(EntityItemPickupEvent event)
    {
    	ItemStack stack = event.item.getEntityItem();
    	Item item = stack.getItem();
    	
    	if(item != this && item != Items.emerald) return;
    	
    	boolean success = event.entityPlayer.inventory.addItemStackToInventory(stack);
    	
    	if(success)
    	{
	    	if(item==this)
	    	{
	    		if(stack.getItemDamage() == 0 && !event.entityPlayer.worldObj.isRemote) event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "legendgear:moneysmall", 0.5f, 1.0f);
	    		if(stack.getItemDamage() == 1 && !event.entityPlayer.worldObj.isRemote) event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "legendgear:moneymid", 0.5f, 1.0f);
	    	}
	    	if(item==Items.emerald)
	    	{
	    		if(!event.entityPlayer.worldObj.isRemote) event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "legendgear:moneybig", 0.5f, 1.0f);
	    	}
    	}
    }
	
	@Override
    public String getUnlocalizedName(net.minecraft.item.ItemStack stack)
    {
        if(stack.getItemDamage() == 1) return "item.pieceEmerald";
        return "item.shardEmerald";
    }
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ireg)
    {
    	shardIcon = ireg.registerIcon(LegendGear2.MODID+":"+"itemEmeraldShard");
    	pieceIcon = ireg.registerIcon(LegendGear2.MODID+":"+"itemEmeraldPiece");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
       if(damage == 1) return pieceIcon;
       return shardIcon;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.stackSize < LegendGear2.emeraldExchangeRate) return par1ItemStack;
		par1ItemStack.stackSize -= LegendGear2.emeraldExchangeRate;
		ItemStack merged;
		if(par1ItemStack.getItemDamage() == 0) 
			{
				merged = new ItemStack(this, 1, 1);
				if(!par2World.isRemote) par2World.playSoundAtEntity(par3EntityPlayer, LegendGear2.MODID+":"+"moneymid", 0.5f, 1.0f);
			}
		else 
			{
				merged = new ItemStack(Items.emerald);
				if(!par2World.isRemote) par2World.playSoundAtEntity(par3EntityPlayer, LegendGear2.MODID+":"+"moneybig", 0.5f, 1.0f);
			}
		
		if (!par3EntityPlayer.inventory.addItemStackToInventory(merged))
        {
          //  par3EntityPlayer.dropPlayerItemWithRandomChoice(merged);
			ForgeHooks.onPlayerTossEvent(par3EntityPlayer, merged, false);
        }
		return par1ItemStack;
    }

}
