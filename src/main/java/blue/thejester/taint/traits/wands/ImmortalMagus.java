package blue.thejester.taint.traits.wands;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import blue.thejester.taint.tools.ToolWand;
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

public class ImmortalMagus extends AbstractTrait implements IWandTrait {

    public static final ImmortalMagus immortalMagus = new ImmortalMagus();

    public ImmortalMagus() {
        super("wand_immortal_magus", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public int modifyCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int condenserLevel, int condenserAmount) {
        return (int) (condenserAmount * (1f + 2f * (stack.getItemDamage() * 1.0f / stack.getMaxDamage())));
    }
}
