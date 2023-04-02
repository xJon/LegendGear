package nmccoy.legendgear.blocks;

import net.minecraft.tileentity.TileEntity;

public class TileEntitySkybeam extends TileEntity {

	public int powerlevel;
	public static int MAX_POWER = 10;
	public boolean powered;
	
	public TileEntitySkybeam() {
		// TODO Auto-generated constructor stub
		
	}
	@Override
	 public double func_82115_m()
	    {
	        return 65536.0D;
	    }
	
	@Override
	public boolean canUpdate() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(!powered)
			{
				//do powerup stuff;
			}
			powerlevel = Math.min(powerlevel+1, MAX_POWER);
			powered = true;
		}
		else
		{
			if(powered)
			{
				//do powerdown stuff;
			}
			powerlevel = Math.max(powerlevel-1, 0);
			powered = false;
		}
	}

}
