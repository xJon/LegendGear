package nmccoy.legendgear;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import nmccoy.legendgear.block.AzuriteOreBlock;
import nmccoy.legendgear.block.CaltropsBlock;
import nmccoy.legendgear.block.FiligreedObsidianBlock;
import nmccoy.legendgear.block.FragstoneBlock;
import nmccoy.legendgear.block.InfusedStarstoneBlock;
import nmccoy.legendgear.block.StarAltar;
import nmccoy.legendgear.block.StarSandBlock;
import nmccoy.legendgear.block.StarglassBlock;
import nmccoy.legendgear.block.StarstoneBlock;
import nmccoy.legendgear.block.TileEntityStarstone;
import nmccoy.legendgear.entity.EntityFallingStar;
import nmccoy.legendgear.entity.EntityMagicBoomerang;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.EntityThrownOrb;
import nmccoy.legendgear.entity.SpellDecorator;
import nmccoy.legendgear.item.BadBow;
import nmccoy.legendgear.item.DimensionalCatalyst;
import nmccoy.legendgear.item.EmeraldShard;
import nmccoy.legendgear.item.ItemCaltropsBlock;
import nmccoy.legendgear.item.ItemMagicBoomerang;
import nmccoy.legendgear.item.MilkChocolate;
import nmccoy.legendgear.item.ReedPipes;
import nmccoy.legendgear.item.StaffFire;
import nmccoy.legendgear.item.StaffIce;
import nmccoy.legendgear.item.StaffTwinkle;
import nmccoy.legendgear.item.StaffZap;
import nmccoy.legendgear.item.StarDust;
import nmccoy.legendgear.item.StarglassOrb;
import nmccoy.legendgear.item.StarglassSword;
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
    public static int maxEmeraldGrassDropsBanked = 6;
    
    
    public static float emeraldAccumulationDistance = 16; 
    
    public static int minXpForEmeralds = 3;
    public static float emeraldsPerXP = 0.5f;
    public static float guaranteedEmeraldRatio = 0.75f;
    
    public static boolean CONFIG_ALLOW_TRINKETS = true;
    public static boolean CONFIG_ALLOW_EMERALD_DROPS = true;
    
    public static ToolMaterial starglass = EnumHelper.addToolMaterial("STARGLASS", 2, 13, 16, 2.0f, 0);
    public static ToolMaterial starsteel = EnumHelper.addToolMaterial("STARSTEEL", 2, 512, 6, 2.0f, 18);
    
    
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
    public static Item starsteelIngot;
    public static Item starsteelDust;
    
    
    public static StarglassSword starglassSword;
    public static StarglassOrb emptyOrb;
    public static DimensionalCatalyst dimensionalCatalyst;
    public static StaffTwinkle twinkleStaff;
    public static StaffFire fireStaff;
    public static StaffZap zapStaff;
    public static StaffIce iceStaff;
    public static ItemMagicBoomerang magicBoomerang;
    public static BadBow badBow;
    
    
    
    public static StarstoneBlock starstoneBlock;
    public static InfusedStarstoneBlock infusedStarstoneBlock;
    public static StarSandBlock starSandBlock;
    public static StarglassBlock starglassBlock;
    public static AzuriteOreBlock azuriteOreBlock;
    public static FiligreedObsidianBlock filigreedObsidianBlock;
    public static StarAltar starAltarBlock;
    public static FragstoneBlock fragstoneBlock;
    
    public static CaltropsBlock caltropsBlock;
   // public static ItemCaltropsBlock itemCaltropsBlock;
    
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    	PlayerEventHandler peh = new PlayerEventHandler();
    	MinecraftForge.EVENT_BUS.register(peh);
    	FMLCommonHandler.instance().bus().register(peh);
    	MinecraftForge.EVENT_BUS.register(new CustomAttributes());
    	
    	emeraldShard = new EmeraldShard();
    	GameRegistry.registerItem(emeraldShard, "emeraldShard"); 
    	
    	 FMLCommonHandler.instance().bus().register(emeraldShard);
    	 MinecraftForge.EVENT_BUS.register(emeraldShard);
    	 
    	 starstoneBlock = new StarstoneBlock();
    	 GameRegistry.registerBlock(starstoneBlock,  "starstoneBlock");
    	 
      	 infusedStarstoneBlock = new InfusedStarstoneBlock();
    	 GameRegistry.registerBlock(infusedStarstoneBlock,  "infusedStarstoneBlock");
    	 GameRegistry.registerTileEntity(TileEntityStarstone.class, "infusedStarstoneBlock");
    	 
    	 starDust = new StarDust();
    	 GameRegistry.registerItem(starDust, "starDust");
    	 
    	 
    	 starSandBlock = new StarSandBlock();
    	 GameRegistry.registerBlock(starSandBlock,  "starSand");
    	 
    	 fragstoneBlock = new FragstoneBlock();
    	 GameRegistry.registerBlock(fragstoneBlock, "fragstone");
    	 
    	 
    	filigreedObsidianBlock = new FiligreedObsidianBlock();
    	 GameRegistry.registerBlock(filigreedObsidianBlock,  "filigreedObsidian");
     	
    	 
    	 
    	 starAltarBlock = new StarAltar();
    	 GameRegistry.registerBlock(starAltarBlock, "starAltar");
    	 
    	 azuriteOreBlock = new AzuriteOreBlock();
    	 GameRegistry.registerBlock(azuriteOreBlock,  "azuriteOre");
    	 
    	 starglassIngot = new Item().setUnlocalizedName("ingotStarglass").setCreativeTab(legendgearTab).setTextureName(MODID+":starglassAnim");
    	 GameRegistry.registerItem(starglassIngot, "ingotStarglass");
    	 starsteelIngot = new Item().setUnlocalizedName("ingotStarsteel").setCreativeTab(legendgearTab).setTextureName(MODID+":starsteelAnim");
    	 GameRegistry.registerItem(starsteelIngot, "ingotStarsteel");
    	 starsteelDust = new Item().setUnlocalizedName("dustStarsteel").setCreativeTab(legendgearTab).setTextureName(MODID+":starsteelDustAnim");
    	 GameRegistry.registerItem(starsteelDust, "dustStarsteel");
    	 
    	 
    	 starglass.customCraftingMaterial = starglassIngot;
    	 
    	 starglassSword = new StarglassSword();
    	 GameRegistry.registerItem(starglassSword, "starglassSword");
    	 MinecraftForge.EVENT_BUS.register(starglassSword);
    	 //starglassBlock = new StarglassBlock();
    	 //GameRegistry.registerBlock(starglassBlock,  "starGlass");
    	 
    	 emptyOrb = new StarglassOrb();
    	 GameRegistry.registerItem(emptyOrb, "emptyOrb");
    	 
    	 
    	 dimensionalCatalyst = new DimensionalCatalyst();
    	 GameRegistry.registerItem(dimensionalCatalyst, "dimensionalCatalyst");
    	
    	 twinkleStaff = new StaffTwinkle();
    	 GameRegistry.registerItem(twinkleStaff, "twinkleStaff");
    	 fireStaff = new StaffFire();
    	 GameRegistry.registerItem(fireStaff, "fireStaff");
    	 zapStaff = new StaffZap();
    	 GameRegistry.registerItem(zapStaff, "zapStaff");
    	 iceStaff = new StaffIce();
    	 GameRegistry.registerItem(iceStaff, "iceStaff");
    	 magicBoomerang = new ItemMagicBoomerang();
    	 GameRegistry.registerItem(magicBoomerang, "magicBoomerang");
    	 
    	 
    	 
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
	    	 
	    	 badBow = new BadBow();
	    	 GameRegistry.registerItem(badBow, "badBow");
	    	 MinecraftForge.EVENT_BUS.register(badBow);
	    	 badBow.addRecipes();
    	 }
    	 starDust.addRecipes();
    	 starSandBlock.addRecipes();
    	 emeraldShard.addRecipes();
    	 GameRegistry.addShapedRecipe(new ItemStack(emptyOrb,8), " X ", "X X", " X ", 'X', starglassIngot);
    	 
    	 GameRegistry.addShapelessRecipe(new ItemStack(dimensionalCatalyst, 8), new ItemStack(starDust, 1, 3), Items.ender_pearl);
    	 GameRegistry.addShapedRecipe(new ItemStack(Items.ender_pearl), "XXX","XOX", "XXX", 'X', dimensionalCatalyst, 'O', new ItemStack(emptyOrb, 1, 0));    	 
    	 
    	 GameRegistry.addShapedRecipe(new ItemStack(magicBoomerang), "GSS", "S  ", "S  ", 'G', starglassIngot, 'S', starsteelIngot);
    	 GameRegistry.addShapedRecipe(new ItemStack(magicBoomerang), "SSG", "  S", "  S", 'G', starglassIngot, 'S', starsteelIngot);
    	 GameRegistry.addShapedRecipe(new ItemStack(magicBoomerang), "S  ", "S  ", "GSS", 'G', starglassIngot, 'S', starsteelIngot);
    	 GameRegistry.addShapedRecipe(new ItemStack(magicBoomerang), "  S", "  S", "SSG", 'G', starglassIngot, 'S', starsteelIngot);
    	 
    	 
    	 
    	 GameRegistry.addShapelessRecipe(new ItemStack(starsteelIngot), dimensionalCatalyst, Items.iron_ingot, new ItemStack(starDust, 1, 3));
    	 
    	 OreDictionary.registerOre("dustStar", new ItemStack(starDust, 1, 0));
    	 OreDictionary.registerOre("dustStarInfused", new ItemStack(starDust, 1, 3));
    	 OreDictionary.registerOre("gemStar", new ItemStack(starDust, 1, 1));
    	 OreDictionary.registerOre("gemStarInfused", new ItemStack(starDust, 1, 4));
    	 OreDictionary.registerOre("blockStar", new ItemStack(starDust, 1, 2));
    	 OreDictionary.registerOre("blockStarInfused", new ItemStack(starDust, 1, 5));
    	 OreDictionary.registerOre("lumpStarglass", starglassIngot);
    	 OreDictionary.registerOre("ingotStarsteel", starsteelIngot);
    	 OreDictionary.registerOre("dustStarsteel", starsteelDust);
    	     	 
    	 GameRegistry.addRecipe(new ShapelessOreRecipe(starsteelDust, "dustStarInfused", "dustIron"));
    	 GameRegistry.addSmelting(starsteelDust, new ItemStack(starsteelIngot), 0);
    	 
    	 emptyOrb.addRecipes();
    	 
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
    	EntityRegistry.registerModEntity(EntityThrownOrb.class, "thrownOrb", id++, this, 128, 5, true);
    	EntityRegistry.registerModEntity(EntityMagicBoomerang.class, "magicBoomerang", id++, this, 128, 10, true);
    	
    	
    	
        GameRegistry.registerWorldGenerator(new AzurineGenerator(), 3);
        
        
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.registerRenderers();
    }
}
