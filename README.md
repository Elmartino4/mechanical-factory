# Mechanical Factory
## Description
A factory style mod that uses mechanical processes to generate blocks items etc. All intended to work with vanilla clients.

## Data driven _recipes_
Put a .json folder with any name into a datapack within the folders anvil, generator, sieve, or weathering.
Call the json file "default(-optionaltext).json" to overwrite the original recipes.
Files named "original(-optionaltext).json" can be overwritten by default files.

### Anvil recipes
```json
[
  {
    "input": [
      "minecraft:input_block_one",
      "modid:input_block_two",
      "anothermodid:input_block_there"
    ],
    "ouput": [
      "minecraft:output_block_one",
      "modid:output_block_two"
    ]
  }
]
```

In the recipe object, `"mixed": false`, can be included to only allow the recipe in the given order, otherwise every permutation of the inputs is included.
The `"mixed"` field defaults to true.

### Generator recipes
```json
[
  {
    "primary_fluid": "minecraft:water",
    "secondary_block": "minecraft:blue_ice",
    "output_block": "minecraft:ice"
  },
  {
    "primary_fluid": "minecraft:flowing_lava",
    "secondary_fluid": "minecraft:water",
    "underneath_block": "minecraft:nether_bricks",
    "output_block": "minecraft:netherrack"
  }
]
```

### Sieve recipes
```json
[
  {
    "input": [
      "minecraft:input_block_one",
      "modid:input_block_two",
      "anothermodid:input_block_there"
    ],
    "ouput": [
      "minecraft:output_block_one",
      "modid:output_block_two"
    ]
  }
]
```

### Weathering recipes
```json
[
  {
    "input": "minecraft:granite",
    "total_weight": 6,
    "delay": 70,
    "outputs": [
      {
        "weight": 5,
        "min": 3,
        "max": 8,
        "item": "minecraft:raw_copper"
      },
      {
        "weight": 6,
        "min": 1,
        "max": 1,
        "item": "minecraft:stone"
      }
    ]
  }
]
```

### Anvil :green_circle:
- cobble -> gravel
- gravel -> sand
- sandstone -> sand
- stone -> cobblestone
- cobblestone + gravel -> tuff
- tuff + gravel -> deepslate
- andesite + sand -> calcite
- stone + sand -> diorite
- stone + gravel -> andersite
- stone + redsand -> granite
- concrete -> concrete powder :yellow_circle:
- netherrack + soul soil -> soul sand + soul soil
- netherrack + flowing lava -> magma block 
- sand + flowing source -> redsand 
- sand + flowing water -> clay block
- mossy cobble + ice -> prismarine

### Seive :green_circle:
works by dispensing an input into a scaffolding, after a specific amount of time an item is dropped and the scaffolding is broken

further details at [this page](https://github.com/Elmartino4/mechanical-factory/blob/1.18/Item_Chances.md)

### Generator :green_circle:
- water + blue ice -> frosted ice
- water + purpur block + lava -> endstone
- blue ice + netherbrick + lava -> netherack

### Weathering :green_circle:
- cobble + water -> mossycobble
- stone bricks + water -> mossy stone bricks
- log + lava -> block of coal (small probability)

### Other features
- dispenser with a diamond tool in the centre attempts to break the block infront :green_circle:
- dispenser with center popped chorus fruit uses one and places a block :green_circle:
- dispenser with bucket pointed into a full cauldron will remove its contents :green_circle:
- piston can push budding amethyst :green_circle:
- piston with a netherwart block in front of it can push lava source blocks
- piston with a wet sponge in front of it can push water source blocks