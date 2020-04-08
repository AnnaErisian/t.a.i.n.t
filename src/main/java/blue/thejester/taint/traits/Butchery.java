package blue.thejester.taint.traits;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ArrayList;
import java.util.List;

public class Butchery extends AbstractTrait {
	public static final Butchery butchery = new Butchery();
	private static final float ENTITY_DROP_BOOST_CHANCE = 0.2f;

	public Butchery() {
		super("butchery",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onXpDrop(LivingExperienceDropEvent event) {
		EntityPlayer player = event.getAttackingPlayer();
		if (player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player.getHeldItemMainhand()), this.identifier)
				&& event.getDroppedExperience() > 0) {
			event.setDroppedExperience(this.getReducedXp(event.getDroppedExperience()));
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (!event.getWorld().isRemote && player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player
				.getHeldItemMainhand()), this.identifier) && event.getExpToDrop() > 0) {
			event.setExpToDrop(this.getReducedXp(event.getExpToDrop()));
		}
	}

	@SubscribeEvent
	public void onMobDrops(LivingDropsEvent event) {
		World w = event.getEntity().getEntityWorld();
		if (random.nextFloat() < .1f && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (!w.isRemote && event.getEntity() instanceof EntityMob && TinkerUtil.hasTrait(TagUtil.getTagSafe
					(player.getHeldItemMainhand()), identifier)) {
				boostDrops(event.getDrops(), ENTITY_DROP_BOOST_CHANCE);
			}
		}
	}

	private int getReducedXp(int xp) {
		float exp = (random.nextFloat()+1) * (random.nextFloat()+1) * xp / 5.0f;
		return Math.round(exp);
	}

	private void boostDrops(List<EntityItem> drops, float boostChance) {
		for(EntityItem d : drops) {
			if(random.nextFloat() > boostChance) {
				d.getItem().setCount(d.getItem().getCount()+1);
			}
		}
	}

}