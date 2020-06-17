package blue.thejester.taint.traits.wands;

import blue.thejester.taint.tools.ToolWand;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.spell.Spell;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Ritualist extends AbstractTrait implements IWandTrait {

    public static final Ritualist ritualist = new Ritualist();

    public Ritualist() {
        super("wand_ritualist", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public float modifyPotency(float value, ToolWand toolWand, ItemStack stack, Spell spell) {
        return 1 + value;
    }

    @Override
    public float modifyCooldownEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value * 5;
    }

    @Override
    public int modifyCondenserLevel(int level) {
        return level + 10;
    }

    @Override
    public int modifyRangeLevel(int level, Element element) {
        return level + 4;
    }

    @Override
    public int modifyBlastLevel(int level, Element element) {
        return level + 3;
    }

    @Override
    public int modifyDurationLevel(int level, Element element) {
        return level + 4;
    }
}
