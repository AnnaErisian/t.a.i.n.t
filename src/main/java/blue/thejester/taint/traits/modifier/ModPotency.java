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
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import java.util.Optional;

public class ModPotency extends ToolModifier implements IWandModifier{

    private static final int countPerLevel = 8;
    private Element element;

    public ModPotency(String id, int color, Element element) {
        super(id, color);
        this.element = element;
        addAspects(new ModifierAspect.MultiAspect(this, 4, countPerLevel, 1));
        this.addAspects(Tools.aspectMagicOnly);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);

        WandNBT wandData = new WandNBT(TagUtil.getTagSafe(rootCompound, Tags.TOOL_DATA_ORIG));
        float potency = getPotency(wandData);
        int level = data.current / countPerLevel;
        for(int count = data.current; count > 0; count--) {
            potency += 0.01;
            potency *= 1.01;
        }

        // each full level gives a flat 0.02 bonus
        potency += level * 0.02f;

        // save it to the tool
        NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
        potency -= getPotency(wandData);
        potency += tag.getFloat(getPotencyTag(wandData));
        tag.setFloat(getPotencyTag(wandData), potency);
    }

    private String getPotencyTag(WandNBT wandData) {
        switch (element) {
            case FIRE: return WandNBT.TAG_FIRE;
            case ICE: return WandNBT.TAG_ICE;
            case LIGHTNING: return WandNBT.TAG_LIGHTNING;
            case EARTH: return WandNBT.TAG_EARTH;
            case NECROMANCY: return WandNBT.TAG_NECROMANCY;
            case SORCERY: return WandNBT.TAG_SORCERY;
            case HEALING: default: return WandNBT.TAG_HEALING;
        }
    }

    private float getPotency(WandNBT wandData) {
        switch (element) {
            case FIRE: return wandData.fire;
            case ICE: return wandData.ice;
            case LIGHTNING: return wandData.lightning;
            case EARTH: return wandData.earth;
            case NECROMANCY: return wandData.necro;
            case SORCERY: return wandData.sorc;
            case HEALING: return wandData.healing;
            default: return 0;
        }
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }
}
