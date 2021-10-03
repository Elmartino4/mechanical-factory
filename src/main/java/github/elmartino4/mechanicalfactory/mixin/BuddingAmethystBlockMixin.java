package github.elmartino4.mechanicalfactory.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BuddingAmethystBlock.class)
public class BuddingAmethystBlockMixin {
    @Inject(at = @At("HEAD"), method = "getPistonBehavior(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/piston/PistonBehavior;", cancellable = true)
    void getPistonBehavior(BlockState state, CallbackInfoReturnable<PistonBehavior> cir) {
        cir.setReturnValue(PistonBehavior.NORMAL);
    }
}
