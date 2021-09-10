package github.elmartino4.mechanicalFactory.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow BlockState block;

    public FallingBlockEntityMixin(EntityType<?> entityType_1, World world_1)
    {
        super(entityType_1, world_1);
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void onTick(CallbackInfo ci)
    {
        BlockPos pos = getBlockPos();
        BlockPos hit =
                this.world.raycast(new RaycastContext(
                        new Vec3d(this.prevX, this.prevY, this.prevZ),
                        getPos(),
                        RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.SOURCE_ONLY, this
                )).getBlockPos().down();

        if (block.isOf(Blocks.ANVIL))
        {
            if (this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
                world.breakBlock(hit, false);
                //world.setBlockState(blockPos_2.down(), Blocks.SAND.getDefaultState(), 3);
            }
        }
    }
}
