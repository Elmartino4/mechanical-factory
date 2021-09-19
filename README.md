# Mechanical Factory
## Description
A factory style mod that uses mechanical processes to generate blocks items etc. All intended to work with vanilla clients.

## Following features are planned:
Features with :yellow_circle: are being implemented atm, features with :green_circle: are already implemented

**TODO; MOVE THIS LIST TO GITHUB PROJECTS**

### Anvil :green_circle:
- cobble -> gravel
- gravel -> sand
- sandstone -> sand
- chisieled stone bricks -> stone bricks
- stone -> cobblestone
- cobblestone + gravel -> tuff
- tuff + gravel -> deepslate
- andesite + sand -> calcite
- stone + sand -> diorite
- stone + gravel -> andersite
- stone + redsand -> granite
- concrete -> concrete powder :yellow_circle:
- netherrack + soul soil -> soul sand + soul soil
- netherrack + flowing lava -> magma block :yellow_circle:
- sand + lava source -> redsand :yellow_circle:
- mossy cobble + ice -> prismarine

### Seive :yellow_circle:
works by dispensing *input* into a scaffolding, after 30 game ticks an item is dropped and the scaffolding is broken

#### Formatting is as follows
 - input (weighing of no drop)
 - output (relative weighing, [min quanitiy - max quantity])

#### Values;
 - endstone (25) ->
   - purpur (10, [1-2])
   - chorus fruit (20, [1-5])
   - popped chorus fruit (15, [1-5])
   - shulker shell (1, [1-3])
   - phantom membrane (2, [2])


- netherack () ->
  - quartz
  - gold nugget
  - rotten flesh
  - gold sword


- soul sand ->
    - nether saplings
    - ghast tear
    - nether wart


- dirt ->
  - any seed
  - potato
  - carrot
  - bone meal


- red sand ->
  - redstone
  - gunpowder
  - glowstone dust
  - blazepowder
  - raw gold


- sand ->
  - clay
  - quartz
  - raw iron
  - raw copper
  - cactus


- grass block ->
  - any sapling
  - pumpkin
  - melon


- gravel ->
  - flint
  - coal
  - raw iron
  - raw gold
  - raw copper


- tuff ->
  - raw iron


- granite ->
  - raw copper


- prismarine ->
  - prismarine shard
  - prismarine crystal
  - sponge
  - any fish

### Generator :green_circle:
- water + blue ice -> frosted ice
- water + purpur block + lava -> endstone
- blue ice + netherbrick + lava -> netherack

### Weathering
- cobble -> mossycobble
- stone bricks -> mossy stone bricks
- copper -> exposed copper (and all other variants)
- log + lava -> block of coal (small probability)

### Other features
- dispenser with endrod in front breaks blocks it points at (requires a continuous redstone signal)
- dispenser with center popped chorus fruit uses one and places a block
- piston with a netherwart block in front of it can push lava source blocks
- piston with a wet sponge in front of it can push water source blocks

