package github.elmartino4.mechanicalFactory.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import github.elmartino4.mechanicalFactory.GeneratorIdentifier;
import github.elmartino4.mechanicalFactory.MechanicalFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @Final @Shadow static ImmutableList<Direction> field_34006;

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    private void receiveNeighbours(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir){
        System.out.println("0; " + new GeneratorIdentifier(Fluids.FLOWING_WATER.getDefaultState(), Fluids.EMPTY.getDefaultState(), Blocks.BLUE_ICE.getDefaultState(), null).toString());
        System.out.println("1; " + new GeneratorIdentifier(Fluids.WATER.getDefaultState(), Fluids.EMPTY.getDefaultState(), Blocks.BLUE_ICE.getDefaultState(), null).toString());

        BlockState down = world.getBlockState(pos.down());
        BlockState out = null;

        for (UnmodifiableIterator<Direction> unmodifiableIterator = field_34006.iterator(); unmodifiableIterator.hasNext(); ) {
            BlockPos iteratedPos = pos.offset(unmodifiableIterator.next().getOpposite());

            FluidState primary = world.getFluidState(pos);

            FluidState secondaryFluid = world.getFluidState(iteratedPos);

            BlockState secondaryBlock = world.getBlockState(iteratedPos);

            BlockState outTemp = MechanicalFactory.generatorMap.get(new GeneratorIdentifier(primary, secondaryFluid, secondaryBlock, null));
            //System.out.println(new GeneratorIdentifier(primary, secondaryFluid, secondaryBlock, null).hashCode());
            out = (outTemp != null) ? outTemp : out;

            //outTemp = MechanicalFactory.generatorMap.get(new GeneratorIdentifier(primary, secondaryFluid, secondaryBlock, down));

            //out = (outTemp != null) ? outTemp : out;

            System.out.println("called inject " + new GeneratorIdentifier(primary, secondaryFluid, secondaryBlock, null).toString());

            Set<GeneratorIdentifier> keySet = MechanicalFactory.generatorMap.keySet();

            System.out.println("key set; ");
            for (GeneratorIdentifier key :
                    keySet) {
                System.out.println(key.toString());
            }

            if(MechanicalFactory.generatorMap.containsKey(new GeneratorIdentifier(primary, secondaryFluid, secondaryBlock, null))) System.out.println("outTemp");
        }

        if(out != null){
            world.setBlockState(pos, out);
            cir.setReturnValue(false);
            System.out.println("found a match");
        }

        System.out.println("2; " + new GeneratorIdentifier(Fluids.WATER.getDefaultState(), Fluids.EMPTY.getDefaultState(), Blocks.BLUE_ICE.getDefaultState(), null).toString());

    }

    /*@Inject(method = "receiveNeighborFluids",
            cancellable = true,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;offset(Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/BlockPos;"
            ))
    private void receiveNeighboursLava(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir){
        System.out.println("called inject");
        if(){

        }
    }*/
}
