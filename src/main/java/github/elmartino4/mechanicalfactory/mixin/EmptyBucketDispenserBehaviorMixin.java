package github.elmartino4.mechanicalfactory.mixin;

import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$9")
public class EmptyBucketDispenserBehaviorMixin {
    @Shadow ItemDispenserBehavior fallbackBehavior;

    @Inject(method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", cancellable = true, at = @At("HEAD"))
    private void dispenseInject(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir){
        BlockPos outPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
        World world = pointer.getWorld();

        Block outBlockOld = world.getBlockState(outPos).getBlock();

        if(outBlockOld instanceof AbstractCauldronBlock){
            world.setBlockState(outPos, Blocks.CAULDRON.getDefaultState());
            boolean level = true;
            Item item2 = null;

            if(outBlockOld == Blocks.LAVA_CAULDRON){
                item2 = Items.LAVA_BUCKET;
            }

            if(outBlockOld == Blocks.WATER_CAULDRON){
                item2 = Items.WATER_BUCKET;
                //level = ((Integer)world.getBlockState(outPos).get((Property)LeveledCauldronBlock.LEVEL)).intValue() == 3;

                //if(world.getBlockState(outPos).getProperties().contains((Property) LeveledCauldronBlock.LEVEL)){

                //}else{
                //    System.out.println("property not found");
                //}
            }

            if(outBlockOld == Blocks.POWDER_SNOW_CAULDRON){
                item2 = Items.POWDER_SNOW_BUCKET;
                //level = (Integer)world.getBlockState(outPos).get((Property) LeveledCauldronBlock.LEVEL) == 3;
            }

            if(item2 != null && level){
                stack.decrement(1);
                if (stack.isEmpty()) {
                    cir.setReturnValue(new ItemStack(item2));
                    world.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, outPos);
                }else{
                    if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(item2)) < 0)
                        this.fallbackBehavior.dispense(pointer, new ItemStack(item2));

                    cir.setReturnValue(stack);
                }
                System.out.println(cir.getReturnValue().toString());
            }
        }
    }
}
