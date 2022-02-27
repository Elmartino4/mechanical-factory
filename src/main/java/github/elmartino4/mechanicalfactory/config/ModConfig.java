package github.elmartino4.mechanicalfactory.config;


import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;
import github.elmartino4.mechanicalfactory.MechanicalFactory;
import github.elmartino4.mechanicalfactory.util.GeneratorIdentifier;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.IOUtils;

public class ModConfig {

    private static File folder = new File("config/mechafactory");
    private static File configFile;
    private static Gson config = new GsonBuilder().setPrettyPrinting().create();
    public static ConfigInstance INSTANCE;

    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("mechanical-factory", "mechanical-data");
            }

            @Override
            public void reload(ResourceManager manager) {
                INSTANCE = new ConfigInstance();
                System.out.println("loading mecha resources");

                boolean gotAnvilDefault = false,
                        gotSieveDefault = false,
                        gotWeatheringDefault = false,
                        gotGeneratorDefault = false;

                for (Identifier id : manager.findResources("factory-config", path -> path.endsWith(".json"))) {
                    if (!id.getPath().matches("factory-config/(anvil|sieve|weathering|generator)+/[\\w_]+\\.json$")){
                        System.out.println(id.getPath());
                        continue;
                    }


                    System.out.println("got " + id.toString());
                    try (InputStream stream = manager.getResource(id).getInputStream()) {
                        String text = IOUtils.toString(stream, StandardCharsets.UTF_8.name());

                        switch (id.getPath().split("/")[1]) {
                            case "anvil" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.anvil);
                                gotAnvilDefault = gotAnvilDefault || id.getPath().split("/")[2].equals("default.json");
                            }
                            case "sieve" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.sieve);
                                gotSieveDefault = gotSieveDefault || id.getPath().split("/")[2].equals("default.json");
                            }
                            case "weathering" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.weathering);
                                gotWeatheringDefault = gotWeatheringDefault || id.getPath().split("/")[2].equals("default.json");
                            }
                            case "generator" -> {
                                loadConfig(id.getPath().split("/")[2], text, ConfigType.generator);
                                gotGeneratorDefault = gotGeneratorDefault || id.getPath().split("/")[2].equals("default.json");
                            }
                        }
                    } catch (Exception e) {
                        MechanicalFactory.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                    }
                }

                if (!gotAnvilDefault)
                    INSTANCE.initAnvilMap();

                if (!gotSieveDefault)
                    INSTANCE.initSieveMap();

                if (!gotWeatheringDefault)
                    INSTANCE.initWeatherMap();

                if (!gotGeneratorDefault)
                    INSTANCE.initGeneratorMap();
            }
        });
    }

    public static void loadConfig(String name, String innerText, ConfigType type) {
        JsonArray array = JsonParser.parseString(innerText).getAsJsonArray();

        for (JsonElement elem : array) {
            JsonObject obj = elem.getAsJsonObject();

            if (type == ConfigType.anvil) {
                /*if (obj.has("input")) {
                    boolean containsFluids = false;

                    for (JsonElement inputElem : obj.get("input").getAsJsonArray()) {
                        containsFluids = containsFluids || Registry.FLUID.containsId(new Identifier(inputElem.toString()));
                    }

                    if (containsFluids) {

                    } else {

                    }
                    for (JsonElement inputElem : obj.get("input").getAsJsonArray()) {

                    }
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, input missing");
                    continue;
                }*/
            } else if (type == ConfigType.sieve) {

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

                INSTANCE.weatheringMap.put(input, primary, output, probability);
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

                if (obj.has("output")) {
                    output = Registry.BLOCK.get(new Identifier(obj.get("output").getAsString()));
                } else {
                    MechanicalFactory.LOGGER.error("Bad datapack file {" + name + "}, output missing");
                    continue;
                }

                System.out.println("found generator");
                INSTANCE.generatorMap.add(new GeneratorIdentifier(primary, secondaryF, secondaryB, under, output));
            }
        }
    }

    protected enum ConfigType {
        anvil, sieve, weathering, generator
    }
}
