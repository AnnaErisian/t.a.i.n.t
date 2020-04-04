package blue.thejester.taint.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.TinkerCommons;

public class GlowingTool extends AbstractTrait {

    public static final GlowingTool glowing = new GlowingTool();

    public GlowingTool() {
        super("glowing_tool_taint", 0xffffaa);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && isSelected) {
            BlockPos pos = entity.getPosition();
            if (entity instanceof EntityLivingBase && world.getLightFromNeighbors(pos) < 8) {
                for (BlockPos candidate : new BlockPos[]{pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {
                    if (TinkerCommons.blockGlow.addGlow(world, candidate, EnumFacing.values()[random.nextInt(6)])) {
                        tool.damageItem(3, (EntityLivingBase) entity);
                        return;
                    }
                }
            }
        }
    }
}
