package blue.thejester.taint.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class MasterChef extends AbstractTrait {
	public static final MasterChef master_chef = new MasterChef();

	public static final float ON_MINE_CHANCE = 0.01f;
	public static final float ON_KILL_CHANCE = 0.03f;

	public MasterChef() {
		super("master_chef",0xffffff);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		saturate(player, ON_MINE_CHANCE);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if(wasHit && target != null && target.getHealth() == 0) {
			saturate(player, ON_KILL_CHANCE);
		}
	}

	protected static void saturate(EntityLivingBase player, float chance) {
		if (!player.world.isRemote && player instanceof EntityPlayer && random.nextFloat() < chance) {
			((EntityPlayer)player).getFoodStats().addStats(4, 20);
		}
	}
}