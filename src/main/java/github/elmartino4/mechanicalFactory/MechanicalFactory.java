package github.elmartino4.mechanicalFactory;

import github.elmartino4.mechanicalFactory.config.ModConfig;
import github.elmartino4.mechanicalFactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalFactory.util.SieveIdentifier;

import github.elmartino4.mechanicalFactory.util.WeatheringMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

import java.util.*;

public class MechanicalFactory implements ModInitializer {
	public static HashMap<List<Block>, List<Block>> anvilMap = new HashMap<>();
	public static ArrayList<GeneratorIdentifier> generatorMap = new ArrayList<>();
	public static WeatheringMap weatheringMap = new WeatheringMap();

	public static HashMap<Item, SieveIdentifier> sieveMap = new HashMap<>();
	public static int sieveTimer = 40;

	@Override
	public void onInitialize() {
		System.out.println("Loaded Mechanical Factory");
		ModConfig.init();

		initAnvilMap();
		initGeneratorMap();
		initWeatherMap();

		SieveIdentifier temp = new SieveIdentifier(25);
		//SieveIdentifier temp = new SieveIdentifier(0);

		temp.put(10, 1, 2, Items.PURPUR_BLOCK);
		temp.put(20, 1, 5, Items.CHORUS_FRUIT);
		temp.put(15, 1, 5, Items.POPPED_CHORUS_FRUIT);
		temp.put(1, 1, 3, Items.SHULKER_SHELL);
		temp.put(2, 2, 2, Items.PHANTOM_MEMBRANE);

		sieveMap.put(Items.END_STONE, temp.clone());


	}

	private static void initAnvilMap(){
		anvilMap.put(Arrays.asList(Blocks.STONE), Arrays.asList(Blocks.COBBLESTONE));
		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE), Arrays.asList(Blocks.GRAVEL));
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
}
