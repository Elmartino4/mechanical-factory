package github.elmartino4.mechanicalFactory;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.Fluids;
import net.minecraft.network.MessageType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WeatherableBlockState extends BlockState{
    public Block blockFinal;
    private int delayTicks = 5;
    private float decayProb = 0.9F;
    private boolean inWater = false;
    private int decayCooldown = 0;

    public WeatherableBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> codec, int delayTicks, float decayProb) {
        super(block, propertyMap, codec);

        this.decayProb = decayProb;
        this.delayTicks = delayTicks;
    }

    /*public WeatherableBlockState updateWeatherableBlock(int delayTicks, float decayProb){
        this.delayTicks = delayTicks;
        this.decayProb = decayProb;

        return this;
    }*/

    @Override
    public void randomTick(ServerWorld world, BlockPos pos, Random random) {
        System.out.println("called tick");

        if(decayCooldown == delayTicks) tryDecay(random, world, pos);
        if(decayCooldown > 0){
            decayCooldown--;

            MinecraftClient mc = MinecraftClient.getInstance();

            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("called random tick"), mc.player.getUuid());
        }

        super.randomTick(world, pos, random);
    }

    @Override
    public void neighborUpdate(World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean oldInWater = inWater;

        inWater = false;

        if (world.getFluidState(pos.north()).getFluid() == Fluids.WATER) inWater = true;
        if (world.getFluidState(pos.north()).getFluid() == Fluids.FLOWING_WATER) inWater = true;
        if (world.getFluidState(pos.south()).getFluid() == Fluids.WATER) inWater = true;
        if (world.getFluidState(pos.south()).getFluid() == Fluids.FLOWING_WATER) inWater = true;

        if (world.getFluidState(pos.east()).getFluid() == Fluids.WATER) inWater = true;
        if (world.getFluidState(pos.east()).getFluid() == Fluids.FLOWING_WATER) inWater = true;
        if (world.getFluidState(pos.west()).getFluid() == Fluids.WATER) inWater = true;
        if (world.getFluidState(pos.west()).getFluid() == Fluids.FLOWING_WATER) inWater = true;

        if (!oldInWater && inWater && decayCooldown == 0) decayCooldown = delayTicks;

        System.out.println("called with; in water = " + inWater);

        super.neighborUpdate(world, pos, block, fromPos, notify);
    }

    private void tryDecay(Random random, ServerWorld world, BlockPos pos){
        if(random.nextFloat() < decayProb) world.setBlockState(pos, blockFinal.getDefaultState());
    }

    public boolean hasRandomTicks(BlockState state){
        return true;
    }
}
