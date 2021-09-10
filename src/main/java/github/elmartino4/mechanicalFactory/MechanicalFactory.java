package github.elmartino4.mechanicalFactory;

import github.elmartino4.mechanicalFactory.config.ModConfig;
import net.fabricmc.api.ModInitializer;

public class MechanicalFactory implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Loaded Mechanical Factory");
		ModConfig.init();
	}
}
