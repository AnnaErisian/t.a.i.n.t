package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class AbsoluteDominion extends AbstractTrait {
	public static final AbsoluteDominion absoluteDominion = new AbsoluteDominion();

	public AbsoluteDominion() {
		super("absolute_domain",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void captureEntity(PlayerInteractEvent.EntityInteract event) {
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		if (event.getWorld().isRemote
				|| !event.getEntityPlayer().isSneaking()
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())
				|| ToolHelper.getCurrentDurability(event.getItemStack()) < durabilityCost(event.getTarget())
				|| event.getTarget() instanceof EntityPlayer) {
			return;
		}
		synchronized (event.getTarget()) {
			Entity entity = event.getTarget();
			if(entity instanceof EntityLiving) {
				boolean newDisabledState = !((EntityLiving)entity).isAIDisabled();
				((EntityLiving)entity).setNoAI(newDisabledState);
				event.getEntityPlayer().sendMessage(new TextComponentTranslation(
						newDisabledState
								? Util.translateFormatted("msg.taint.absolutedomainmodifier.disable", entity.getDisplayName().getUnformattedText())
								: Util.translateFormatted("msg.taint.absolutedomainmodifier.release", entity.getDisplayName().getUnformattedText())));
			}
		}
		ToolHelper.damageTool(event.getItemStack(), durabilityCost(event.getTarget()), event.getEntityLiving());
		Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
		event.getEntityPlayer().swingArm(event.getHand());
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
	}

	private int durabilityCost(Entity entity) {
		return Math.max(15, entity instanceof EntityLivingBase ?
				(int)((EntityLivingBase)entity).getHealth() * 100 : 150);
	}
}