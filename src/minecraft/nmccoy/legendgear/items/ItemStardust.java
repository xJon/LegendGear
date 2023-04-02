package nmccoy.legendgear.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityFallingStar;

public class ItemStardust extends Item {

	public ItemStardust(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(58);
		setItemName("starDust");
		setCreativeTab(CreativeTabs.tabMaterials);
		hasSubtypes = true;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		// TODO Auto-generated method stub
		if(par1 == 1) return 75;
		if(par1 == 2) return 74;
		return 58;
	}
	
	@Override
	public String getItemNameIS(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		if(par1ItemStack.getItemDamage() == 1) return "starPiece";
		if(par1ItemStack.getItemDamage() == 2) return "starStone";
		
		return "starDust";
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		//if(par2EntityPlayer.capabilities.isCreativeMode && !par3World.isRemote && par1ItemStack.getItemDamage() == 0)
		//{
		//	par3World.spawnEntityInWorld(new EntityFallingStar(par2EntityPlayer));
		//	return true;
		//}
		if(par1ItemStack.getItemDamage() == 2)
		{
	        int var11 = par3World.getBlockId(par4, par5, par6);

	        if (var11 == Block.snow.blockID)
	        {
	            par7 = 1;
	        }
	        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID
	                && (Block.blocksList[var11] == null || !Block.blocksList[var11].isBlockReplaceable(par3World, par4, par5, par6)))
	        {
	            if (par7 == 0)
	            {
	                --par5;
	            }

	            if (par7 == 1)
	            {
	                ++par5;
	            }

	            if (par7 == 2)
	            {
	                --par6;
	            }

	            if (par7 == 3)
	            {
	                ++par6;
	            }

	            if (par7 == 4)
	            {
	                --par4;
	            }

	            if (par7 == 5)
	            {
	                ++par4;
	            }
	        }

	        if (par1ItemStack.stackSize == 0)
	        {
	            return false;
	        }
	        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
	        {
	            return false;
	        }
	        else if (par5 == 255 && Block.blocksList[LegendGear.blockStarstone.blockID].blockMaterial.isSolid())
	        {
	            return false;
	        }
	        else if (par3World.canPlaceEntityOnSide(LegendGear.blockStarstone.blockID, par4, par5, par6, false, par7, par2EntityPlayer))
	        {
	            Block var12 = LegendGear.blockStarstone;
	            int var13 = this.getMetadata(par1ItemStack.getItemDamage());
	            int var14 = LegendGear.blockStarstone.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

	            if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, var14))
	            {
	                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
	                --par1ItemStack.stackSize;
	            }

	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
		return false;
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		// TODO Auto-generated method stub
		par3List.add(new ItemStack(LegendGear.itemStardust, 1, 0));
		par3List.add(new ItemStack(LegendGear.itemStardust, 1, 1));
		par3List.add(new ItemStack(LegendGear.itemStardust, 1, 2));
		
	}
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
       if (!world.setBlockAndMetadataWithNotify(x, y, z, LegendGear.blockStarstone.blockID, metadata))
       {
               return false;
       }

       if (world.getBlockId(x, y, z) == LegendGear.blockStarstone.blockID)
       {
    	   LegendGear.blockStarstone.onBlockPlacedBy(world, x, y, z, player);
    	   LegendGear.blockStarstone.onPostBlockPlaced(world, x, y, z, metadata);
       }

       return true;
    }


}
