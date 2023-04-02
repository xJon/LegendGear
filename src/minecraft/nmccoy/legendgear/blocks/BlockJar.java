package nmccoy.legendgear.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.client.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockJar extends BlockContainer {


	public BlockJar(int par1) {
		super(par1, Material.glass);
		setStepSound(soundGlassFootstep);
		setTextureFile(CommonProxy.BLOCK_PNG);
		setHardness(0);
		setBlockName("blockClayJar");
		setCreativeTab(LegendGear.creativeTab);

        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getBlockTextureFromSide(int par1) {
		// TODO Auto-generated method stub
		if(par1 == ForgeDirection.UP.ordinal())
			return 7;
		if(par1 == ForgeDirection.DOWN.ordinal())
			return 9;
		
			return 8;
	}
	
	 /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return CommonProxy.jarRenderID;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityJar();
	}
	

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		if(!par1World.isRemote)
		{
			TileEntityJar tej = (TileEntityJar)par1World.getBlockTileEntity(par2, par3, par4);
			if(tej != null && tej.contents != null)
			{
				EntityItem ei = new EntityItem(par1World, par2+0.5, par3+0.5, par4+0.5, tej.contents);
				ei.delayBeforeCanPickup = 10;
				par1World.spawnEntityInWorld(ei);
			}
		}
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		// TODO Auto-generated method stub
		if(!par1World.isRemote && par5Entity instanceof EntityItem)
		{
			EntityItem ei = (EntityItem) par5Entity;
			TileEntityJar tej = (TileEntityJar)par1World.getBlockTileEntity(par2, par3, par4);
			if(tej != null && (tej.contents == null) && !ei.isDead)
			{
				tej.contents = ei.func_92014_d();
				ei.setDead();
				par1World.playSoundAtEntity(ei, "mob.chicken.plop", 0.3f, 0.5f+(float)Math.random()*0.2f);
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		// TODO Auto-generated method stub
		return new ArrayList<ItemStack>();
	}

}
