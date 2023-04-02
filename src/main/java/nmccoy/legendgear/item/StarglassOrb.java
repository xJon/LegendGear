package nmccoy.legendgear.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntityThrownOrb;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class StarglassOrb extends Item {

	public static enum OrbTypes
	{
		empty(false, true), 
		water(true), 
		lava(true), 
		//milk(false), 
		blast(true), 
		twinkle(true, true), 
		fire(true, true), 
		ice(true, true), 
		zap(true, true);
		
		private final boolean canBeThrown;
		private final boolean craftIngredient;
		private String additionalInfo;
		private OrbTypes()
		{
			canBeThrown = false;
			craftIngredient = false;
		}
		private OrbTypes(boolean throwable)
		{
			canBeThrown = throwable;
			craftIngredient = false;
		}
		private OrbTypes(boolean throwable, boolean ingredient)
		{
			canBeThrown = throwable;
			craftIngredient = ingredient;
		}
		
		
	}
	private static final String prefix=LegendGear2.MODID+":item.orb.";
	private IIcon[] icons;
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_,
			List list, boolean p_77624_4_) {
		// TODO Auto-generated method stub
		super.addInformation(stack, p_77624_2_, list, p_77624_4_);
		int damage = stack.getItemDamage();
		if(damage >= OrbTypes.values().length) list.add(ChatFormatting.RED+"Something has gone horribly wrong.");
		else 
		{
			OrbTypes type = OrbTypes.values()[damage];
			if(type.craftIngredient) list.add("Usable in crafting");
			if(type.canBeThrown) list.add("Throwable");
		}
	}
	
	public int types()
	{
		return OrbTypes.values().length;
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack) {
		if(par1ItemStack.getItemDamage() == OrbTypes.twinkle.ordinal()) return true;
		if(par1ItemStack.getItemDamage() == OrbTypes.fire.ordinal()) return true;
		if(par1ItemStack.getItemDamage() == OrbTypes.ice.ordinal()) return true;
		if(par1ItemStack.getItemDamage() == OrbTypes.zap.ordinal()) return true;
		
		return false;
	};
	
	public StarglassOrb()
	{
		this.setUnlocalizedName("emptyOrb").setCreativeTab(LegendGear2.legendgearTab).setTextureName(LegendGear2.MODID+":emptyOrb");
		setMaxStackSize(16);
	}
	@Override public IIcon getIconFromDamage(int damage) {
		return icons[damage];
	};
	
	@Override public void registerIcons(net.minecraft.client.renderer.texture.IIconRegister register) {
		icons = new IIcon[OrbTypes.values().length];
		for(int i = 0; i < icons.length; i++)
		{
			String name=LegendGear2.MODID+":"+OrbTypes.values()[i].toString()+"Orb";
			System.out.println(name);
			icons[i] = register.registerIcon(name);
		}
	};
	
	public void addRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.water.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.water_bucket);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.lava.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.lava_bucket);
	//	GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.milk.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.milk_bucket);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.blast.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.gunpowder, Items.flint);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.twinkle.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, new ItemStack(LegendGear2.starDust, 1, 4));
		
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.ice.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst,LegendGear2.dimensionalCatalyst, new ItemStack(LegendGear2.starDust, 1, 4),  Blocks.snow);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.fire.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst,LegendGear2.dimensionalCatalyst, new ItemStack(LegendGear2.starDust, 1, 4), Items.lava_bucket);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.zap.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst,LegendGear2.dimensionalCatalyst, new ItemStack(LegendGear2.starDust, 1, 4), Blocks.redstone_block);
		
		
		
	}
	
	@Override public boolean getHasSubtypes() {return true;}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return "item.orb."+OrbTypes.values()[getDamage(stack)].toString();
	};
	
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
       for(int i=0; i<OrbTypes.values().length; i++)
       {
    	   list.add(new ItemStack(item, 1, i));
       }
    }
    
	
    public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	if(OrbTypes.values()[getDamage(stack)].canBeThrown)
    	{
	        if (!p_77659_3_.capabilities.isCreativeMode)
	        {
	            --stack.stackSize;
	        }
	
	        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	
	        if (!p_77659_2_.isRemote)
	        {
	        	EntityThrownOrb orb =new EntityThrownOrb(p_77659_2_, p_77659_3_);
	        	orb.orbIndex = getDamage(stack);
	            p_77659_2_.spawnEntityInWorld(orb);
	        }
    	}
        return stack;
    }
}
