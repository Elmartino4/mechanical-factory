package github.elmartino4.mechanicalfactory;

import github.elmartino4.mechanicalfactory.config.ConfigInstance;
import github.elmartino4.mechanicalfactory.config.ModConfig;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MechanicalFactory implements ModInitializer {


	public static final Logger LOGGER = LogManager.getLogger("Mechanical Factory");

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded Mechanical Factory");

		ModConfig.init();
	}
}
