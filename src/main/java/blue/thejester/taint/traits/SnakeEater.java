package blue.thejester.taint.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SnakeEater extends AbstractArmorTrait {

    public static final SnakeEater snakeEater = new SnakeEater("snakeEater", 0xffffff);


    public SnakeEater(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        if(!world.isRemote && world.getTotalWorldTime() % 10 == 0) {
            PotionEffect poison = player.getActivePotionEffect(MobEffects.POISON);
            PotionEffect slow = player.getActivePotionEffect(MobEffects.SLOWNESS);
            PotionEffect levitate = player.getActivePotionEffect(MobEffects.LEVITATION);
            PotionEffect wither = player.getActivePotionEffect(MobEffects.WITHER);
            PotionEffect blind = player.getActivePotionEffect(MobEffects.BLINDNESS);
            PotionEffect hunger = player.getActivePotionEffect(MobEffects.HUNGER);
            PotionEffect miningFatigue = player.getActivePotionEffect(MobEffects.MINING_FATIGUE);
            PotionEffect nausea = player.getActivePotionEffect(MobEffects.NAUSEA);
            PotionEffect weakness = player.getActivePotionEffect(MobEffects.WEAKNESS);
            if(poison != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, poison.getDuration(), poison.getAmplifier()));
                player.removePotionEffect(MobEffects.POISON);
            }
            if(slow != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, slow.getDuration(), slow.getAmplifier()));
                player.removePotionEffect(MobEffects.SLOWNESS);
            }
            if(levitate != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, levitate.getDuration(), levitate.getAmplifier()));
                player.removePotionEffect(MobEffects.LEVITATION);
            }
            if(wither != null) {
                player.removePotionEffect(MobEffects.WITHER);
                player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, (20 * wither.getDuration()) + 200, wither.getAmplifier()));
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, wither.getDuration(), wither.getAmplifier()));
            }
            if(blind != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 50 * blind.getDuration()));
                player.removePotionEffect(MobEffects.BLINDNESS);
            }
            if(hunger != null) {
                player.getFoodStats().addStats(4, 4);
                player.removePotionEffect(MobEffects.HUNGER);
            }
            if(miningFatigue != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.HASTE, miningFatigue.getDuration(), miningFatigue.getAmplifier()));
                player.removePotionEffect(MobEffects.MINING_FATIGUE);
            }
            if(nausea != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 50 * nausea.getDuration()));
                player.removePotionEffect(MobEffects.NAUSEA);
            }
            if(weakness != null) {
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, weakness.getDuration(), weakness.getAmplifier()));
                player.removePotionEffect(MobEffects.WEAKNESS);
            }
        }
    }
}
