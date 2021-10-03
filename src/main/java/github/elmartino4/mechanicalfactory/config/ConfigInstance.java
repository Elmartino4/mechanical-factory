package github.elmartino4.mechanicalfactory.config;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigInstance {
    public HashMap<List<Integer>, List<Integer>> anvilMap = new HashMap<>();

    public int scanDistance;

    public ConfigInstance(){
        scanDistance = 2;

        //Blocks.COBBLESTONE.asItem()
        //Item.getRawId(Blocks.GRAVEL.asItem()
        anvilMap.put(Arrays.asList(Item.getRawId(Items.COBBLESTONE)), Arrays.asList(Item.getRawId(Items.GRAVEL)));
        anvilMap.put(Arrays.asList(Item.getRawId(Items.STONE), Item.getRawId(Items.GRAVEL)), Arrays.asList(Item.getRawId(Items.ANDESITE)));
        anvilMap.put(Arrays.asList(Item.getRawId(Items.GRAVEL), Item.getRawId(Items.STONE)), Arrays.asList(Item.getRawId(Items.ANDESITE)));
        anvilMap.put(Arrays.asList(Item.getRawId(Items.ANDESITE)), Arrays.asList(Item.getRawId(Items.DIRT)));
    }
}
