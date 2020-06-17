package blue.thejester.taint.tools;

import blue.thejester.taint.traits.wands.IWandTrait;
import electroblob.wizardry.constants.Constants;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.WandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nullable;

public class TaintWandHelper {
    public static int getAttunementLevel(ItemStack stack) {
        return WandHelper.getUpgradeLevel(stack, WizardryItems.attunement_upgrade);
    }

    public static int getStorageLevel(ItemStack stack) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.storage_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyStorageLevel(level);
            }
        }
        return level;
    }

    public static int getSiphonLevel(ItemStack stack) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.siphon_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifySiphonLevel(level);
            }
        }
        return level;
    }

    public static int getMeleeLevel(ItemStack stack) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyMeleeLevel(level);
            }
        }
        return level;
    }

    public static int getCondenserLevel(ItemStack stack) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.condenser_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyCondenserLevel(level);
            }
        }
        return level;
    }

    public static int getBlastLevel(ItemStack stack, @Nullable Element element) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.blast_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyBlastLevel(level, element);
            }
        }
        return level;
    }

    public static int getCooldownLevel(ItemStack stack, @Nullable Element element) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.cooldown_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyCooldownLevel(level, element);
            }
        }
        return level;
    }

    public static int getDurationLevel(ItemStack stack, @Nullable Element element) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.duration_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyDurationLevel(level, element);
            }
        }
        return level;
    }

    public static int getRangeLevel(ItemStack stack, @Nullable Element element) {
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.range_upgrade);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                level = ((IWandTrait) t).modifyRangeLevel(level, element);
            }
        }
        return level;
    }

    public static float getCooldownEffect(ItemStack stack, EntityPlayer player, Spell spell) {
        int level = getCooldownLevel(stack, spell.getElement());
        float value =  1.0f - level * Constants.COOLDOWN_REDUCTION_PER_LEVEL;
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                value = ((IWandTrait) t).modifyCooldownEffect(value, stack, player, spell);
            }
        }
        return value;
    }

    public static float getRangeEffect(ItemStack stack, EntityPlayer player, Spell spell) {
        int level = getRangeLevel(stack, spell.getElement());
        float value =  1.0f + level * Constants.RANGE_INCREASE_PER_LEVEL;
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                value = ((IWandTrait) t).modifyRangeEffect(value, stack, player, spell);
            }
        }
        return value;
    }

    public static float getDurationEffect(ItemStack stack, EntityPlayer player, Spell spell) {
        int level = getDurationLevel(stack, spell.getElement());
        float value =  1.0f + level * Constants.DURATION_INCREASE_PER_LEVEL;
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                value = ((IWandTrait) t).modifyDurationEffect(value, stack, player, spell);
            }
        }
        return value;
    }

    public static float getBlastEffect(ItemStack stack, EntityPlayer player, Spell spell) {
        int level = getBlastLevel(stack, spell.getElement());
        float value =  1.0f + level * Constants.BLAST_RADIUS_INCREASE_PER_LEVEL;
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                value = ((IWandTrait) t).modifyBlastEffect(value, stack, player, spell);
            }
        }
        return value;
    }

    public static void triggerCondenser(ToolWand toolWand, ItemStack stack, World world, Entity entity) {
        int condenserLevel = getCondenserLevel(stack);
        int condenserRefill = condenserLevel;
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                condenserRefill = ((IWandTrait) t).modifyCondenserRecharge(toolWand, stack, world, entity, condenserLevel, condenserRefill);
            }
        }
        toolWand.rechargeMana(stack, condenserRefill);
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                ((IWandTrait) t).onCondenserRecharge(toolWand, stack, world, entity, condenserRefill);
            }
        }
    }

    public static float getPotencyIncrease(ToolWand toolWand, ItemStack stack, Spell spell) {
        float value = getPotencyIncreaseElement(toolWand, stack, spell.getElement());
        for(ITrait t : ToolHelper.getTraits(stack)) {
            if(t instanceof IWandTrait) {
                value = ((IWandTrait) t).modifyPotency(value, toolWand, stack, spell);
            }
        }
        return value;
    }

    public static float getPotencyIncreaseElement(ToolWand toolWand, ItemStack stack, Element element) {
        WandNBT nbt = new WandNBT(TagUtil.getToolTag(stack));
        switch (element) {
            case FIRE: return nbt.fire;
            case ICE: return nbt.ice;
            case LIGHTNING: return nbt.lightning;
            case NECROMANCY: return nbt.necro;
            case EARTH: return nbt.earth;
            case SORCERY: return nbt.sorc;
            case HEALING: return nbt.healing;
        }
        return 0;
    }
}
