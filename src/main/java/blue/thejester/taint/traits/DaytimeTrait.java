package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public abstract class DaytimeTrait extends AbstractTrait {
//	public static final DaytimeTrait flowerpower = new DaytimeTrait();

	private int besttime;

	public DaytimeTrait() {
		this("INVALID",0xffffff, 0);
	}
	public DaytimeTrait(String name, int color, int besttime) {
		super(name, color);
		this.besttime = besttime;
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		float coeff = calcTemporalProximityFactor(event.getEntityPlayer().getEntityWorld(), event.getPos());
		event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * coeff);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		float extraDamage = 2f * calcTemporalProximityFactor(player.getEntityWorld(), player.getPosition());
		return extraDamage + super.damage(tool, player, target, damage, newDamage, isCritical);
	}

	protected float calcTemporalProximityFactor(World world, BlockPos pos) {
		/** The current world time in ticks, ranging from 0 to 23999. */
		long time = world.getWorldTime();
		long distA = (time % 24000 - besttime % 24000) % 24000;
		long distB = (besttime % 24000 - time % 24000) % 24000;
		//dist is ~ from 0 to 1, with 0 being best and 1 the worst
		float dist = Math.min(distA, distB) / 12000f;
		//this make a curve similar to a logistic function
		return (float) (1.1f + Math.pow(Math.cos(dist), 7)/2f);
	}

}