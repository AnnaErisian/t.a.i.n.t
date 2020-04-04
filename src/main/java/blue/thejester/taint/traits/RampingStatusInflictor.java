package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class RampingStatusInflictor extends AbstractTrait {

	public static final RampingStatusInflictor ice_age = new RampingStatusInflictor("ice_age", 0xffffff, MobEffects.SLOWNESS, 130, 5);
	public static final RampingStatusInflictor nuclear_winter = new RampingStatusInflictor("nuclear_winter", 0xffffff, MobEffects.WITHER, 260, 5);
	public static final RampingStatusInflictor toxic = new RampingStatusInflictor("toxic", 0xffffff, MobEffects.POISON, 130, 5);

	private Potion effect;
	private int duration;
	private int ampLimit;

	public RampingStatusInflictor(String identifier, int color) {
		super(identifier, color);
	}

	public RampingStatusInflictor(String identifier, int color, Potion eff, int dur)
	{
		this(identifier, color);
		effect = eff;
		duration = dur;
	}

	public RampingStatusInflictor(String identifier, int color, Potion eff, int dur, int ampLim) {
		this(identifier, color, eff, dur);
		ampLimit = ampLim;
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			int amp = -1;
			PotionEffect potionEffect = target.getActivePotionEffect(effect);
			if (potionEffect != null) amp = potionEffect.getAmplifier();
			amp = Math.min(ampLimit, amp+1);
			target.addPotionEffect(new PotionEffect(effect, duration, amp));
		}
	}
}