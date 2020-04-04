package blue.thejester.taint.traits;

import blue.thejester.taint.traits.potioneffects.SlipperyPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Slippery extends AbstractTrait {
	public static final Slippery slippery = new Slippery();

	public Slippery() {
		super("slippery",0xffffff);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			target.addPotionEffect(new PotionEffect(SlipperyPotion.potion, 100, 0, false, false));
		}
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
		return newKnockback + 1f;
	}
}