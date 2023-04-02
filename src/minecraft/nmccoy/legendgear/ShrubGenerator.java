package nmccoy.legendgear;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import cpw.mods.fml.common.IWorldGenerator;

public class ShrubGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		if(world.provider.canRespawnHere() && random.nextInt(LegendGear.shrubRarity) == 0)
		{
			int x = chunkX*16+random.nextInt(16);
			int z = chunkZ*16+random.nextInt(16);
			int biome = world.provider.getBiomeGenForCoords(x, z).biomeID;
			for(int i=0; i < LegendGear.shrubDisabledBiomes.length; i++)
				if(biome == LegendGear.shrubDisabledBiomes[i]) return;
			new ShrubClusterMaker().generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z), z);
		}

	}

}
