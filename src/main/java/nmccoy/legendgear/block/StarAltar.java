package nmccoy.legendgear.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.StarSpirit;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StarAltar extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;
    
	
	public StarAltar() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
		setHarvestLevel("pickaxe", 3);
		
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(soundTypePiston);
		setBlockName("starAltar");
		//setBlockTextureName(LegendGear2.MODID+":starAltar");
		setCreativeTab(LegendGear2.legendgearTab);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister regi)
    {
        this.blockIcon = regi.registerIcon(LegendGear2.MODID+":starAltarSide");
        this.topIcon = regi.registerIcon(LegendGear2.MODID+":starAltarTop");
        this.bottomIcon = regi.registerIcon("obsidian");
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
   public boolean renderAsNormalBlock() {
    	return false;
    };
    
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 0 ? this.bottomIcon : (side == 1 ? this.topIcon : this.blockIcon);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityAltar();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		// TODO Auto-generated method stub
		if(!world.isRemote && player.getHeldItem() != null)
		{
			StarSpirit.handleOffering(StarSpirit.ENDCHILD, player, player.getHeldItem());
		}
		return true;
	}
	
}
