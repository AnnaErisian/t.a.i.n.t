package blue.thejester.taint.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Detonator extends AbstractTrait {
	public static final Detonator detonator = new Detonator();

	public Detonator() {
		super("detonator_taint",0xffffff);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && !player.world.isRemote) {
			player.world.createExplosion(player, target.posX, target.posY, target.posZ, 0.3f, false);
			player.world.createExplosion(target, target.posX, target.posY, target.posZ, 2.5f, false);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos blockpos, EntityLivingBase player, boolean wasEffective) {
		super.afterBlockBreak(tool, world, state, blockpos, player, wasEffective);
		if(!world.isRemote) {
			float r1 = random.nextFloat() - 0.5f;
			float r2 = random.nextFloat() - 0.5f;
			float r3 = random.nextFloat() - 0.5f;
			float r4 = random.nextFloat() * 2.0f;
			world.createExplosion(player, blockpos.getX()+r1, blockpos.getY()+r2, blockpos.getZ()+r3, 3.0f+r4, true);
		}
	}
}