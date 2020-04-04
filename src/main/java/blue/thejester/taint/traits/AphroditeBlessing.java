package blue.thejester.taint.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class AphroditeBlessing extends AbstractTrait {
	public static final AphroditeBlessing aphroditeBlessing = new AphroditeBlessing();

	public AphroditeBlessing() {
		super("aphrodite_blessing",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void captureEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getWorld().isRemote
				|| !event.getEntityPlayer().isSneaking()
				|| event.getItemStack() == null
				|| (!(event.getTarget() instanceof EntityVillager)
				&& !(event.getTarget() instanceof EntityAnimal))
				|| !TinkerUtil.hasTrait(TagUtil.getTagSafe(event.getItemStack()), getIdentifier())
				|| ToolHelper.getCurrentDurability(event.getItemStack()) < 10
				|| event.getTarget() instanceof EntityPlayer) {
			return;
		}
		synchronized (event.getTarget()) {
			Entity entity = event.getTarget();
			if(entity instanceof EntityVillager) {
				((EntityVillager) entity).setIsWillingToMate(true);
			}
			if(entity instanceof EntityAnimal) {
				((EntityAnimal) entity).setInLove(event.getEntityPlayer());
			}
		}
		ToolHelper.damageTool(event.getItemStack(), 10, event.getEntityLiving());
		event.getEntityPlayer().swingArm(event.getHand());
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
	}
}