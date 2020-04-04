package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Hyperspeed extends AbstractTrait {
	public static final Hyperspeed hyperspeed = new Hyperspeed();
	private static final int DURABILITY_COST = 5;

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
		if (event.getWorld().isRemote
				|| event.getEntityPlayer().isSneaking()
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) {
			return;
		}
		synchronized (event.getEntityPlayer()) {
			if(event.getEntityPlayer() != null) {
				EntityPlayer player = event.getEntityPlayer();
				PotionEffect potionEffect = player.getActivePotionEffect(MobEffects.HASTE);
				//Make sure we didn't literally just apply the effect
				if(potionEffect != null && potionEffect.getDuration() != 20*45) {
					int amp = -1;
					if (potionEffect != null) amp = potionEffect.getAmplifier();
					amp = Math.min(4, amp+1);
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 20*45, amp));
				} else {
					player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 20*45));
				}
			}
		}
		if(event.getEntityPlayer() != null) {
			EntityPlayer player = event.getEntityPlayer();
			ToolHelper.damageTool(event.getItemStack(), 15 + 5 * player.getActivePotionEffect(MobEffects.HASTE).getAmplifier(), event.getEntityLiving());
		}
		Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
		event.getEntityPlayer().swingArm(event.getHand());
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
	}
}