package nmccoy.legendgear.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import nmccoy.legendgear.MiniParticle;
import nmccoy.legendgear.entity.SpellDecorator;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSpellDecoration extends Render {

	public RenderSpellDecoration() {
		// TODO Auto-generated constructor stub
		//System.out.println("renderSpellDecoration constructed");
	}

	
	public void renderMiniParticle(MiniParticle mip, int mode, float subtick, double power)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(mip.x+mip.vx*subtick, mip.y+mip.vy*subtick, mip.z+mip.vz*subtick);
		double age = mip.age + subtick/mip.maxLife;
		if(mode == SpellID.Twinkle.ordinal())
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4d(1, 1.5-age, 0.5+age, 1);
			GL11.glLineWidth(2);
			drawCross(2, Math.sin(age*Math.PI)*(power-1)/4, Math.PI/2);	
		}
		if(mode == SpellID.Fire1.ordinal())
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			//GL11.glColor4d(1, 3-age*3, 2-age*3, 1);
			GL11.glColor4d(1, 2-age*2, 1-age*2, 1);
			//GL11.glLineWidth(2);
			//drawDiamondCrescent(age, Math.sin(age*Math.PI*3/4+Math.PI/4)*power/5, 0);
			drawPolySolid(4, Math.sin(age*Math.PI*3/4+Math.PI/4)*power/5, Math.PI/2+age*Math.PI*2);
		}
		if(mode == SpellID.Lightning1.ordinal())
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glLineWidth(2);
			int side = 1;
			//if(mip.uniqueness < 0.5) side = -1;
			double phase;
			GL11.glPushMatrix();
			double s = power*0.15;
			if(age < 0.5)
			{
				double f = Math.sin((age+mip.uniqueness)*30)*0.5+0.5;
				GL11.glColor3d(f*2, 1, 2-f*2);
				diamondZig(s, s*2, Math.sin(age*Math.PI*4));
			}
			else
			{	
				GL11.glBegin(GL11.GL_LINES);
				phase = age*2-1;
				double f = Math.sin((age+mip.uniqueness)*30)*0.5+0.5;
				GL11.glColor3d(f*2, 1, 2-f*2);
				double h = Math.sin(phase*Math.PI)*2.5;
				GL11.glVertex3d(-mip.x, -mip.y, -mip.z);
				GL11.glVertex3d(mip.x*h-mip.x, mip.y*h-mip.y, mip.z*h-mip.z);
				GL11.glEnd();
			}
			
			GL11.glPopMatrix();
		}
		if(mode == SpellID.Ice1.ordinal())
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4d(0.5+(1-Math.sin(age*Math.PI))*0.5, 0.5*(1-Math.sin(age*Math.PI))+0.5, 1, 1);
			//GL11.glLineWidth(2);
			//drawDiamondCrescent(age, (1+age)*power/14, 0);
			double size = (power)/4; 
			if(age < 0.5)
			{
				drawCross(3, size*(1-age*2)*2, Math.PI/2);
			}
			else if(age < 0.75)
			{
				drawPolySolid(6, size*(age-0.5)*4, Math.PI/2);
			}
			else
			{
				drawPolyOutline(6, size, Math.PI/2);
			}
			
		}
		
		GL11.glPopMatrix();
	}
	
	public void drawDiamondCrescent(double phase, double size, double tilt)
	{
		GL11.glPushMatrix();
		billboardTransform();
		double top = 1;
		if(phase < 0.5) top = -Math.cos(phase*2 * Math.PI);
		double bottom = -1;
		if(phase > 0.5) bottom = -Math.cos((phase-0.5)*2 * Math.PI);
		GL11.glBegin(GL11.GL_TRIANGLES);
		//GL11.glScaled(size, size, size);
		//GL11.glRotated(tilt, 0, 0, 1);
		GL11.glVertex3d(0, bottom*size, 0);
		GL11.glVertex3d(size, 0, 0);
		GL11.glVertex3d(0, top*size, 0);
		GL11.glVertex3d(0, top*size, 0);
		GL11.glVertex3d(-size, 0, 0);
		GL11.glVertex3d(0, bottom*size, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void diamondZig(double w, double h, double phase)
	{
		GL11.glPushMatrix();
		billboardTransform();
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex3d(0, (1-Math.abs(phase))*h, 0);
		GL11.glVertex3d(-phase*w, 0, 0);
		GL11.glVertex3d(phase*w, 0, 0);
		GL11.glVertex3d(0, -(1-Math.abs(phase))*h, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void drawCross(int spikes,  double size, double tilt)
	{
		GL11.glPushMatrix();
		billboardTransform();
		GL11.glBegin(GL11.GL_LINES);
		double dTh = Math.PI/spikes;
		for(int i = 0; i < spikes; i++)
		{
			double theta = i*dTh + tilt;
			double x = Math.cos(theta) * size;
			double y = Math.sin(theta) * size;
			GL11.glVertex3d(x, y, 0);
			GL11.glVertex3d(-x, -y, 0);
			
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void drawPolySolid(int spikes,  double size, double tilt)
	{
		GL11.glPushMatrix();
		billboardTransform();
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		double dTh = Math.PI*2/spikes;
		for(int i = 0; i < spikes; i++)
		{
			double theta = i*dTh + tilt;
			double x = Math.cos(theta) * size;
			double y = Math.sin(theta) * size;
			GL11.glVertex3d(x, y, 0);
			
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void drawPolyOutline(int spikes,  double size, double tilt)
	{
		GL11.glPushMatrix();
		billboardTransform();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		double dTh = Math.PI*2/spikes;
		for(int i = 0; i < spikes; i++)
		{
			double theta = i*dTh + tilt;
			double x = Math.cos(theta) * size;
			double y = Math.sin(theta) * size;
			GL11.glVertex3d(x, y, 0);
			
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void drawStar(int spikes, double tightness, double size, double tilt)
	{
		GL11.glPushMatrix();
		billboardTransform();
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex3d(0, 0, 0);
		double dTh = Math.PI*2/spikes;
		for(int i = 0; i < spikes; i++)
		{
			double theta = i*dTh + tilt;
			double x = Math.cos(theta) * size;
			double y = Math.sin(theta) * size;
			GL11.glVertex3d(x, y, 0);
			theta += dTh*0.5;
			x= Math.cos(theta) * size * tightness;
			y=Math.sin(theta) * size * tightness;
			GL11.glVertex3d(x, y, 0);
		}
		//add last point to finish star
		double theta = tilt;
		double x = Math.cos(theta) * size;
		double y = Math.sin(theta) * size;
		GL11.glVertex3d(x, y, 0);
		
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	
	private void renderSpellEffect(SpellDecorator dec, float subtick)
	{
        for(MiniParticle mip:dec.particles)
        {
        	if(mip.hibernateTime <= 0) renderMiniParticle(mip, dec.spellType, subtick, dec.power);
        }
	}
	
	private void billboardTransform()
	{
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	}
	
	@Override
	public void doRender(Entity entity, double par2,
			double par4, double par6, float notSubtick,
			float subtick) {
		// TODO Auto-generated method stub
        GL11.glPushMatrix();
      //  System.out.println(subtick);
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
      //  GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
      //  GL11.glAlphaFunc(GL11.GL_GREATER, 0);
      //  GL11.glDepthMask(false);
        //do rendering here
        SpellDecorator dec = (SpellDecorator) entity;
        
        renderSpellEffect(dec, subtick);
        
        if(dec.isCrit)
        {
        	float burstTime = dec.longLife + subtick;
        	float phase = burstTime / 10;
        	if(phase <= 1)
        	{
        		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        		GL11.glColor4f(1, 1, 1, 1-phase);
        		GL11.glLineWidth(2);
        		TestRendering.drawHorizontalRing(0, phase*0.3, 0, dec.radius, 8);
        		TestRendering.drawHorizontalRing(0, -phase*0.3, 0, dec.radius, 8);
        		//TestRendering.drawHorizontalRing(0, 0, 0, dec.radius+phase, 8);
        		
        		
        	}
        }
        
       // System.out.println("rendering happening");
                
        ////////////////////////////////
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
       // GL11.glDepthFunc(GL11.GL_LESS);
       // GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		// TODO Auto-generated method stub
		return null;
	}

}
