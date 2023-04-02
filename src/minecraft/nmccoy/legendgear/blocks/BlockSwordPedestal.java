package nmccoy.legendgear.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import cpw.mods.fml.common.asm.transformers.MarkerTransformer;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockSwordPedestal extends BlockContainer {

	public BlockSwordPedestal(int par1) {
		super(par1, Material.rock);
		setStepSound(soundStoneFootstep);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setHardness(4);
		setBlockName("blockSwordPedestal");
		setCreativeTab(LegendGear.creativeTab);
		//setRequiresSelfNotify();
	}


	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return new ItemStack(this);
	}
	
	@Override
	public boolean isProvidingWeakPower(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		return (par1iBlockAccess.getBlockMetadata(par2, par3, par4)&8)!=0  && par5 == 1;
	}
	@Override
	public boolean isProvidingStrongPower(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		return isProvidingWeakPower(par1iBlockAccess, par2, par3, par4, par5);
	}
	
	@Override
	public boolean canProvidePower() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int meta) {
		// TODO Auto-generated method stub
		
		if((meta & 2) == 0) 
		{
	        if(par1 == ForgeDirection.UP.ordinal() || par1 == ForgeDirection.DOWN.ordinal()) return 15;
	        if(par1 == ForgeDirection.NORTH.ordinal() || par1 == ForgeDirection.SOUTH.ordinal()) return 13;
	        if(par1 == ForgeDirection.WEST.ordinal() || par1 == ForgeDirection.EAST.ordinal()) return 12;
		}
		else
			{
		        if(par1 == ForgeDirection.UP.ordinal() || par1 == ForgeDirection.DOWN.ordinal()) return 14;
		        if(par1 == ForgeDirection.NORTH.ordinal() || par1 == ForgeDirection.SOUTH.ordinal()) return 12;
		        if(par1 == ForgeDirection.WEST.ordinal() || par1 == ForgeDirection.EAST.ordinal()) return 13;
			}
			
        return 12;
        
	}
	
	public static boolean isSword(ItemStack item)
	{
		//System.out.println("Array: "+LegendGear.alsoCountAsSwords);
		//System.out.println("id: "+item.itemID);
		int ind =  Arrays.binarySearch(LegendGear.alsoCountAsSwords, item.itemID);
		//System.out.println("index: "+ind);
		return item != null && (item.getItem() instanceof ItemSword || ind >= 0);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y,
			int z, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		// TODO Auto-generated method stub
		
			TileEntityPedestal tej = (TileEntityPedestal)par1World.getBlockTileEntity(x, y, z);
			
			int meta = par1World.getBlockMetadata(x, y, z);
			
			if(tej != null && par5EntityPlayer.getCurrentEquippedItem() == null && tej.contents != null)
			{
				if(!par1World.isRemote)
				{
					par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem] = tej.contents;
					tej.contents = null;
					par1World.playSoundAtEntity(par5EntityPlayer, "assets.swordtake", 1.0f, 1.0f);
					meta = meta & 7;
					par1World.setBlockMetadataWithNotify(x, y, z, meta);
					par1World.setBlockWithNotify(x, y+1, z, 0);
					
					par1World.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
					par1World.markBlockForUpdate(x, y, z);
					//tej.sendInfo();
				}
				return true;
			}
			else if(tej != null && par5EntityPlayer.getCurrentEquippedItem() != null && tej.contents == null &&
					isSword(par5EntityPlayer.getCurrentEquippedItem()) && par1World.isAirBlock(x, y+1, z))
			{
				if(!par1World.isRemote)
				{
					tej.contents = par5EntityPlayer.getCurrentEquippedItem();
					par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem] = null;
					par1World.playSoundAtEntity(par5EntityPlayer, "assets.swordplace", 1.0f, 1.0f);
					meta = meta | 8;
					par1World.setBlockMetadataWithNotify(x, y, z, meta);
					par1World.setBlockAndMetadataWithNotify(x, y+1, z, LegendGear.blockPedestalTech.blockID, meta);
					par1World.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
					par1World.markBlockForUpdate(x, y, z);
					//tej.sendInfo();
				}
				return true;
			}
		
		return false;
		
	}
	
	public void ejectSword(World par1World, int x, int y, int z, boolean stillAround)
	{
		if(!par1World.isRemote)
		{
			TileEntityPedestal tej = (TileEntityPedestal)par1World.getBlockTileEntity(x, y, z);
			if(tej != null && tej.contents != null)
			{
				EntityItem ei = new EntityItem(par1World, x+0.5, y+0.5, z+0.5, tej.contents);
				ei.delayBeforeCanPickup = 10;
				par1World.spawnEntityInWorld(ei);
				if(stillAround)
				{
					int meta = par1World.getBlockMetadata(x, y, z); 
					tej.contents = null;
					par1World.setBlockMetadataWithNotify(x, y, z, meta & 7);
					par1World.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
					par1World.markBlockForUpdate(x, y, z);
				}
			}
		}
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		ejectSword(par1World, par2, par3, par4, false);
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		// TODO Auto-generated method stub
		this.setBlockBounds(0.25f, 0.0F, 0f, 0.75f, 0.5f, 1.0f);
	}
	
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean var5 = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 2) != 0;
        

        if (var5)
        {
            this.setBlockBounds(0f, 0.0F, 0.25f, 1.0F, 0.5f, 0.75f);
        }
        else
        {
            this.setBlockBounds(0.25f, 0.0F, 0f, 0.75f, 0.5f, 1.0f);
        }
    }
	
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		// TODO Auto-generated method stub
		return super.getBlockDropped(world, x, y, z, 0, fortune);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityPedestal();
	}

	@Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (var6 == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
        }

        if (var6 == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (var6 == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
        }
    }
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}
}
