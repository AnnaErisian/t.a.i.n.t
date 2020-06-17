package blue.thejester.taint.traits.modifier;

import blue.thejester.taint.modules.Tools;
import blue.thejester.taint.tools.WandNBT;
import electroblob.wizardry.constants.Element;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import java.util.Optional;

public class ModSlots extends ToolModifier implements IWandModifier {

    private static final int countPerLevel = 3;

    public ModSlots(String id, int color) {
        super(id, color);
        addAspects(new ModifierAspect.MultiAspect(this, 1, countPerLevel, 1));
        this.addAspects(Tools.aspectMagicOnly);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);

        WandNBT wandData = new WandNBT(TagUtil.getTagSafe(rootCompound, Tags.TOOL_DATA_ORIG));
        int slots = wandData.upgradeSlots;
        for(int count = data.current; count > 0; count--) {
            slots ++;
        }

        // save it to the tool
        NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
        slots -= wandData.upgradeSlots;
        slots += tag.getFloat(WandNBT.TAG_UPGRADE_SLOTS);
        tag.setFloat(WandNBT.TAG_UPGRADE_SLOTS, slots);
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }
}
