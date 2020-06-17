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
import vazkii.botania.api.mana.*;

public class BotanicalMana extends AbstractTrait implements IWandTrait {

    private static final int MANA_DRAW = 100;

    public static final BotanicalMana mana = new BotanicalMana();

    public BotanicalMana() {
        super("wand_mana", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public int modifyCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int condenserLevel, int condenserAmount) {
        if (TagUtil.getTagSafe(stack).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            return condenserAmount;
        }
        if (!world.isRemote
                && entity instanceof EntityPlayer
                && (toolWand.isManaFull(stack) || ToolHelper.getCurrentDurability(stack) < ToolHelper.getMaxDurability(stack))
                && Toggle.getToggleState(stack, identifier)) {
            for(int i = 0; i < (condenserLevel == 0 ? 1 : condenserLevel); i++) {
                if(drawMana((EntityPlayer)entity)) {
                    condenserAmount += 3;
                }
            }
        }
        return condenserAmount;
    }


    private static boolean drawMana(EntityPlayer ent) {
        IItemHandler handler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for (int i=0; i<handler.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(handler.getStackInSlot(i), ent, MANA_DRAW, true)) {
                return true;
            }
        }

        IBaublesItemHandler ib = BaublesApi.getBaublesHandler(ent);
        for (int i=0; i<ib.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(ib.getStackInSlot(i), ent, MANA_DRAW, true)) {
                return true;
            }
        }

        return false;
    }
}
