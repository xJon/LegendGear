package nmccoy.legendgear.items;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagicMirror extends Item {
	public MagicMirror(int id){
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTransport);
		setIconIndex(0);
		setItemName("magicMirror");
		setMaxDamage(0);
		setFull3D();
	}
	@Override
	public boolean isDamageable()
	{
		return false;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[]{getCreativeTab(), LegendGear.creativeTab};
	}
	
	public String getTextureFile () {
        return CommonProxy.ITEMS_PNG;
	}
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 70;
    }
	public boolean shouldRotateAroundWhenRendering()
	{
	     return false;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if(!isNBTOutdoors(par1ItemStack) && par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("lastSkyX") && par2World.provider.canRespawnHere())
        {
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        	//par2World.playSoundAtEntity(par3EntityPlayer, "portal.trigger", 1.0F, 1.0F);
        }
        else par2World.playSoundAtEntity(par3EntityPlayer, "random.fizz", 0.5F, 0.5F);
        return par1ItemStack;
    }
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		par3EntityPlayer.clearItemInUse();
	}
	
	private boolean isOutdoors(EntityPlayer player, World world)
	{
		return world.canBlockSeeTheSky((int)Math.floor(player.posX), (int)Math.floor(player.posY+2.1), (int)Math.floor(player.posZ));
	}
	private boolean isNBTOutdoors(ItemStack item)
	{
		return item.getItemDamage() == 1;	
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par3Entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)par3Entity;
			if(!par2World.isRemote)
			{
				if(!par1ItemStack.hasTagCompound()) par1ItemStack.setTagCompound(new NBTTagCompound());
				NBTTagCompound nbt = par1ItemStack.getTagCompound();
				if(isOutdoors(player, par2World))
				{
					nbt.setDouble("lastSkyX", player.posX);
					nbt.setDouble("lastSkyY", player.posY);
					nbt.setDouble("lastSkyZ", player.posZ);
					par1ItemStack.setItemDamage(1);
				}
				else
				{
					if(par2World.getTotalWorldTime() < nbt.getLong("lastwarp") + 60 && nbt.hasKey("lastSkyX"))
						par1ItemStack.setItemDamage(1);
					else
						par1ItemStack.setItemDamage(0);
				}
			}
			
			
			
		}
	}

	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int time)
	{
		int dur = stack.getMaxItemUseDuration()-time;
		float progress = dur *1.0f/stack.getMaxItemUseDuration();
		if(player.worldObj.isRemote)
		{
			if(player instanceof EntityClientPlayerMP)
			{
				((EntityClientPlayerMP) player).timeInPortal = Math.max(progress* 0.95f, ((EntityClientPlayerMP) player).timeInPortal);
			}	
			double theta = Math.PI * 6 * progress;
			double r = 2* (1 - progress);
			for(int i = 0; i < 3; i++)
			{
				LegendGear.proxy.addRuneParticle(player.worldObj, player.posX + Math.cos(theta) * r, player.posY-player.yOffset,  player.posZ + Math.sin(theta) * r, 0, 0.1, 0, 1.5f);
				
				//player.worldObj.spawnParticle("instantSpell", player.posX + Math.cos(theta) * r, player.posY-1.6,  player.posZ + Math.sin(theta) * r, 0, 2, 0);
				theta += Math.PI*2/3;
			}						
		}
		
		int soundOften = 8;
		if(dur % soundOften == 0)
			player.worldObj.playSoundAtEntity(player, "assets.sine", 0.3f, 0.2f * (2+dur/soundOften));
	}

	
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(!isOutdoors(par3EntityPlayer, par2World) && par2World.provider.canRespawnHere())
		{
			par3EntityPlayer.fallDistance = 0.0F;
			
			NBTTagCompound nbt = par1ItemStack.getTagCompound();
			double tx = nbt.getDouble("lastSkyX");
			double ty = nbt.getDouble("lastSkyY");
			double tz = nbt.getDouble("lastSkyZ");
			
			if(par2World.isRemote) par2World.spawnParticle("hugeexplosion", par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 0, 0, 0);
			
			EntityPlayer p = par3EntityPlayer;
			p.setPositionAndUpdate(tx, ty, tz);
			while(p.isEntityInsideOpaqueBlock()) p.setPositionAndUpdate(p.posX, p.posY+2, p.posZ);
			par3EntityPlayer.clearItemInUse();
			
			par2World.playSoundAtEntity(par3EntityPlayer, "portal.travel", 1.0F, 1.0F);
			if(par2World.isRemote) par2World.spawnParticle("hugeexplosion", tx, ty, tz, 0, 0, 0);
			
			//par3EntityPlayer.experienceTotal = par3EntityPlayer.experienceTotal (100-LegendGear.magicMirrorCost)/100;
			
			nbt.setLong("lastwarp", par2World.getTotalWorldTime());
		}
		return par1ItemStack;
    }
	
	@Override public int getIconFromDamage(int damage)
	{
		if(damage == 0) return 0;
		else return 1;
	}
	
	@Override
	public int getIconIndex(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		if(usingItem == stack)
			return 5-useRemaining%4;
		else
			return getIconFromDamage(stack.getItemDamage());
    }
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }
	
}
