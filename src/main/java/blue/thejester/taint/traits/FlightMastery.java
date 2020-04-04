package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FlightMastery extends AbstractArmorTrait {

    public static final FlightMastery flightmastery = new FlightMastery("flightmastery", 0xffffff);

    public FlightMastery(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if(source.damageType.equals("flyIntoWall")) {
            if(damage < 4) {
                evt.setCanceled(true);
                return 0;
            } else if(damage < 36) {
                return damage / 2f;
            } else {
                return 18f;
            }
        }
        return newDamage;
    }
}
