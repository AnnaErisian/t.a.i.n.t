package blue.thejester.taint.traits.wands;

import blue.thejester.taint.tools.ToolWand;
import electroblob.wizardry.spell.Spell;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class ForceOfNature extends AbstractTrait implements IWandTrait {

    public static final ForceOfNature forceOfNature = new ForceOfNature();

    public ForceOfNature() {
        super("wand_forceofnature", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public float modifyDurationEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        if(player.world.rand.nextFloat() < 0.5) {
            return value * 2;
        }
        return value;
    }

    @Override
    public float modifyBlastEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        if(player.world.rand.nextFloat() < 0.5) {
            return value * 2;
        }
        return value;
    }

    @Override
    public float modifyCooldownEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        if(player.world.rand.nextFloat() < 0.5) {
            return value / 2f;
        }
        return value;
    }
}
