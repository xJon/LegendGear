package nmccoy.legendgear.block;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import nmccoy.legendgear.LegendGear2;

public class StarSandBlock extends BlockFalling {
	public StarSandBlock()
	{
		super(Material.sand);
		setBlockTextureName(LegendGear2.MODID+":starSandAnim");
		setBlockName("starSand");
		setHardness(0.5F);
		setStepSound(soundTypeSand);
		setLightLevel(0f);
		setCreativeTab(LegendGear2.legendgearTab);
		
	}
	
	@Override 
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	};
	
	public void addRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(this), new ItemStack(Blocks.sand), new ItemStack(LegendGear2.starDust, 1, 0));
	}
	
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(Blocks.sand));
        if(world.rand.nextInt(6) <= 2+fortune)
        	ret.add(new ItemStack(LegendGear2.starDust, 1, 0));
        return ret;
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return true;
	}
}
