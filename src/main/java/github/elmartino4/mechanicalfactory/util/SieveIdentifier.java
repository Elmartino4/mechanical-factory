package github.elmartino4.mechanicalfactory.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class SieveIdentifier {
    int defaultWeighing;
    int delayTicks;
    ArrayList<OutItemData> data = new ArrayList<>();

    public SieveIdentifier(int defaultWeighing, int delayTicks){
        this.delayTicks = delayTicks;
        this.defaultWeighing = defaultWeighing;
    }

    protected SieveIdentifier(int defaultWeighing, int delayTicks, ArrayList<OutItemData> data){
        this.defaultWeighing = defaultWeighing;
        this.delayTicks = delayTicks;
        this.data = data;
    }

    public SieveIdentifier clone(){
        return new SieveIdentifier(this.defaultWeighing, this.delayTicks, this.data);
    }

    public int getDelay(){
        return this.delayTicks;
    }

    public void empty(int defaultWeighing){
        this.defaultWeighing = defaultWeighing;
        data = new ArrayList<>();
    }

    public ItemStack selectItem(Random random){
        int i = defaultWeighing;

        for (OutItemData aData : data) {
            i += aData.getWeighing();
        }

        int val = random.nextInt(i);

        if(val < defaultWeighing){
            //System.out.println("returned null due to default weighing");
            return null;
        }

        i = defaultWeighing;

        for (OutItemData aData : data) {
            if (i <= val && val < i + aData.getWeighing())
                return aData.getStack(random);

            i += aData.getWeighing();
        }

        //System.out.println("returned null due to failed get");
        return null;
    }

    public void put(int weighing, int minRange, int maxRange, Item item){
        data.add(new OutItemData(weighing, minRange, maxRange, item));
    }

    public void put(int weighing, Item item){
        put(weighing, 1, 1, item);
    }

    public void setDefaultWeighing(int defaultWeighing){
        this.defaultWeighing = defaultWeighing;
    }
}

class OutItemData {
    int weighing;
    int minRange;
    int maxRange;
    Item item;

    public OutItemData(int weighing, int minRange, int maxRange, Item item){
        this.weighing = weighing;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.item = item;
    }

    public ItemStack getStack(){
        return getStack(new Random());
    }

    public ItemStack getStack(Random random){
        if(maxRange == minRange) return new ItemStack(item, minRange);

        int quant = minRange + random.nextInt(maxRange - minRange);
        //System.out.println(quant);
        return new ItemStack(item, quant);
    }

    public int getWeighing() {
        return weighing;
    }
}