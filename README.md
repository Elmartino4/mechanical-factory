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
- netherrack + flowing lava -> magma block 
- sand + lava source -> redsand 
- sand + flowing water -> clay block
- mossy cobble + ice -> prismarine

### Seive :green_circle:
works by dispensing an input into a scaffolding, after 2 seconds an item is dropped and the scaffolding is broken

further details at [this page](https://github.com/Elmartino4/mechanical-factory/blob/1.17/Item_Chances.md)

### Generator :green_circle:
- water + blue ice -> frosted ice
- water + purpur block + lava -> endstone
- blue ice + netherbrick + lava -> netherack

### Weathering :green_circle:
- cobble -> mossycobble
- stone bricks -> mossy stone bricks
- copper -> exposed copper (and all other variants) :yellow_circle:
- log + lava -> block of coal (small probability)

### Other features
- dispenser with a diamond tool in the centre attempts to break the block infront :green_circle:
- dispenser with center popped chorus fruit uses one and places a block :green_circle:
- dispenser with bucket pointed into a full cauldron will remove its contents :green_circle:
- piston can push budding amethyst :green_circle:
- piston with a netherwart block in front of it can push lava source blocks
- piston with a wet sponge in front of it can push water source blocks