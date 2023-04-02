package nmccoy.legendgear.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import nmccoy.legendgear.LegendGear2;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BadBow extends ItemBow {

	public BadBow()
	{
		super();
		setCreativeTab(LegendGear2.legendgearTab);
		setUnlocalizedName("badBow");
	}
	
	private IIcon[] iconArray;

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon(LegendGear2.MODID+":badbow");
        
        this.iconArray = new IIcon[3];
        this.iconArray[0] = p_94581_1_.registerIcon(LegendGear2.MODID+":badbow_pulling_1");
        this.iconArray[1] = p_94581_1_.registerIcon(LegendGear2.MODID+":badbow_pulling_2");
        this.iconArray[2] = p_94581_1_.registerIcon(LegendGear2.MODID+":badbow_pulling_2");
    }
     @Override
    public int getItemEnchantability() {
    	// TODO Auto-generated method stub
    	return 0;
    }     
     
     public void addRecipes()
     {
    	 GameRegistry.addShapedRecipe(new ItemStack(this), " SW", "S W", " SW", 'S', Items.string, 'W', Items.stick);
    	 GameRegistry.addShapedRecipe(new ItemStack(this), "WS ", "W S", "WS ", 'S', Items.string, 'W', Items.stick);
    	 
     }
     
     @SubscribeEvent 
     public void fireBowBadly(ArrowLooseEvent ale)
     {
    	 if(ale.bow.getItem() == this)
    		 ale.charge=3;
     }
     
     @SideOnly(Side.CLIENT)
     @Override
     public IIcon getItemIconForUseDuration(int p_94599_1_)
     {
         return this.iconArray[p_94599_1_];
     }
}
