package github.elmartino4.mechanicalFactory.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DropperBlockMixin {
    /*@Inject(method="neighborUpdate", at = @At("HEAD"))
    private void injectUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci){
        boolean hasPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean isTriggered = ((Boolean)state.get((Property) Properties.TRIGGERED)).booleanValue();

        BlockPointer pointer = (BlockPointer) new BlockPointerImpl((ServerWorld) world, pos);
        Direction direc = (Direction)pointer.getBlockState().get((Property)DispenserBlock.FACING);

        if(hasPower && !isTriggered && world.getBlockState(pos.offset(direc)).getBlock() == Blocks.SCAFFOLDING){

        }
    }*/
}
