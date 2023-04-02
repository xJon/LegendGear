package nmccoy.legendgear.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCaltropsBlock extends ItemBlock {
	IIcon icon;
	public ItemCaltropsBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
		// TODO Auto-generated constructor stub
		setTextureName(LegendGear2.MODID+":itemCaltrops");
	
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		// TODO Auto-generated method stub
		return icon;
	}
	
	@Override
	public IIcon getIconFromDamage(int p_77617_1_) {
		// TODO Auto-generated method stub
		return icon;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ireg)
    {
    	icon = ireg.registerIcon(LegendGear2.MODID+":"+"itemCaltrops");
    }
   
    @Override
    public int getSpriteNumber() {
    	// TODO Auto-generated method stub
    	return 1;
    }
    
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public IIcon getIconIndex(ItemStack p_77650_1_) {
		// TODO Auto-generated method stub
		return icon;
	}
	/*
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		// TODO Auto-generated method stub
		EntityItemCaltrops eic = new EntityItemCaltrops(world, location.posX, location.posY, location.posZ, itemstack);
		EntityItem old = (EntityItem) location;
		eic.delayBeforeCanPickup = old.delayBeforeCanPickup;
		eic.motionX = old.motionX;
		eic.motionY = old.motionY;
		eic.motionZ = old.motionZ;
		return eic;
	}*/
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, net.minecraft.entity.player.EntityPlayer player) {
		stack.stackSize--;
		player.dropPlayerItemWithRandomChoice(new ItemStack(stack.getItem()), false);
		return stack;
	};
	
	public static boolean trySettlingOn(World world, double x, double y, double z)
	{
		int px = (int)Math.floor(x);
		int py = (int)Math.floor(y);
		int pz = (int)Math.floor(z);
		if(world.getBlock(px, py, pz) == LegendGear2.caltropsBlock) return false;
		if(LegendGear2.caltropsBlock.canPlaceBlockAt(world, px, py, pz))
		{
			world.setBlock(px, py, pz, LegendGear2.caltropsBlock);
			world.playSoundEffect(x, y, z, LegendGear2.MODID+":caltropsland", 1.0f, world.rand.nextFloat()*0.4f+0.9f);
			return true;
		}
		return false;
	}
	
	@Override 
	public boolean onEntityItemUpdate(EntityItem ei) {
		// TODO Auto-generated method stub
		//super.onUpdate();
		if(!ei.worldObj.isRemote && ei.onGround)
		{
			boolean settled = false;
			if(!settled) settled = trySettlingOn(ei.worldObj, ei.posX, ei.posY, ei.posZ);
			if(!settled) settled = trySettlingOn(ei.worldObj, ei.posX+0.5, ei.posY, ei.posZ);
			if(!settled) settled = trySettlingOn(ei.worldObj, ei.posX-0.5, ei.posY, ei.posZ);
			if(!settled) settled = trySettlingOn(ei.worldObj, ei.posX, ei.posY, ei.posZ+0.5);
			if(!settled) settled = trySettlingOn(ei.worldObj, ei.posX, ei.posY, ei.posZ-0.5);
			if(settled)
			{
				ItemStack stack = ei.getEntityItem();
				stack.stackSize--;
				if(stack.stackSize <= 0) ei.setDead();
			}
		}
		return false;
	}

}
