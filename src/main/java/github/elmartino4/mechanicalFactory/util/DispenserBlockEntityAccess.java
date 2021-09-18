package github.elmartino4.mechanicalFactory.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface DispenserBlockEntityAccess {
    void setItem(Item itm);

    Item getItem();

    void setItemIndex(int itm);

    int getItemIndex();
}
