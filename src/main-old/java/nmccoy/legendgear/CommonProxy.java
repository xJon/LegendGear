package nmccoy.legendgear;

import net.minecraft.world.World;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.SpellDecorator;

public class CommonProxy {
    //public static String ITEMS_PNG = "/nmccoy/legendgear/assets/items.png";
    //public static String BLOCK_PNG = "/nmccoy/legendgear/assets/blocks.png";
    public static String XP_PNG = "/nmccoy/legendgear/assets/xporb.png";
    public static String STAR_PNG = "/nmccoy/legendgear/assets/star.png";
    public static String BEAM_PNG = "/nmccoy/legendgear/assets/beam.png";
    public static String CHAOS_PNG = "/nmccoy/legendgear/assets/chaosrainbow2.png";
    public static String RED_HEADBAND_PNG = "/nmccoy/legendgear/assets/red_headband.png";
    public static String BLUE_HEADBAND_PNG = "/nmccoy/legendgear/assets/blue_headband.png";
    public static String GREEN_HEADBAND_PNG = "/nmccoy/legendgear/assets/green_headband.png";
    public static String WINDBOOTS_PNG = "/nmccoy/legendgear/assets/windboots.png";
    
    

	public static int renderPass;
	
	public static int jarRenderID;
	public static int pedestalRenderID;
	public static int starstoneRenderID;
    
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }
    public void registerSounds() {
    }
    
    public void addRuneParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {	
    }
    public void addScrambleParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {	
    }
    public void addFlareParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    }
    public void addSparkleParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    }
    
    public void decorateSpell(EntitySpellEffect spell)
    {
    	
    }
}
