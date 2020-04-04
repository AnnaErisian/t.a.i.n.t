package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Delicious_Armor extends AbstractArmorTrait {

    public static final Delicious_Armor delicious = new Delicious_Armor("delicious", 0xffffff);

    public Delicious_Armor(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(damage > 0.5) {
            Delicious.spillSmore(player.world, player.posX, player.posY, player.posZ, Delicious.ON_HURT_CHANCE);
        }
        return newDamage;
    }
}
