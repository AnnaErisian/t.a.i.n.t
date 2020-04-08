package blue.thejester.taint.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulCatcher extends AbstractArmorTrait {

    public static final SoulCatcher soulcatcher = new SoulCatcher("soul_catcher", 0xffffff);

    public SoulCatcher(String identifier, int color) {
        super(identifier, color);
    }


    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (player.getHealth() < 15) {
            mods.armor *= 15f/player.getHealth();
            mods.toughness *= Math.sqrt(15f/player.getHealth());
        } else if (player.getHealth() > 30) {
            mods.armor *= 30f/player.getHealth();
            mods.toughness *= Math.sqrt(30f/player.getHealth());
        }
        return super.getModifications(player, mods, armor, source, damage, slot);
    }
}
