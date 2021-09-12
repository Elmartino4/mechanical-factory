package github.elmartino4.mechanicalFactory;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.Tag;

public class GeneratorIdentifier {
    FluidState primary;
    FluidState secondaryFluid;
    BlockState secondaryBlock;
    BlockState underneathBlock;

    public GeneratorIdentifier(FluidState primary, FluidState secondaryFluid, BlockState secondaryBlock, BlockState underneathBlock){
        primary = primary.getFluid().getDefaultState();
        secondaryFluid = secondaryFluid.getFluid().getDefaultState();
        this.primary = primary;
        this.secondaryFluid = secondaryFluid;
        this.secondaryBlock = secondaryBlock;
        this.underneathBlock = underneathBlock;
    }

    public String toString(){
        String[] strs = new String[4];
        strs[0] = primary.toString() + " @ " + primary.hashCode();
        strs[1] = secondaryFluid.toString() + " @ " + secondaryFluid.hashCode();
        strs[2] = secondaryBlock.toString() + " @ " + secondaryBlock.hashCode();
        strs[3] = (underneathBlock != null) ? underneathBlock.toString() : "null";
        //strs[3] += " @ " + underneathBlock.hashCode();
        return String.format("{primary: %s, secondaryFluid: %s, secondaryBlock: %s, underneathBlock: %s} at ", strs) + this.hashCode();
    }

    public int hashCode(){
        //primary.hashCode()%200 + 200*(secondaryFluid.hashCode()%200) + ;
        int out = 0;
        if(primary != null) out += primary.hashCode()*3;
        if(secondaryFluid != null) out += secondaryFluid.hashCode()*5;
        if(secondaryBlock != null) out += secondaryBlock.hashCode()*7;
        if(underneathBlock != null) out += underneathBlock.hashCode()*11;
        return out;
    }

    public boolean equals(Object o){
        return this.hashCode() == o.hashCode();
    }
}
