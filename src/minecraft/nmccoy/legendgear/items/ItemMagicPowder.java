package nmccoy.legendgear.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;

public class ItemMagicPowder extends Item {

	public ItemMagicPowder(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setTextureFile(CommonProxy.ITEMS_PNG);
		setIconIndex(62);
		setItemName("magicPowder");
		setCreativeTab(CreativeTabs.tabMisc);
		maxStackSize=16;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack item,
			EntityPlayer player, World world, int x, int y,
			int z, int par7, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		boolean success = false;
		int block = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(block == LegendGear.mysticShrub.blockID && meta == 2)
		{
			success = true;
			world.setBlockMetadataWithNotify(x, y, z, 0);
			world.markBlockForUpdate(x, y, z);
		}
		if(block == Block.pumpkin.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.melon.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		else if(block == Block.melon.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.pumpkin.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		} 
		
		if(block == Block.mushroomBrown.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.mushroomRed.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		else if(block == Block.mushroomRed.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.mushroomBrown.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		
		if(block == Block.plantRed.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.plantYellow.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		} else if(block == Block.plantYellow.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.plantRed.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		
		if(block == Block.stoneBrick.blockID)
		{
			world.setBlockMetadataWithNotify(x, y, z, (meta+1)%4);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		if(block == Block.sandStone.blockID)
		{
			world.setBlockMetadataWithNotify(x, y, z, (meta+1)%3);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		
		if(block == Block.cobblestone.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.cobblestoneMossy.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		else if(block == Block.cobblestoneMossy.blockID)
		{
			world.setBlockWithNotify(x, y, z, Block.cobblestone.blockID);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		if(block == Block.sapling.blockID)
		{
			world.setBlockMetadataWithNotify(x, y, z, (meta+1)%4);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		if(block == Block.cloth.blockID)
		{
			world.setBlockMetadataWithNotify(x, y, z, (meta+1)%16);
			world.markBlockForUpdate(x, y, z);
			success = true;
		}
		
		if(success)
		{
			if(world.isRemote)
			{
				world.spawnParticle("largeexplode", x+0.5, y+0.5, z+0.5, 0, 0, 0);
				for(int i = 0; i<20; i++)
					LegendGear.proxy.addSparkleParticle(world, x+0.5+itemRand.nextGaussian()*0.5, y+0.5+itemRand.nextGaussian()*0.5, z+0.5+itemRand.nextGaussian()*0.5, 0, -0.02, 0, 1);
			}
			else
			{
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "assets.sprinkle", 0.2f, 1.0f);
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "assets.transform", 1.0f, 0.7f+itemRand.nextFloat()*0.5f);
			}
			if(!player.capabilities.isCreativeMode) item.stackSize--;
		}
		return success;
	}
	
	@ForgeSubscribe
	public void handleSprinkleOnEntity(EntityInteractEvent eie)
	{
		ItemStack item = eie.entityPlayer.getCurrentEquippedItem();
		if(item != null && item.itemID == this.itemID)
		{
			boolean success = false;
			if(eie.target instanceof EntityAgeable)
			{
				EntityAgeable ea = (EntityAgeable) eie.target;
				if(!ea.worldObj.isRemote)
				{
					if(ea.isChild())
							ea.setGrowingAge(0);
					else ea.setGrowingAge(-20*300);
				}
				else
				{
					ea.worldObj.spawnParticle("largeexplode", ea.posX, ea.posY+ea.height/2, ea.posZ, 0, 0, 0);
				}
				success = true;
				
			}
			if(eie.target instanceof EntitySheep)
			{
				EntitySheep es = (EntitySheep) eie.target;
				if(!es.worldObj.isRemote) es.setFleeceColor((int)(Math.random()*16));
				success = true;
			}
			
			if(eie.target instanceof EntityCreeper)
			{
				EntityCreeper ec = (EntityCreeper) eie.target;
				if(!ec.worldObj.isRemote) ec.getDataWatcher().updateObject(17, Byte.valueOf((byte)1));
				success = true;
			}
			
			if(eie.target instanceof EntityZombie)
			{
				EntityZombie ez = (EntityZombie) eie.target;
				
				if(ez.getCurrentItemOrArmor(4) == null)
				{
					ez.setCurrentItemOrArmor(4, new ItemStack(Block.pumpkin));
					ez.getEntityData().setBoolean("pumpkined", true);
					success = true;
				}
				else if(ez.getEntityData().getBoolean("pumpkined") && !ez.getEntityData().getBoolean("meloned") )
				{
					ez.setCurrentItemOrArmor(4, new ItemStack(Block.melon));
					ez.getEntityData().setBoolean("meloned", true);
					ez.getEntityData().setBoolean("pumpkined", false);
					success = true;
				} else if(ez.getEntityData().getBoolean("meloned") && !ez.getEntityData().getBoolean("pumpkined") )
				{
					ez.setCurrentItemOrArmor(4, new ItemStack(Block.pumpkin));
					ez.getEntityData().setBoolean("meloned", false);
					ez.getEntityData().setBoolean("pumpkined", true);
					success = true;
				}
			}
			
			
			
			
			if(success)
			{
				if(eie.target.worldObj.isRemote)
				{
					eie.target.worldObj.spawnParticle("largeexplode", eie.target.posX, eie.target.posY+eie.target.height/2, eie.target.posZ, 0, 0, 0);
					
					for(int i = 0; i<20; i++)
						LegendGear.proxy.addSparkleParticle(eie.target.worldObj, eie.target.posX+itemRand.nextGaussian()*0.5, eie.target.posY+0.5+itemRand.nextGaussian()*0.5, eie.target.posZ+itemRand.nextGaussian()*0.5, 0, -0.02, 0, 1);
				}
				else
				{
					eie.target.worldObj.playSoundAtEntity(eie.target, "assets.sprinkle", 0.2f, 1.0f);
					eie.target.worldObj.playSoundAtEntity(eie.target, "assets.transform", 1.0f, 0.7f+itemRand.nextFloat()*0.5f);
				}
				if(!eie.entityPlayer.capabilities.isCreativeMode) item.stackSize--;
			}
		}
		
		
		
	}
	
}
