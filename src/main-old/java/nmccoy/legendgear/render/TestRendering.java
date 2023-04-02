package nmccoy.legendgear.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import nmccoy.legendgear.item.SpellItem;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;



public class TestRendering {

	public static void drawHorizontalRing(double x, double y, double z, double radius, int segments)
	{
		drawHorizontalRing(x, y, z, radius, segments, 0);
	}

	public static void drawHorizontalRing(double x, double y, double z, double radius, int segments, float twist)
	{
		drawHorizontalRing(x, y, z, radius, segments, twist, 1);
	}
	
	public static void drawHorizontalRing(double x, double y, double z, double radius, int segments, float twist, int stride)
	{
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int i = 0; i < segments; i++)
		{
			double theta = Math.PI*2 / segments * (i*stride) + Math.PI*2*(twist+0.125);
			double dx = Math.cos(theta)*radius;
			double dz = Math.sin(theta)*radius;
			GL11.glVertex3d(x+dx,y,z+dz);
		}
		GL11.glEnd();
	}
	
	public static void drawFancyReticle(EntityPlayer player, Vec3 pos, double radius, float progress)
	{
		float phase = (float)(Minecraft.getSystemTime() % 1000 / 1000f);
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.02f);
		
		GL11.glLineWidth(1);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glColor4ub((byte)255,(byte)255,(byte)255, (byte)64);
		drawHorizontalRing(pos.xCoord, pos.yCoord+radius*0.866f, pos.zCoord, radius/2, 8,0,5);
		drawHorizontalRing(pos.xCoord, pos.yCoord-radius*0.866f, pos.zCoord, radius/2, 8,0,5);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
		GL11.glVertex3d(pos.xCoord, pos.yCoord-radius, pos.zCoord);
		GL11.glEnd();
		/*
		GL11.glDepthFunc(GL11.GL_GREATER);
		GL11.glColor4ub((byte)255,(byte)255,(byte)255, (byte)32);
		drawHorizontalRing(pos.xCoord, pos.yCoord+radius*0.866f, pos.zCoord, radius/2, 8,0,5);
		drawHorizontalRing(pos.xCoord, pos.yCoord-radius*0.866f, pos.zCoord, radius/2, 8,0,5);*/
		
		if(!player.isUsingItem())
		{
			GL11.glLineWidth(2);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glColor4ub((byte)255,(byte)255,(byte)255, (byte)128);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);
			GL11.glDepthFunc(GL11.GL_GREATER);
			GL11.glColor4ub((byte)255,(byte)255,(byte)255, (byte)32);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);
		}
		else if(progress < 1)
		{
			GL11.glLineWidth(3);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glColor4f(1,progress,0, 1);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);
			GL11.glDepthFunc(GL11.GL_GREATER);
			GL11.glColor4f(1,progress,0, 0.3f);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);

			GL11.glLineWidth(2);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glColor4f(1,progress,0, 1);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*progress, 8, -progress);
			GL11.glDepthFunc(GL11.GL_GREATER);
			GL11.glColor4f(1,progress,0, 0.3f);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*progress, 8, -progress);
			
			double flux1 = 1-Math.cos(progress*Math.PI/2);
			double flux2 = 1-Math.cos(progress*Math.PI*3/2);
			double flux3 = 1-Math.cos(progress*Math.PI*5/2);
			
			GL11.glLineWidth(2f);
			GL11.glColor4f(1,1,1,0.5f*progress);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux1, 8, progress/2, 5);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux2, 8, progress, 5);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux3, 8, -progress/2, 5);
			/*
			GL11.glDepthFunc(GL11.GL_GREATER);
			GL11.glColor4f(1,1,1, 0.125f*progress);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux1, 8, progress/2, 5);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux2, 8, progress, 5);
			drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius*flux3, 8, -progress/2, 5);*/
		}
		else
		{
			 phase = (float)Math.sin(phase*Math.PI*2*10)/2+0.5f;
				GL11.glLineWidth(3);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glColor4f(1,(phase)*0.5f+0.5f,(1-phase)*0.5f+0.5f, 1);
				drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);
				GL11.glLineWidth(2);
				drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8, 0, 5);
				
				GL11.glDepthFunc(GL11.GL_GREATER);
				GL11.glColor4f(1,(phase)*0.5f+0.5f,(1-phase)*0.5f+0.5f, 0.3f);
				GL11.glLineWidth(3);
				drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8);
				//GL11.glLineWidth(2);
				//drawHorizontalRing(pos.xCoord, pos.yCoord, pos.zCoord, radius, 8, 0, 5);
		}	
	}
	
	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		EntityClientPlayerMP player = mc.thePlayer;
		
		ItemStack weapon = player.getHeldItem();
		
		if(weapon != null && player.getHeldItem().getItem() instanceof SpellItem)
		{
							
			double doubleX = mc.thePlayer.posX - 0.5;
			double doubleY = mc.thePlayer.posY + 0.1;
			double doubleZ = mc.thePlayer.posZ - 0.5;
			double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks; 
			double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
			double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
			
			Vec3 playerLook = player.getLookVec();
		//	System.out.println("Look:"+playerLook);
			Vec3 playerEye = player.getPosition(event.partialTicks);
			//System.out.println("Eye:"+playerEye);
			double castRange = SpellItem.getPlayerCastRange(player);
			double castRadius = SpellItem.getPlayerCastRadius(player);
			//System.out.println(castRange);
			
			Vec3 targetPos = SpellItem.getRayTargetResult(playerEye, playerLook, castRange, player.worldObj);
			
			//System.out.println("Target:"+targetPos+" player:"+playerEye);
			float retreat = -1/64f;
			targetPos = targetPos.addVector(playerLook.xCoord*retreat, playerLook.yCoord*retreat, playerLook.zCoord*retreat);
			
			GL11.glPushMatrix();
			GL11.glTranslated(-playerX, -playerY, -playerZ);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
			float mx = (int) mc.thePlayer.posX;
			float my = (int) mc.thePlayer.posY - mc.thePlayer.eyeHeight;
			float mz = (int) mc.thePlayer.posZ;
			/*
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3f(mx+0.4f,my,mz+0.4f);
			GL11.glVertex3f(mx-0.4f,my,mz-0.4f);
			GL11.glVertex3f(mx+0.4f,my,mz-0.4f);
			GL11.glVertex3f(mx-0.4f,my,mz+0.4f);
			GL11.glEnd();*/
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
/*
			GL11.glLineWidth(3);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glColor4ub((byte)255,(byte)0,(byte)0, (byte)255);
			drawHorizontalRing(targetPos.xCoord, targetPos.yCoord, targetPos.zCoord, castRadius, 32);
			GL11.glDepthFunc(GL11.GL_GREATER);
			GL11.glColor4ub((byte)255,(byte)128,(byte)128, (byte)32);
			drawHorizontalRing(targetPos.xCoord, targetPos.yCoord, targetPos.zCoord, castRadius, 32);
			*/
			drawFancyReticle(player, targetPos, castRadius, SpellItem.getCastingProgress(weapon, player, event.partialTicks));
			
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();	
		}
	}
}
