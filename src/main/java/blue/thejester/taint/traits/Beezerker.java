package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Beezerker extends AbstractArmorTrait {

    public static final Beezerker beezerker = new Beezerker("beezerker", 0xffffff);

    public Beezerker(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(!source.isUnblockable()) {
            //when hit, convert all but 1 of the damage into poison (high enough damage will become higher levels of poison, breakpoints at each 8 damage). When poisoned, also gain Strength and Haste to match.
            int level = (int) (newDamage / 8);
            switch (level) {
                case 0:
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (25*(damage-1)), 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, (int) (25*(damage-1)), 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.HASTE, (int) (25*(damage-1)), 0));
                    break;
                default:
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (12*(damage-1)), 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, (int) (12*(damage-1)), 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.HASTE, (int) (12*(damage-1)), 1));
            }
            return 1;
        }
        return newDamage;
    }
}
