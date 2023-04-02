package nmccoy.legendgear;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

import net.minecraftforge.common.util.ForgeDirection;

public class AzurineGenerator implements IWorldGenerator {
	public static final int attempts = 1;
	public static final int minAltitude = 100;
	public static final int maxAltitude = 110;
	public static final float density = 0.5f;
	public AzurineGenerator() {
		// TODO Auto-generated constructor stub
	}

	private boolean canBlockBreathe(World world, int x, int y, int z)
	{
		if(world.isAirBlock(x+1, y, z)) return true;
		if(world.isAirBlock(x-1, y, z)) return true;
		if(world.isAirBlock(x, y+1, z)) return true;
		if(world.isAirBlock(x, y-1, z)) return true;
		if(world.isAirBlock(x, y, z+1)) return true;
		if(world.isAirBlock(x, y, z-1)) return true;
		return false;
	}
	
	private void generateOres(Random random, World world, int coreX, int coreY, int coreZ, ForgeDirection airDirection)
	{
		Block azurineOre = LegendGear2.azuriteOreBlock;
		Block azurineCrystal = Blocks.torch;
		int r = 2;
		int h = 2;
		if(airDirection==ForgeDirection.EAST || airDirection==ForgeDirection.WEST) // fill a north-south-aligned plane
		{
			int x = coreX;
			for(int y = coreY - h; y <= coreY + h; y++)
				for(int z = coreZ - r; z <= coreZ + r; z++)
				{
					if(random.nextFloat() < density && world.getBlock(x, y, z) == Blocks.stone && world.isAirBlock(x+airDirection.offsetX, y, z))
					{
						world.setBlock(x, y, z, azurineOre);
						world.setBlock(x+airDirection.offsetX, y, z, azurineCrystal);
					}
				}
		}
		if(airDirection==ForgeDirection.NORTH || airDirection==ForgeDirection.SOUTH) // fill a north-south-aligned plane
		{
			int z = coreZ;
			for(int y = coreY - h; y <= coreY + h; y++)
				for(int x = coreX - r; x <= coreX + r; x++)
				{
					if(random.nextFloat() < density && world.getBlock(x, y, z) == Blocks.stone && world.isAirBlock(x, y, z+airDirection.offsetZ))
					{
						world.setBlock(x, y, z, azurineOre);
						world.setBlock(x, y, z+airDirection.offsetZ, azurineCrystal);
					}
				}
		}
						
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		
		if(world.provider.dimensionId == 0)
		{
			for(int i = 0; i < attempts; i++)
			{
				int y = random.nextInt(maxAltitude+1-minAltitude)+minAltitude;
				int localX = random.nextInt(16);
				for(int localZ = 0; localZ < 16; localZ++)
				{
					int x = localX + chunkX*16;
					int z = localZ + chunkZ*16;
					
					Block a = world.getBlock(x,y,z);
					Block b = world.getBlock(x,y,z+1);
					if(a==Blocks.stone && b==Blocks.air) generateOres(random, world, x, y, z, ForgeDirection.SOUTH);
					if(a==Blocks.air && b==Blocks.stone) generateOres(random, world, x, y, z+1, ForgeDirection.NORTH);
				}
				int localZ = random.nextInt(16);
				for(localX = 0; localX < 16; localX++)
				{
					int x = localX + chunkX*16;
					int z = localZ + chunkZ*16;
					
					Block a = world.getBlock(x,y,z);
					Block b = world.getBlock(x+1,y,z);
					if(a==Blocks.stone && b==Blocks.air) generateOres(random, world, x, y, z, ForgeDirection.EAST);
					if(a==Blocks.air && b==Blocks.stone) generateOres(random, world, x+1, y, z, ForgeDirection.WEST);
				}
			}
		}
	}

}
