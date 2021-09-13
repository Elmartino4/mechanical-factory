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
    BlockState blockOut;

    public GeneratorIdentifier(FluidState primary, FluidState secondaryFluid, BlockState secondaryBlock, BlockState underneathBlock, BlockState out){
        primary = primary.getFluid().getDefaultState();
        if (secondaryFluid != null) secondaryFluid = secondaryFluid.getFluid().getDefaultState();
        this.primary = primary;
        this.secondaryFluid = secondaryFluid;
        this.secondaryBlock = secondaryBlock;
        this.underneathBlock = underneathBlock;
        this.blockOut = out;
    }

    public int getSimilarity(FluidState primary, FluidState secondaryFluid, BlockState secondaryBlock, BlockState underneathBlock){
        int out = 0;

        if(this.secondaryBlock == secondaryBlock) out += 2;
        if(this.secondaryFluid == secondaryFluid.getFluid().getDefaultState()) out += 3;
        if(this.underneathBlock == underneathBlock) out += 6;

        if(this.primary != primary.getFluid().getDefaultState()) out = 0;
        if(this.underneathBlock != underneathBlock && this.underneathBlock != null) out = 0;
        if(this.secondaryFluid != secondaryFluid.getFluid().getDefaultState() && this.secondaryBlock != secondaryBlock) out = 0;

        //System.out.println("out = " + out);
        return out;
    }

    public BlockState getBlockOut() {
        return blockOut;
    }
}
