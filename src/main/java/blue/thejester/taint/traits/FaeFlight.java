package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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

public class FaeFlight extends AbstractTrait {
	public static final FaeFlight faeFlight = new FaeFlight();
	private static final int DURABILITY_COST = 5;

	public FaeFlight() {
		super("fae_flight",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void activateFlightEn(PlayerInteractEvent.EntityInteract event) {
		fly(event);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void activateFlightBl(PlayerInteractEvent.RightClickBlock event) {
		fly(event);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void activateFlightEm(PlayerInteractEvent.RightClickItem event) {
		fly(event);
	}

	private void fly(PlayerInteractEvent event) {
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		if (event.getWorld().isRemote
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) {
			return;
		}
		synchronized (event.getEntity()) {
			Entity entity = event.getEntity();
			if(entity instanceof EntityLivingBase) {
				EntityLivingBase el = (EntityLivingBase) entity;
				entity.fallDistance = 0;
				if(el.isSneaking()) {
					el.removePotionEffect(MobEffects.LEVITATION);
				} else if(ToolHelper.getCurrentDurability(event.getItemStack()) >= DURABILITY_COST){
					el.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20*4, 2, false, true));
					ToolHelper.damageTool(event.getItemStack(), DURABILITY_COST, event.getEntityLiving());
				}
			}
		}
		Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
		event.getEntityPlayer().swingArm(event.getHand());
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
	}
}