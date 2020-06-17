package blue.thejester.taint.traits.wands;

import blue.thejester.taint.tools.ToolWand;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.spell.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IWandTrait {
    public default int modifyStorageLevel(int level) {
        return level;
    }

    public default int modifySiphonLevel(int level) {
        return level;
    }

    public default int modifyMeleeLevel(int level) {
        return level;
    }

    public default int modifyCondenserLevel(int level) {
        return level;
    }

    public default int modifyBlastLevel(int level, Element element) {
        return level;
    }

    public default int modifyCooldownLevel(int level, Element element) {
        return level;
    }

    public default int modifyDurationLevel(int level, Element element) {
        return level;
    }

    public default int modifyRangeLevel(int level, Element element) {
        return level;
    }

    public default float modifyCooldownEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value;
    }

    public default float modifyRangeEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value;
    }

    public default float modifyBlastEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value;
    }

    public default float modifyDurationEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value;
    }

    public default void onCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int rechargeAmount) {

    }

    public default int modifyCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int condenserLevel, int condenserAmount) {
        return condenserAmount;
    }

    public default float modifyPotency(float value, ToolWand toolWand, ItemStack stack, Spell spell) {
        return value;
    }
}
