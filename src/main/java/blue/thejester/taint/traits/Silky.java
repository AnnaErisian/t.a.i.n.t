package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Silky extends AbstractTrait {
	public static final Silky silky = new Silky();

	public Silky() {
		super("silky",0xffffff);
	}

	@Override
	public boolean canApplyTogether(IToolMod otherModifier) {
		return !otherModifier.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier())
				&& !otherModifier.getIdentifier().equals(TinkerModifiers.modLuck.getIdentifier());
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment != Enchantments.LOOTING
				&& enchantment != Enchantments.FORTUNE;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);

		// add silktouch if it's not present
		ToolBuilder.addEnchantment(rootCompound, Enchantments.SILK_TOUCH);
	}
}