package github.elmartino4.mechanicalFactory.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public class IceBlockMixin {
    @Inject(method = "melt", at = @At("TAIL"))
    private void meltInject(BlockState state, World world, BlockPos pos, CallbackInfo ci){
        if(state.getBlock() == Blocks.FROSTED_ICE)world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1, 1);
    }

    /*@Inject(method = "melt", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/util/math/BlockPos$Mutable;set(Lnet/minecraft/util/math/Vec3i;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/BlockPos$Mutable;"))
    private void meltInjectButMore(BlockState state, World world, BlockPos pos, CallbackInfo ci){

    }*/
}
