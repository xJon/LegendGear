package nmccoy.legendgear.client;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.ExtraSounds;
import nmccoy.legendgear.LegendGear;
import nmccoy.legendgear.blocks.TileEntityPedestal;
import nmccoy.legendgear.blocks.TileEntitySkybeam;
import nmccoy.legendgear.blocks.TileEntityStarstone;
import nmccoy.legendgear.entities.EntityArrowStorm;
import nmccoy.legendgear.entities.EntityBomb;
import nmccoy.legendgear.entities.EntityBombBlast;
import nmccoy.legendgear.entities.EntityCaptureEgg;
import nmccoy.legendgear.entities.EntityEarthMedallion;
import nmccoy.legendgear.entities.EntityFallingStar;
import nmccoy.legendgear.entities.EntityFireMedallion;
import nmccoy.legendgear.entities.EntityFireblast;
import nmccoy.legendgear.entities.EntityGrindStar;
import nmccoy.legendgear.entities.EntityMagicBoomerang;
import nmccoy.legendgear.entities.EntityQuake;
import nmccoy.legendgear.entities.EntityShotHook;
import nmccoy.legendgear.entities.EntityWhirlwind;
import nmccoy.legendgear.entities.EntityWindMedallion;
import nmccoy.legendgear.render.EntityFireSwirlFX;
import nmccoy.legendgear.render.EntityMagicRuneFX;
import nmccoy.legendgear.render.EntityMagicScrambleFX;
import nmccoy.legendgear.render.EntitySparkleFX;
import nmccoy.legendgear.render.RenderBomb;
import nmccoy.legendgear.render.RenderBoomerang;
import nmccoy.legendgear.render.RenderFallingStar;
import nmccoy.legendgear.render.RenderGrindStar;
import nmccoy.legendgear.render.RenderHookshot;
import nmccoy.legendgear.render.RenderJar;
import nmccoy.legendgear.render.RenderMedallion;
import nmccoy.legendgear.render.RenderPrismaticXP;
import nmccoy.legendgear.render.RenderStarstone;
import nmccoy.legendgear.render.TileEntityPedestalRender;
import nmccoy.legendgear.render.TileEntitySkybeamRender;
import nmccoy.legendgear.render.TileEntityStarstoneRender;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;



public class ClientProxy extends CommonProxy {
	

	
	
    @Override
    public void registerRenderers() {
            MinecraftForgeClient.preloadTexture(ITEMS_PNG);
            MinecraftForgeClient.preloadTexture(BLOCK_PNG);
            if(LegendGear.allowCaptureEgg) RenderingRegistry.registerEntityRenderingHandler(EntityCaptureEgg.class, new RenderSnowball(12));
           
           	RenderingRegistry.registerEntityRenderingHandler(EntityEarthMedallion.class, new RenderMedallion(6));
           	RenderingRegistry.registerEntityRenderingHandler(EntityQuake.class, new RenderMedallion(255));
           	RenderingRegistry.registerEntityRenderingHandler(EntityWindMedallion.class, new RenderMedallion(7));
           	RenderingRegistry.registerEntityRenderingHandler(EntityArrowStorm.class, new RenderMedallion(255));
           	RenderingRegistry.registerEntityRenderingHandler(EntityMagicBoomerang.class, new RenderBoomerang());
           	RenderingRegistry.registerEntityRenderingHandler(EntityFireblast.class, new RenderMedallion(255));
           	RenderingRegistry.registerEntityRenderingHandler(EntityFireMedallion.class, new RenderMedallion(8));
            RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb());
        	RenderingRegistry.registerEntityRenderingHandler(EntityBombBlast.class, new RenderMedallion(255));
        	RenderingRegistry.registerEntityRenderingHandler(EntityWhirlwind.class, new RenderMedallion(255));
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityShotHook.class, new RenderHookshot(46));
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityGrindStar.class, new RenderGrindStar());
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityFallingStar.class, new RenderFallingStar());
        	
        	
        	jarRenderID = RenderingRegistry.getNextAvailableRenderId();
        	RenderingRegistry.registerBlockHandler(new RenderJar());
        	starstoneRenderID = RenderingRegistry.getNextAvailableRenderId();
        	RenderingRegistry.registerBlockHandler(new RenderStarstone());
        	
        	
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRender());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkybeam.class, new TileEntitySkybeamRender());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStarstone.class, new TileEntityStarstoneRender());
        	
        	
        	if(LegendGear.prismaticXP) RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, new RenderPrismaticXP());
        	
    }
    @Override
    public void registerSounds() {
    	MinecraftForge.EVENT_BUS.register(new ExtraSounds());
    }
    
    @Override
    public void addRuneParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    	if(world.isRemote)
		{
			EntityMagicRuneFX emrfx = new EntityMagicRuneFX(world, x, y, z, vx, vy, vz, scale);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(emrfx);
		}
    }
    
    public void addScrambleParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    	if(world.isRemote)
		{
			EntityMagicRuneFX emrfx = new EntityMagicScrambleFX(world, x, y, z, vx, vy, vz, scale);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(emrfx);
		}
    }
    
    public void addFlareParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    	if(world.isRemote)
		{
			EntityFireSwirlFX emrfx = new EntityFireSwirlFX(world, x, y, z, vx, vy, vz, scale);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(emrfx);
		}
    }
    
    public void addSparkleParticle(World world, double x, double y, double z, double vx, double vy, double vz, float scale)
    {
    	if(world.isRemote)
		{
			EntitySparkleFX emrfx = new EntitySparkleFX(world, x, y, z, vx, vy, vz, scale);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(emrfx);
		}
    }
    
    
    
}
