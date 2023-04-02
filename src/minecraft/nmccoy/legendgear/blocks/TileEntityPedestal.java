package nmccoy.legendgear.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.management.PlayerInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TileEntityPedestal extends TileEntityJar {

	public TileEntityPedestal() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
	{
	NBTTagCompound tag = packet.customParam1;
		if (tag.hasKey("Item"))
	    {
	        this.contents = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item"));
	    }
		else this.contents = null;
		//System.out.println("gotPacket, has item: "+tag.hasKey("Item"));
	}
	
	@Override
	public boolean canUpdate() {
		// TODO Auto-generated method stub
		return true;
	}
	
//	public void sendInfo()
//	{
//		if(this.worldObj instanceof WorldServer)
//		{
//			WorldServer ws = (WorldServer)worldObj;
//			
//			int var4 = xCoord >> 4;
//	        int var5 = zCoord >> 4;
//	        PlayerInstance var6 = ws.getPlayerManager().getOrCreateChunkWatcher(var4, var5, false);
//	
//	        if (var6 != null)
//	        {
//	            var6.sendToAllPlayersWatchingChunk(getDescriptionPacket());
//	            //System.out.println("desc sent");
//	        }
//		}
//		//else
//			//System.out.println("not server");
//	}
	
	@Override
	public boolean shouldRefresh(int oldID, int newID, int oldMeta,
			int newMeta, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return(oldID != newID);
		
	}
	
	@Override
	public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        //System.out.println("getting packet with item:" + var1.hasKey("Item"));
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
        
    }
}
