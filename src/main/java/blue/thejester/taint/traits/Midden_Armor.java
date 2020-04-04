package blue.thejester.taint.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;
import thebetweenlands.common.config.BetweenlandsConfig;

import java.util.List;

public class Midden_Armor extends AbstractArmorTrait {

    private static final float bonus = 1.0f;

    public static Midden_Armor midden_armor = new Midden_Armor();

    public Midden_Armor() {
        super("midden", TextFormatting.GOLD);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {

        if (player != null && player.dimension == BetweenlandsConfig.WORLD_AND_DIMENSION.dimensionId) {
            mods.addArmor(bonus);
            mods.addToughness(bonus);
        }
        return mods;
    }


    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String speed  = String.format(LOC_Extra + ".armor", getModifierIdentifier());
        String damage = String.format(LOC_Extra + ".toughness", getModifierIdentifier());

        return ImmutableList.of(
                Util.translateFormatted(speed , Util.df.format(bonus)),
                Util.translateFormatted(damage, Util.df.format(bonus))
        );
    }
}
