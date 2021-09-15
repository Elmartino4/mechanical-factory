package github.elmartino4.mechanicalFactory.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostedIceBlock.class)
public class FrostedIceBlockMixin {
    @Inject(method = "increaseAge", at = @At("HEAD"), cancellable = true)
    private void increaseAgeInject(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        Direction[] all = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN, Direction.UP };
        for(Direction one : all) {
            if(world.getBlockState(pos.offset(one)).getBlock() == Blocks.BLUE_ICE){
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
