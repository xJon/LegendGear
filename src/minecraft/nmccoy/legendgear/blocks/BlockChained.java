package nmccoy.legendgear.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class BlockChained extends Block {

	public BlockChained(int par1) {
		super(par1, Material.wood);
		// TODO Auto-generated constructor stub
		setHardness(-1);
		setResistance(6000000.0F);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setTickRandomly(true);
		setBlockName("blockChained");
		setStepSound(soundWoodFootstep);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		// TODO Auto-generated method stub
		return 32+par2;
	}
	
	
	@Override
	public int tickRate() {
		// TODO Auto-generated method stub
		return 10;
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		// TODO Auto-generated method stub
		if(par1World.getBlockMetadata(par2, par3, par4) == 1)
			par1World.setBlockWithNotify(par2, par3, par4, 0);
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int x, int y,
			int z, int neighborID) {
		// TODO Auto-generated method stub
		if(par1World.getBlockMetadata(x, y, z) != 1)
		{
			boolean unlockedNeighbor = false;
			if(par1World.getBlockId(x+1, y, z) == blockID && par1World.getBlockMetadata(x+1, y, z) == 1) unlockedNeighbor = true;
			if(par1World.getBlockId(x-1, y, z) == blockID && par1World.getBlockMetadata(x-1, y, z) == 1) unlockedNeighbor = true;
			if(par1World.getBlockId(x, y+1, z) == blockID && par1World.getBlockMetadata(x, y+1, z) == 1) unlockedNeighbor = true;
			if(par1World.getBlockId(x, y-1, z) == blockID && par1World.getBlockMetadata(x, y-1, z) == 1) unlockedNeighbor = true;
			if(par1World.getBlockId(x, y, z+1) == blockID && par1World.getBlockMetadata(x, y, z+1) == 1) unlockedNeighbor = true;
			if(par1World.getBlockId(x, y, z-1) == blockID && par1World.getBlockMetadata(x, y, z-1) == 1) unlockedNeighbor = true;
			
			if(unlockedNeighbor)
			{
				par1World.setBlockMetadataWithNotify(x, y, z, 1);
				par1World.markBlockForUpdate(x, y, z);
				par1World.scheduleBlockUpdate(x, y, z, this.blockID, tickRate());
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y,
			int z, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		// TODO Auto-generated method stub
		int meta = par1World.getBlockMetadata(x, y, z);
		ItemStack item =par5EntityPlayer.getCurrentEquippedItem();
		
		if(meta > 1 && item != null && item.itemID == LegendGear.itemKey.itemID && item.getItemDamage() == meta-2)
		{
			par1World.setBlockMetadataWithNotify(x, y, z, 1);
			par1World.scheduleBlockUpdate(x, y, z, this.blockID, tickRate());
			par1World.playSoundEffect(x+0.5, y+0.5, z+0.5, "assets.unchain", 1.0f, 1.0f);
			item.stackSize--;
			return true;
		}
		return false;
	}
	
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 2));
		subItems.add(new ItemStack(this, 1, 3));
		subItems.add(new ItemStack(this, 1, 4));
	}


}
