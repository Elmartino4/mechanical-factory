package github.elmartino4.mechanicalfactory.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class WeatheringMap {
    private HashMap<WeatheringMapData, OutputData> map = new HashMap<>();
    private ArrayList<Block> list = new ArrayList<>();
    private static final Direction[] dirList = {Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.WEST};

    public void put(Block main, Fluid secondary, Block becomes, float probability){
        map.put(new WeatheringMapData(main, secondary), new OutputData(becomes, probability));
        if(!list.contains(main)) list.add(main);
    }

    public boolean checkContains(Block main){
        return list.contains(main);
    }

    public void tick(ServerWorld world, BlockPos pos, Random random){
        ArrayList<Fluid> fluidList = new ArrayList<>();

        for (Direction dir : dirList) {
            Fluid temp = world.getFluidState(pos.offset(dir)).getFluid();
            if(!fluidList.contains(temp) && temp != Fluids.EMPTY) fluidList.add(temp);
        }

        Block block = world.getBlockState(pos).getBlock();
        for(Fluid fluid : fluidList){
            WeatheringMapData dat =  new WeatheringMapData(block, fluid);
            if(block == Blocks.COBBLESTONE){
                //System.out.println("called tick on " + block.toString() + " with \n" + dat.toString());
            }
            OutputData data = map.get(dat);

            if(data != null){
                //System.out.println("found data");
                Block becomes = data.getBecomes(random);
                if(becomes != null){
                    //System.out.println("checked change");
                    world.setBlockState(pos, becomes.getDefaultState());
                    return;
                }
            }
        }
    }

    public class WeatheringMapData{
        private final Block main;
        private final Fluid secondary;

        public WeatheringMapData(Block main, Fluid secondary){
            this.main = main;
            this.secondary = secondary;
        }

        @Override
        public boolean equals(Object o) {
            return this.hashCode() == o.hashCode();
        }

        @Override
        public int hashCode() {
            return Objects.hash(main, secondary);
        }

        @Override
        public String toString() {
            return "WeatheringMapData{" +
                    "main=" + main +
                    ", secondary=" + secondary +
                    "} @ " + hashCode();
        }
    }

    public class OutputData{
        private final Block becomes;
        private final float probability;

        public OutputData(Block becomes, float probability){
            this.becomes = becomes;
            this.probability = probability;
        }

        public Block getBecomes(Random random){
            if(random.nextFloat() < probability) return becomes;
            return null;
        }

        @Override
        public String toString() {
            return "OutputData{" +
                    "becomes=" + becomes +
                    ", probability=" + probability +
                    '}';
        }
    }
}
