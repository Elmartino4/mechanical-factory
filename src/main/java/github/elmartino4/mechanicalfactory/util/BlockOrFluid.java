package github.elmartino4.mechanicalfactory.util;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

import java.util.Objects;

public class BlockOrFluid {
    public final Block block;
    public final Fluid fluid;

    public BlockOrFluid(Block block){
        this.block = block;
        this.fluid = null;
    }

    public BlockOrFluid(Fluid fluid){
        this.block = null;
        this.fluid = fluid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            //System.out.println("equal");
            return true;
        }
        //if (o == null || getClass() != o.getClass()) return false;
        BlockOrFluid that = (BlockOrFluid) o;
        return that.block == this.block || that.fluid == this.fluid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, fluid);
    }

    @Override
    public String toString() {
        if(block != null)
            return block.toString();

        return fluid.toString();
    }
}
