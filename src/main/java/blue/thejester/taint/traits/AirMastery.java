package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AirMastery extends AbstractArmorTrait {

    public static final AirMastery airmastery = new AirMastery("airmastery", 0xffffff);

    public AirMastery(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if(source.damageType.equals("fall")) {
            evt.setCanceled(true);
            return 0;
        }
        return newDamage;
    }
}
