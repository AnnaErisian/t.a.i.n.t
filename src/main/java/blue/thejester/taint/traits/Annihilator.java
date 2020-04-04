package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Annihilator extends AbstractTrait {
	public static final Annihilator annihilator = new Annihilator();

	public Annihilator() {
		super("annihilator",0xffffff);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && !(target instanceof EntityPlayer)) {
			if(target.getHealth() * 10 <= target.getMaxHealth()) {
				//We have a target, give them a colossal slap
				if(player != null) {
					target.attackEntityFrom(new EntityDamageSource("annihilator", player), 1000);
				} else {
					target.setHealth(0);
				}
				//And make them poof on the next tick
				target.deathTime = 19;
			}
		}
	}

}