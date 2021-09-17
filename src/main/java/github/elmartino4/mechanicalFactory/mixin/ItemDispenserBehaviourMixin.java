package github.elmartino4.mechanicalFactory.mixin;

import github.elmartino4.mechanicalFactory.MechanicalFactory;
import github.elmartino4.mechanicalFactory.util.SieveIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ItemDispenserBehavior.class)
public class ItemDispenserBehaviourMixin {
    BlockPos pos;

    @ModifyVariable(method = "dispenseSilently", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEINTERFACE, target = "Lnet/minecraft/util/math/BlockPointer;getBlockState()Lnet/minecraft/block/BlockState;"))
    private BlockPointer getVar(BlockPointer original){
        //System.out.println("called inject");
        pos = original.getPos();
        return original;
    }

    @ModifyArgs(method = "dispenseSilently", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/dispenser/ItemDispenserBehavior;spawnItem(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/Position;)V"))
    //args are; spawnItem(World world, ItemStack stack, int offset, Direction side, Position pos)
    private void argMixin(Args args) {
        //System.out.println("checked block = " + pos.offset((Direction)args.get(3)).toShortString());
        World world = args.get(0);
        BlockPos pos2 = pos.offset((Direction)args.get(3));
        BlockState state = world.getBlockState(pos2);

        if(state.getBlock() == Blocks.SCAFFOLDING && world.getBlockState(pos).getBlock() == Blocks.DROPPER){
            //System.out.println("is Scaffolding");
            SieveIdentifier id = MechanicalFactory.sieveMap.get(((ItemStack)args.get(1)).getItem());
            if(id != null){
                ItemStack itm = id.selectItem(world.random);
                System.out.println("set Item " + ((itm != null) ? itm.getTranslationKey() : "null"));

                ItemEntity itmEnt;
                itmEnt = new ItemEntity(world, pos2.getX() + 0.5, pos2.getY() + 0.2, pos2.getZ() + 0.5, (ItemStack)args.get(1));

                args.set(1, itm);
                world.removeBlock(pos2, false);
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos2, Block.getRawIdFromState(Blocks.SCAFFOLDING.getDefaultState()));


                ((ItemEntityAccessor) itmEnt).setItemAge(6000 - 20 * MechanicalFactory.sieveTimer);
                itmEnt.setNoGravity(true);
                itmEnt.setPickupDelayInfinite();
                itmEnt.setVelocity(0, 0, 0);
                world.spawnEntity(itmEnt);

            }
        }
    }

    @Inject(method = "spawnItem", at = @At("HEAD"), cancellable = true)
    private static void injectToCancel(World world, ItemStack stack, int offset, Direction side, Position pos, CallbackInfo ci){
        if(stack == null) ci.cancel();
    }
}