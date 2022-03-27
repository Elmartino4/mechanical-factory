package github.elmartino4.mechanicalfactory.config;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import github.elmartino4.mechanicalfactory.MechanicalFactory;
import github.elmartino4.mechanicalfactory.util.BlockOrFluid;
import github.elmartino4.mechanicalfactory.util.GeneratorIdentifier;
import github.elmartino4.mechanicalfactory.util.SieveIdentifier;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.IOUtils;

public class ModConfig {
    public static ConfigInstance INSTANCE;
    public static ConfigInstance ORIGINAL_INSTANCE;

    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("mechanical-factory", "mechanical-data");
            }

            @Override
            public void reload(ResourceManager manager) {
                INSTANCE = new ConfigInstance();
                ORIGINAL_INSTANCE = new ConfigInstance();
                System.out.println("loading mecha resources");

                boolean gotAnvilDefault = false,
                        gotSieveDefault = false,
                        gotWeatheringDefault = false,
                        gotGeneratorDefault = false;

                for (Identifier id : manager.findResources("factory-config", path -> path.endsWith(".json"))) {
                    if (!id.getPath().matches("factory-config/(anvil|sieve|weathering|generator)/[\\w_\\-]+\\.json$")){
                        System.out.println(id.getPath());
                        continue;
                    }


                    System.out.println("got " + id.toString());
                    try (InputStream stream = manager.getResource(id).getInputStream()) {
                        String text = IOUtils.toString(stream, StandardCharsets.UTF_8.name());

                        switch (id.getPath().split("/")[1]) {
                            case "anvil" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.anvil);
                                gotAnvilDefault = gotAnvilDefault || id.getPath().split("/")[2].matches("default([\\-_\\w]+)?\\.json");
                            }
                            case "sieve" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.sieve);
                                gotSieveDefault = gotSieveDefault || id.getPath().split("/")[2].matches("default([\\-_\\w]+)?\\.json");
                            }
                            case "weathering" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.weathering);
                                gotWeatheringDefault = gotWeatheringDefault || id.getPath().split("/")[2].matches("default([\\-_\\w]+)?\\.json");
                            }
                            case "generator" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.generator);
                                gotGeneratorDefault = gotGeneratorDefault || id.getPath().split("/")[2].matches("default([\\-_\\w]+)?\\.json");
                            }
                        }
                    } catch (Exception e) {
                        MechanicalFactory.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                    }
                }

                if (!gotAnvilDefault){
                    INSTANCE.anvilMap.putAll(ORIGINAL_INSTANCE.anvilMap);
                    INSTANCE.specialAnvilMap.putAll(ORIGINAL_INSTANCE.specialAnvilMap);
                    //INSTANCE.initAnvilMap();
                }

                if (!gotSieveDefault) {
                    INSTANCE.sieveMap.putAll(ORIGINAL_INSTANCE.sieveMap);
                    //INSTANCE.initSieveMap();
                }

                if (!gotWeatheringDefault) {
                    //INSTANCE.initWeatherMap();
                    INSTANCE.weatheringMap.combine(ORIGINAL_INSTANCE.weatheringMap);
                }

                if (!gotGeneratorDefault) {
                    //INSTANCE.initGeneratorMap();
                    INSTANCE.generatorMap.addAll(ORIGINAL_INSTANCE.generatorMap);
                }
            }
        });
    }

    public static void loadConfig(String name, String innerText, ConfigType type) {
        JsonArray array = JsonParser.parseString(innerText).getAsJsonArray();

        OUTER_LOOP: for (JsonElement elem : array) {
            JsonObject obj = elem.getAsJsonObject();

            if (type == ConfigType.anvil) {
                List<Block> inputB = new ArrayList<>();;
                List<BlockOrFluid> inputBorF = new ArrayList<>();;
                List<Block> output = new ArrayList<>();


                if (obj.has("input")) {
                    boolean containsFluids = false;

                    for (JsonElement inputElem : obj.get("input").getAsJsonArray()) {
                        containsFluids = containsFluids || Registry.FLUID.containsId(new Identifier(inputElem.getAsString()));
                    }

                    if (containsFluids) {
                        for (JsonElement inputElem : obj.get("input").getAsJsonArray()) {
                            if (Registry.FLUID.containsId(new Identifier(inputElem.getAsString()))) {
                                inputBorF.add(new BlockOrFluid(Registry.FLUID.get(new Identifier(inputElem.getAsString()))));
                            } else if (Registry.BLOCK.containsId(new Identifier(inputElem.getAsString()))) {
                                inputBorF.add(new BlockOrFluid(Registry.BLOCK.get(new Identifier(inputElem.getAsString()))));
                            } else {
                                MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, input is dodgy missing");
                                continue OUTER_LOOP;
                            }
                        }
                    } else {
                        for (JsonElement inputElem : obj.get("input").getAsJsonArray()) {
                            inputB.add(Registry.BLOCK.get(new Identifier(inputElem.getAsString())));
                        }
                    }
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, input missing");
                    continue;
                }

                if (obj.has("output")) {
                    for (JsonElement inputElem : obj.get("output").getAsJsonArray()) {
                        output.add(Registry.BLOCK.get(new Identifier(inputElem.getAsString())));
                    }
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, output missing");
                    continue;
                }

                System.out.println("found anvil");

                if (name.matches("original([\\-_\\w]+)?\\.json")){
                    if (inputB.size() > 0) {

                        ORIGINAL_INSTANCE.anvilMap.put(inputB, output);
                    } else {
                        ORIGINAL_INSTANCE.specialAnvilMap.put(inputBorF, output);
                    }
                } else {
                    if (inputB.size() > 0) {
                        INSTANCE.anvilMap.put(inputB, output);
                    } else {
                        INSTANCE.specialAnvilMap.put(inputBorF, output);
                    }
                }
            } else if (type == ConfigType.sieve) {
                Item input;
                int weighing = -1;
                int ticks;

                if (obj.has("input")) {
                    input = Registry.ITEM.get(new Identifier(obj.get("input").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, input missing");
                    continue;
                }

                if (obj.has("total_weight")) {
                    weighing = obj.get("total_weight").getAsInt();
                }

                if (obj.has("delay")) {
                    ticks = obj.get("delay").getAsInt();
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, ticks missing");
                    continue;
                }

                SieveIdentifier sieveIdentifier = new SieveIdentifier(weighing, ticks);

                if (obj.has("outputs")) {
                    for (JsonElement inputElem : obj.get("outputs").getAsJsonArray()) {
                        int subWeighing, min, max;
                        Item item;
                        JsonObject subObj = inputElem.getAsJsonObject();

                        if (subObj.has("weight")) {
                            subWeighing = subObj.get("weight").getAsInt();
                        } else {
                            MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, weighing missing");
                            continue OUTER_LOOP;
                        }

                        if (subObj.has("min")) {
                            min = subObj.get("min").getAsInt();
                        } else {
                            MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, min missing");
                            continue OUTER_LOOP;
                        }

                        if (subObj.has("max")) {
                            max = subObj.get("max").getAsInt();
                        } else {
                            MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, max missing");
                            continue OUTER_LOOP;
                        }

                        if (subObj.has("item")) {
                            item = Registry.ITEM.get(new Identifier(subObj.get("item").getAsString()));
                        } else {
                            MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, item missing");
                            continue OUTER_LOOP;
                        }

                        sieveIdentifier.put(subWeighing, min, max, item);
                    }
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, output missing");
                    continue;
                }

                if (weighing == -1) {
                    sieveIdentifier.updateDefaultWeighing();
                }

                if (name.matches("original([\\-_\\w]+)?\\.json")) {
                    ORIGINAL_INSTANCE.sieveMap.put(input, sieveIdentifier);
                } else {
                    INSTANCE.sieveMap.put(input, sieveIdentifier);
                }

            } else if (type == ConfigType.weathering) {
                Block input, output;
                Fluid primary;
                float probability;

                if (obj.has("fluid")) {
                    primary = Registry.FLUID.get(new Identifier(obj.get("fluid").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, fluid missing");
                    continue;
                }

                if (obj.has("input")) {
                    input = Registry.BLOCK.get(new Identifier(obj.get("input").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, input missing");
                    continue;
                }

                if (obj.has("output")) {
                    output = Registry.BLOCK.get(new Identifier(obj.get("output").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, output missing");
                    continue;
                }

                if (obj.has("probability")) {
                    probability = obj.get("output").getAsFloat();
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, probability missing");
                    continue;
                }

                if (name.matches("original([\\-_\\w]+)?\\.json")) {
                    ORIGINAL_INSTANCE.weatheringMap.put(input, primary, output, probability);
                } else {
                    INSTANCE.weatheringMap.put(input, primary, output, probability);
                }

            } else if (type == ConfigType.generator) {
                Fluid primary, secondaryF = null;
                Block secondaryB = null, under = null, output;
                if (obj.has("primary_fluid")) {
                    primary = Registry.FLUID.get(new Identifier(obj.get("primary_fluid").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, primary_fluid missing");
                    continue;
                }

                if (obj.has("secondary_fluid")) {
                    secondaryF = Registry.FLUID.get(new Identifier(obj.get("secondary_fluid").getAsString()));
                } else if (obj.has("secondary_block")) {
                    secondaryB = Registry.BLOCK.get(new Identifier(obj.get("secondary_block").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, secondary_fluid or secondary_block missing");
                    continue;
                }

                if (obj.has("underneath_block")) {
                    under = Registry.BLOCK.get(new Identifier(obj.get("underneath_block").getAsString()));
                }

                if (obj.has("output_block")) {
                    output = Registry.BLOCK.get(new Identifier(obj.get("output_block").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, output missing");
                    continue;
                }

                System.out.println("found generator");
                if (name.matches("original([\\-_\\w]+)?\\.json")) {
                    ORIGINAL_INSTANCE.generatorMap.add(new GeneratorIdentifier(primary, secondaryF, secondaryB, under, output));
                } else {
                    INSTANCE.generatorMap.add(new GeneratorIdentifier(primary, secondaryF, secondaryB, under, output));
                }

            }
        }
    }

    protected enum ConfigType {
        anvil, sieve, weathering, generator
    }
}
