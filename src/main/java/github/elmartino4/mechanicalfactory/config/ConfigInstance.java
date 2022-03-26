package github.elmartino4.mechanicalfactory.config;

import github.elmartino4.mechanicalfactory.util.BlockOrFluid;
import github.elmartino4.mechanicalfactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalfactory.util.SieveIdentifier;
import github.elmartino4.mechanicalfactory.util.WeatheringMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

import java.util.*;

public class ConfigInstance {
    public HashMap<List<Block>, List<Block>> anvilMap = new HashMap<>();
    public HashMap<List<BlockOrFluid>, List<Block>> specialAnvilMap = new HashMap<>();

    public ArrayList<GeneratorIdentifier> generatorMap = new ArrayList<>();
    public WeatheringMap weatheringMap = new WeatheringMap();

    public HashMap<Item, SieveIdentifier> sieveMap = new HashMap<>();

    /*public void merge(ConfigInstance otherInstance) {
        anvilMap.putAll(otherInstance.anvilMap);
        specialAnvilMap.putAll(otherInstance.specialAnvilMap);
        generatorMap.addAll(otherInstance.generatorMap);
        sieveMap.putAll(otherInstance.sieveMap);
        weatheringMap.combine(otherInstance.weatheringMap);
    }*/


}