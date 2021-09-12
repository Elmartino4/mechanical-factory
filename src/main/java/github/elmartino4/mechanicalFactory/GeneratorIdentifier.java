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
        if (secondaryFluid != null) secondaryFluid = secondaryFluid.getFluid().getDefaultState();
        this.primary = primary;
        this.secondaryFluid = secondaryFluid;
        this.secondaryBlock = secondaryBlock;
        this.underneathBlock = underneathBlock;
    }

    public String toString(){
        String[] strs = new String[4];
        strs[0] = primary.toString() + " @ " + primary.hashCode();
        strs[1] = (secondaryFluid != null) ? secondaryFluid.toString() + " @ " + secondaryFluid.hashCode(): "null";
        strs[2] = (secondaryBlock != null) ? secondaryBlock.toString() + " @ " + secondaryBlock.hashCode() : "null";
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

    public int hashCode(boolean secondaryIsBlock, boolean underneath){
        int out = 0;

        if(primary != null) out += primary.hashCode()*3;
        if(secondaryFluid != null && !secondaryIsBlock) out += secondaryFluid.hashCode()*5;
        if(secondaryBlock != null && secondaryIsBlock) out += secondaryBlock.hashCode()*7;
        if(underneathBlock != null && underneath) out += underneathBlock.hashCode()*11;

        return out;
    }

    public BlockState getSecondaryBlock(){
        return secondaryBlock;
    }

    public FluidState getSecondaryFluid(){
        return secondaryFluid;
    }

    public BlockState getUnderneathBlock() {
        return underneathBlock;
    }

    public boolean equals(Object o){
        GeneratorIdentifier oAsID = (GeneratorIdentifier)o;
        if (this.hashCode() == o.hashCode()) return true;
        if (this.secondaryBlock == null || oAsID.getSecondaryBlock() == null){
            if (this.hashCode(false, true) == oAsID.hashCode(false, true)) return true;
            if (this.underneathBlock == null || oAsID.getUnderneathBlock() == null)
                if (this.hashCode(false, false) == oAsID.hashCode(false, false)) return true;
        }

        if (this.secondaryFluid == null || oAsID.getSecondaryFluid() == null){
            if (this.hashCode(true, true) == oAsID.hashCode(true, true)) return true;
            if (this.underneathBlock == null || oAsID.getUnderneathBlock() == null)
                if (this.hashCode(true, false) == oAsID.hashCode(true, false)) return true;
        }
        return false;
    }
}
