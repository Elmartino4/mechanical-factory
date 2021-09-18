package github.elmartino4.mechanicalFactory.mixin;

import github.elmartino4.mechanicalFactory.util.DispenserBlockEntityAccess;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DispenserBlockEntity.class)
public class DispenserBlockEntityMixin implements DispenserBlockEntityAccess {
    @Unique Item takenItem;

    @Unique int itemIndex;

    @Override
    public void setItem(Item itm) {
        takenItem = itm;
    }

    @Override
    public Item getItem() {
        return takenItem;
    }

    @Override
    public void setItemIndex(int itm) {
        itemIndex = itm;
    }

    @Override
    public int getItemIndex() {
        return itemIndex;
    }
}