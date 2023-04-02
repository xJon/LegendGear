package nmccoy.legendgear;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import nmccoy.legendgear.blocks.BlockBombFlower;
import nmccoy.legendgear.blocks.BlockCaltrops;
import nmccoy.legendgear.blocks.BlockChained;
import nmccoy.legendgear.blocks.BlockGrindNode;
import nmccoy.legendgear.blocks.BlockJar;
import nmccoy.legendgear.blocks.BlockSkybeam;
import nmccoy.legendgear.blocks.BlockStarstone;
import nmccoy.legendgear.blocks.BlockSugarCube;
import nmccoy.legendgear.blocks.BlockSwordPedestal;
import nmccoy.legendgear.blocks.BlockSwordPedestalTechnical;
import nmccoy.legendgear.blocks.MysticShrub;
import nmccoy.legendgear.blocks.ShrubItemBlock;
import nmccoy.legendgear.blocks.TileEntityJar;
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
import nmccoy.legendgear.events.AugmentedSwordHandler;
import nmccoy.legendgear.events.EmeraldSoundHandler;
import nmccoy.legendgear.events.ForgeEventHooksHandler;
import nmccoy.legendgear.events.JumpNoticeHandler;
import nmccoy.legendgear.events.PacketHandler;
import nmccoy.legendgear.events.StarfallHandler;
import nmccoy.legendgear.items.AeroAmulet;
import nmccoy.legendgear.items.EarthMedallion;
import nmccoy.legendgear.items.EmeraldShard;
import nmccoy.legendgear.items.FireMedallion;
import nmccoy.legendgear.items.GeoAmulet;
import nmccoy.legendgear.items.HeartPickup;
import nmccoy.legendgear.items.ItemBlockChained;
import nmccoy.legendgear.items.ItemBomb;
import nmccoy.legendgear.items.ItemBombBag;
import nmccoy.legendgear.items.ItemCaptureEgg;
import nmccoy.legendgear.items.ItemHookshot;
import nmccoy.legendgear.items.ItemKey;
import nmccoy.legendgear.items.ItemMagicBoomerang;
import nmccoy.legendgear.items.ItemMagicPowder;
import nmccoy.legendgear.items.ItemMilkChocolate;
import nmccoy.legendgear.items.ItemRockCandy;
import nmccoy.legendgear.items.ItemSimplePipes;
import nmccoy.legendgear.items.ItemSlimeSword;
import nmccoy.legendgear.items.ItemStardust;
import nmccoy.legendgear.items.ItemTitanBand;
import nmccoy.legendgear.items.MagicMirror;
import nmccoy.legendgear.items.MysticSeed;
import nmccoy.legendgear.items.PhoenixFeather;
import nmccoy.legendgear.items.PyroAmulet;
import nmccoy.legendgear.items.Quiver;
import nmccoy.legendgear.items.WindMedallion;
import nmccoy.legendgear.recipes.MedallionSwordRecipe;
import nmccoy.legendgear.recipes.QuiverEmptyingRecipe;
import nmccoy.legendgear.recipes.QuiverFillingRecipe;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="LegendGear", name="Legend Gear", version="1.3.2")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"LegendGearJump"}, packetHandler=PacketHandler.class)
public class LegendGear {

	    // The instance of your mod that Forge uses.
        @Instance("LegendGear")
        public static LegendGear instance;
        
	    // Says where the client and server 'proxy' code is loaded.
	    @SidedProxy(clientSide="nmccoy.legendgear.client.ClientProxy", serverSide="nmccoy.legendgear.CommonProxy")
	    public static CommonProxy proxy;
	    
	    public static CreativeTabs creativeTab;
	    
	    public static boolean prismaticXP;
	    public static boolean antiGrinder;
	    
	    public static boolean magicMirrorAllowed;
	    private static int magicMirrorID;
	    public static Item magicMirror;
	    //public static int magicMirrorCost;
	    
	    public static boolean emeraldShardsAllowed;
	    private static int emeraldShardID;
	    public static Item emeraldShard;
	    public static int emeraldShardExchangeRate;
	    public static double emeraldDropMinMult;
	    public static double emeraldDropScale;
	    
	    public static boolean quiverAllowed;
	    private static int quiverID;
	    public static Item quiver;
	    public static int maxQuiverCapacity = 20;
	    
	    private static int heartPickupID;
	    public static Item heartPickup;
	    public static boolean heartsAllowed;
	    public static double heartMinMult;
	    public static double heartDropScale;
	    public static int maxHeartDrop = 10;
	    
	    public static boolean shrubsAllowed;
	    public static boolean shrubSuperPrizes;
	    public static double shrubJackpotChance;
	    private static int mysticShrubID;
	    public static Block mysticShrub;
	    public static double mysticShrubHeartChance;
	    public static double mysticShrubShardChance;
	    public static double mysticShrubArrowChance;
	    public static int shrubRarity;
	    private static int mysticSeedID;
	    public static Item mysticSeed;
	    public static int[] shrubDisabledBiomes;
	    public static boolean subtleShrubs = false;
	    public static double shrubGenStarChance;
	    
	    
	    private static int boostBlockID;
	   public static Block boostBlock;
	   private static int magicIceID;
	   public static Block magicIce;
	   
	   
	   public static boolean allowPFeather;
	   private static int phoenixID;
	   public static Item phoenixFeather;
	   
	   private static int captureEggItemID;
	   public static Item captureEggItem;
	   public static boolean allowCaptureEgg;
	    
	   public static boolean allowMedallions;
	   private static int earthMedallionID;
	   public static Item earthMedallion;
	   private static int geoAmuletID;
	   public static Item geoAmulet;
	   
	   private static int windMedallionID;
	   public static Item windMedallion;
	   private static int aeroAmuletID;
	   public static Item aeroAmulet;
	   
	   private static int fireMedallionID;
	   public static Item fireMedallion;
	   private static int pyroAmuletID;
	   public static Item pyroAmulet;
	   
	   private static int magicBoomerangID;
	   public static Item magicBoomerang;
	   public static int magicBoomerangDamage;
	   
	   public static boolean allowBombs;
	   private static int bombItemID;
	   public static Item bombItem;
	   public static int[] bombableBlocks;
	   public static int bombDamage;
	   
	   private static int bombBagID;
	   public static Item bombBag;
	   public static int maxBombBagCapacity;
	   
	   private static int bombFlowerID;
	   public static Block bombFlower;
	   
	   private static int titanBandID;
	   public static Item titanBand;
	   
	   private static int icarusWingsID;
	   public static Item icarusWings;
	   
	   private static int rockCandyID;
	   public static Item rockCandy;
	   
	   public static boolean allowCandy;
	   private static int sugarCubeID;
	   public static Block sugarCube;
	   
	   public static boolean enableBombArrows = true;
	   
	   
	   public static boolean debugShrubs = false;
	   
	   public static boolean enableCraftyLoading;
	   
	   public static int shrubcount = 0;
	   
	   private static int jarBlockID;
	   public static Block jarBlock;
	   
	   private static int itemHookshotID;
	   public static Item itemHookshot;
	   
	   private static int blockCaltropsID;
	   public static Block blockCaltrops;
	   
	   private static int blockPedestalID;
	   public static BlockSwordPedestal blockPedestal;
	   
	   private static int blockChainedID;
	   public static Block blockChained;
	   
	   private static int blockGrindNodeID;
	   public static Block blockGrindNode;
	   
	   private static int blockPedestalTechID;
	   public static Block blockPedestalTech;
	   
	   private static int itemKeyID;
	   public static Item itemKey;
	   
	   private static int itemStardustID;
	   public static Item itemStardust;
	   
	   public static boolean enableStarfall;
	   public static int starFallRarity = 20*60*7/2;
	   public static int fallenStarLifetime = 20*22;
	   
	   private static int itemMagicPowderID;
	   public static Item itemMagicPowder;
	   //public static int itemStarPieceID;
	   //public static Item itemStarPiece;
	   
	   
	   private static int itemSlimeSwordID;
	   public static Item itemSlimeSword;
	   
	   private static int blockStarstoneID;
	   public static Block blockStarstone;
	   
	   private static int blockSkybeamID;
	   public static Block blockSkybeam;
	   
	   private static int itemSimplePipesID;
	   public static Item itemSimplePipes;
	   
	   private static int itemMilkChocolateID;
	   public static Item itemMilkChocolate;
	   
	   public static int[] quiverLeakBows;
	   public static int[] alsoCountAsSwords;
	   public static int[] extraHookshotBlocks;
	   
	   
	   public static EnumArmorMaterial armorFeathers = EnumHelper.addArmorMaterial("FEATHERS", 11, new int[]{1, 3, 2, 1}, 15);
	   public static EnumToolMaterial toolMaterialSlimeball = EnumHelper.addToolMaterial("SLIME", 0, 131, 1.0f, 0, 18); 
	   
	   
	    @PreInit
        public void preInit(FMLPreInitializationEvent event) {
	    	 Configuration config = new Configuration(event.getSuggestedConfigurationFile());
	    	 System.out.println("Loaded config from "+event.getSuggestedConfigurationFile().getAbsolutePath());
	    	 // loading the configuration from its file
             config.load();
             
             //toggles
             prismaticXP = config.get("toggles", "PrismaticXP", true).getBoolean(true);
             antiGrinder = config.get("toggles", "AntiGrinder", false, "enable safeguard against emerald/heart drops in grinders").getBoolean(false);
             magicMirrorAllowed = config.get("toggles", "MagicMirrorAllowed", true).getBoolean(true);
             emeraldShardsAllowed = config.get("toggles", "EmeraldShardsAllowed", true).getBoolean(true);
             quiverAllowed = config.get("toggles", "QuiverAllowed", true).getBoolean(true);
             heartsAllowed = config.get("toggles", "HeartsAllowed", true, "Whether hearts will drop from monsters and mystic shrubs").getBoolean(true);
             shrubsAllowed = config.get("toggles", "MysticShrubAllowed", true, "will mystic shrubs exist in the world?").getBoolean(true);
             allowCaptureEgg = false;//config.get(Configuration.CATEGORY_GENERAL, "CaptureEggAllowed", false).getBoolean(false);
             shrubSuperPrizes = config.get("toggles", "MysticShrubSuperPrizes", true, "will mystic shrubs drop better items in a storm?").getBoolean(true);
             allowPFeather = config.get("toggles", "PhoenixFeatherAllowed", true).getBoolean(true);
             allowMedallions = config.get("toggles", "MedallionsAllowed", true).getBoolean(true);
             allowBombs = config.get("toggles", "BombsAllowed", true).getBoolean(true);
             allowCandy = config.get("toggles", "AllowCandy", true).getBoolean(true);
             enableCraftyLoading = config.get("toggles", "EnableCraftyLoading", false, "Can quivers and bomb bags be loaded/unloaded on a crafting grid? Buggy with non-standard crafting interfaces.").getBoolean(false);
             enableStarfall = config.get("toggles", "EnableStarfall", true).getBoolean(true);
             
             //settings
             
             starFallRarity = config.get(Configuration.CATEGORY_GENERAL, "StarfallRarity", 20*60*7/2).getInt();
      	   	 fallenStarLifetime = config.get(Configuration.CATEGORY_GENERAL, "FallenStarLifetime", 20*22).getInt();

             maxBombBagCapacity = config.get(Configuration.CATEGORY_GENERAL, "BombBagCapacity", 50).getInt();
             if(maxBombBagCapacity > 999) maxBombBagCapacity = 999;
             emeraldShardExchangeRate = config.get(Configuration.CATEGORY_GENERAL, "EmeraldShardExchangeRate", 8, "shards per piece, and pieces per emerald").getInt();
             maxQuiverCapacity = config.get(Configuration.CATEGORY_GENERAL, "QuiverMaxCapacity", 200).getInt();
             if(maxQuiverCapacity > 999) maxQuiverCapacity = 999;
             
             mysticShrubHeartChance = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubHeartChance", 0.2, "chance a non-charged shrub will drop a heart").getDouble(0.2);
             if(!heartsAllowed) mysticShrubHeartChance = 0;
             mysticShrubShardChance = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubShardChance", 0.2, "chance a non-charged shrub will drop an emerald shard").getDouble(0.2);
             if(!emeraldShardsAllowed) mysticShrubShardChance = 0;
             mysticShrubArrowChance = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubArrowChance", 0.2, "chance a non-charged shrub will drop an arrow").getDouble(0.2);
             shrubRarity = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubRarity", 8, "A shrub cluster will generate in 1 of every N chunks on average").getInt(8);
             shrubJackpotChance = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubJackpotChance", 0.05, "Chance of a glowing shrub dropping a special prize").getDouble(0.05);
             shrubDisabledBiomes = config.get(Configuration.CATEGORY_GENERAL, "ShrubDisabledBiomes", new int[]{2, 5, 8, 9, 10, 11, 12, 13, 17, 19}, "Mystic shrubs won't spawn in these biome IDs").getIntList();
             //subtleShrubs = config.get(Configuration.CATEGORY_GENERAL, "SubtleShrubs", true, "Should shrubs be blended with the local greenery?").getBoolean(true);
             shrubGenStarChance = config.get(Configuration.CATEGORY_GENERAL, "MysticShrubGenStarChance", 0.3, "Chance of a shrub cluster being generated in a star shape").getDouble(0.3);
             
             magicBoomerangDamage = config.get(Configuration.CATEGORY_GENERAL, "MagicBoomerangDamage", 6).getInt();
             bombDamage = config.get(Configuration.CATEGORY_GENERAL, "BombDamage", 5).getInt();
             
             
            
             //items
             magicMirrorID = config.getItem("MagicMirror", 24600).getInt();
             emeraldShardID = config.getItem("EmeraldShard", 24601).getInt();
             quiverID = config.getItem("Quiver", 24602).getInt();
             heartPickupID = config.getItem("HeartPickup", 24603).getInt();
             phoenixID = config.getItem("PhoenixFeather", 24604).getInt();
             captureEggItemID = config.getItem("CaptureEgg", 24605).getInt();
             mysticSeedID = config.getItem("MysticSeed", 24606).getInt();
             
             earthMedallionID = config.getItem("EarthMedallion", 24607).getInt();
             windMedallionID = config.getItem("WindMedallion", 24608).getInt();
             fireMedallionID = config.getItem("FireMedallion", 24609).getInt();
             
             aeroAmuletID = config.getItem("AeroAmulet", 24620).getInt();
             geoAmuletID = config.getItem("GeoAmulet", 24621).getInt();
             pyroAmuletID = config.getItem("PyroAmulet", 24622).getInt();
            
             titanBandID = config.getItem("TitanBand", 24626).getInt();
             
             magicBoomerangID = config.getItem("MagicBoomerang", 24630).getInt();
             rockCandyID = config.getItem("RockCandy", 24631).getInt();
             itemHookshotID = config.getItem("Hookshot", 24632).getInt();
             itemMilkChocolateID = config.getItem("MilkChocolate", 24633).getInt();
             
             bombItemID = config.getItem("ThrowableBomb", 24640).getInt();
             bombBagID = config.getItem("BombBag", 24641).getInt();
             
             itemKeyID = config.getItem("OneUseKey", 24650).getInt();
             itemStardustID = config.getItem("Stardust", 24651).getInt();
             itemMagicPowderID = config.getItem("MagicPowder", 24652).getInt();
             itemSimplePipesID = config.getItem("SimplePipes", 24653).getInt();
             
             icarusWingsID = config.getItem("IcarusWings", 24661).getInt();
             itemSlimeSwordID = config.getItem("SlimeSword", 24662).getInt();
             
             //blocks
             mysticShrubID = config.getBlock("MysticShrub", 2460).getInt();
             jarBlockID = config.getBlock("ClayJar", 2461).getInt();
             magicIceID = config.getBlock("MagicIce", 2462).getInt();
             sugarCubeID = config.getBlock("SugarCube", 2463).getInt();
             bombFlowerID = config.getBlock("BombFlower", 2464).getInt();
             blockCaltropsID = config.getBlock("Caltrops", 2465).getInt();
             blockPedestalID = config.getBlock("SwordPedestal", 2466).getInt();
             blockChainedID = config.getBlock("ChainedBlock", 2467).getInt();
             blockGrindNodeID = config.getBlock("GrindNode", 2468).getInt();
             blockPedestalTechID = config.getBlock("SwordPedestalTechnical", 2469).getInt();
             blockStarstoneID = config.getBlock("StarstoneBlock", 2470).getInt();             
             blockSkybeamID = config.getBlock("Skybeam", 2471).getInt();
             
             //arrays
             bombableBlocks = config.get(Configuration.CATEGORY_GENERAL, "BombableBlocks", new int[]{Block.cobblestone.blockID, Block.tnt.blockID, jarBlockID}, "These block IDs can be broken by bombs").getIntList();
             Arrays.sort(bombableBlocks);
             
             
             quiverLeakBows = config.get("compatibility", "QuiverLeakBows", new int[]{}, "Holding these items makes quivers 'leak' one arrow at a time (add 256 to config id values to get actual item ID)").getIntList();
             Arrays.sort(quiverLeakBows);
             
             alsoCountAsSwords = config.get("compatibility", "PedestalSwords", new int[]{}, "these item IDs will be treated by pedestals as swords (add 256 to config id values to get actual item ID)").getIntList();
             Arrays.sort(alsoCountAsSwords);
             
             extraHookshotBlocks = config.get("compatibility", "ExtraHookshotBlocks", new int[]{}, "add IDs of non-wooden blocks here to enable the hookshot to grab them").getIntList();
             Arrays.sort(extraHookshotBlocks);
             
             
             
             config.save();
             
             proxy.registerSounds();            
             
        }
        
        @Init
        public void load(FMLInitializationEvent event) {
        	
        	creativeTab = new CreativeTabLegendGear("lgTab");
        	LanguageRegistry.instance().addStringLocalization("itemGroup.lgTab", "en_US", "LegendGear");
        	
                MinecraftForge.EVENT_BUS.register(new ForgeEventHooksHandler());
                
                if(magicMirrorAllowed)
                {
                	magicMirror=new MagicMirror(magicMirrorID);
	                LanguageRegistry.addName(magicMirror, "Magic Mirror");
	                ItemStack goldStack = new ItemStack(Item.ingotGold);
	                ItemStack diamondStack = new ItemStack(Item.diamond);
	                ItemStack epStack = new ItemStack(Item.enderPearl);
	                
	                GameRegistry.addRecipe(new ItemStack(magicMirror), 
	                		"ege", "gdg", "ege", 'e', epStack, 'g', goldStack, 'd', diamondStack);
                }
                
                if(emeraldShardsAllowed)
                {
                	emeraldShard = new EmeraldShard(emeraldShardID);
                	
                	ItemStack shardStack = new ItemStack(emeraldShard, 1, 0);
                	ItemStack pieceStack = new ItemStack(emeraldShard, 1, 1);
                	ItemStack emeraldStack = new ItemStack(Item.emerald);
                	
                	LanguageRegistry.addName(shardStack, "Emerald Shard");
                	LanguageRegistry.addName(pieceStack, "Emerald Piece");
                	
                	GameRegistry.addRecipe(new ItemStack(emeraldShard, emeraldShardExchangeRate, 1), "e", 'e', emeraldStack);
                	GameRegistry.addRecipe(new ItemStack(emeraldShard, emeraldShardExchangeRate, 0), "p", 'p', pieceStack);
                	
                	GameRegistry.registerPickupHandler(new EmeraldSoundHandler());
                }	
                if(quiverAllowed)
                {
                	quiver = new Quiver(quiverID);
                	LanguageRegistry.addName(quiver, "Quiver");
                	GameRegistry.addRecipe(new ItemStack(quiver, 1, 1), "l l", "lal", " l ", 'l', new ItemStack(Item.leather), 'a', new ItemStack(Item.arrow));
                	
                	if(enableCraftyLoading)
                	{
	                	QuiverFillingRecipe qfr = new QuiverFillingRecipe(LegendGear.quiver, Item.arrow, maxQuiverCapacity);
	                	QuiverEmptyingRecipe qer = new QuiverEmptyingRecipe(LegendGear.quiver, Item.arrow);
	                	
	                	GameRegistry.addRecipe(qfr);
	                	GameRegistry.registerCraftingHandler(qfr);
	                	GameRegistry.addRecipe(qer);
	                	GameRegistry.registerCraftingHandler(qer);
                	}
                	else
                	{
                		QuiverEmptyingRecipe qer = new QuiverEmptyingRecipe(LegendGear.quiver, Item.arrow);
                		GameRegistry.addRecipe(qer);
                	}
                }
                
                if(heartsAllowed)
                {
		            heartPickup = new HeartPickup(heartPickupID);
		            LanguageRegistry.addName(heartPickup, "Heart");
                }
                
                if(shrubsAllowed)
                {
	                mysticShrub = new MysticShrub(mysticShrubID, Material.plants).setStepSound(Block.soundGrassFootstep);
	                mysticSeed = new MysticSeed(mysticSeedID, mysticShrub.blockID, Block.grass.blockID);
	        		GameRegistry.registerBlock(mysticShrub, ShrubItemBlock.class, "mysticShrubBlock");
	                
	        		LanguageRegistry.addName(mysticSeed, "Mystic Seed");
	                LanguageRegistry.addName(new ItemStack(mysticShrub,1, 0), "Mystic Shrub");
	                LanguageRegistry.addName(new ItemStack(mysticShrub,1, 1), "Energized Mystic Shrub");
	                GameRegistry.registerWorldGenerator(new ShrubGenerator());
                }
                
                if(allowPFeather)
                {
                	phoenixFeather = new PhoenixFeather(phoenixID);
                	LanguageRegistry.addName(phoenixFeather, "Phoenix Feather");
                	GameRegistry.addRecipe(new ItemStack(phoenixFeather), "tpt", "pfp", "tpt",
                			't', new ItemStack(Item.ghastTear), 'p', new ItemStack(Item.blazePowder), 'f', new ItemStack(Item.feather));
                }
                if(allowCaptureEgg)
                {
	                captureEggItem = new ItemCaptureEgg(captureEggItemID);
	            	LanguageRegistry.addName(captureEggItem, "Capture Egg");
	            	EntityRegistry.registerModEntity(EntityCaptureEgg.class, "captureEgg", 1, instance, 64, 10, true);
                }
                
                if(allowMedallions)
                {
	                earthMedallion = new EarthMedallion(earthMedallionID);
	            	LanguageRegistry.addName(earthMedallion, "Earth Medallion");
	            	EntityRegistry.registerModEntity(EntityEarthMedallion.class, "earthMedallion", 2, instance, 64, 10, true);
	            	EntityRegistry.registerModEntity(EntityQuake.class, "earthMedallionQuake", 3, instance, 64, 10, true);
	            	GameRegistry.addRecipe(new ItemStack(earthMedallion, 1, EarthMedallion.MAX_DAMAGE-1), "gbg", "bdb", "gbg", 'g', Item.goldNugget, 'b', Item.brick, 'd', Block.dirt);
	            	
	            	windMedallion = new WindMedallion(windMedallionID);
	            	LanguageRegistry.addName(windMedallion, "Wind Medallion");
	            	EntityRegistry.registerModEntity(EntityWindMedallion.class, "windMedallion", 4, instance, 64, 10, true);
	            	EntityRegistry.registerModEntity(EntityArrowStorm.class, "windMedallionStorm", 5, instance, 64, 10, true);
	            	GameRegistry.addRecipe(new ItemStack(windMedallion, 1, WindMedallion.MAX_DAMAGE-1), "gbg", "bab", "gbg", 'g', Item.goldNugget, 'b', Item.brick, 'a', Item.arrow);
	            	
	            	fireMedallion = new FireMedallion(fireMedallionID);
	            	LanguageRegistry.addName(fireMedallion, "Fire Medallion");
	            	EntityRegistry.registerModEntity(EntityFireMedallion.class, "fireMedallion", 9, instance, 64, 10, true);
	            	EntityRegistry.registerModEntity(EntityFireblast.class, "fireMedallionBlast", 10, instance, 64, 10, true);
	            	GameRegistry.addRecipe(new ItemStack(fireMedallion, 1, FireMedallion.MAX_DAMAGE-1), "gbg", "bcb", "gbg", 'g', Item.goldNugget, 'b', Item.brick, 'c', Item.coal);
	            	GameRegistry.addRecipe(new ItemStack(fireMedallion, 1, FireMedallion.MAX_DAMAGE-1), "gbg", "bcb", "gbg", 'g', Item.goldNugget, 'b', Item.brick, 'c', new ItemStack(Item.coal, 1, 1));
	            	
	            	
	            	aeroAmulet = new AeroAmulet(aeroAmuletID);
	            	LanguageRegistry.addName(aeroAmulet, "Aero Amulet");
	            	GameRegistry.addRecipe(new ItemStack(aeroAmulet, 1, 0), " g ", "g g", " m ", 'g', Item.ingotGold, 'm', LegendGear.windMedallion);
	            	
	            	geoAmulet = new GeoAmulet(geoAmuletID);
	            	LanguageRegistry.addName(geoAmulet, "Geo Amulet");
	            	GameRegistry.addRecipe(new ItemStack(geoAmulet, 1, 0), " g ", "g g", " m ", 'g', Item.ingotGold, 'm', LegendGear.earthMedallion);
	            	
	            	pyroAmulet = new PyroAmulet(pyroAmuletID);
	            	LanguageRegistry.addName(pyroAmulet, "Pyro Amulet");
	            	GameRegistry.addRecipe(new ItemStack(pyroAmulet, 1, 0), " g ", "g g", " m ", 'g', Item.ingotGold, 'm', LegendGear.fireMedallion);
	            	
	            	
	            	titanBand = new ItemTitanBand(titanBandID);
	            	LanguageRegistry.addName(titanBand, "Titan Band");
	            	GameRegistry.addRecipe(new ItemStack(titanBand), "glg", "lml", "glg", 'g', Item.ingotGold, 'l', Item.leather, 'm', LegendGear.earthMedallion);
	      
	            	MinecraftForge.EVENT_BUS.register(titanBand);
	            	
	            	GameRegistry.addRecipe(new MedallionSwordRecipe());
	            	
	            	MinecraftForge.EVENT_BUS.register(new AugmentedSwordHandler());
	            	EntityRegistry.registerModEntity(EntityWhirlwind.class, "whirlwind", 11, instance, 64, 10, true);
                }
                
                magicBoomerang = new ItemMagicBoomerang(magicBoomerangID);
                LanguageRegistry.addName(magicBoomerang, "Magic Boomerang");
                GameRegistry.addRecipe(new ItemStack(magicBoomerang), "dgg", "g  ", "g  ", 'g', Item.ingotGold, 'd', Item.diamond);
                GameRegistry.addRecipe(new ItemStack(magicBoomerang), "ggd", "  g", "  g", 'g', Item.ingotGold, 'd', Item.diamond);
                GameRegistry.addRecipe(new ItemStack(magicBoomerang), "  g", "  g", "ggd", 'g', Item.ingotGold, 'd', Item.diamond);
                GameRegistry.addRecipe(new ItemStack(magicBoomerang), "g  ", "g  ", "dgg", 'g', Item.ingotGold, 'd', Item.diamond);
                
                
                EntityRegistry.registerModEntity(EntityMagicBoomerang.class, "magicBoomerang", 6, instance, 64, 5, true);
                
                itemHookshot = new ItemHookshot(itemHookshotID);
                LanguageRegistry.addName(itemHookshot, "Hookshot");
                EntityRegistry.registerModEntity(EntityShotHook.class, "hookshotHook", 12, instance, 64, 5, true);
                
                GameRegistry.addRecipe(new ItemStack(itemHookshot), " ii", " fi", "d  ", 'i', Item.ingotIron, 'f', Item.fishingRod, 'd', Block.dispenser);
                
               // magicIce = new BlockMagicIce(magicIceID, Material.ice).setStepSound(Block.soundGlassFootstep);
               // GameRegistry.registerBlock(magicIce, ItemBlock.class, "blockMagicIce");
               // LanguageRegistry.addName(new ItemStack(magicIce), "Magic Ice");
                if(allowBombs)
                {
	                bombItem = new ItemBomb(bombItemID);
	                LanguageRegistry.addName(bombItem, "Bomb");
	                EntityRegistry.registerModEntity(EntityBomb.class, "throwableBomb", 7, instance, 64, 60, true);
	                
	                bombBag = new ItemBombBag(bombBagID);
	                LanguageRegistry.addName(bombBag, "Bomb Bag");
	                
	                if(enableCraftyLoading)
	                {
		                QuiverFillingRecipe qfr = new QuiverFillingRecipe(LegendGear.bombBag, LegendGear.bombItem, maxBombBagCapacity);
	                	QuiverEmptyingRecipe qer = new QuiverEmptyingRecipe(LegendGear.bombBag, LegendGear.bombItem);
	                	
	                	GameRegistry.addRecipe(qfr);
	                	GameRegistry.registerCraftingHandler(qfr);
	                	GameRegistry.addRecipe(qer);
	                	GameRegistry.registerCraftingHandler(qer);        
	                }
	                else
	                {
	                	QuiverEmptyingRecipe qer = new QuiverEmptyingRecipe(LegendGear.bombBag, LegendGear.bombItem);
	                	GameRegistry.addRecipe(qer);
	                }
                	//GameRegistry.addShapelessRecipe(new ItemStack(bombItem), Block.gravel, Item.gunpowder);
                	GameRegistry.addRecipe(new ItemStack(bombBag, 1, 1), "l l", "lbl", "lll", 'l', Item.leather, 'b', bombItem);
                	
                	
                	EntityRegistry.registerModEntity(EntityBombBlast.class, "bombExplosion", 8, instance, 64, 10, true);
	                
                	bombFlower = new BlockBombFlower(bombFlowerID, Material.plants);
                	GameRegistry.registerBlock(bombFlower, ItemBlock.class, "blockBombFlower");
	                LanguageRegistry.addName(new ItemStack(bombFlower), "Bomb Flower");
	                
	                GameRegistry.registerWorldGenerator(new BombFlowerGenerator());
	                
	                BlockDispenser.dispenseBehaviorRegistry.putObject(bombItem, bombItem);
                }
                
                if(allowCandy)
                {
	                rockCandy = new ItemRockCandy(rockCandyID);
	                LanguageRegistry.addName(new ItemStack(rockCandy, 1, 1), "Redstone Rock Candy");
	                LanguageRegistry.addName(new ItemStack(rockCandy, 1, 3), "Emerald Rock Candy");
	                LanguageRegistry.addName(new ItemStack(rockCandy, 1, 2), "Lapis Rock Candy");
	                LanguageRegistry.addName(new ItemStack(rockCandy, 1, 4), "Diamond Rock Candy");
	                
	                sugarCube = new BlockSugarCube(sugarCubeID, Material.cake);
	                GameRegistry.registerBlock(sugarCube, ItemBlock.class, "blockSugarCube");
	                LanguageRegistry.addName(new ItemStack(sugarCube), "Sugar Cube");
	                
	                GameRegistry.addRecipe(new ItemStack(sugarCube), "sss", "sss", "sss", 's', Item.sugar);
	                GameRegistry.addShapelessRecipe(new ItemStack(Item.sugar, 9), LegendGear.sugarCube);
	                
	                GameRegistry.addShapelessRecipe(new ItemStack(rockCandy, 1, 1), 
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		Item.stick, Item.redstone, Item.bucketWater);
	                
	                GameRegistry.addShapelessRecipe(new ItemStack(rockCandy, 1, 3), 
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		Item.stick, Item.emerald, Item.bucketWater);
	                
	                GameRegistry.addShapelessRecipe(new ItemStack(rockCandy, 1, 2), 
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		Item.stick, new ItemStack(Item.dyePowder, 1, 4), Item.bucketWater);
	                
	                GameRegistry.addShapelessRecipe(new ItemStack(rockCandy, 1, 4), 
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		LegendGear.sugarCube, LegendGear.sugarCube, LegendGear.sugarCube,
	                		Item.stick, Item.diamond, Item.bucketWater);
	                
	                itemMilkChocolate = new ItemMilkChocolate(itemMilkChocolateID);
	                LanguageRegistry.addName(itemMilkChocolate, "Milk Chocolate");
	                GameRegistry.addShapelessRecipe(new ItemStack(itemMilkChocolate), Item.bucketMilk, new ItemStack(Item.dyePowder, 1, 3), Item.sugar);
                }
                
                jarBlock = new BlockJar(jarBlockID);
                GameRegistry.registerBlock(jarBlock, ItemBlock.class, "blockClayJar");
                LanguageRegistry.addName(new ItemStack(jarBlock), "Clay Jar");
                GameRegistry.registerTileEntity(TileEntityJar.class, "tileEntityJar");
                GameRegistry.addRecipe(new ItemStack(jarBlock, 16), "b b", "b b", "bbb", 'b', Item.brick);
                
                
                
                blockCaltrops = new BlockCaltrops(blockCaltropsID);
                GameRegistry.registerBlock(blockCaltrops, ItemBlock.class, "blockCaltrops");
                LanguageRegistry.addName(new ItemStack(blockCaltrops), "Caltrops");
                GameRegistry.addRecipe(new ItemStack(blockCaltrops, 4), " i ", " i ", "i i", 'i', Item.ingotIron);
                
                blockPedestal = new BlockSwordPedestal(blockPedestalID);
                GameRegistry.registerBlock(blockPedestal, ItemBlock.class, "blockPedestal");
                LanguageRegistry.addName(new ItemStack(blockPedestal), "Sword Pedestal");
                GameRegistry.registerTileEntity(TileEntityPedestal.class, "tileEntityPedestal");
                GameRegistry.addRecipe(new ItemStack(blockPedestal), " s ", "sss", 's', Block.stone);
                blockPedestalTech = new BlockSwordPedestalTechnical(blockPedestalTechID);
                GameRegistry.registerBlock(blockPedestalTech, ItemBlock.class, "blockPedestalTechnical");
                
                
                blockChained = new BlockChained(blockChainedID);
                GameRegistry.registerBlock(blockChained, ItemBlockChained.class, "blockChained");
                LanguageRegistry.addName(new ItemStack(blockChained), "Chained Block");
                LanguageRegistry.addName(new ItemStack(blockChained, 1, 2), "Iron Lock");
                LanguageRegistry.addName(new ItemStack(blockChained, 1, 3), "Gold Lock");
                LanguageRegistry.addName(new ItemStack(blockChained, 1, 4), "Diamond Lock");
                
                
                
                itemKey = new ItemKey(itemKeyID);
                LanguageRegistry.addName(new ItemStack(itemKey,1, 0), "Iron Key");
                LanguageRegistry.addName(new ItemStack(itemKey,1, 1), "Gold Key");
                LanguageRegistry.addName(new ItemStack(itemKey,1, 2), "Diamond Key");
                
                itemStardust = new ItemStardust(itemStardustID);
                LanguageRegistry.addName(new ItemStack(itemStardust, 1, 0), "Stardust");
                LanguageRegistry.addName(new ItemStack(itemStardust, 1, 1), "Star Piece");
                LanguageRegistry.addName(new ItemStack(itemStardust, 1, 2), "Starstone");
                
                GameRegistry.addShapelessRecipe(new ItemStack(itemStardust, 3, 0), new ItemStack(itemStardust, 1, 1));
                GameRegistry.addShapelessRecipe(new ItemStack(itemStardust, 9, 1), new ItemStack(itemStardust, 2, 2));
                GameRegistry.addRecipe(new ItemStack(itemStardust, 1, 2), "sss", "sss", "sss", 's', new ItemStack(itemStardust, 1, 1));
                
                itemMagicPowder = new ItemMagicPowder(itemMagicPowderID);
                LanguageRegistry.addName(itemMagicPowder, "Magic Powder");
                GameRegistry.addShapelessRecipe(new ItemStack(itemMagicPowder), new ItemStack(itemStardust, 1, 0), Block.mushroomBrown);
                GameRegistry.addShapelessRecipe(new ItemStack(itemMagicPowder), new ItemStack(itemStardust, 1, 0), Block.mushroomRed);
                
                itemSimplePipes = new ItemSimplePipes(itemSimplePipesID);
                LanguageRegistry.addName(itemSimplePipes, "Reed Pipes");
                GameRegistry.addRecipe(new ItemStack(itemSimplePipes), "rrr", "rr ", "r  ", 'r', Item.reed);
                
                blockSkybeam = new BlockSkybeam(blockSkybeamID);
                GameRegistry.registerBlock(blockSkybeam, ItemBlock.class, "blockSkybeam");
                LanguageRegistry.addName(blockSkybeam, "Skybeam Block");
                GameRegistry.registerTileEntity(TileEntitySkybeam.class, "tileEntitySkybeam");
                GameRegistry.addRecipe(new ItemStack(blockSkybeam), "ooo", "oso", "ooo", 'o', Block.obsidian, 's', new ItemStack(itemStardust, 1, 2));
                
                MinecraftForge.EVENT_BUS.register(itemMagicPowder);
                
                blockStarstone = new BlockStarstone(blockStarstoneID);
                GameRegistry.registerBlock(blockStarstone, ItemBlock.class, "blockStarstone");
                LanguageRegistry.addName(blockStarstone, "Starstone");
                GameRegistry.registerTileEntity(TileEntityStarstone.class, "tileEntityStarstone");
           
                
                toolMaterialSlimeball.customCraftingMaterial = Item.slimeBall;
                itemSlimeSword = new ItemSlimeSword(itemSlimeSwordID);
                LanguageRegistry.addName(itemSlimeSword, "Slime Sword");
                GameRegistry.addRecipe(new ItemStack(itemSlimeSword), "b", "b", "s", 'b', Item.slimeBall, 's', Item.stick);
                
                
                blockGrindNode = new BlockGrindNode(blockGrindNodeID);
                GameRegistry.registerBlock(blockGrindNode, ItemBlock.class, "grindNode");
                LanguageRegistry.addName(new ItemStack(blockGrindNode), "Starbeam Torch");
                GameRegistry.addRecipe(new ItemStack(blockGrindNode, 8), " g ", "gdg", " i ", 'g', itemStardust, 'd', Item.diamond, 'i', Item.ingotGold);
                
                EntityRegistry.registerModEntity(EntityGrindStar.class, "grindStar", 13, instance, 64, 5, true);
                
                EntityRegistry.registerModEntity(EntityFallingStar.class, "fallingStar", 14, instance, 128, 10, true);
                
                MinecraftForge.EVENT_BUS.register(new StarfallHandler());
                
             
                MinecraftForge.EVENT_BUS.register(new JumpNoticeHandler());
//                armorFeathers.customCraftingMaterial = Item.feather;
//                icarusWings = new ItemIcarusWings(icarusWingsID);
//                LanguageRegistry.addName(icarusWings, "Icarus Wing Armor");
//                MinecraftForge.EVENT_BUS.register(icarusWings);
                
                proxy.registerRenderers();	
//                boostBlock = new BoostBlock(boostBlockID, Material.rock);
//                GameRegistry.registerBlock(boostBlock, ItemBoostBlock.class, "boostBlock");
//                LanguageRegistry.addName(new ItemStack(boostBlock,1, 0), "Speed Boost Block");
//                LanguageRegistry.addName(new ItemStack(boostBlock,1, 1), "Jump Boost Block");
//                LanguageRegistry.addName(new ItemStack(boostBlock,1, 2), "Regen Boost Block");
                
                
                
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        	ThaumcraftApi.registerObjectTag(itemStardust.itemID, 0, new ObjectTags().add(EnumTag.LIGHT, 2).add(EnumTag.MAGIC, 1).add(EnumTag.POWER, 2));
        	ThaumcraftApi.registerObjectTag(itemStardust.itemID, 1, new ObjectTags().add(EnumTag.LIGHT, 6).add(EnumTag.MAGIC, 4).add(EnumTag.POWER, 6).add(EnumTag.PURE, 2));
        	
        	if(quiverAllowed) ThaumcraftApi.registerComplexObjectTag(quiver.itemID, -1, new ObjectTags().add(EnumTag.BEAST, 8).add(EnumTag.FLESH, 8).add(EnumTag.CLOTH, 4).add(EnumTag.VOID, 1));
        	
        	ThaumcraftApi.registerComplexObjectTag(blockGrindNode.blockID, -1, new ObjectTags().add(EnumTag.MOTION, 4).add(EnumTag.MAGIC, 2).add(EnumTag.LIGHT, 2).add(EnumTag.CRYSTAL, 1).add(EnumTag.VALUABLE, 1));
        	if(allowPFeather) ThaumcraftApi.registerObjectTag(phoenixFeather.itemID, -1, new ObjectTags().add(EnumTag.FIRE, 8).add(EnumTag.EXCHANGE, 4).add(EnumTag.HEAL, 8).add(EnumTag.MAGIC, 8).add(EnumTag.FLIGHT, 2));
        	if(allowBombs) 
        	{
        		ThaumcraftApi.registerObjectTag(bombItem.itemID, -1, new ObjectTags().add(EnumTag.FIRE, 4).add(EnumTag.DESTRUCTION, 4).add(EnumTag.WEAPON, 2));
        		ThaumcraftApi.registerComplexObjectTag(bombBag.itemID, -1, new ObjectTags().add(EnumTag.BEAST, 11).add(EnumTag.FLESH, 11).add(EnumTag.CLOTH, 5).add(EnumTag.VOID, 1));
        		ThaumcraftApi.registerObjectTag(bombFlower.blockID, -1, new ObjectTags().add(EnumTag.FIRE, 4).add(EnumTag.DESTRUCTION, 4).add(EnumTag.FLOWER, 2));
        	}
        	ThaumcraftApi.registerComplexObjectTag(blockCaltrops.blockID, -1, new ObjectTags().add(EnumTag.WEAPON, 1).add(EnumTag.TRAP, 1).add(EnumTag.METAL, 4));
        	ThaumcraftApi.registerComplexObjectTag(magicBoomerang.itemID, -1, new ObjectTags().add(EnumTag.WEAPON, 8).add(EnumTag.FLIGHT, 4).add(EnumTag.MAGIC, 4).add(EnumTag.METAL, 26).add(EnumTag.VALUABLE, 15));
        	ThaumcraftApi.registerComplexObjectTag(jarBlock.blockID, -1,  new ObjectTags().add(EnumTag.VOID, 1).add(EnumTag.EARTH, 1));
        	if(allowMedallions)
        	{
        		ThaumcraftApi.registerComplexObjectTag(fireMedallion.itemID, -1, new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.FIRE, 4).add(EnumTag.EXCHANGE, 2).add(EnumTag.VALUABLE, 1));
        		ThaumcraftApi.registerComplexObjectTag(windMedallion.itemID, -1, new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.WIND, 4).add(EnumTag.EXCHANGE, 2).add(EnumTag.VALUABLE, 1));
        		ThaumcraftApi.registerComplexObjectTag(earthMedallion.itemID, -1, new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.EARTH, 4).add(EnumTag.EXCHANGE, 2).add(EnumTag.VALUABLE, 1));
        		
        		ThaumcraftApi.registerComplexObjectTag(aeroAmulet.itemID, -1, new ObjectTags().add(EnumTag.ARMOR, 4).add(EnumTag.MAGIC, 4).add(EnumTag.VALUABLE, 7));
        		ThaumcraftApi.registerComplexObjectTag(geoAmulet.itemID, -1, new ObjectTags().add(EnumTag.ARMOR, 4).add(EnumTag.MAGIC, 4).add(EnumTag.VALUABLE, 7));
        		ThaumcraftApi.registerComplexObjectTag(pyroAmulet.itemID, -1, new ObjectTags().add(EnumTag.ARMOR, 4).add(EnumTag.MAGIC, 4).add(EnumTag.VALUABLE, 7));
        	}
        	if(shrubsAllowed)
        	{
        		ThaumcraftApi.registerObjectTag(mysticSeed.itemID, -1, new ObjectTags().add(EnumTag.PLANT, 1).add(EnumTag.EXCHANGE, 1).add(EnumTag.VALUABLE, 4).add(EnumTag.MAGIC, 8));
        	}
        	
        	
	   }
}
