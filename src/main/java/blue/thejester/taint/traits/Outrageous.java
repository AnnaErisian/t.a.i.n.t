/**
 *  code based on Too OP from https://github.com/MinecraftModDevelopmentMods/MoarTinkers
 */

package blue.thejester.taint.traits;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerEvent;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Outrageous extends AbstractTrait {
	public static final Outrageous outrageous = new Outrageous();

	public Outrageous() {
		super("outrageous",0xffffff);
	}

	@SubscribeEvent
	public void onToolBuilding(TinkerEvent.OnItemBuilding event) {
		if (TinkerUtil.hasTrait(event.tag, this.getIdentifier())) {
			ToolNBT data = TagUtil.getToolStats(event.tag);
			NBTTagCompound toolTag = data.get();
			toolTag.setInteger(Tags.FREE_MODIFIERS, 0);
			TagUtil.setToolTag(event.tag, data.get());
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
		toolTag.setInteger(Tags.FREE_MODIFIERS, 0);
		TagUtil.setToolTag(rootCompound, toolTag);
	}

}