package github.elmartino4.mechanicalfactory.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import github.elmartino4.mechanicalfactory.util.BlockOrFluid;
import github.elmartino4.mechanicalfactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalfactory.util.SieveIdentifier;
import github.elmartino4.mechanicalfactory.util.WeatheringMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ConfigInstance {
    public HashMap<List<Block>, List<Block>> anvilMap = new HashMap<>();
    public HashMap<List<BlockOrFluid>, List<Block>> specialAnvilMap = new HashMap<>();

    public ArrayList<GeneratorIdentifier> generatorMap = new ArrayList<>();
    public WeatheringMap weatheringMap = new WeatheringMap();

    public HashMap<Item, SieveIdentifier> sieveMap = new HashMap<>();

    public void merge(ConfigInstance otherInstance) {
        anvilMap.putAll(otherInstance.anvilMap);
        specialAnvilMap.putAll(otherInstance.specialAnvilMap);
        generatorMap.addAll(otherInstance.generatorMap);
        sieveMap.putAll(otherInstance.sieveMap);
        weatheringMap.combine(otherInstance.weatheringMap);
    }

    public static String sieveMapJson() {
        ConfigInstance inst = new ConfigInstance();
        inst.initSieveMap();

        JsonArray outObj = new JsonArray();


        for (Map.Entry<Item, SieveIdentifier> entry : inst.sieveMap.entrySet()) {
            JsonObject entryObj = new JsonObject();
            entryObj.add("input", new JsonPrimitive(Registry.ITEM.getId(entry.getKey()).toString()));

            entryObj.add("total_weight", new JsonPrimitive(entry.getValue().defaultWeighing));
            entryObj.add("delay", new JsonPrimitive(entry.getValue().delayTicks));

            JsonArray dataArray = new JsonArray();
            for (SieveIdentifier.OutItemData data : entry.getValue().data) {
                JsonObject dataObj = new JsonObject();

                dataObj.add("weight", new JsonPrimitive(data.getWeighing()));

                dataObj.add("min", new JsonPrimitive(data.minRange));
                dataObj.add("max", new JsonPrimitive(data.maxRange));
                dataObj.add("item", new JsonPrimitive(Registry.ITEM.getId(data.item).toString()));

                dataArray.add(dataObj);
            }

            entryObj.add("outputs", dataArray);

            outObj.add(entryObj);
        }


        System.out.println(outObj.toString());
        return outObj.getAsString();
    }

    public void initSieveMap(){
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
