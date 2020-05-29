package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.tools.ranged.ProjectileLauncherCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Hyperspeed extends AbstractTrait {
	public static final Hyperspeed hyperspeed = new Hyperspeed();
	private static final int DURABILITY_COST = 5;
	private static final int MAX_AMP = 4;
	private static final int DURATION = 20 * 45;

	public Hyperspeed() {
		super("hyperspeed",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void activateFlightEn(PlayerInteractEvent.EntityInteract event) {
		hasten(event);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void activateFlightBl(PlayerInteractEvent.RightClickBlock event) {
		hasten(event);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void activateFlightEm(PlayerInteractEvent.RightClickItem event) {
		hasten(event);
	}

	private void hasten(PlayerInteractEvent event) {
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		PotionEffect potionEffect = player.getActivePotionEffect(MobEffects.HASTE);
		int amp = -1;
		int remainingTime = 0;
		if (potionEffect != null) {
			amp = potionEffect.getAmplifier();
			remainingTime = potionEffect.getDuration();
		}
		amp = Math.min(MAX_AMP, amp+1);
		if (event.getWorld().isRemote
				|| event.getEntityPlayer().isSneaking()
				|| event.getItemStack() == null
				|| event.getItemStack().getItemUseAction() == EnumAction.BLOCK
				|| event.getItemStack().getItem() instanceof ProjectileLauncherCore
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) {
			return;
		}
		synchronized (event.getEntityPlayer()) {
			if(event.getEntityPlayer() != null) {
				//Make sure we didn't literally just apply the effect
				if(potionEffect != null && potionEffect.getDuration() != DURATION) {
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, DURATION, amp));
				} else {
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, DURATION));
				}
			}
		}
		int cost = (int) ((15 + Math.pow(5, amp)) * (DURATION - remainingTime) / DURATION);
		ToolHelper.damageTool(event.getItemStack(), cost, event.getEntityPlayer());
		Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
		event.getEntityPlayer().swingArm(event.getHand());
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
	}
}