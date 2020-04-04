package blue.thejester.taint.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.TinkerCommons;

public class GlowingAllArmor extends AbstractArmorTrait {

    public static final GlowingAllArmor glowing = new GlowingAllArmor();

    public GlowingAllArmor() {
        super("glowing_taint", 0xffffaa);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        if(!world.isRemote) {

            BlockPos pos = player.getPosition();

            if(world.getLightFromNeighbors(pos) < 8) {
                for(BlockPos candidate : new BlockPos[]{pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {

                    if(TinkerCommons.blockGlow.addGlow(world, candidate, EnumFacing.values()[random.nextInt(6)])) {
                        ArmorHelper.damageArmor(armor, DamageSource.causePlayerDamage(player), 1, player);
                        return;
                    }
                }
            }
        }
    }
}
