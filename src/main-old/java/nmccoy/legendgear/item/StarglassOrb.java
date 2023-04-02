package nmccoy.legendgear.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;


public class StarglassOrb extends Item {

	public static enum OrbTypes
	{
		empty, water(true), lava(true), milk(true), blast(true), twinkle;
		
		private final boolean canBeThrown;
		private OrbTypes()
		{
			canBeThrown = false;
		}
		private OrbTypes(boolean throwable)
		{
			canBeThrown = throwable;
		}
		
	}
	private static final String prefix=LegendGear2.MODID+":item.orb.";
	private IIcon[] icons;
	
	public int types()
	{
		return OrbTypes.values().length;
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack) {
		if(par1ItemStack.getItemDamage() == OrbTypes.twinkle.ordinal()) return true;
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
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.milk.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.milk_bucket);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, OrbTypes.blast.ordinal()), new ItemStack(this, 1, 0), LegendGear2.dimensionalCatalyst, Items.gunpowder, Items.flint);
		
		
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
	
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (!p_77659_3_.capabilities.isCreativeMode)
        {
            --p_77659_1_.stackSize;
        }

        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!p_77659_2_.isRemote)
        {
            p_77659_2_.spawnEntityInWorld(new EntitySnowball(p_77659_2_, p_77659_3_));
        }

        return p_77659_1_;
    }
}
