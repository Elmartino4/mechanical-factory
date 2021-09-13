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
        //GeneratorIdentifier temp = new GeneratorIdentifier(Fluids.FLOWING_WATER.getDefaultState(), null, Blocks.BLUE_ICE.getDefaultState(), null);
        //System.out.println("0; " + temp.toString() + temp.hashCode(true, false));
        //System.out.println("1; " + temp.toString() + temp.hashCode(true, false));

        BlockState down = world.getBlockState(pos.down());

        int matchVal = 1;
        int matchIndex = -1;

        for (UnmodifiableIterator<Direction> unmodifiableIterator = field_34006.iterator(); unmodifiableIterator.hasNext(); ) {
            BlockPos iteratedPos = pos.offset(unmodifiableIterator.next().getOpposite());

            FluidState primary = world.getFluidState(pos);

            FluidState secondaryFluid = world.getFluidState(iteratedPos);

            BlockState secondaryBlock = world.getBlockState(iteratedPos);

            //find best match

            for (int i = 0; i < MechanicalFactory.generatorMap.size(); i++) {
                GeneratorIdentifier gi = MechanicalFactory.generatorMap.get(i);
                int tempMatchVal = gi.getSimilarity(primary, secondaryFluid, secondaryBlock, down);
                if(tempMatchVal > matchVal){
                    matchVal = tempMatchVal;
                    matchIndex = i;
                    //System.out.println("found a match");
                }
            }
        }

        if(matchIndex != -1){
            world.setBlockState(pos, MechanicalFactory.generatorMap.get(matchIndex).getBlockOut());
            cir.setReturnValue(false);
            System.out.println("found a match");
        }
    }
}
