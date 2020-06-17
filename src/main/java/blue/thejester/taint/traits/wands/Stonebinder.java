package blue.thejester.taint.traits.wands;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import blue.thejester.taint.tools.ToolWand;
import electroblob.wizardry.spell.Spell;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;
import vazkii.botania.api.mana.ManaItemHandler;

public class Stonebinder extends AbstractTrait implements IWandTrait {

    public static final Stonebinder stonebinder = new Stonebinder();

    public Stonebinder() {
        super("wand_stonebinder", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public float modifyCooldownEffect(float value, ItemStack stack, EntityPlayer player, Spell spell) {
        return value * (1 - 0.25f * (stack.getItemDamage() * 1.0f / stack.getMaxDamage()));
    }
}
