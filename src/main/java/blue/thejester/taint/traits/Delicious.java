package blue.thejester.taint.traits;

import nc.init.NCItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Delicious extends AbstractTrait {
	public static final Delicious delicious = new Delicious();

	public static final float ON_HURT_CHANCE = 0.005f;
	public static final float ON_MINE_CHANCE = 0.0005f;
	public static final float ON_HIT_CHANCE = 0.005f;

	public Delicious() {
		super("delicious",0xffffff);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		spillSmore(player.world, pos.getX(), pos.getY(), pos.getZ(), ON_MINE_CHANCE);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if(wasHit && target != null) {
			spillSmore(player.world, target.posX, target.posY, target.posZ, ON_HIT_CHANCE);
		}
	}

	protected static void spillSmore(World world, double x, double y, double z, float chance) {
		if (!world.isRemote && random.nextFloat() < chance) {
			EntityItem entity = new EntityItem(world, x, y, z, new ItemStack(NCItems.smore));
			world.spawnEntity(entity);
		}
	}
}