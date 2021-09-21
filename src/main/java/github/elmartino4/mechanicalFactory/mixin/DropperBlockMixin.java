package github.elmartino4.mechanicalFactory.mixin;

import github.elmartino4.mechanicalFactory.util.DispenserBlockEntityAccess;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropperBlock.class)
public class DropperBlockMixin {
    BlockPos pos;
    ServerWorld world;

    @Inject(method = "dispense", at = @At("HEAD"))
    private void getPos(ServerWorld world, BlockPos pos, CallbackInfo ci){
        this.pos = pos;
        this.world = world;
    }

    @ModifyVariable(method = "dispense", at = @At("STORE"), ordinal = 1)
    private ItemStack modifyOutStack(ItemStack original){
        //System.out.println(original.toString());
        DispenserBlockEntity ent = (DispenserBlockEntity)world.getBlockEntity(pos);
        Item item = ((DispenserBlockEntityAccess)ent).getItem();
        if(item != null)
            return new ItemStack(item);
        return original;
    }

    @ModifyVariable(method = "dispense", at = @At("STORE"), ordinal = 0)
    private int modifyOutStackIndex(int original){
        DispenserBlockEntity ent = (DispenserBlockEntity)world.getBlockEntity(pos);
        int itemIndex = ((DispenserBlockEntityAccess)ent).getItemIndex();
        if(itemIndex != -1)
            return itemIndex;
        return original;
    }

    @Inject(method = "dispense", at = @At(value = "INVOKE", target = "net/minecraft/block/entity/DispenserBlockEntity.setStack (ILnet/minecraft/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void cancelSetStack(ServerWorld world, BlockPos pos, CallbackInfo ci){
        DispenserBlockEntity ent = (DispenserBlockEntity)world.getBlockEntity(pos);
        Item item = ((DispenserBlockEntityAccess)ent).getItem();
        if(item != null)
            ci.cancel();
    }
}
