package blue.thejester.taint.traits;

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

public class Decimator extends AbstractTrait {
	public static final Decimator decimator = new Decimator();
	private static final float ENTITY_DROP_DESTROY_CHANCE = 0.2f;
	private static final float BLOCK_DROP_DESTROY_CHACE = 0.3f;

	public Decimator() {
		super("decimator",0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onXpDrop(LivingExperienceDropEvent event) {
		EntityPlayer player = event.getAttackingPlayer();
		if (player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player.getHeldItemMainhand()), this.identifier)
				&& event.getDroppedExperience() > 0) {
			event.setDroppedExperience(this.getBoostedXp(event.getDroppedExperience()));
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (!event.getWorld().isRemote && player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player
				.getHeldItemMainhand()), this.identifier) && event.getExpToDrop() > 0) {
			event.setExpToDrop(this.getBoostedXp(event.getExpToDrop()));
		}
	}

	@SubscribeEvent
	public void onMobDrops(LivingDropsEvent event) {
		World w = event.getEntity().getEntityWorld();
		if (random.nextFloat() < .1f && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (!w.isRemote && event.getEntity() instanceof EntityMob && TinkerUtil.hasTrait(TagUtil.getTagSafe
					(player.getHeldItemMainhand()), identifier)) {
				reduceDrops(event.getDrops(), ENTITY_DROP_DESTROY_CHANCE);
			}
		}
	}

	private int getBoostedXp(int xp) {
		float exp = (random.nextFloat()/3+1) * (random.nextFloat()/3+1) * xp * 1.2f;
		return Math.round(exp);
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
		List<ItemStack> drops = event.getDrops();
		reduceDrops(drops, BLOCK_DROP_DESTROY_CHACE);
		super.blockHarvestDrops(tool, event);

	}

	private void reduceDrops(List drops, float deleteChance) {
		ArrayList<Object> newDrops = new ArrayList<>();
		for(Object d : drops) {
			if(random.nextFloat() > deleteChance) {
				newDrops.add(d);
			}
		}
		drops.clear();
		//we know this is safe because everything to add comes from the set pre-clear
		drops.addAll(newDrops);
	}

}