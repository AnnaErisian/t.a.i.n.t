package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Tetraboost extends AbstractTraitLeveled {
	public static final Tetraboost tetraboost = new Tetraboost(1);
	public static final Tetraboost tetraboost2 = new Tetraboost(2);

	public Tetraboost(String identifier, int color, int maxLevels, int levels) {
		super(identifier, color, maxLevels, levels);
	}

	public Tetraboost(int levels) {
		this("tetraboost", 0xffffff, 2, levels);
	}



	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			int level = TinkerUtil.getModifierTag(tool, name).getInteger("level");
			if(level > 2) {
				target.addPotionEffect(new PotionEffect(MobEffects.POISON, 160, 2));
				target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 1));
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 220, 2));
				target.setFire(10);
			} else if(level == 2) {
				target.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1));
				target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40, 0));
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 140, 1));
				target.setFire(5);
			} else {
				switch (random.nextInt(4)) {
					case 0:
						target.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1));
						break;
					case 1:
						target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40, 0));
						break;
					case 2:
						target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 140, 1));
						break;
					case 3:
						target.setFire(5);
				}
			}
		}
	}
}