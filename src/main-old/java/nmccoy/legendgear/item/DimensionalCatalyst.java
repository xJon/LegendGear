package nmccoy.legendgear.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nmccoy.legendgear.LegendGear2;

public class DimensionalCatalyst extends Item {

	public DimensionalCatalyst() {
		// TODO Auto-generated constructor stub
		this.setUnlocalizedName("dimensionalCatalyst").setCreativeTab(LegendGear2.legendgearTab).setTextureName(LegendGear2.MODID+":dimensionalCatalyst");
	}
	
	@Override
	public boolean hasEffect(ItemStack p_77636_1_) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		// TODO Auto-generated method stub
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(player.capabilities.allowEdit && block.canHarvestBlock(player, meta) && world.canMineBlock(player, x, y, z) && !block.hasTileEntity(meta) && block.getMobilityFlag()==0 && !world.isRemote)
		{ 
			for(int i=0; i<6; i++)
			{
				int dx = world.rand.nextInt(3)-1;
				int dy = world.rand.nextInt(3)-1;
				int dz = world.rand.nextInt(3)-1;
				
				int px = dx+x;
				int py = dy+y;
				int pz = dz+z;
				
				if(block.canPlaceBlockAt(world, px, py, pz) && world.isAirBlock(px, py, pz))
				{
					world.setBlock(px, py, pz, block, meta, 3);
					world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0f, 1.0f);
					//world.spawnParticle(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
					world.setBlockToAir(x, y, z);
					stack.stackSize--;
					return true;
				}
			}
		}
		return false;
	}

}
