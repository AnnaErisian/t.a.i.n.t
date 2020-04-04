package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class StatusInflictor extends AbstractTrait {

	public static final StatusInflictor ascendant = new StatusInflictor("ascendant", 0xffffff, MobEffects.LEVITATION, 20 * 2, 5);
	public static final StatusInflictor antigravity = new StatusInflictor("antigravity", 0xffffff, MobEffects.LEVITATION, 20 * 7, 0);
	public static final StatusInflictor frosty = new StatusInflictor("frosty", 0xffffff, MobEffects.SLOWNESS, 5 * 20, 1);
	public static final StatusInflictor tarcoated = new StatusInflictor("tarcoated", 0xffffff, MobEffects.SLOWNESS, 5 * 20, 0);

	private Potion effect;
	private int duration;
	private int amplifier;

	public StatusInflictor(String identifier, int color) {
		super(identifier, color);
	}

	public StatusInflictor(String identifier, int color, Potion eff, int dur)
	{
		this(identifier, color);
		effect = eff;
		duration = dur;
	}

	public StatusInflictor(String identifier, int color, Potion eff, int dur, int amp) {
		this(identifier, color, eff, dur);
		amplifier = amp;
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			target.addPotionEffect(new PotionEffect(effect, duration, amplifier));
		}
	}
}