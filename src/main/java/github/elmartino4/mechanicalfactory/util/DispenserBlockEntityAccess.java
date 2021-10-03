package github.elmartino4.mechanicalfactory.util;

import net.minecraft.item.Item;

public interface DispenserBlockEntityAccess {
    void setItem(Item itm);

    Item getItem();

    void setItemIndex(int itm);

    int getItemIndex();

    int getAndIterateBreakProgress();

    void setBreakProgressNone();
}
