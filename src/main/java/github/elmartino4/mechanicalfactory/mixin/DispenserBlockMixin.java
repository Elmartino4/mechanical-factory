package github.elmartino4.mechanicalfactory.mixin;

import github.elmartino4.mechanicalfactory.MechanicalFactory;
import github.elmartino4.mechanicalfactory.util.DispenserBlockEntityAccess;
import github.elmartino4.mechanicalfactory.util.SieveIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    private static final DispenserBehavior BEHAVIOR = (DispenserBehavior)new ItemDispenserBehavior();

    @Unique int slot;

    @Inject(method = "dispense", at = @At("HEAD"), cancellable = true)
    private void injectDispense(ServerWorld world, BlockPos pos, CallbackInfo ci){

        BlockPointer pointer = (BlockPointer) new BlockPointerImpl((ServerWorld) world, pos);
        Direction direc = (Direction)pointer.getBlockState().get((Property)DispenserBlock.FACING);

        BlockPos target = pos.offset(direc);

        if(world.getBlockState(pos).getBlock() == Blocks.DISPENSER){
            //System.out.println("called trigger");

            DispenserBlockEntity ent = (DispenserBlockEntity)world.getBlockEntity(pos);

            Item middleItem = ent.getStack(4).getItem();
            if(middleItem == Items.POPPED_CHORUS_FRUIT){

                int slot = ent.chooseNonEmptySlot();
                for (int i = 0; i < 20 && slot == 4; i++) {
                    slot = ent.chooseNonEmptySlot();
                }

                Block toPlace = Block.getBlockFromItem(ent.getStack(slot).getItem());

                if(slot != 4 && toPlace != Blocks.AIR){
                    if(world.isAir(target) || toPlace == Blocks.DRAGON_EGG){

                        world.playSound(null, pos.offset(direc),
                                toPlace.getSoundGroup(toPlace.getDefaultState()).getPlaceSound(),
                                SoundCategory.BLOCKS, 1.0F, 1.0F);

                        world.setBlockState(pos.offset(direc), toPlace.getDefaultState());
                        ent.removeStack(slot, 1);
                        ent.removeStack(4, 1);

                        //System.out.println("cancelled to " + toPlace.toString());
                        ci.cancel();
                        return;
                    }
                }
            } else if(middleItem instanceof MiningToolItem){
                if(((ToolItem)middleItem).getMaterial() == ToolMaterials.DIAMOND){
                    DispenserBlockEntityAccess ent2 = (DispenserBlockEntityAccess) world.getBlockEntity(pos);
                    BlockState toBreak = world.getBlockState(target);
                    ItemStack toolStack = ent.getStack(4);

                    float breakTime = getBreakTime(toBreak, toolStack, world, target);

                    int progress = ent2.getAndIterateBreakProgress();


                    if(breakTime * (progress * 1.5F + 1) >= 0.7F){
                        ent2.setBreakProgressNone();
                        //break the darn block
                        //System.out.println("Break");
                        world.breakBlock(pos.offset(direc), false);
                        world.setBlockBreakingInfo(-1, pos.offset(direc), -1);

                        LootContext.Builder lootContext = (new LootContext.Builder(world))
                                .random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter((Vec3i)target))
                                .parameter(LootContextParameters.TOOL, toolStack)
                                .optionalParameter(LootContextParameters.BLOCK_ENTITY, world.getBlockEntity(target));

                        toBreak.getDroppedStacks(lootContext).forEach((ItemStack stack) -> {Block.dropStack(world, target, stack);});

                        if(toolStack.damage(1, new Random(), null)){
                            ent.removeStack(4);
                        }else{
                            ent.setStack(4, toolStack);
                        }

                    }else{
                        world.setBlockBreakingInfo(-1, target, (int)(breakTime * (progress * 1.5F + 1) * 10.0F));
                    }

                    ci.cancel();
                    return;
                }
            }
        }
    }

    private static float getBreakTime(BlockState toBreak, ItemStack tool, World world, BlockPos breakPos){
        float mult = tool.getMiningSpeedMultiplier(toBreak);

        if(mult > 1.0F){
            int eff = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, tool);
            if(eff > 0){
                mult += eff * eff + 1;
            }
        }

        mult /= (!toBreak.isToolRequired() || tool.isSuitableFor(toBreak)) ? 30 : 100;
        mult /= toBreak.getHardness(world, breakPos);

        return mult;
    }

    @Inject(method="neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void injectUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci){

        boolean hasPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean isTriggered = (Boolean)state.get((Property) Properties.TRIGGERED);

        BlockPointer pointer = (BlockPointer) new BlockPointerImpl((ServerWorld) world, pos);
        Direction direc = (Direction)pointer.getBlockState().get((Property)DispenserBlock.FACING);

        if(fromPos.equals(pos.offset(direc))){
            ((DispenserBlockEntityAccess)world.getBlockEntity(pos)).setBreakProgressNone();
            world.setBlockBreakingInfo(-1, pos.offset(direc), -1);
        }

        if(world.getBlockTickScheduler().isScheduled(pos, Blocks.DROPPER) || world.getBlockTickScheduler().isScheduled(pos, Blocks.DISPENSER)){
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

            SieveIdentifier id = MechanicalFactory.sieveMap.get(itm);

            if(id == null){
                ((DispenserBlockEntityAccess)ent).setItem(null);
                ((DispenserBlockEntityAccess)ent).setItemIndex(slot);
                //System.out.println("stopped cancel because of " + itm.getTranslationKey());
                return;
            }

            int delay = id.getDelay();

            //System.out.println("cancelled partly for " + delay);

            world.getBlockTickScheduler().schedule(pos, Blocks.DROPPER, delay);
            world.setBlockState(pos, (BlockState)state.with((Property) Properties.TRIGGERED, true), Block.NO_REDRAW);

            ent.removeStack(slot, 1);

            //System.out.println("cancelled partly +3");

            BlockPos pos2 = pos.offset(direc);
            ItemEntity itmEnt = new ItemEntity(world, pos2.getX() + 0.5, pos2.getY() + 0.2, pos2.getZ() + 0.5, new ItemStack(itm, 1));
            ((ItemEntityAccessor) itmEnt).setItemAge(6000 - delay);
            itmEnt.setNoGravity(true);
            itmEnt.setPickupDelayInfinite();
            itmEnt.setVelocity(0, 0, 0);
            world.spawnEntity(itmEnt);

            //System.out.println("cancelled ");

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
