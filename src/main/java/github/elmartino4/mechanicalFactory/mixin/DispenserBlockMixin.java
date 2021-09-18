package github.elmartino4.mechanicalFactory.mixin;

import github.elmartino4.mechanicalFactory.MechanicalFactory;
import github.elmartino4.mechanicalFactory.util.DispenserBlockEntityAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    private static final DispenserBehavior BEHAVIOR = (DispenserBehavior)new ItemDispenserBehavior();

    @Unique int slot;

    //Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;
    //Lnet/minecraft/block/entity/DispenserBlockEntity;getStack(I)Lnet/minecraft/item/ItemStack;
    //INVOKEVIRTUAL net/minecraft/block/entity/DispenserBlockEntity.getStack (I)Lnet/minecraft/item/ItemStack;
    @Inject(method="neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void injectUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci){
        boolean hasPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean isTriggered = (Boolean)state.get((Property) Properties.TRIGGERED);

        BlockPointer pointer = (BlockPointer) new BlockPointerImpl((ServerWorld) world, pos);
        Direction direc = (Direction)pointer.getBlockState().get((Property)DispenserBlock.FACING);

        if(world.getBlockTickScheduler().isScheduled(pos, Blocks.DROPPER)){
            ci.cancel();
            return;
        }

        if(hasPower && !isTriggered && world.getBlockState(pos.offset(direc)).getBlock() == Blocks.SCAFFOLDING && world.getBlockState(pos).getBlock() == Blocks.DROPPER){
            DispenserBlockEntity ent = (DispenserBlockEntity)world.getBlockEntity(pos);
            int slot = ent.chooseNonEmptySlot();
            //System.out.println("cancelled partly");

            if(slot < 0){
                world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0);
                world.emitGameEvent(GameEvent.DISPENSE_FAIL, pos);

                return;
            }

            Item itm = ent.getStack((slot)).getItem();

            //System.out.println("cancelled partly +1");

            if(!MechanicalFactory.sieveMap.containsKey(itm)){
                ((DispenserBlockEntityAccess)ent).setItem(null);
                ((DispenserBlockEntityAccess)ent).setItemIndex(slot);
                System.out.println("stopped cancel because of " + itm.getTranslationKey());
                return;
            }

            //System.out.println("cancelled partly +2");

            world.getBlockTickScheduler().schedule(pos, Blocks.DROPPER, MechanicalFactory.sieveTimer);
            world.setBlockState(pos, (BlockState)state.with((Property) Properties.TRIGGERED, true), Block.NO_REDRAW);

            ent.removeStack(slot, 1);

            //System.out.println("cancelled partly +3");

            BlockPos pos2 = pos.offset(direc);
            ItemEntity itmEnt = new ItemEntity(world, pos2.getX() + 0.5, pos2.getY() + 0.2, pos2.getZ() + 0.5, new ItemStack(itm, 1));
            ((ItemEntityAccessor) itmEnt).setItemAge(6000 - MechanicalFactory.sieveTimer);
            itmEnt.setNoGravity(true);
            itmEnt.setPickupDelayInfinite();
            itmEnt.setVelocity(0, 0, 0);
            world.spawnEntity(itmEnt);

            System.out.println("cancelled ");

            //Item
            ((DispenserBlockEntityAccess)ent).setItem(itm);
            ((DispenserBlockEntityAccess)ent).setItemIndex(slot);
            ci.cancel();
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void scheduleTickInject(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){
        Item itm = ((DispenserBlockEntityAccess)world.getBlockEntity(pos)).getItem();
        if(itm != null){
            ((DispenserBlockEntityAccess)world.getBlockEntity(pos)).setItem(null);
            ((DispenserBlockEntityAccess)world.getBlockEntity(pos)).setItemIndex(-1);

            newDispense(world, pos, itm);

            ci.cancel();
        }
    }

    private void newDispense(ServerWorld world, BlockPos pos, Item itm) {
        BlockPointerImpl lv = new BlockPointerImpl(world, pos);

        BEHAVIOR.dispense((BlockPointer)lv, new ItemStack(itm));
    }
}
