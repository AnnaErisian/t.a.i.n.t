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

public class Warmage extends AbstractTrait implements IWandTrait {

    public static final Warmage warmage = new Warmage();

    public Warmage() {
        super("wand_warmage", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public void onCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int rechargeAmount) {
        if(entity instanceof EntityLivingBase) {
            if(world.rand.nextFloat() < 0.1 * rechargeAmount) {
                ((EntityLivingBase) entity).heal(1);
            }
        }
    }
}
