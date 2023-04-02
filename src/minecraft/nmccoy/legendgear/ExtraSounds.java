package nmccoy.legendgear;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ExtraSounds {
	 @ForgeSubscribe
	    public void onSound(SoundLoadEvent event)
	    {
	        try 
	        {
	            event.manager.soundPoolSounds.addSound("assets/sine.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/sine.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/heart.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/aheart.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/speedboost.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/speedboost.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/moneysmall.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/moneysmall.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/moneymid.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/moneymid.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/moneybig.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/moneybig.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/partialcharge.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/partialcharge.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/fullcharge.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/fullcharge.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/repel.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/repel.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/boomerang.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/boomerang.ogg"));
		        
	            event.manager.soundPoolSounds.addSound("assets/lift.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/lift.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/throw.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/throw.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/firebuild.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/firebuild.ogg"));
		        
	            event.manager.soundPoolSounds.addSound("assets/highcharge.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/highcharge.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/swordcharge.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/swordcharge.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/whirlwind.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/whirlwind.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/popin.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/popin.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/swordplace.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/swordplace.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/swordtake.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/swordtake.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/unchain.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/unchain.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/wah.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/wah.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/wahwah.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/wahwah.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/bah.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/bah.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/gah.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/gah.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/starfall.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/starfall.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/starcaught.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/starcaught.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/twinkle.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/twinkle.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/sprinkle.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/sprinkle.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/transform.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/transform.ogg"));
	            
	            event.manager.soundPoolSounds.addSound("assets/fluteattack.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/fluteattack.ogg"));
	            event.manager.soundPoolSounds.addSound("assets/flutesustain.ogg", LegendGear.class.getResource("/nmccoy/legendgear/assets/flutesustain.ogg"));
	            
	            
	            
	        } 
	        catch (Exception e)
	        {
	            System.err.println("Failed to register one or more sounds.");
	        }
	    }
}
