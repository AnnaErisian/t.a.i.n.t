package blue.thejester.taint.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class SoulDrain extends AbstractTrait {
	public static final SoulDrain soulDrain = new SoulDrain();

	public SoulDrain(String identifier, int color) {
		super(identifier, color);
	}

	public SoulDrain() {
		this("soulDrain", 0xffffff);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		//85% damage plus 1/6th the target's health, max +10 (for 60HP)
		return newDamage * 0.85f + Math.max(10, target.getMaxHealth() / 6f);
	}

}