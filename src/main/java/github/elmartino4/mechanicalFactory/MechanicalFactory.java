package github.elmartino4.mechanicalFactory;

import github.elmartino4.mechanicalFactory.config.ModConfig;
import github.elmartino4.mechanicalFactory.util.BlockOrFluid;
import github.elmartino4.mechanicalFactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalFactory.util.SieveIdentifier;

import github.elmartino4.mechanicalFactory.util.WeatheringMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;

public class MechanicalFactory implements ModInitializer {
	public static HashMap<List<Block>, List<Block>> anvilMap = new HashMap<>();
	public static HashMap<List<BlockOrFluid>, List<Block>> specialAnvilMap = new HashMap<>();

	public static ArrayList<GeneratorIdentifier> generatorMap = new ArrayList<>();
	public static WeatheringMap weatheringMap = new WeatheringMap();

	public static HashMap<Item, SieveIdentifier> sieveMap = new HashMap<>();

	@Override
	public void onInitialize() {
		System.out.println("Loaded Mechanical Factory");
		ModConfig.init();

		initAnvilMap();
		initGeneratorMap();
		initWeatherMap();
		initSieveMap();
	}

	private static void initAnvilMap(){
		anvilMap.put(Arrays.asList(Blocks.STONE), Arrays.asList(Blocks.COBBLESTONE));
		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE), Arrays.asList(Blocks.GRAVEL));
		anvilMap.put(Arrays.asList(Blocks.MOSSY_COBBLESTONE), Arrays.asList(Blocks.GRAVEL));
		anvilMap.put(Arrays.asList(Blocks.SANDSTONE), Arrays.asList(Blocks.SAND));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL), Arrays.asList(Blocks.SAND));

		anvilMap.put(Arrays.asList(Blocks.STONE, Blocks.GRAVEL), Arrays.asList(Blocks.ANDESITE));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL, Blocks.STONE), Arrays.asList(Blocks.ANDESITE));

		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE, Blocks.GRAVEL), Arrays.asList(Blocks.TUFF));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL, Blocks.COBBLESTONE), Arrays.asList(Blocks.TUFF));

		anvilMap.put(Arrays.asList(Blocks.TUFF, Blocks.GRAVEL), Arrays.asList(Blocks.DEEPSLATE));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL, Blocks.TUFF), Arrays.asList(Blocks.DEEPSLATE));

		anvilMap.put(Arrays.asList(Blocks.NETHERRACK, Blocks.SOUL_SOIL), Arrays.asList(Blocks.SOUL_SOIL, Blocks.SOUL_SAND));

		anvilMap.put(Arrays.asList(Blocks.STONE_BRICKS), Arrays.asList(Blocks.CRACKED_STONE_BRICKS));
		anvilMap.put(Arrays.asList(Blocks.DEEPSLATE_BRICKS), Arrays.asList(Blocks.CRACKED_DEEPSLATE_BRICKS));

		anvilMap.put(Arrays.asList(Blocks.ANDESITE, Blocks.SAND), Arrays.asList(Blocks.CALCITE));
		anvilMap.put(Arrays.asList(Blocks.SAND, Blocks.ANDESITE), Arrays.asList(Blocks.CALCITE));

		anvilMap.put(Arrays.asList(Blocks.STONE, Blocks.SAND), Arrays.asList(Blocks.DIORITE));
		anvilMap.put(Arrays.asList(Blocks.SAND, Blocks.STONE), Arrays.asList(Blocks.DIORITE));

		anvilMap.put(Arrays.asList(Blocks.STONE, Blocks.GRAVEL), Arrays.asList(Blocks.ANDESITE));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL, Blocks.STONE), Arrays.asList(Blocks.ANDESITE));

		anvilMap.put(Arrays.asList(Blocks.STONE, Blocks.RED_SAND), Arrays.asList(Blocks.GRANITE));
		anvilMap.put(Arrays.asList(Blocks.RED_SAND, Blocks.STONE), Arrays.asList(Blocks.GRANITE));

		anvilMap.put(Arrays.asList(Blocks.MOSSY_COBBLESTONE, Blocks.ICE), Arrays.asList(Blocks.PRISMARINE));
		anvilMap.put(Arrays.asList(Blocks.ICE, Blocks.MOSSY_COBBLESTONE), Arrays.asList(Blocks.PRISMARINE));

		anvilMap.put(Arrays.asList(Blocks.FROSTED_ICE, Blocks.FROSTED_ICE, Blocks.FROSTED_ICE), Arrays.asList(Blocks.ICE));
		anvilMap.put(Arrays.asList(Blocks.ICE, Blocks.ICE, Blocks.ICE), Arrays.asList(Blocks.PACKED_ICE));
		anvilMap.put(Arrays.asList(Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.PACKED_ICE), Arrays.asList(Blocks.BLUE_ICE));

		specialAnvilMap.put(Arrays.asList(new BlockOrFluid(Fluids.FLOWING_LAVA), new BlockOrFluid(Blocks.NETHERRACK)), Arrays.asList(Blocks.MAGMA_BLOCK));
		specialAnvilMap.put(Arrays.asList(new BlockOrFluid(Fluids.LAVA), new BlockOrFluid(Blocks.SAND)), Arrays.asList(Blocks.RED_SAND));
		specialAnvilMap.put(Arrays.asList(new BlockOrFluid(Fluids.FLOWING_WATER), new BlockOrFluid(Blocks.CLAY)), Arrays.asList(Blocks.CLAY));

		List<BlockOrFluid> list = Arrays.asList(new BlockOrFluid(Fluids.FLOWING_LAVA), new BlockOrFluid(Blocks.NETHERRACK));
		System.out.println(list + " @ " + list.hashCode());
	}

	private static void initGeneratorMap(){
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_WATER, null, Blocks.BLUE_ICE, null, Blocks.FROSTED_ICE));
		generatorMap.add(new GeneratorIdentifier(Fluids.WATER, null, Blocks.BLUE_ICE, null, Blocks.ICE));

		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA, Fluids.FLOWING_WATER, null, Blocks.PURPUR_BLOCK, Blocks.END_STONE));
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA, Fluids.WATER, null, Blocks.PURPUR_BLOCK, Blocks.END_STONE));

		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA, Fluids.FLOWING_WATER, null, Blocks.NETHER_BRICKS, Blocks.NETHERRACK));
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA, Fluids.WATER, null, Blocks.NETHER_BRICKS, Blocks.NETHERRACK));
	}

	private static void initWeatherMap(){
		weatheringMap.put(Blocks.COBBLESTONE, Fluids.WATER, Blocks.MOSSY_COBBLESTONE, 0.7F);
		weatheringMap.put(Blocks.COBBLESTONE, Fluids.FLOWING_WATER, Blocks.MOSSY_COBBLESTONE, 0.3F);

		weatheringMap.put(Blocks.OAK_LOG, Fluids.FLOWING_LAVA, Blocks.COAL_BLOCK, 0.9F);
		weatheringMap.put(Blocks.OAK_LOG, Fluids.LAVA, Blocks.COAL_BLOCK, 1.0F);

		weatheringMap.put(Blocks.STONE_BRICKS, Fluids.WATER, Blocks.MOSSY_STONE_BRICKS, 0.7F);
		weatheringMap.put(Blocks.STONE_BRICKS, Fluids.FLOWING_WATER, Blocks.MOSSY_STONE_BRICKS, 0.3F);
	}

	private static void initSieveMap(){
		SieveIdentifier temp = new SieveIdentifier(25, 70);

		temp.put(10, 1, 2, Items.PURPUR_BLOCK);
		temp.put(3, 1, 1, Items.CHORUS_FRUIT);
		temp.put(20, 1, 5, Items.CHORUS_FRUIT);
		temp.put(15, 1, 5, Items.POPPED_CHORUS_FRUIT);
		temp.put(1, 1, 3, Items.SHULKER_SHELL);
		temp.put(2, 2, 2, Items.PHANTOM_MEMBRANE);

		sieveMap.put(Items.END_STONE, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(6, 70);

		temp.put(3, 1, 3, Items.QUARTZ);
		temp.put(3, 2, 6, Items.GOLD_NUGGET);
		temp.put(7, 1, 10, Items.ROTTEN_FLESH);
		temp.put(2, 1, 2, Items.GOLD_INGOT);

		sieveMap.put(Items.NETHERRACK, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(8, 40);

		temp.put(4, 1, 1, Items.CRIMSON_FUNGUS);
		temp.put(4, 1, 1, Items.WARPED_FUNGUS);
		temp.put(1, 1, 3, Items.GHAST_TEAR);
		temp.put(3, 2, 5, Items.NETHER_WART);

		sieveMap.put(Items.SOUL_SAND, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(12, 50);

		temp.put(8, 2, 3, Items.WHEAT_SEEDS);
		temp.put(5, 2, 3, Items.BEETROOT_SEEDS);
		temp.put(4, 1, 3, Items.MELON_SEEDS);
		temp.put(4, 1, 3, Items.PUMPKIN_SEEDS);
		temp.put(5, 1, 2, Items.POTATO);
		temp.put(5, 1, 2, Items.CARROT);
		temp.put(9, 2, 4, Items.BONE_MEAL);

		sieveMap.put(Items.DIRT, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(12, 40);

		temp.put(14, 1, 1, Items.SAND);
		temp.put(3, 1, 1, Items.REDSTONE);
		temp.put(2, 1, 3, Items.GUNPOWDER);
		temp.put(3, 2, 4, Items.GLOWSTONE_DUST);
		temp.put(2, 1, 2, Items.BLAZE_POWDER);
		temp.put(4, 2, 5, Items.RAW_GOLD);

		sieveMap.put(Items.RED_SAND, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(28, 40);

		temp.put(8, 2, 3, Items.CLAY_BALL);
		temp.put(3, 1, 3, Items.QUARTZ);
		temp.put(2, 2, 3, Items.RAW_IRON);
		temp.put(3, 2, 3, Items.RAW_COPPER);
		temp.put(12, 1, 2, Items.CACTUS);
		temp.put(14, 1, 1, Items.COARSE_DIRT);

		sieveMap.put(Items.SAND, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(2, 50);

		temp.put(10, 1, 1, Items.DIRT);
		temp.put(2, 1, 3, Items.OAK_SAPLING);
		temp.put(2, 1, 2, Items.SPRUCE_SAPLING);
		temp.put(2, 1, 2, Items.BIRCH_SAPLING);
		temp.put(1, 1, 1, Items.ACACIA_SAPLING);
		temp.put(1, 1, 1, Items.JUNGLE_SAPLING);
		temp.put(3, 2, 4, Items.PUMPKIN_SEEDS);
		temp.put(3, 2, 4, Items.MELON_SLICE);
		temp.put(4, 1, 2, Items.SUGAR_CANE);

		sieveMap.put(Items.GRASS_BLOCK, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(22, 45);

		temp.put(20, 1, 2, Items.FLINT);
		temp.put(2, 4, 8, Items.FLINT);
		temp.put(8, 1, 4, Items.COAL);
		temp.put(2, 5, 12, Items.COAL);
		temp.put(2, 2, 4, Items.RAW_IRON);
		temp.put(1, 2, 4, Items.RAW_GOLD);

		sieveMap.put(Items.GRAVEL, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(5, 70);

		temp.put(3, 3, 7, Items.RAW_IRON);
		temp.put(4, 1, 1, Items.STONE);
		temp.put(1, 1, 1, Items.DEEPSLATE);

		sieveMap.put(Items.TUFF, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(6, 70);

		temp.put(5, 3, 8, Items.RAW_COPPER);
		temp.put(6, 1, 1, Items.STONE);

		sieveMap.put(Items.GRANITE, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(8, 70);

		temp.put(3, 1, 4, Items.PRISMARINE_SHARD);
		temp.put(3, 1, 4, Items.PRISMARINE_CRYSTALS);
		temp.put(2, 1, 2, Items.WET_SPONGE);
		temp.put(7, 3, 5, Items.COD);
		temp.put(6, 3, 5, Items.SALMON);
		temp.put(4, 3, 4, Items.TROPICAL_FISH);
		temp.put(3, 2, 4, Items.PUFFERFISH);

		sieveMap.put(Items.PRISMARINE, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(16, 35);

		temp.put(10, 1, 1, Items.GRAVEL);
		temp.put(14, 1, 3, Items.FLINT);
		temp.put(6, 1, 2, Items.EMERALD);
		temp.put(9, 3, 4, Items.RAW_COPPER);
		temp.put(6, 2, 3, Items.RAW_GOLD);
		temp.put(1, 5, 8, Items.RAW_GOLD);
		temp.put(9, 3, 4, Items.RAW_IRON);

		sieveMap.put(Items.CLAY, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(3, 70);

		temp.put(15, 2, 5, Items.CLAY_BALL);
		temp.put(3, 6, 12, Items.CLAY_BALL);
		temp.put(6, 1, 1, Items.COARSE_DIRT);

		sieveMap.put(Items.TERRACOTTA, temp.clone());

		//------------------------------------------------------------------------------------------------------
		temp = new SieveIdentifier(16, 35);

		temp.put(8, 2, 5, Items.IRON_NUGGET);
		temp.put(3, 7, 12, Items.IRON_NUGGET);
		temp.put(1, 14, 30, Items.IRON_NUGGET);
		temp.put(10, 1, 3, Items.COAL);
		temp.put(4, 4, 6, Items.COAL);

		sieveMap.put(Items.FLINT, temp.clone());
	}
}
