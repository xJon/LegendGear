package nmccoy.legendgear.events;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// TODO Auto-generated method stub
		if(packet.channel.equals("LegendGearJump"))
		{
			//System.out.println("got jump packet");
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int id = 0;
			int dimension = 0;
			Entity ent;
			 try 
			 {
                 id = inputStream.readInt();   
                 dimension = inputStream.readInt();
                 ent = DimensionManager.getWorld(dimension).getEntityByID(id);
                 if(ent != null)
                	 ent.getEntityData().setBoolean("clientJustJumped", true);
                 
	         } catch (IOException e) {
	                 e.printStackTrace();
	                 return;
	         }
		}
		
	}

}
