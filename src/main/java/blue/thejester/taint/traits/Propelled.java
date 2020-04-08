package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Propelled extends AbstractTrait {
	public static final Propelled propelled = new Propelled();
	private static final int DURABILITY_COST = 10;
	private static final double VELOCITY = 1.85;

	public Propelled() {
		super("propelled",0xffffff);
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
		World world = event.getWorld();
		if (!world.isRemote
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) {
			return;
		}
		Entity entity = event.getEntity();
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase el = (EntityLivingBase) entity;
			if(ToolHelper.getCurrentDurability(event.getItemStack()) >= DURABILITY_COST){
				Vec3d vec = el.getLookVec();
				el.motionX = vec.x * VELOCITY;
				el.motionY = vec.y * VELOCITY;
				el.motionZ = vec.z * VELOCITY;
				el.velocityChanged = true;
				world.playSound(null, el.posX, el.posY, el.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
				ToolHelper.damageTool(event.getItemStack(), DURABILITY_COST, event.getEntityLiving());
				Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				event.setCancellationResult(EnumActionResult.SUCCESS);

			}
		}
	}
}