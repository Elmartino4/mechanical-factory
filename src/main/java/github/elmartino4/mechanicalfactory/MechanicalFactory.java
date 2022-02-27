package github.elmartino4.mechanicalfactory;

import github.elmartino4.mechanicalfactory.config.ModConfig;
import github.elmartino4.mechanicalfactory.util.BlockOrFluid;
import github.elmartino4.mechanicalfactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalfactory.util.SieveIdentifier;

import github.elmartino4.mechanicalfactory.util.WeatheringMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MechanicalFactory implements ModInitializer {


	public static final Logger LOGGER = LogManager.getLogger("Mechanical Factory");

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded Mechanical Factory");
		ModConfig.init();


	}
}
