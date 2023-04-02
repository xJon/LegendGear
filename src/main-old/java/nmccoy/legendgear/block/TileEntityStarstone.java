package nmccoy.legendgear.block;

import net.minecraft.tileentity.TileEntity;

public class TileEntityStarstone extends TileEntity {

	public TileEntityStarstone() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass==1;
	}
	
	@Override
	 public double getMaxRenderDistanceSquared()
	 {
	     return 65536.0D;
	 }
	
	@Override public boolean canUpdate()
	{
		return false;
	}
}
