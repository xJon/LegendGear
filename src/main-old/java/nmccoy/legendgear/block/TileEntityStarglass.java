package nmccoy.legendgear.block;

import net.minecraft.tileentity.TileEntity;

public class TileEntityStarglass extends TileEntity {

	public TileEntityStarglass() {
		// TODO Auto-generated constructor stub
		
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
