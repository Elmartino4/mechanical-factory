package github.elmartino4.mechanicalFactory.mixin;

import github.elmartino4.mechanicalFactory.MechanicalFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(targets = "net/minecraft/block/AbstractBlock$AbstractBlockState")
public abstract class AbstractBlockStateMixin {
    @Shadow abstract Block getBlock();

    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void checkInject(CallbackInfoReturnable<Boolean> cir){
        if (MechanicalFactory.weatheringMap.checkContains(getBlock())){
            cir.setReturnValue(true);
            //System.out.println("found block");
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void doTick(ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){
        MechanicalFactory.weatheringMap.tick(world, pos, random);
    }
}
