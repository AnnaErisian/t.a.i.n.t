package blue.thejester.taint.traits;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.ModBlocks;

public class FlowerPower extends AbstractTrait {
	public static final FlowerPower flowerpower = new FlowerPower();

	public FlowerPower() {
		super("flowerpower",0xffffff);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		super.afterBlockBreak(tool, world, state, pos, player, wasEffective);
		placeFlowers(world, player);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		placeFlowers(player.world, player);
	}

	private void placeFlowers(World world, EntityLivingBase player) {
		if(world.getWorldTime() % 4 == 0) {
			int x = (int) (player.posX + random.nextInt(7) - 3);
			int y = (int) (player.posY + random.nextInt(4) - 2);
			int z = (int) (player.posZ + random.nextInt(7) - 3);
			BlockPos p = new BlockPos(x,y,z);
			BlockPos up = p.up();
			if(world.isAirBlock(up) && world.getBlockState(up).getMaterial() != Material.WATER && ModBlocks.flower.canPlaceBlockAt(world, up)) {
				world.setBlockState(up, ModBlocks.flower.getDefaultState().withProperty(BotaniaStateProps.COLOR, EnumDyeColor.byDyeDamage(random.nextInt(16))));
			}
		}
	}
}