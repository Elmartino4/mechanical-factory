package github.elmartino4.mechanicalfactory.mixin;

import github.elmartino4.mechanicalfactory.MechanicalFactory;
import github.elmartino4.mechanicalfactory.config.ModConfig;
import net.minecraft.block.Block;
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
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "hasRandomTicks()Z", at = @At("HEAD"), cancellable = true)
    private void checkInject(CallbackInfoReturnable<Boolean> cir){
        if (ModConfig.INSTANCE.weatheringMap.checkContains(getBlock())){
            cir.setReturnValue(true);
            //System.out.println("found block");
        }
    }

    @Inject(method = "randomTick(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", at = @At("HEAD"))
    private void doTick(ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){
        ModConfig.INSTANCE.weatheringMap.tick(world, pos, random);
    }
}
