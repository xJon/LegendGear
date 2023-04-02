package nmccoy.legendgear.block;

import tv.twitch.broadcast.StartFlags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import nmccoy.legendgear.StarSpirit;

public class TileEntityAltar extends TileEntity {

	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		super.updateEntity();
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);

        if (entityplayer != null)
        {
        	
        }
	}
	
	
	
	public int checkDedication()
	{
		return StarSpirit.ENDCHILD;
	}
}
