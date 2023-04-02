package nmccoy.legendgear.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;

public class MilkChocolate extends Item {

	public MilkChocolate()
	{
		super();
		this.setMaxStackSize(16);
		this.setCreativeTab(LegendGear2.legendgearTab);
		setUnlocalizedName("milkChocolate");
		setTextureName(LegendGear2.MODID+":itemMilkChocolate");
	}
	
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.eat;
    }
	
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }
	
    public void addRecipes()
    {
    	GameRegistry.addShapelessRecipe(new ItemStack(this), Items.milk_bucket, Items.sugar, new ItemStack(Items.dye, 1, 3));
    }

    
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        if (!world.isRemote)
        {
            player.curePotionEffects(new ItemStack(Items.milk_bucket));
            player.getFoodStats().addStats(2, 0);
        }

        return stack;
    }
    
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	if(p_77659_3_.canEat(false))
    		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
}
