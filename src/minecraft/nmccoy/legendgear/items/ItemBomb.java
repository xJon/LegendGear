package nmccoy.legendgear.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.entities.EntityBomb;

public class ItemBomb extends Item implements IBehaviorDispenseItem{


	public ItemBomb(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		setMaxStackSize(16);
		setCreativeTab(CreativeTabs.tabCombat);
		setIconIndex(41);
		hasSubtypes = false;
		setTextureFile(CommonProxy.ITEMS_PNG);
		setItemName("itemBomb");
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityBomb(par2World, par3EntityPlayer, EntityBomb.LONG_FUSE_TIME));
        }

        return par1ItemStack;
    }
	@Override
    public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        World var3 = par1IBlockSource.getWorld();
        IPosition var4 = BlockDispenser.func_82525_a(par1IBlockSource);
        EnumFacing var5 = EnumFacing.func_82600_a(par1IBlockSource.func_82620_h());
        EntityBomb var6 = new EntityBomb(var3, var4.getX(), var4.getY(), var4.getZ(), EntityBomb.LONG_FUSE_TIME);
        var6.setThrowableHeading((double)var5.func_82601_c(), 0.10000000149011612D, (double)var5.func_82599_e(), 0.2f, 0.2f);
        var3.spawnEntityInWorld((Entity)var6);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
}
