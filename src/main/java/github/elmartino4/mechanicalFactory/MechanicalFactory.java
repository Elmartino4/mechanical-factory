package github.elmartino4.mechanicalFactory;

import github.elmartino4.mechanicalFactory.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MechanicalFactory implements ModInitializer {
	public static HashMap<List<BlockState>, List<BlockState>> anvilMap = new HashMap<>();
	public static HashMap<GeneratorIdentifier, BlockState> generatorMap = new HashMap<>();

	@Override
	public void onInitialize() {
		System.out.println("Loaded Mechanical Factory");
		ModConfig.init();

		anvilMap.put(Arrays.asList(Blocks.STONE.getDefaultState()), Arrays.asList(Blocks.COBBLESTONE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE.getDefaultState()), Arrays.asList(Blocks.GRAVEL.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.SAND.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.ANDESITE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.STONE.getDefaultState()), Arrays.asList(Blocks.ANDESITE.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.COBBLESTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.TUFF.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.COBBLESTONE.getDefaultState()), Arrays.asList(Blocks.TUFF.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.TUFF.getDefaultState(), Blocks.GRAVEL.getDefaultState()), Arrays.asList(Blocks.DEEPSLATE.getDefaultState()));
		anvilMap.put(Arrays.asList(Blocks.GRAVEL.getDefaultState(), Blocks.TUFF.getDefaultState()), Arrays.asList(Blocks.DEEPSLATE.getDefaultState()));

		anvilMap.put(Arrays.asList(Blocks.NETHERRACK.getDefaultState(), Blocks.SOUL_SOIL.getDefaultState()), Arrays.asList(Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SOIL.getDefaultState()));

		//GeneratorIdentifier(Tag.Identified< Fluid > primary, Tag.Identified<Fluid> secondaryFluid, BlockState secondaryBlock, BlockState underneathBlock)

		generatorMap.put(new GeneratorIdentifier(Fluids.FLOWING_WATER.getDefaultState(), null, Blocks.BLUE_ICE.getDefaultState(), null), Blocks.FROSTED_ICE.getDefaultState());
		generatorMap.put(new GeneratorIdentifier(Fluids.WATER.getDefaultState(), null, Blocks.BLUE_ICE.getDefaultState(), null), Blocks.ICE.getDefaultState());

		generatorMap.put(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.FLOWING_WATER.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.PURPUR_BLOCK.getDefaultState()), Blocks.END_STONE.getDefaultState());
		generatorMap.put(new GeneratorIdentifier(Fluids.FLOWING_LAVA.getDefaultState(), Fluids.WATER.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.PURPUR_BLOCK.getDefaultState()), Blocks.END_STONE.getDefaultState());
	}
}
