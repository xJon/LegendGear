package nmccoy.legendgear;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class BombFlowerGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		int tries = 8;
		int spread = 9;
		float spreadchance = 0.5f;
		for(int i = 0; i < tries; i++)
		{
			int x = chunkX*16+random.nextInt(16);
			int z = chunkZ*16+random.nextInt(16);
			int y;
	        for (y=1; !world.isAirBlock(x, y, z); ++y)
	        {
	            ;
	        }

			if(LegendGear.bombFlower.canPlaceBlockAt(world, x, y, z))
			{
				for(int xo = 0; xo < spread; xo++)
					for(int zo = 0; zo < spread; zo++)
					{
						int nx = x+xo-spread/2;
						int nz = z+zo-spread/2;
						if(LegendGear.bombFlower.canPlaceBlockAt(world, nx, y, nz) &&
								random.nextFloat() < spreadchance)
							world.setBlockWithNotify(nx, y, nz, LegendGear.bombFlower.blockID);
					}
			}
		}
	}

}
