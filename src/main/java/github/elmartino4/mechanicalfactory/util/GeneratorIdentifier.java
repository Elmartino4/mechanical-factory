package github.elmartino4.mechanicalfactory.util;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

public class GeneratorIdentifier {
    Fluid primary;
    Fluid secondaryFluid;
    Block secondaryBlock;
    Block underneathBlock;
    Block blockOut;

    public GeneratorIdentifier(Fluid primary, Fluid secondaryFluid, Block secondaryBlock, Block underneathBlock, Block out){
        this.primary = primary;
        this.secondaryFluid = secondaryFluid;
        this.secondaryBlock = secondaryBlock;
        this.underneathBlock = underneathBlock;
        this.blockOut = out;
    }

    public int getSimilarity(Fluid primary, Fluid secondaryFluid, Block secondaryBlock, Block underneathBlock){
        int out = 0;

        if(this.secondaryBlock == secondaryBlock) out += 2;
        if(this.secondaryFluid == secondaryFluid) out += 3;
        if(this.underneathBlock == underneathBlock) out += 6;

        if(this.primary != primary) out = 0;
        if(this.underneathBlock != underneathBlock && this.underneathBlock != null) out = 0;
        if(this.secondaryFluid != secondaryFluid && this.secondaryBlock != secondaryBlock) out = 0;

        //System.out.println("out = " + out);
        return out;
    }

    public Block getBlockOut() {
        return blockOut;
    }
}
