package blue.thejester.taint.traits;

import landmaster.plustic.traits.Mana;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Arcane extends AbstractTrait {
	public static final Arcane arcane = new Arcane();

	public Arcane() {
		super("arcane",0xffffff);
		aspects.clear(); //remove the "single" aspect since this is a tool type trait
	}

	@Override
	public boolean canApplyTogether(IToolMod otherModifier) {
		return !otherModifier.getIdentifier().equals(TinkerModifiers.modMendingMoss.getIdentifier())
				&& !otherModifier.getIdentifier().equals(TinkerModifiers.modReinforced.getIdentifier())
				&& !otherModifier.getIdentifier().equals(Mana.mana.getIdentifier());
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return super.canApplyTogether(enchantment) && enchantment != Enchantments.MENDING;
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) {
		return true;
	}
}