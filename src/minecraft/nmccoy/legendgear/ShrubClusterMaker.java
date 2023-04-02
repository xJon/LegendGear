package nmccoy.legendgear;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ShrubClusterMaker extends WorldGenerator {

	public ShrubClusterMaker() {
		// TODO Auto-generated constructor stub
	}

	public ShrubClusterMaker(boolean par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean putShrub(World world, int x, int y, int z)
	{
		return putShrub(world, x, y, z, 0);
	}
	public static boolean putShrub(World world, int x, int y, int z, int meta)
	{
		if (LegendGear.mysticShrub.canPlaceBlockAt(world, x, y, z) && LegendGear.mysticShrub.canBlockStay(world, x, y, z))
        {
            world.setBlockAndMetadataWithNotify(x, y, z, LegendGear.mysticShrub.blockID, meta);
            return true;
        }
		return false;
	}
	
	public static void buildShrubStar(World var1, int x, int y, int z)
	{
		buildShrubStar(var1, x, y, z, 0);
	}
	
	public static void buildShrubStar(World var1, int x, int y, int z, int meta)
	{
		putShrub(var1, x-1, y, z, meta);
		putShrub(var1, x, y, z-1, meta);
		putShrub(var1, x+1, y, z, meta);
		putShrub(var1, x, y, z+1, meta);
		putShrub(var1, x-3, y, z, meta);
		putShrub(var1, x, y, z-3, meta);
		putShrub(var1, x+3, y, z, meta);
		putShrub(var1, x, y, z+3, meta);
		putShrub(var1, x-2, y, z-2, meta);
		putShrub(var1, x+2, y, z-2, meta);
		putShrub(var1, x+2, y, z+2, meta);
		putShrub(var1, x-2, y, z+2, meta);
	}

	@Override
	public boolean generate(World var1, Random var2, int x, int var4,
			int z) {
		int y = var1.getTopSolidOrLiquidBlock(x, z);
		
		
		if(var2.nextDouble() < LegendGear.shrubGenStarChance)
		{//make "clock"
			buildShrubStar(var1, x, y, z);
		}
		else
		{
			//make clump
			putShrub(var1, x, y, z+1);
			putShrub(var1, x+1, y, z+1);
			for(int i=-1; i<3; i++) putShrub(var1, x+i, y, z);
			for(int i=-1; i<3; i++) putShrub(var1, x+i, y, z-1);
			putShrub(var1, x, y, z-2);
			putShrub(var1, x+1, y, z-2);
		}
		
		if(LegendGear.debugShrubs) 
		{
			//System.out.println("shrub at "+x+", "+z);
			if((x >= 0) && (x < 256) && (z >= 0) && (z < 256))
			{
				//var1.setBlockWithNotify(x, y+5, z, Block.glowStone.blockID);
				System.out.println("Shrub clusters made:" + ++LegendGear.shrubcount);
			}
		}
		
		var1.updateAllLightTypes(x, y, z);
		
		return true;
	}

}
