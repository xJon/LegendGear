package nmccoy.legendgear.render;

import net.minecraft.util.MathHelper;

public class Rainbow {

	public static float r(float phase)
	{
		phase *= Math.PI*2;
        float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
        float g = (MathHelper.sin(phase + (float)Math.PI*2/3) + 1.0F) * 0.5F;
        float b = (MathHelper.sin(phase + (float)Math.PI*4/3) + 1.0F) * 0.5F;
        float resat = Math.min(r, Math.min(g, b));
        r -= resat;
        g -= resat;
        b -= resat;
        float scaler = 1/Math.max(r, Math.max(g, b));
        
        r = Math.min(scaler*r, 1.0f);
        g = Math.min(scaler*g, 1.0f);
        b = Math.min(scaler*b, 1.0f);
        
        return r;
	}
	
	public static float g(float phase)
	{
		phase *= Math.PI*2;
        float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
        float g = (MathHelper.sin(phase + (float)Math.PI*2/3) + 1.0F) * 0.5F;
        float b = (MathHelper.sin(phase + (float)Math.PI*4/3) + 1.0F) * 0.5F;
        float resat = Math.min(r, Math.min(g, b));
        r -= resat;
        g -= resat;
        b -= resat;
        float scaler = 1/Math.max(r, Math.max(g, b));
        
        r = Math.min(scaler*r*0.5f + 0.5f, 1.0f);
        g = Math.min(scaler*g*0.5f + 0.5f, 1.0f);
        b = Math.min(scaler*b*0.5f + 0.5f, 1.0f);
        
        return g;
	}
	public static float b(float phase)
	{
		phase *= Math.PI*2;
        float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
        float g = (MathHelper.sin(phase + (float)Math.PI*2/3) + 1.0F) * 0.5F;
        float b = (MathHelper.sin(phase + (float)Math.PI*4/3) + 1.0F) * 0.5F;
        float resat = Math.min(r, Math.min(g, b));
        r -= resat;
        g -= resat;
        b -= resat;
        float scaler = 1/Math.max(r, Math.max(g, b));
        
        r = Math.min(scaler*r*0.5f + 0.5f, 1.0f);
        g = Math.min(scaler*g*0.5f + 0.5f, 1.0f);
        b = Math.min(scaler*b*0.5f + 0.5f, 1.0f);
        
        return b;
	}
}
