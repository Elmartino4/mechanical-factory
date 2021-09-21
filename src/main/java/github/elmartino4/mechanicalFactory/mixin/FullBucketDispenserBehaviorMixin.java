package github.elmartino4.mechanicalFactory.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$8")
public class FullBucketDispenserBehaviorMixin {
    @Inject(method = "dispenseSilently", cancellable = true, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/dispenser/ItemDispenserBehavior;dispense(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private void dispenseInject(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir){
        BlockPos outPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
        World world = pointer.getWorld();

        if(world.getBlockState(outPos).getBlock() == Blocks.CAULDRON){
            ItemStack outStack = new ItemStack(Items.BUCKET);

            if(stack.getItem() == Items.LAVA_BUCKET){
                world.setBlockState(outPos, Blocks.LAVA_CAULDRON.getDefaultState());
                world.playSound(null, outPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                cir.setReturnValue(outStack);
                return;
            }

            if(stack.getItem() == Items.WATER_BUCKET){
                world.setBlockState(outPos, Blocks.WATER_CAULDRON.getDefaultState().with((Property) LeveledCauldronBlock.LEVEL, Integer.valueOf(3)));
                world.playSound(null, outPos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                cir.setReturnValue(outStack);
                return;
            }

            if(stack.getItem() == Items.POWDER_SNOW_BUCKET){
                world.setBlockState(outPos, Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with((Property)LeveledCauldronBlock.LEVEL, Integer.valueOf(3)));
                world.playSound(null, outPos, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, SoundCategory.BLOCKS, 1.0F, 1.0F);
                cir.setReturnValue(outStack);
                return;
            }
        }
    }
}
