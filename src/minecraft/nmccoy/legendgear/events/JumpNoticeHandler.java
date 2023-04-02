package nmccoy.legendgear.events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.network.PacketDispatcher;

public class JumpNoticeHandler {
	@ForgeSubscribe
	public void HandleJumpEdge(LivingUpdateEvent lue) {
		// TODO Auto-generated method stub
		if(lue.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer ep = (EntityPlayer) lue.entityLiving;
			if(ep.getEntityData().getBoolean("clientJustJumped"))
			{
				ep.getEntityData().setBoolean("clientJustJumped", false);
				ep.getEntityData().setBoolean("justJumped", true);
			}
			else ep.getEntityData().setBoolean("justJumped", false);
			
			if(ep.isJumping)
			{
				boolean wasJumping = ep.getEntityData().getBoolean("wasJumping");
				if(!wasJumping && ep.isJumping)
				{
					ep.getEntityData().setBoolean("justJumped", true);
					if(ep.worldObj.isRemote)
					{
						ByteArrayOutputStream baos = new ByteArrayOutputStream(8);
						DataOutputStream outputStream = new DataOutputStream(baos);
						try {
						        outputStream.writeInt(ep.entityId);
						        outputStream.writeInt(ep.dimension);
						} catch (Exception ex) {
						        ex.printStackTrace();
						}
						Packet250CustomPayload packet = new Packet250CustomPayload();
						packet.channel = "LegendGearJump";
						packet.data = baos.toByteArray();
						packet.length = baos.size();
						//send packet
						PacketDispatcher.sendPacketToServer(packet);
						//System.out.println("sent jump packet");
					}
				}
				
				//System.out.println("worn!");
			}
			ep.getEntityData().setBoolean("wasJumping", ep.isJumping);
		}
	}
}
