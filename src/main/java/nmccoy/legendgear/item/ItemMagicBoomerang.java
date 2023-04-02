package nmccoy.legendgear.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntityMagicBoomerang;

public class ItemMagicBoomerang extends Item {

	public static final int MAX_DAMAGE = 512;
	
	public ItemMagicBoomerang() {
		// TODO Auto-generated constructor stub
		setMaxStackSize(1);
		setCreativeTab(LegendGear2.legendgearTab);
		//setIconIndex(40);
		setMaxDamage(MAX_DAMAGE);
		setFull3D();
		hasSubtypes = false;
		//setTextureFile(CommonProxy.ITEMS_PNG);
		setUnlocalizedName("magicBoomerang");
		setTextureName(LegendGear2.MODID+":starsteelBoomerang");
		
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material) {
		// TODO Auto-generated method stub
		return material.getItem() == LegendGear2.starsteelIngot;
	}
		
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		if(!par2World.isRemote)
		{
			EntityMagicBoomerang emb = new EntityMagicBoomerang(par2World, par3EntityPlayer, par1ItemStack.copy());
			emb.thrown_from_slot = par3EntityPlayer.inventory.currentItem;
			par2World.spawnEntityInWorld(emb);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}
		par1ItemStack.stackSize=0;
		return par1ItemStack;
	}

}
