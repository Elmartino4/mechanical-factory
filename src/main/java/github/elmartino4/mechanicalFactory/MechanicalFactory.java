package github.elmartino4.mechanicalFactory;

import github.elmartino4.mechanicalFactory.config.ModConfig;
import github.elmartino4.mechanicalFactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalFactory.util.SieveIdentifier;

import net.fabricmc.api.ModInitializer;
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
	public static HashMap<List<BlockState>, List<BlockState>> anvilMap = new HashMap<>();
	public static ArrayList<GeneratorIdentifier> generatorMap = new ArrayList<>();
	public static HashMap<Item, SieveIdentifier> sieveMap = new HashMap<>();
	public static int sieveTimer = 40;

	@Override
	public void onInitialize() {
		System.out.println("Loaded Mechanical Factory");
		ModConfig.init();

		initAnvilMap();
		initGeneratorMap();

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
		anvilMap.put(Arrays.asList(Blocks.STONE.getDefaultState()), Arrays.asList(Blocks.COBBLESTONE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE.getDefaultState()), Arrays.asList(Blocks.GRAVEL.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.SAND.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.ANDESITE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.STONE.getDefaultState()), Arrays.asList(Blocks.ANDESITE.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.TUFF.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.COBBLESTONE.getDefaultState()), Arrays.asList(Blocks.TUFF.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.TUFF.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.DEEPSLATE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.TUFF.getDefaultState()), Arrays.asList(Blocks.DEEPSLATE.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.NETHERRACK.getDefaultState(), Blocks.SOUL_SOIL.getDefaultState()), Arrays.asList(Blocks.SOUL_SOIL.getDefaultState(), Blocks.SOUL_SAND.getDefaultState()));
	}

	private static void initGeneratorMap(){
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_WATER.getDefaultState(), null, Blocks.BLUE_ICE.getDefaultState(), null, Blocks.FROSTED_ICE.getDefaultState()));
		generatorMap.add(new GeneratorIdentifier(Fluids.WATER.getDefaultState(), null, Blocks.BLUE_ICE.getDefaultState(), null, Blocks.ICE.getDefaultState()));

		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.FLOWING_WATER.getDefaultState(), null, Blocks.PURPUR_BLOCK.getDefaultState(), Blocks.END_STONE.getDefaultState()));
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.WATER.getDefaultState(), null, Blocks.PURPUR_BLOCK.getDefaultState(), Blocks.END_STONE.getDefaultState()));

		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.FLOWING_WATER.getDefaultState(), null, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHERRACK.getDefaultState()));
		generatorMap.add(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.WATER.getDefaultState(), null, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHERRACK.getDefaultState()));
	}
}
