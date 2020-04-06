package blue.thejester.taint.traits;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.TinkerUtil;


public class SuperstarShim extends AbstractArmorTraitLeveled {

    public SuperstarShim(String identifier, int color, int maxLevels, int levels) {
        super(identifier, color, maxLevels, levels);
    }

    @Override
    public void onUpdate(ItemStack piece, World world, Entity player, int itemSlot, boolean isSelected) {
        updateClient(piece, world, player, itemSlot, isSelected);
    }

    public void updateClient(ItemStack piece, World world, Entity player, int itemSlot, boolean isSelected) {
        //NO-OP
    }

}
