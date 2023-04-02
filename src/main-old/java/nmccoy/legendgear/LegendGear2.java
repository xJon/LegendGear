package nmccoy.legendgear;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import nmccoy.legendgear.block.AzuriteOreBlock;
import nmccoy.legendgear.block.CaltropsBlock;
import nmccoy.legendgear.block.InfusedStarstoneBlock;
import nmccoy.legendgear.block.StarSandBlock;
import nmccoy.legendgear.block.StarglassBlock;
import nmccoy.legendgear.block.StarstoneBlock;
import nmccoy.legendgear.block.TileEntityStarstone;
import nmccoy.legendgear.entity.EntityFallingStar;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.SpellDecorator;
import nmccoy.legendgear.item.DimensionalCatalyst;
import nmccoy.legendgear.item.EmeraldShard;
import nmccoy.legendgear.item.ItemCaltropsBlock;
import nmccoy.legendgear.item.MilkChocolate;
import nmccoy.legendgear.item.ReedPipes;
import nmccoy.legendgear.item.StaffTwinkle;
import nmccoy.legendgear.item.StarDust;
import nmccoy.legendgear.item.StarglassOrb;
import nmccoy.legendgear.item.StarglassSword;
import nmccoy.legendgear.render.TestRendering;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
@Mod(modid = LegendGear2.MODID, version = LegendGear2.VERSION, name="LegendGear")
public class LegendGear2 {
    public static final String MODID = "legendgear";
    public static final String VERSION = "2.pre0";
    
    public static int emeraldExchangeRate = 8;
    public static int starFadeTime = 20*22;
    public static int starCooldown = 20*30;
    public static int starCooldownFuzz = 20*15;
    public static int starKarmaCost = 10000; //~8 minutes
    public static int huntingKarmaBonus = 300; //15 seconds
    
    public static int maxEmeraldDropsBanked = 6;
    public static float emeraldAccumulationDistance = 16; 
    
    public static int minXpForEmeralds = 3;
    public static float emeraldsPerXP = 0.5f;
    public static float guaranteedEmeraldRatio = 0.75f;
    
    public static boolean CONFIG_ALLOW_TRINKETS = true;
    public static boolean CONFIG_ALLOW_EMERALD_DROPS = true;
    
    public static ToolMaterial starglass = EnumHelper.addToolMaterial("STARGLASS", 2, 13, 16, 2.0f, 0);
    
    @Instance("LegendGear")
    public static LegendGear2 instance;
    
    @SidedProxy(clientSide="nmccoy.legendgear.client.ClientProxy", serverSide="nmccoy.legendgear.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs legendgearTab = new CreativeTabs("lgTab")
    {
    	public Item getTabIconItem()
    	{
    		return starDust;
    	}
    };
    
    //ITEM DECLARATIONS
    public static EmeraldShard emeraldShard;
    public static StarDust starDust;
    public static MilkChocolate milkChocolate;
    public static ReedPipes reedPipes;
    public static Item starglassIngot;
    public static StarglassSword starglassSword;
    public static StarglassOrb emptyOrb;
    public static DimensionalCatalyst dimensionalCatalyst;
    public static StaffTwinkle twinkleStaff;
    
    public static StarstoneBlock starstoneBlock;
    public static InfusedStarstoneBlock infusedStarstoneBlock;
    public static StarSandBlock starSandBlock;
    public static StarglassBlock starglassBlock;
    public static AzuriteOreBlock azuriteOreBlock;
    
    public static CaltropsBlock caltropsBlock;
   // public static ItemCaltropsBlock itemCaltropsBlock;
    
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    	MinecraftForge.EVENT_BUS.register(new CustomAttributes());
    	
    	emeraldShard = new EmeraldShard();
    	GameRegistry.registerItem(emeraldShard, "emeraldShard"); 
    	emeraldShard.addRecipes();
    	 FMLCommonHandler.instance().bus().register(emeraldShard);
    	 MinecraftForge.EVENT_BUS.register(emeraldShard);
    	 
    	 starstoneBlock = new StarstoneBlock();
    	 GameRegistry.registerBlock(starstoneBlock,  "starstoneBlock");
    	 
      	 infusedStarstoneBlock = new InfusedStarstoneBlock();
    	 GameRegistry.registerBlock(infusedStarstoneBlock,  "infusedStarstoneBlock");
    	 GameRegistry.registerTileEntity(TileEntityStarstone.class, "infusedStarstoneBlock");
    	 
    	 starDust = new StarDust();
    	 GameRegistry.registerItem(starDust, "starDust");
    	 starDust.addRecipes();
    	 
    	 starSandBlock = new StarSandBlock();
    	 GameRegistry.registerBlock(starSandBlock,  "starSand");
    	 starSandBlock.addRecipes();
    	 
    	 azuriteOreBlock = new AzuriteOreBlock();
    	 GameRegistry.registerBlock(azuriteOreBlock,  "azuriteOre");
    	 
    	 starglassIngot = new Item().setUnlocalizedName("ingotStarglass").setCreativeTab(legendgearTab).setTextureName(MODID+":starglassAnim");
    	 GameRegistry.registerItem(starglassIngot, "ingotStarglass");
    	 
    	 starglass.customCraftingMaterial = starglassIngot;
    	 
    	 starglassSword = new StarglassSword();
    	 GameRegistry.registerItem(starglassSword, "starglassSword");
    	 MinecraftForge.EVENT_BUS.register(starglassSword);
    	 //starglassBlock = new StarglassBlock();
    	 //GameRegistry.registerBlock(starglassBlock,  "starGlass");
    	 
    	 emptyOrb = new StarglassOrb();
    	 GameRegistry.registerItem(emptyOrb, "emptyOrb");
    	 GameRegistry.addShapedRecipe(new ItemStack(emptyOrb,8), " X ", "X X", " X ", 'X', starglassIngot);
    	 
    	 dimensionalCatalyst = new DimensionalCatalyst();
    	 GameRegistry.registerItem(dimensionalCatalyst, "dimensionalCatalyst");
    	 GameRegistry.addShapelessRecipe(new ItemStack(dimensionalCatalyst, 8), new ItemStack(starDust, 1, 3), Items.ender_pearl);
    	 GameRegistry.addShapedRecipe(new ItemStack(Items.ender_pearl), "XXX","XOX", "XXX", 'X', dimensionalCatalyst, 'O', new ItemStack(emptyOrb, 1, 0));    	 
    	
    	 twinkleStaff = new StaffTwinkle();
    	 
    	 GameRegistry.registerItem(twinkleStaff, "twinkleStaff");
    	 
    	 
    	 
    	 if(CONFIG_ALLOW_TRINKETS)
    	 {
	    	 milkChocolate = new MilkChocolate();
	    	 GameRegistry.registerItem(milkChocolate, "milkChocolate");
	    	 milkChocolate.addRecipes();
	    	 
	    	 reedPipes = new ReedPipes();
	    	 GameRegistry.registerItem(reedPipes, "reedPipes");
	    	 reedPipes.addRecipes();
	    	 
	    	 caltropsBlock = new CaltropsBlock();
	    	 GameRegistry.registerBlock(caltropsBlock, ItemCaltropsBlock.class, "caltrops");
	    	 
	    	 caltropsBlock.addRecipes();
    	 }
    	 
    	 GameRegistry.registerFuelHandler(new DebugFuelTest());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
       // System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
       
      //  Configuration config = new Configuration(event.());

       // config.load();

        // Configuration goes here.
        	
      //  config.save();
    	int id = 0;
    	EntityRegistry.registerModEntity(EntityFallingStar.class, "fallingStar", id++, this, 128, 10, true);
    	//EntityRegistry.registerModEntity(EntityItemCaltrops.class, "entityItemCaltrops", id++, this, 128, 1, true);
    	EntityRegistry.registerModEntity(EntitySpellEffect.class, "entitySpellEffect", id++, this, 128, 1, true);
    	EntityRegistry.registerModEntity(SpellDecorator.class, "spellDecorator", id++, this, 128, 1, true);
    	
    	
    	
        GameRegistry.registerWorldGenerator(new AzurineGenerator(), 3);
        
        MinecraftForge.EVENT_BUS.register(new TestRendering());
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.registerRenderers();
    }
}
