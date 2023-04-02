package nmccoy.legendgear.client;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.common.MinecraftForge;
import nmccoy.legendgear.CommonProxy;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.block.TileEntityStarstone;
import nmccoy.legendgear.entity.EntityFallingStar;
import nmccoy.legendgear.entity.EntityMagicBoomerang;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.EntityThrownOrb;
import nmccoy.legendgear.entity.SpellDecorator;
import nmccoy.legendgear.render.RenderBoomerang;
import nmccoy.legendgear.render.RenderFallingStar;
import nmccoy.legendgear.render.RenderPrismaticXP;
import nmccoy.legendgear.render.RenderSpellDecoration;
import nmccoy.legendgear.render.RenderThrownOrb;
import nmccoy.legendgear.render.TestRendering;
import nmccoy.legendgear.render.TileEntityStarstoneRender;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;



public class ClientProxy extends CommonProxy {
	public static int starglassRenderID;

	
	
    @Override
    public void registerRenderers() {
            //MinecraftForgeClient.preloadTexture(ITEMS_PNG);
            //MinecraftForgeClient.preloadTexture(BLOCK_PNG);
            //if(LegendGear.allowCaptureEgg) RenderingRegistry.registerEntityRenderingHandler(EntityCaptureEgg.class, new RenderSnowball(12));
           /*
           	RenderingRegistry.registerEntityRenderingHandler(EntityEarthMedallion.class, new RenderSnowball(LegendGear.earthMedallion));
           	RenderingRegistry.registerEntityRenderingHandler(EntityQuake.class, new RenderNothing());
           	RenderingRegistry.registerEntityRenderingHandler(EntityWindMedallion.class, new RenderSnowball(LegendGear.windMedallion));
           	RenderingRegistry.registerEntityRenderingHandler(EntityArrowStorm.class, new RenderNothing());
           	RenderingRegistry.registerEntityRenderingHandler(EntityMagicBoomerang.class, new RenderBoomerang());
           	RenderingRegistry.registerEntityRenderingHandler(EntityFireblast.class, new RenderNothing());
           	RenderingRegistry.registerEntityRenderingHandler(EntityFireMedallion.class, new RenderSnowball(LegendGear.fireMedallion));
            RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb());
        	RenderingRegistry.registerEntityRenderingHandler(EntityBombBlast.class, new RenderNothing());
        	RenderingRegistry.registerEntityRenderingHandler(EntityWhirlwind.class, new RenderNothing());
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityShotHook.class, new RenderHookshot());
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntityGrindStar.class, new RenderGrindStar());
        	*/
        	RenderingRegistry.registerEntityRenderingHandler(EntityFallingStar.class, new RenderFallingStar());
        	RenderingRegistry.registerEntityRenderingHandler(SpellDecorator.class, new RenderSpellDecoration());
        	RenderingRegistry.registerEntityRenderingHandler(EntityThrownOrb.class, new RenderThrownOrb(LegendGear2.emptyOrb));
        	
        	MinecraftForge.EVENT_BUS.register(new TestRendering());
        	/*
        	RenderingRegistry.registerEntityRenderingHandler(EntityEnderMedallion.class, new RenderSnowball(LegendGear.enderMedallion));
        	RenderingRegistry.registerEntityRenderingHandler(EntityEnderBomb.class, new RenderEnderBomb());
        	
        	jarRenderID = RenderingRegistry.getNextAvailableRenderId();
        	RenderingRegistry.registerBlockHandler(new RenderJar());
        	starstoneRenderID = RenderingRegistry.getNextAvailableRenderId();
        	RenderingRegistry.registerBlockHandler(new RenderStarstone());
        	
        	
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRender());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkybeam.class, new TileEntitySkybeamRender());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStarstone.class, new TileEntityStarstoneRender());
        	
        	
        	if(LegendGear.prismaticXP) 
        	
        	*/
        	RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, new RenderPrismaticXP());
        	RenderingRegistry.registerEntityRenderingHandler(EntityMagicBoomerang.class, new RenderBoomerang());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStarstone.class, new TileEntityStarstoneRender());
        	
        	//starglassRenderID = RenderingRegistry.getNextAvailableRenderId();
        	//RenderingRegistry.registerBlockHandler(new RenderStarglass());
    }
    /*
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
    }*/
    
    public void decorateSpell(EntitySpellEffect spell)
    {
    	if(spell.worldObj.isRemote) spell.worldObj.spawnEntityInWorld(new SpellDecorator(spell));
    }
    
    
}
