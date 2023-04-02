package nmccoy.legendgear.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntityFallingStar;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StarDust extends Item {
	
	private static int INFUSE_LEVEL = 3;

	private static String[] names={"starDust", "starPiece", "starStone", "starDustCharged", "starPieceCharged", "starStoneCharged"};
	
	public StarDust() {
			// TODO Auto-generated constructor stub
			super();
			// TODO Auto-generated constructor stub
			//setTextureFile(CommonProxy.ITEMS_PNG);
			//setIconIndex(58);
			setUnlocalizedName("itemStarDust");
			setCreativeTab(LegendGear2.legendgearTab);
			hasSubtypes = true;

			
		}
	
		@Override
		public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
		{
			if(stack.getItemDamage() == 1) list.add("Spend "+INFUSE_LEVEL+" XP levels to infuse");
		}
	
	
	
		IIcon icon_dust;
		IIcon icon_piece;
		IIcon icon_stone;
		
		@Override
		public boolean hasEffect(ItemStack par1ItemStack) {
			// TODO Auto-generated method stub
			return par1ItemStack.getItemDamage() > 2;
		}
		
		@Override
		public int getMaxItemUseDuration(ItemStack stack)
		{
			return 64;
		}
		
	    public void addRecipes()
	    {
	    	ItemStack threeDust = new ItemStack(this, 3, 0);
	    	ItemStack onePiece = new ItemStack(this, 1, 1);
	    	ItemStack ninePieces = new ItemStack(this, 9, 1);
	    	ItemStack oneStone = new ItemStack(this, 1, 2);
	    	
	    	ItemStack threeCDust = new ItemStack(this, 3, 3);
	    	ItemStack oneCPiece = new ItemStack(this, 1, 4);
	    	ItemStack nineCPieces = new ItemStack(this, 9, 4);
	    	ItemStack oneCStone = new ItemStack(this, 1, 5);
	    	
	    	
	    	GameRegistry.addShapelessRecipe(ninePieces, oneStone);
	    	GameRegistry.addShapelessRecipe(threeDust, onePiece);
	    	GameRegistry.addRecipe(oneStone, "PPP", "PPP", "PPP", 'P', onePiece);
	    	//GameRegistry.addShapelessRecipe(onePiece, eightShards);
	    	//GameRegistry.addShapelessRecipe(oneEmerald, eightPieces);
	    	GameRegistry.addShapelessRecipe(nineCPieces, oneCStone);
	    	GameRegistry.addShapelessRecipe(threeCDust, oneCPiece);
	    	GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 3));
	    	GameRegistry.addRecipe(oneCStone, "PPP", "PPP", "PPP", 'P', oneCPiece);
	    }
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerIcons(IIconRegister par1IconRegister) {
			//super.updateIcons(par1IconRegister);
	
			icon_dust = par1IconRegister.registerIcon(LegendGear2.MODID+":"+"starDustAnim");
			icon_piece = par1IconRegister.registerIcon(LegendGear2.MODID+":"+"starPieceAnim");
			icon_stone = par1IconRegister.registerIcon(LegendGear2.MODID+":"+"starStoneAnim");
		}
		
		@Override
		public String getUnlocalizedName(ItemStack par1ItemStack) {
			// TODO Auto-generated method stub
			int damage = par1ItemStack.getItemDamage();
			if(damage >= names.length) damage = 0;
			return "item."+names[damage];
			
		}
		
		@Override
		public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
		{
			if(!player.worldObj.isRemote)
			{
				if(count == 56) player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":"+"partialcharge", 0.2f, 1.0f);
			}
		}
		
		@Override
	    public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer player)
	    {
			if(stack.getItemDamage() == 1 && player.experienceLevel >= INFUSE_LEVEL)
			{
				player.setItemInUse(stack, getMaxItemUseDuration(stack));
			}
	    	return stack;
	    }

		@Override
	    public EnumRarity getRarity(ItemStack stack)
	    {
	        return stack.getItemDamage() >=3 ? EnumRarity.epic : EnumRarity.uncommon;
	    }
		
		@Override
		public boolean onItemUse(ItemStack par1ItemStack,
				EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
				int par6, int par7, float par8, float par9, float par10) {
			// TODO Auto-generated method stub
			if(par2EntityPlayer.capabilities.isCreativeMode && !par3World.isRemote && par1ItemStack.getItemDamage() == 0)
			{
				par3World.spawnEntityInWorld(new EntityFallingStar(par2EntityPlayer));
				//PlayerEventHandler.addPlayerStarCharge(par2EntityPlayer, 2000);
				
				return true;
			}
			else if(par1ItemStack.getItemDamage() == 2)
			{
				ItemBlock ib = (ItemBlock)ItemBlock.getItemFromBlock(LegendGear2.starstoneBlock);
				boolean canPlace = ib.func_150936_a(par3World, par4, par5, par6, par7, par2EntityPlayer, par1ItemStack);
				if(canPlace) return ib.onItemUse( par1ItemStack,
						 par2EntityPlayer,  par3World,  par4,  par5,
						 par6,  par7,  par8,  par9,  par10);
			}
			else if(par1ItemStack.getItemDamage() == 5)
			{
				ItemBlock ib = (ItemBlock)ItemBlock.getItemFromBlock(LegendGear2.infusedStarstoneBlock);
				boolean canPlace = ib.func_150936_a(par3World, par4, par5, par6, par7, par2EntityPlayer, par1ItemStack);
				if(canPlace) return ib.onItemUse( par1ItemStack,
						 par2EntityPlayer,  par3World,  par4,  par5,
						 par6,  par7,  par8,  par9,  par10);
			}
			return false;
		}
		
		@Override
		public EnumAction getItemUseAction(ItemStack par1ItemStack) {
			// TODO Auto-generated method stub
			//if(par1ItemStack.getItemDamage() == 1) return EnumAction.block;
			return EnumAction.none;
		}
		
	    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	    {
	    	if(stack.getItemDamage() == 1 && player.experienceLevel >= INFUSE_LEVEL)
	    	{
		        --stack.stackSize;
		        player.addExperienceLevel(-INFUSE_LEVEL);
		        
		        if(!world.isRemote) world.playSoundAtEntity(player, LegendGear2.MODID+":"+"truncatedfullcharge", 0.2f, 1.0f);
		        
		        if(stack.stackSize <= 0) return new ItemStack(this, 1, 4);

	            if (!player.inventory.addItemStackToInventory(new ItemStack(this, 1, 4)))
	            {
	                player.dropPlayerItemWithRandomChoice(new ItemStack(this, 1, 4), false);
	            }
	    	}
	        return stack;
	    }
		
	    @Override
	    @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tabs, List list)
	    {
	       for(int i=0; i<names.length; i++)
	       {
	    	   list.add(new ItemStack(item, 1, i));
	       }
	    }
		
		@Override
		public IIcon getIconFromDamage(int par1) {
			// TODO Auto-generated method stub
			if(par1 == 1 || par1 == 4) return icon_piece;
			if(par1 == 2 || par1 == 5) return icon_stone;
			return icon_dust;
		}
		

}
